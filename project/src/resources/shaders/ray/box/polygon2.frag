#version 430 compatibility

const uint AREA = 0;
const uint MONO = 1;

struct fragment {
    uint color;
    float depth;
    float ao;
    uint prev;
};

const int EMPTY = 0;
const int LINE = 2;
const int PLANE = 3;

const int EDGE = 1;
const int V0 = 2;
const int V1 = 3;

const uint CW = 0;
const uint CCW = 1;

// viewport
uniform vec4 viewport;
uniform uvec2 window;
// probe & surface
uniform float probeRadius;
uniform bool sas;

// ambient occlusion & lighting
uniform bool phong;
uniform bool ao;
uniform float lambda;
uniform float volumeSize;
uniform float aoExponent;
uniform float aoThreshold;
uniform bool silhouettes;
uniform bool bfmod;

// cavity coloring
uniform uint surfaceLabel;
uniform uint cavityColoring;
uniform vec3 cavityColor1;
uniform vec3 cavityColor2;

// tunnel coloring
uniform float tunnelAOThreshold;
uniform vec3 tunnelColor;

// clipping by isolated tori
uniform uint maxSphereIsolatedTori;

in vec4 objPos;
in vec4 camPos;
in vec4 lightPos;
in float radius;
in float RR;
in vec4 color;

in flat uint index;
in flat uint label;
in flat uint circleStart;
in flat uint circleEnd;
in flat uint orientation;
// area
in flat float area;

uniform usamplerBuffer circlesTex;
uniform samplerBuffer edgesCircleTex;
uniform samplerBuffer edgesLineTex;
uniform usamplerBuffer sphereIsolatedCountsTex;
uniform samplerBuffer sphereIsolatedVSTex;
uniform sampler3D aoVolumeTex;

layout(std430) buffer ABuffer {
    fragment fragments[];
};

layout(std430) buffer ABufferIndex {
    uint fragCount;
    uint fragIndices[];
};

layout(std430) buffer Debug {
    vec4 debug[];
};

void storeFragment(vec4 color, float depth, float ao) {
    uvec2 coord = uvec2(floor(gl_FragCoord.xy));
    uint index = atomicAdd(fragCount, 1);
    fragments[index].color = packUnorm4x8(color);
    fragments[index].depth = depth;
    fragments[index].ao = ao;
    fragments[index].prev = atomicExchange(fragIndices[coord.y * window.x + coord.x], index);
}

float squaredLength(vec3 v);
void storeIntersection(vec3 position, vec3 normal, vec3 eye, vec4 color, float Ka, float Kd, bool bfmod);
int planePlaneIntersection(vec4 plane1, vec4 plane2, out vec3 p1, out vec3 p2);
vec3 rotate(vec3 v, vec3 axis, float angle);
int sphericalLineArcIntersection(vec4 sphere, vec4 circle, vec4 arc, vec3 p, vec3 o, inout int di);

void main() {
    //storeFragment(color, 10.0, 1.0); discard; // DEBUG
    // transform fragment coordinates from window coordinates to view coordinates.
    vec4 coord = gl_FragCoord
        * vec4(viewport.z, viewport.w, 2.0, 0.0) 
        + vec4(-1.0, -1.0, -1.0, 1.0);

    // transform fragment coordinates from view coordinates to object coordinates.
    coord = gl_ModelViewProjectionMatrixInverse * coord;
    coord /= coord.w;
    coord -= objPos; // ... and to glyph space

    // calc the viewing ray
    vec3 ray = normalize(coord.xyz - camPos.xyz);

    // calculate the geometry-ray-intersection
    float d1 = -dot(camPos.xyz, ray);                  // projected length of the cam-sphere-vector onto the ray
    float d2s = dot(camPos.xyz, camPos.xyz) - d1 * d1; // off axis of cam-sphere-vector and ray
    float radicand = RR - d2s;                         // square of difference of projected length and lambda

    if (radicand < 0.0) {
        discard;
    }

    float sqrtRad = sqrt(radicand);
    float lambda1 = d1 - sqrtRad; // - for outer
    float lambda2 = d1 + sqrtRad; // + for inner
    vec3 sphereint1 = lambda1 * ray + camPos.xyz; // outer intersection point
    vec3 sphereint2 = lambda2 * ray + camPos.xyz; // inner intersection point
    // "calc" normal at intersection point
    vec3 normal1 = sphereint1 / radius; // + for outer
    vec3 normal2 = -sphereint2 / radius; // - for inner

    vec3 intPos1 = sphereint1 + objPos.xyz;
    vec3 intPos2 = sphereint2 + objPos.xyz;

    bool inner = false;
    bool outer = false;
    vec4 sphere = vec4(objPos.xyz, radius);
    
    int count = 0; // DEBUG
    /*if (sphericalLineArcIntersection(sphere, circle1, arc1, intPos1, outside)) { count++; outer = !outer; }
    if (sphericalLineArcIntersection(sphere, circle2, arc2, intPos1, outside)) { count++; outer = !outer; }
    if (sphericalLineArcIntersection(sphere, circle3, arc3, intPos1, outside)) { count++; outer = !outer; }
    if (sphericalLineArcIntersection(sphere, circle4, arc4, intPos1, outside)) { count++; outer = !outer; }
    if (sphericalLineArcIntersection(sphere, circle5, arc5, intPos1, outside)) { count++; outer = !outer; }

    if (sphericalLineArcIntersection(sphere, circle1, arc1, intPos2, outside)) inner = !inner;
    if (sphericalLineArcIntersection(sphere, circle2, arc2, intPos2, outside)) inner = !inner;
    if (sphericalLineArcIntersection(sphere, circle3, arc3, intPos2, outside)) inner = !inner;
    if (sphericalLineArcIntersection(sphere, circle4, arc4, intPos2, outside)) inner = !inner;
    if (sphericalLineArcIntersection(sphere, circle5, arc5, intPos2, outside)) inner = !inner;*/

    // calc point outside polygon
    uvec4 edge = texelFetch(circlesTex, int(circleStart));
    uint edgeIdx = edge.z;
    // get circle and line
    //vec4 circle = texelFetch(edgesCircleTex, int(edgeIdx));
    vec4 vs = texelFetch(edgesCircleTex, int(edgeIdx));
    //circle.xyz = circle.xyz - objPos.xyz;
    //float t = length(circle.xyz);
    vec3 relPos = vs.xyz - objPos.xyz;
    float dist = length(relPos);
    // compute small circle / intersection plane
    float ar = sas ? radius - probeRadius : radius;
    float r = (ar * ar) + (dist * dist) - (vs.w * vs.w);
    r = r / (2.0 * dist * dist);
    vec3 vec = relPos * r;
    vec4 circle;
    circle.w = sqrt((ar * ar) - dot(vec, vec));
    if (sas) { // if (!sas)
        //circle.w = circle.w * radius / (radius + probeRadius);
        circle.w = circle.w * (ar + probeRadius) / ar;
    }
    //vec3 n = circle.xyz / t;
    //vec3 p;
    if (sas) {
        //p = circle.xyz; // SAS
        circle.xyz = (ar + probeRadius) / ar * vec;
    } else {
        //p = t / (radius + probeRadius) * radius * n; // SES
        circle.xyz = vec;
    }
    //circle.xyz = p;
    vec4 line = texelFetch(edgesLineTex, int(edgeIdx));
    line.xyz = circle.w * line.xyz;
    
    //vec3 outside = objPos.xyz + radius * n;
    vec3 outside = objPos.xyz + radius * normalize(relPos);
    
    if (index == 13) {
        //debug[0] = vec4(oldcircle.xyz - objPos.xyz, oldcircle.w);
        //debug[1] = circle;
        //debug[2] = vec4(outside, r);
        //debug[3] = vs;
        /*debug[1] = circle;
        debug[2] = line;
        debug[3] = vec4(outside, length(line.xyz));*/
    }

    // ray-vertex hit handling
    int prev = (orientation == CCW) ? V0 : V1;
    vec4 firstPlane;
    vec4 prevPlane;
    bvec2 firstVertex = bvec2(false, false);
    bvec2 prevVertex = bvec2(false, false);
    int vertexCount = 0; // DEBUG

    // DEBUG
    int di = 0;
    int ndi = -1;
    if (index == 25) {
        debug[29] = vec4(orientation, prev, 0.0, 0.0);
    }
    
    // test first edge
    int result = sphericalLineArcIntersection(sphere, circle, line, intPos1, outside, di);
    if (result > EDGE) {
        outer = !outer; count++; // DEBUG
        firstPlane.xyz = circle.xyz;
        firstPlane.w = -dot(circle.xyz, sphere.xyz + circle.xyz);
        prevPlane = firstPlane;
        firstVertex.x = result == prev;
        prevVertex.x = !firstVertex.x;
        //vertexCount++; // DEBUG
    } else if (result == EDGE) {
        outer = !outer; count++;
    }
    if (sphericalLineArcIntersection(sphere, circle, line, intPos2, outside, ndi) != EMPTY) {
        inner = !inner;
    }

    // test all other edges
    for (uint i = circleStart + 1; i < circleEnd; i++) {
        edge = texelFetch(circlesTex, int(i));
        edgeIdx = edge.z;
        // get circle and line
        //circle = texelFetch(edgesCircleTex, int(edgeIdx));
        vs = texelFetch(edgesCircleTex, int(edgeIdx));
        //circle.xyz = circle.xyz - objPos.xyz;
        relPos = vs.xyz - objPos.xyz;
        dist = length(relPos);
        // compute small circle / intersection plane
        ar = sas ? radius - probeRadius : radius;
        r = (ar * ar) + (dist * dist) - (vs.w * vs.w);
        r = r / (2.0 * dist * dist);
        vec = relPos * r;
        circle.w = sqrt((ar * ar) - dot(vec, vec));
        if (sas) { // if (!sas)
            //circle.w = circle.w * radius / (radius + probeRadius);
            circle.w = circle.w * (ar + probeRadius) / ar;
        }
        //t = length(circle.xyz);
        //n = circle.xyz / t;
        if (sas) {
            //p = circle.xyz; // SAS
            circle.xyz = (ar + probeRadius) / ar * vec;
        } else {
            //p = t / (radius + probeRadius) * radius * n; // SES
            circle.xyz = vec;
        }
        //circle.xyz = p;
        line = texelFetch(edgesLineTex, int(edgeIdx));
        line.xyz = circle.w * line.xyz;
        // test edge
        result = sphericalLineArcIntersection(sphere, circle, line, intPos1, outside, di);
        if (result > EDGE) {
            outer = !outer; count++; // DEBUG
            vec4 plane;
            plane.xyz = circle.xyz;
            plane.w = -dot(plane.xyz, sphere.xyz + circle.xyz);
            if (prevVertex.x && result == prev) {
                vec4 pos = vec4(intPos1, 1.0);
                if (dot(prevPlane, pos) <= 0.0 && dot(plane, pos) <= 0.0) {
                    outer = !outer;
                    count++;
                    vertexCount++; // DEBUG
                    if (index == 25) {
                        debug[30] = vec4(2.0 * (i - circleStart - 1) + (prev == V0 ? 1 : 0));
                        debug[31] = vec4(2.0 * (i - circleStart) + (prev == V0 ? 0 : 1));
                    }
                }
                prevVertex.x = false;
            } else if (result != prev) {
                prevPlane = plane;
                prevVertex.x = true;
            } else {
                prevVertex.x = false;
            }
            //vertexCount++; // DEBUG
        } else if (result == EDGE) {
            outer = !outer; count++; // DEBUG
            prevVertex.x = false;
        }
        if (sphericalLineArcIntersection(sphere, circle, line, intPos2, outside, ndi) != EMPTY) {
            inner = !inner;
        }
    }

    // test last and first arc
    if (circleEnd - circleStart > 2 && firstVertex.x && prevVertex.x) {
        vec4 pos = vec4(intPos1, 1.0);
        if (dot(firstPlane, pos) <= 0.0 && dot(prevPlane, pos) <= 0.0) {
            outer = !outer;
            count++;
            vertexCount++; // DEBUG
            if (index == 25) {
                debug[30] = vec4(prev == V0 ? 1.0 : 0.0);
                debug[31] = vec4(2.0 * (circleEnd - 1) + (prev == V0 ? 0 : 1));
            }
        }
    }

    // clip by isolated torus plane
    if (!sas) {
        /*if (dot(plane.xyz, plane.xyz) > 0) {
            if (dot(plane.xyz, intPos1) + plane.z < 0.0) {
                outer = false;
            }
            if (dot(plane.xyz, intPos2) + plane.z < 0.0) {
                inner = false;
            }
        }*/
        uint isolatedCount = texelFetch(sphereIsolatedCountsTex, int(index)).r;
        for (uint i = 0; i < isolatedCount; i++) {
            vec4 vs = texelFetch(sphereIsolatedVSTex, int(index * maxSphereIsolatedTori + i));
            if (squaredLength(intPos1 - vs.xyz) < vs.w * vs.w) {
                outer = false;
                if (!inner) {
                    break;
                }
            }
            if (squaredLength(intPos2 - vs.xyz) < vs.w * vs.w) {
                inner = false;
                if (!outer) {
                    break;
                }
            }
        }
    }

    //if (vertexCount > 1) {
        outer = true; // DEBUG
    //}
    if (!inner && !outer) {
        discard;
    }

    vec3 eye = -normalize(ray);
    if (outer) {
        if (label == surfaceLabel) {
            vec4 col = color;
            if (count == 0) {
                col.rgb = vec3(0.2, 0.2, 0.2);
            } /*else if (count == 1) {
                col.rgb = vec3(1.0, 0.0, 1.0);
            } else if (count == 2) {
                col.rgb = vec3(0.0, 1.0, 0.0);
            }*/
            else {
                if (count % 2 > 0) {
                    col.rgb = vec3(0.4 + 0.6 * (count - 1) / 8.0, 0.2, 0.2);
                } else {
                    col.rgb = vec3(0.2, 0.4 + 0.6 * count / 8.0, 0.2);
                }
            }
            if (vertexCount == 1) {
                col.rgb = vec3(1.0, 1.0, 0.0);
            } else if (vertexCount == 2) {
                col.rgb = vec3(0.0, 1.0, 1.0);
            }
            /*if (orientation == CCW) {
                col.rgb = vec3(0.5, 0.0, 1.0);
            }*/
            storeIntersection(intPos1, normal1, eye, col /*color*/, 0.2, 0.8, false);
        } else {
            storeIntersection(intPos1, normal1, eye, color /*vec4(1.0, 1.0, 1.0, 0.5)*/, 0.2, 0.8 /*0.4*/, bfmod);
        }
    }

    if (inner) {
        if (label == surfaceLabel) {
            storeIntersection(intPos2, normal2, eye, color /*vec4(1.0, 1.0, 1.0, 0.5)*/, 0.2, 0.8 /*0.4*/, bfmod);
        } else {
            storeIntersection(intPos2, normal2, eye, color, 0.2, 0.8, false);
        }
    }

    discard;
}

float squaredLength(vec3 v) {
    return dot(v, v);
}

void storeIntersection(vec3 position, vec3 normal, vec3 eye, vec4 color, float Ka, float Kd, bool bfmod) {
    if (label != surfaceLabel) {
        // color cavity
        if (cavityColoring == MONO) {
            color.rgb = cavityColor1;
        } else {
            color.rgb = mix(cavityColor1, cavityColor2, area);
        }
    }

    float aoFactor = texture3D(aoVolumeTex, (position + lambda * normal) / volumeSize).r;
    if (label == surfaceLabel && aoFactor > tunnelAOThreshold && !bfmod) {
        //color.rgb = tunnelColor;
        aoFactor = uintBitsToFloat(-2);
    }

    vec4 fragColor = color;
    if (phong) {
        float Id = max(0.0, dot(normal, eye));
        fragColor.rgb = Ka * fragColor.rgb + Kd * Id * fragColor.rgb; // ambient + diffuse term
    }

    if (ao /*&& !bfmod*/) {
        //fragColor.rgb *= max(1.0 - aoFactor, 0.0);
        //fragColor.a = min(color.a + aoFactor, 1.0);
        fragColor.a = min(pow(aoFactor / aoThreshold, aoExponent), 1.0);
    }

    if (silhouettes) {
        //fragColor.a = min(1.0, color.a / abs(dot(normal1, normalize(ray))));
        fragColor.a = pow(fragColor.a, dot(normal, eye) + 1.0);
    }

    if (bfmod) {
        fragColor.a = fragColor.a * (1.0 - dot(normal, eye /* + C */));
    }

    vec4 Ding = vec4(position, 1.0);
    float depth = dot(gl_ModelViewProjectionMatrixTranspose[2], Ding);
    float depthW = dot(gl_ModelViewProjectionMatrixTranspose[3], Ding);
    float fragDepth = ((depth / depthW) + 1.0) * 0.5;

    // store front fragment to A-buffer
    if (0.0 <= fragDepth && fragDepth <= 1.0) {
        storeFragment(fragColor, depthW /*fragDepth*/, aoFactor);
    }
}

int planePlaneIntersection(vec4 plane1, vec4 plane2, out vec3 p, out vec3 dir) {
    const float EPSILON = 0.001;
    float n1n2 = dot(plane1.xyz, plane2.xyz);
    if (abs(n1n2) >= 1.0 - EPSILON) {
        // The planes are parallel. Check if they are coplanar.
        float diff = plane1.w - n1n2 * plane2.w;
        /*if (n1n2 >= 0.0) {
            // Normals are in same direction, need to look at c0-c1.
            diff = plane1.d - plane2.d;
        } else {
            // Normals are in opposite directions, need to look at c0+c1.
            diff = plane1.d + plane2.d;
        }*/

        if (abs(diff) < EPSILON) {
            return PLANE;
        } else {
            return EMPTY;
        }
    }

    float invDet = 1.0 / (1.0 - n1n2 * n1n2);
    float c1 = (-plane1.w + n1n2 * plane2.w) * invDet;
    float c2 = (-plane2.w + n1n2 * plane1.w) * invDet;

    p = c1 * plane1.xyz + c2 * plane2.xyz;
    dir = normalize(cross(plane1.xyz, plane2.xyz));

    return LINE;
}

vec3 rotate(vec3 v, vec3 axis, float angle) {
    float s = sin(angle);
    float c = cos(angle);
    return v * c + cross(axis, v) * s + axis * dot(axis, v) * (1.0 - c);
}

int sphericalLineArcIntersection(vec4 sphere, vec4 circle, vec4 arc, vec3 p, vec3 o, inout int di) {
    // test p and o lie on opposite sides of circle plane
    vec3 circlePos = sphere.xyz + circle.xyz;
    vec4 circlePlane;
    circlePlane.xyz = normalize(circle.xyz);
    circlePlane.w = -dot(circlePlane.xyz, circlePos);

    // compute arc end points
    vec3 zAxis = cross(circlePlane.xyz, arc.xyz / circle.w);
    float zLen = sqrt(1.0 - arc.w * arc.w);
    vec3 pa1 = sphere.xyz + circle.xyz;
    pa1 += /*circle.w **/ arc.w * arc.xyz;
    vec3 pa2 = pa1;
    pa1 += circle.w * zLen * zAxis;
    pa2 -= circle.w * zLen * zAxis;
    vec3 va1 = (pa1 - sphere.xyz) / sphere.w;
    vec3 va2 = (pa2 - sphere.xyz) / sphere.w;
    //float dist1 = sphere.w * abs(dot(linePlane.xyz, va1));
    //float dist2 = sphere.w * abs(dot(linePlane.xyz, va2));
    if (di >= 0 && index == 25) {
        debug[di++] = vec4(pa1, 0.0);
        debug[di++] = vec4(pa2, 0.0);
    }

    if (dot(circlePlane, vec4(p, 1.0)) > 0 && dot(circlePlane, vec4(o, 1.0)) > 0) {
        return EMPTY /*false*/;
    }

    // find intersection of line circle and arc circle
    vec4 linePlane;
    //linePlane.xyz = cross((o - sphere.xyz) / sphere.w, (p - sphere.xyz) / sphere.w) /* (sphere.w * sphere.w)*/;
    linePlane.xyz = normalize(cross(o - sphere.xyz, p - sphere.xyz));
    linePlane.w = -dot(linePlane.xyz, sphere.xyz);
    
    vec3 a, dir;
    if (planePlaneIntersection(circlePlane, linePlane, a, dir) != LINE) {
        return EMPTY /*false*/;
    }
    
    // project sphere center onto line and compute distance to intersections
    vec3 projPos = a + (dot(sphere.xyz - a, dir) / dot(dir, dir)) * dir;
    vec3 projVec = projPos - sphere.xyz;
    if (dot(projVec, projVec) > RR) {
        return EMPTY /*false*/;
    }

    float d = sqrt(RR - dot(projVec, projVec));
    
    // choose intersection point between p and o
    int count = 0;
    vec3 line = sphere.w * normalize(p + o - 2.0 * sphere.xyz);
    float lineAngleDist = dot(p - sphere.xyz, line);
    float arcAngleDist = arc.w * dot(arc.xyz, arc.xyz);
    
    const float EPS = 0.9999;
    int result = EDGE;
    vec3 intPos = projPos - d * dir;
    vec3 lineIntVec = intPos - sphere.xyz;
    if (dot(lineIntVec, line) >= lineAngleDist) {
        // test whether inersection lies within arc
        vec3 arcIntVec = intPos - circlePos;
        if (dot(arcIntVec, arc.xyz) >= arcAngleDist) {
            // test whether intersection goes through vertex
            float dist1 = dot(lineIntVec / sphere.w, va1);
            float dist2 = dot(lineIntVec / sphere.w, va2);
            if (dist1 > EPS || dist2 > EPS) {
                result = V0;
            }
            count++;
        }
    }
    
    intPos = projPos + d * dir;
    lineIntVec = intPos - sphere.xyz;
    if (dot(lineIntVec, line) >= lineAngleDist) {
        // test whether inersection lies within arc
        vec3 arcIntVec = intPos - circlePos;
        if (dot(arcIntVec, arc.xyz) >= arcAngleDist) {
            // test whether intersection goes through vertex
            float dist1 = dot(lineIntVec / sphere.w, va1);
            float dist2 = dot(lineIntVec / sphere.w, va2);
            if (dist1 > EPS || dist2 > EPS) {
                result = V1;
            }
            count++;
        }
    }
    
    return (count == 1) ? result : EMPTY;
}