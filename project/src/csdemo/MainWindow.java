package csdemo;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

/**
 *
 * @author Adam Jurcik <xjurc@fi.muni.cz>
 */
public class MainWindow extends javax.swing.JFrame {

    private GLJPanel panel;
    private Scene scene;
    private FPSAnimator animator;
    private boolean fullscreen = false;
    // camera
    private boolean mouseEnabled = false;
    private Robot robot;
    private Cursor blankCursor;
    private GraphicsConfiguration gc;
    // resolution
    private Resolution[] resolutions;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        
        setTitle("Contour-buildup");
        
        GLProfile profile = GLProfile.get(GLProfile.GL4bc);
        panel = new GLJPanel(new GLCapabilities(profile));
        panel.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
        
        add(panel, BorderLayout.CENTER);
        
        scene = new Scene();
        scene.setDynamicsListener(new DynamicsListener() {
            @Override
            public void dynamicsLoaded(Dynamics dynamics) {
                snapshotSpinner.setValue(1);
                snapshotLabel.setText(Integer.toString(dynamics.getSnapshotCount()));
            }

            @Override
            public void snapshotChanged(int snapshot) {
                snapshotSpinner.setValue(snapshot + 1);
            }
        });
        
        panel.addGLEventListener(scene);
        
        panel.requestFocusInWindow();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                MainWindow.this.keyPressed(e);
            }
        });
        
        animator = new FPSAnimator(panel, 100, true);
        animator.setUpdateFPSFrames(FPSAnimator.DEFAULT_FRAMES_PER_INTERVAL / 30, null);
        animator.start();
        
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // create a new blank cursor.
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg,
                new Point(0, 0), "blank cursor");
        
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MainWindow.this.mouseMoved(e);
            }
        });
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float fps = animator.getLastFPS();
                setTitle(String.format("FPS: %.2f", fps));
            }
        });
        timer.setInitialDelay(1000);
        timer.start();
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) probeSpinner.getEditor();
        editor.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                scene.setProbeRadius((float) probeSpinner.getValue());
            }
        });
        
        resolutions = new Resolution[] {
            Resolution.RECT_800_600,
            Resolution.SQUARE_800,
            Resolution.RECT_1024_768, 
            Resolution.SQUARE_1024
        };
        
        panel.setPreferredSize(new Dimension(800, 600));
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        guiTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        paramsPanel = new javax.swing.JPanel();
        dataPanel = new javax.swing.JPanel();
        dataTextField = new javax.swing.JTextField();
        dataButton = new javax.swing.JButton();
        dynamicsPanel = new javax.swing.JPanel();
        playButton = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 32767));
        snapshotSpinner = new javax.swing.JSpinner();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 32767));
        jLabel21 = new javax.swing.JLabel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 32767));
        snapshotLabel = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel22 = new javax.swing.JLabel();
        speedSpinner = new javax.swing.JSpinner();
        surfacePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        probeSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        surfaceComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        sphereColorPanel = new javax.swing.JPanel();
        surfaceColorPanel = new javax.swing.JPanel();
        torusColorPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        triangleColorPanel = new javax.swing.JPanel();
        surfaceVisibleCheckBox = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        cavitiesPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        thresholdSlider = new javax.swing.JSlider();
        jLabel16 = new javax.swing.JLabel();
        cavityColorPanel2 = new javax.swing.JPanel();
        cavityColorPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        coloringComboBox = new javax.swing.JComboBox();
        cavitiesVisibleCheckBox = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        tunnelsPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        tunnelColorPanel = new javax.swing.JPanel();
        tunnelAOThresholdSlider = new javax.swing.JSlider();
        jLabel25 = new javax.swing.JLabel();
        transparencyPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        transparencySlider = new javax.swing.JSlider();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        phongCheckBox = new javax.swing.JCheckBox();
        aoCheckBox = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        ambientExponentSpinner = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        ambientThresholdSpinner = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        silhouettes = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        backfaceCheckBox = new javax.swing.JCheckBox();
        otherPanel = new javax.swing.JPanel();
        resolutionComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(90, 0), new java.awt.Dimension(90, 0), new java.awt.Dimension(80, 32767));
        develPanel = new javax.swing.JPanel();
        debugPanel = new javax.swing.JPanel();
        spheresCheckBox = new javax.swing.JCheckBox();
        trianglesCheckBox = new javax.swing.JCheckBox();
        toriCheckBox = new javax.swing.JCheckBox();
        sphereSpinner = new javax.swing.JSpinner();
        selectSphereCheckBox = new javax.swing.JCheckBox();
        selectTriangleCheckBox = new javax.swing.JCheckBox();
        triangleSpinner = new javax.swing.JSpinner();
        autoupdateCheckBox = new javax.swing.JCheckBox();
        updateButton = new javax.swing.JButton();
        updateGraphButton = new javax.swing.JButton();
        selectTorusCheckBox = new javax.swing.JCheckBox();
        torusSpinner = new javax.swing.JSpinner();
        renderPlaneCheckBox = new javax.swing.JCheckBox();
        planeEquationText = new javax.swing.JTextField();
        cavitySpinner = new javax.swing.JSpinner();
        selectCavityCheckBox = new javax.swing.JCheckBox();
        renderPointCheckBox = new javax.swing.JCheckBox();
        pointText = new javax.swing.JTextField();
        moleculeVisibleCheckBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        renderingModeComboBox = new javax.swing.JComboBox();
        testGromacsButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        ligandThresholdSpinner = new javax.swing.JSpinner();
        dynamicsInterpolationComboBox = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        backOpacityExponentSpinner = new javax.swing.JSpinner();
        frontOpacityMaxExponentSpinner = new javax.swing.JSpinner();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        ligandColorPanel = new javax.swing.JPanel();
        splatCheckBox = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(100, 1000), new java.awt.Dimension(100, 1000));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout(4, 4));

        guiTabbedPane.setPreferredSize(new java.awt.Dimension(250, 600));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        paramsPanel.setLayout(new javax.swing.BoxLayout(paramsPanel, javax.swing.BoxLayout.PAGE_AXIS));

        dataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Data"));
        dataPanel.setLayout(new java.awt.GridBagLayout());

        dataTextField.setEditable(false);
        dataTextField.setPreferredSize(new java.awt.Dimension(170, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        dataPanel.add(dataTextField, gridBagConstraints);

        dataButton.setText("...");
        dataButton.setMinimumSize(new java.awt.Dimension(25, 23));
        dataButton.setPreferredSize(new java.awt.Dimension(25, 23));
        dataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        dataPanel.add(dataButton, gridBagConstraints);

        paramsPanel.add(dataPanel);

        dynamicsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Dynamics"));
        dynamicsPanel.setLayout(new javax.swing.BoxLayout(dynamicsPanel, javax.swing.BoxLayout.LINE_AXIS));

        playButton.setText("Play");
        playButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        playButton.setPreferredSize(new java.awt.Dimension(45, 23));
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        dynamicsPanel.add(playButton);
        dynamicsPanel.add(filler5);

        snapshotSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        snapshotSpinner.setMinimumSize(new java.awt.Dimension(50, 20));
        snapshotSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        snapshotSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                snapshotSpinnerStateChanged(evt);
            }
        });
        dynamicsPanel.add(snapshotSpinner);
        dynamicsPanel.add(filler1);

        jLabel21.setText("/");
        dynamicsPanel.add(jLabel21);
        dynamicsPanel.add(filler6);

        snapshotLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        snapshotLabel.setText("-");
        snapshotLabel.setMinimumSize(new java.awt.Dimension(20, 14));
        snapshotLabel.setPreferredSize(new java.awt.Dimension(20, 14));
        dynamicsPanel.add(snapshotLabel);
        dynamicsPanel.add(filler4);

        jLabel22.setText("Speed:");
        dynamicsPanel.add(jLabel22);

        speedSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(2.0f), Float.valueOf(0.5f), null, Float.valueOf(0.5f)));
        speedSpinner.setMinimumSize(new java.awt.Dimension(15, 20));
        speedSpinner.setPreferredSize(new java.awt.Dimension(35, 20));
        speedSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSpinnerStateChanged(evt);
            }
        });
        dynamicsPanel.add(speedSpinner);

        paramsPanel.add(dynamicsPanel);

        surfacePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Surface"));
        surfacePanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Probe size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        surfacePanel.add(jLabel1, gridBagConstraints);

        probeSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(1.4f), Float.valueOf(0.1f), null, Float.valueOf(0.1f)));
        probeSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        probeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                probeSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        surfacePanel.add(probeSpinner, gridBagConstraints);

        jLabel2.setText("Surface:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        surfacePanel.add(jLabel2, gridBagConstraints);

        surfaceComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SAS", "SES" }));
        surfaceComboBox.setSelectedIndex(1);
        surfaceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                surfaceComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        surfacePanel.add(surfaceComboBox, gridBagConstraints);

        jLabel6.setText("Spheres:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        surfacePanel.add(jLabel6, gridBagConstraints);

        sphereColorPanel.setBackground(new java.awt.Color(255, 0, 0));
        sphereColorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sphereColorPanel.setPreferredSize(new java.awt.Dimension(18, 18));
        sphereColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sphereColorPanelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        surfacePanel.add(sphereColorPanel, gridBagConstraints);

        surfaceColorPanel.setBackground(new java.awt.Color(255, 153, 153));
        surfaceColorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        surfaceColorPanel.setOpaque(false);
        surfaceColorPanel.setPreferredSize(new java.awt.Dimension(18, 62));
        surfaceColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                surfaceColorPanelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        surfacePanel.add(surfaceColorPanel, gridBagConstraints);

        torusColorPanel.setBackground(new java.awt.Color(0, 0, 255));
        torusColorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        torusColorPanel.setPreferredSize(new java.awt.Dimension(18, 18));
        torusColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                torusColorPanelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        surfacePanel.add(torusColorPanel, gridBagConstraints);

        jLabel7.setText("Tori:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        surfacePanel.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Triangles:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        surfacePanel.add(jLabel8, gridBagConstraints);

        triangleColorPanel.setBackground(new java.awt.Color(0, 255, 0));
        triangleColorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        triangleColorPanel.setPreferredSize(new java.awt.Dimension(18, 18));
        triangleColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                triangleColorPanelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        surfacePanel.add(triangleColorPanel, gridBagConstraints);

        surfaceVisibleCheckBox.setSelected(true);
        surfaceVisibleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                surfaceVisibleCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        surfacePanel.add(surfaceVisibleCheckBox, gridBagConstraints);

        jLabel20.setText("Visible:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 4);
        surfacePanel.add(jLabel20, gridBagConstraints);

        paramsPanel.add(surfacePanel);

        cavitiesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Cavities"));
        cavitiesPanel.setLayout(new java.awt.GridBagLayout());

        jLabel15.setText("Threshold:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        cavitiesPanel.add(jLabel15, gridBagConstraints);

        thresholdSlider.setMajorTickSpacing(25);
        thresholdSlider.setMinorTickSpacing(25);
        thresholdSlider.setPaintLabels(true);
        thresholdSlider.setValue(0);
        thresholdSlider.setPreferredSize(new java.awt.Dimension(100, 37));
        thresholdSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                thresholdSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        cavitiesPanel.add(thresholdSlider, gridBagConstraints);

        jLabel16.setText("Colors:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        cavitiesPanel.add(jLabel16, gridBagConstraints);

        cavityColorPanel2.setBackground(new java.awt.Color(255, 0, 255));
        cavityColorPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cavityColorPanel2.setPreferredSize(new java.awt.Dimension(18, 18));
        cavityColorPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cavityColorPanel2MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 1.0;
        cavitiesPanel.add(cavityColorPanel2, gridBagConstraints);

        cavityColorPanel1.setBackground(new java.awt.Color(255, 255, 0));
        cavityColorPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cavityColorPanel1.setPreferredSize(new java.awt.Dimension(18, 18));
        cavityColorPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cavityColorPanel1MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        cavitiesPanel.add(cavityColorPanel1, gridBagConstraints);

        jLabel17.setText("Coloring mode:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        cavitiesPanel.add(jLabel17, gridBagConstraints);

        coloringComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AREA", "MONO" }));
        coloringComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coloringComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        cavitiesPanel.add(coloringComboBox, gridBagConstraints);

        cavitiesVisibleCheckBox.setSelected(true);
        cavitiesVisibleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cavitiesVisibleCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        cavitiesPanel.add(cavitiesVisibleCheckBox, gridBagConstraints);

        jLabel19.setText("Visible:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        cavitiesPanel.add(jLabel19, gridBagConstraints);

        paramsPanel.add(cavitiesPanel);

        tunnelsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tunnels"));
        tunnelsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel18.setText("Color:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        tunnelsPanel.add(jLabel18, gridBagConstraints);

        tunnelColorPanel.setBackground(new java.awt.Color(0, 255, 0));
        tunnelColorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tunnelColorPanel.setPreferredSize(new java.awt.Dimension(18, 18));
        tunnelColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tunnelColorPanelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        tunnelsPanel.add(tunnelColorPanel, gridBagConstraints);

        tunnelAOThresholdSlider.setMajorTickSpacing(50);
        tunnelAOThresholdSlider.setMinorTickSpacing(25);
        tunnelAOThresholdSlider.setPaintLabels(true);
        tunnelAOThresholdSlider.setValue(85);
        tunnelAOThresholdSlider.setPreferredSize(new java.awt.Dimension(100, 37));
        tunnelAOThresholdSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tunnelAOThresholdSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        tunnelsPanel.add(tunnelAOThresholdSlider, gridBagConstraints);

        jLabel25.setText("Occlusion threshold:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 4, 0, 4);
        tunnelsPanel.add(jLabel25, gridBagConstraints);

        paramsPanel.add(tunnelsPanel);

        transparencyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Transparency"));
        transparencyPanel.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Linear transparency:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        transparencyPanel.add(jLabel3, gridBagConstraints);

        transparencySlider.setMinorTickSpacing(25);
        transparencySlider.setPreferredSize(new java.awt.Dimension(100, 23));
        transparencySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                transparencySliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        transparencyPanel.add(transparencySlider, gridBagConstraints);

        jLabel9.setText("Phong lighting:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        transparencyPanel.add(jLabel9, gridBagConstraints);

        jLabel10.setText("Ambient occlusion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        transparencyPanel.add(jLabel10, gridBagConstraints);

        phongCheckBox.setSelected(true);
        phongCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phongCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        transparencyPanel.add(phongCheckBox, gridBagConstraints);

        aoCheckBox.setSelected(true);
        aoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aoCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        transparencyPanel.add(aoCheckBox, gridBagConstraints);

        jLabel11.setText("Ambient exponent:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        transparencyPanel.add(jLabel11, gridBagConstraints);

        ambientExponentSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(0.1f)));
        ambientExponentSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        ambientExponentSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ambientExponentSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        transparencyPanel.add(ambientExponentSpinner, gridBagConstraints);

        jLabel12.setText("Ambient threshold:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        transparencyPanel.add(jLabel12, gridBagConstraints);

        ambientThresholdSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.7f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));
        ambientThresholdSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        ambientThresholdSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ambientThresholdSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        transparencyPanel.add(ambientThresholdSpinner, gridBagConstraints);

        jLabel13.setText("Silhouettes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 4);
        transparencyPanel.add(jLabel13, gridBagConstraints);

        silhouettes.setSelected(true);
        silhouettes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                silhouettesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        transparencyPanel.add(silhouettes, gridBagConstraints);

        jLabel14.setText("Backface modulation:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        transparencyPanel.add(jLabel14, gridBagConstraints);

        backfaceCheckBox.setSelected(true);
        backfaceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backfaceCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 1.0;
        transparencyPanel.add(backfaceCheckBox, gridBagConstraints);

        paramsPanel.add(transparencyPanel);

        otherPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Other"));
        otherPanel.setLayout(new java.awt.GridBagLayout());

        resolutionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "800x600", "800x800", "1024x768", "1024x1024" }));
        resolutionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resolutionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        otherPanel.add(resolutionComboBox, gridBagConstraints);

        jLabel4.setText("Resolution:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        otherPanel.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        otherPanel.add(filler2, gridBagConstraints);

        paramsPanel.add(otherPanel);

        jScrollPane1.setViewportView(paramsPanel);

        guiTabbedPane.addTab("Parameters", jScrollPane1);

        develPanel.setLayout(new javax.swing.BoxLayout(develPanel, javax.swing.BoxLayout.PAGE_AXIS));

        debugPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Debug"));
        debugPanel.setLayout(new java.awt.GridBagLayout());

        spheresCheckBox.setSelected(true);
        spheresCheckBox.setText("Render spheres");
        spheresCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spheresCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 4);
        debugPanel.add(spheresCheckBox, gridBagConstraints);

        trianglesCheckBox.setSelected(true);
        trianglesCheckBox.setText("Render triangles");
        trianglesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trianglesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(trianglesCheckBox, gridBagConstraints);

        toriCheckBox.setSelected(true);
        toriCheckBox.setText("Render tori");
        toriCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toriCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(toriCheckBox, gridBagConstraints);

        sphereSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        sphereSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        sphereSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sphereSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(12, 4, 0, 4);
        debugPanel.add(sphereSpinner, gridBagConstraints);

        selectSphereCheckBox.setText("Select sphere");
        selectSphereCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSphereCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 4);
        debugPanel.add(selectSphereCheckBox, gridBagConstraints);

        selectTriangleCheckBox.setText("Select triangle");
        selectTriangleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTriangleCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(selectTriangleCheckBox, gridBagConstraints);

        triangleSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        triangleSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        triangleSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                triangleSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(triangleSpinner, gridBagConstraints);

        autoupdateCheckBox.setSelected(true);
        autoupdateCheckBox.setText("Autoupdate");
        autoupdateCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoupdateCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(autoupdateCheckBox, gridBagConstraints);

        updateButton.setText("Update");
        updateButton.setEnabled(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(updateButton, gridBagConstraints);

        updateGraphButton.setText("Update SG");
        updateGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateGraphButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(updateGraphButton, gridBagConstraints);

        selectTorusCheckBox.setText("Select torus");
        selectTorusCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTorusCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(selectTorusCheckBox, gridBagConstraints);

        torusSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        torusSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        torusSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                torusSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(torusSpinner, gridBagConstraints);

        renderPlaneCheckBox.setText("Render plane");
        renderPlaneCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renderPlaneCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 4);
        debugPanel.add(renderPlaneCheckBox, gridBagConstraints);

        planeEquationText.setPreferredSize(new java.awt.Dimension(190, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(planeEquationText, gridBagConstraints);

        cavitySpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        cavitySpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        cavitySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cavitySpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(cavitySpinner, gridBagConstraints);

        selectCavityCheckBox.setText("Select cavity");
        selectCavityCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCavityCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(selectCavityCheckBox, gridBagConstraints);

        renderPointCheckBox.setText("Render point");
        renderPointCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renderPointCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(renderPointCheckBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        debugPanel.add(pointText, gridBagConstraints);

        moleculeVisibleCheckBox.setText("Render molecule");
        moleculeVisibleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moleculeVisibleCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        debugPanel.add(moleculeVisibleCheckBox, gridBagConstraints);

        develPanel.add(debugPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Test"));
        jPanel1.setMinimumSize(new java.awt.Dimension(234, 200));
        jPanel1.setPreferredSize(new java.awt.Dimension(234, 170));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        renderingModeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OIT (BOX)", "OIT (SPHERE)", "KRONE" }));
        renderingModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renderingModeComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel1.add(renderingModeComboBox, gridBagConstraints);

        testGromacsButton.setText("Test Gromacs");
        testGromacsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testGromacsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(testGromacsButton, gridBagConstraints);

        jLabel5.setText("Ligand distance:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel23.setText("Rendering mode:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel1.add(jLabel23, gridBagConstraints);

        ligandThresholdSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(15.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));
        ligandThresholdSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        ligandThresholdSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ligandThresholdSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel1.add(ligandThresholdSpinner, gridBagConstraints);

        dynamicsInterpolationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEAREST", "LINEAR" }));
        dynamicsInterpolationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dynamicsInterpolationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel1.add(dynamicsInterpolationComboBox, gridBagConstraints);

        jLabel24.setText("Dynamics interpolation:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel1.add(jLabel24, gridBagConstraints);

        jLabel26.setText("Back opacity exponent:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel1.add(jLabel26, gridBagConstraints);

        backOpacityExponentSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(2.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));
        backOpacityExponentSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        backOpacityExponentSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                backOpacityExponentSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel1.add(backOpacityExponentSpinner, gridBagConstraints);

        frontOpacityMaxExponentSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(8.0f), Float.valueOf(1.0f), null, Float.valueOf(1.0f)));
        frontOpacityMaxExponentSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        frontOpacityMaxExponentSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                frontOpacityMaxExponentSpinnerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel1.add(frontOpacityMaxExponentSpinner, gridBagConstraints);

        jLabel27.setText("Front op. max exponent:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel1.add(jLabel27, gridBagConstraints);

        jLabel28.setText("Ligand color:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel1.add(jLabel28, gridBagConstraints);

        ligandColorPanel.setBackground(new java.awt.Color(255, 0, 0));
        ligandColorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ligandColorPanel.setPreferredSize(new java.awt.Dimension(18, 18));
        ligandColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ligandColorPanelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel1.add(ligandColorPanel, gridBagConstraints);

        splatCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                splatCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(splatCheckBox, gridBagConstraints);

        jLabel29.setText("Render splats:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel1.add(jLabel29, gridBagConstraints);

        develPanel.add(jPanel1);
        develPanel.add(filler3);

        guiTabbedPane.addTab("Development", develPanel);

        getContentPane().add(guiTabbedPane, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void probeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_probeSpinnerStateChanged
        scene.setProbeRadius((float) probeSpinner.getValue());
    }//GEN-LAST:event_probeSpinnerStateChanged

    private void surfaceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surfaceComboBoxActionPerformed
        if (surfaceComboBox.getSelectedIndex() == 0) {
            scene.setSurfaceType(Surface.SAS);
        } else {
            scene.setSurfaceType(Surface.SES);
        }
    }//GEN-LAST:event_surfaceComboBoxActionPerformed

    private void spheresCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spheresCheckBoxActionPerformed
        scene.setRenderSpheres(spheresCheckBox.isSelected());
    }//GEN-LAST:event_spheresCheckBoxActionPerformed

    private void trianglesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trianglesCheckBoxActionPerformed
        scene.setRenderTriangles(trianglesCheckBox.isSelected());
    }//GEN-LAST:event_trianglesCheckBoxActionPerformed

    private void toriCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toriCheckBoxActionPerformed
        scene.setRenderTori(toriCheckBox.isSelected());
    }//GEN-LAST:event_toriCheckBoxActionPerformed

    private void transparencySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_transparencySliderStateChanged
        scene.setOpacity((100 - transparencySlider.getValue()) / 100.0f);
    }//GEN-LAST:event_transparencySliderStateChanged

    private void resolutionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resolutionComboBoxActionPerformed
        Resolution resolution = resolutions[resolutionComboBox.getSelectedIndex()];
        panel.setPreferredSize(new Dimension(resolution.getWidth(), resolution.getHeight()));
        pack();
    }//GEN-LAST:event_resolutionComboBoxActionPerformed

    private void selectSphereCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSphereCheckBoxActionPerformed
        scene.setRenderSelectedSphere(selectSphereCheckBox.isSelected());
    }//GEN-LAST:event_selectSphereCheckBoxActionPerformed

    private void sphereSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sphereSpinnerStateChanged
        scene.setSelectedSphere((int) sphereSpinner.getValue());
    }//GEN-LAST:event_sphereSpinnerStateChanged

    private void selectTriangleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTriangleCheckBoxActionPerformed
        scene.setRenderSelectedTriangle(selectTriangleCheckBox.isSelected());
    }//GEN-LAST:event_selectTriangleCheckBoxActionPerformed

    private void triangleSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_triangleSpinnerStateChanged
        scene.setSelectedTriangle((int) triangleSpinner.getValue());
    }//GEN-LAST:event_triangleSpinnerStateChanged

    private void autoupdateCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoupdateCheckBoxActionPerformed
        updateButton.setEnabled(!autoupdateCheckBox.isSelected());
        scene.setAutoupdate(autoupdateCheckBox.isSelected());
    }//GEN-LAST:event_autoupdateCheckBoxActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        scene.update();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void cavitiesVisibleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cavitiesVisibleCheckBoxActionPerformed
        scene.setClipCavities(!cavitiesVisibleCheckBox.isSelected());
    }//GEN-LAST:event_cavitiesVisibleCheckBoxActionPerformed

    private void updateGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateGraphButtonActionPerformed
        scene.updateSurfaceGraph();
    }//GEN-LAST:event_updateGraphButtonActionPerformed

    private void selectTorusCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTorusCheckBoxActionPerformed
        scene.setRenderSelectedTorus(selectTorusCheckBox.isSelected());
    }//GEN-LAST:event_selectTorusCheckBoxActionPerformed

    private void torusSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_torusSpinnerStateChanged
        scene.setSelectedTorus((int) torusSpinner.getValue());
    }//GEN-LAST:event_torusSpinnerStateChanged

    private void renderPlaneCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renderPlaneCheckBoxActionPerformed
        String comps[] = planeEquationText.getText().replace(',', '.').split(" ");
        Vector4f plane = new Vector4f(Float.parseFloat(comps[0]), Float.parseFloat(comps[1]),
                Float.parseFloat(comps[2]), Float.parseFloat(comps[3]));
        scene.setPlane(plane);
        scene.setRenderPlane(renderPlaneCheckBox.isSelected());
    }//GEN-LAST:event_renderPlaneCheckBoxActionPerformed

    private void selectCavityCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCavityCheckBoxActionPerformed
        scene.setRenderSelectedCavity(selectCavityCheckBox.isSelected());
    }//GEN-LAST:event_selectCavityCheckBoxActionPerformed

    private void cavitySpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cavitySpinnerStateChanged
        scene.setSelectedCavity((int) cavitySpinner.getValue());
    }//GEN-LAST:event_cavitySpinnerStateChanged

    private void sphereColorPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sphereColorPanelMouseClicked
        Color color = JColorChooser.showDialog(this, "Sphere color", sphereColorPanel.getBackground());
        if (color != null) {
            surfaceColorPanel.setOpaque(false);
            sphereColorPanel.setOpaque(true);
            torusColorPanel.setOpaque(true);
            triangleColorPanel.setOpaque(true);
            transparencyPanel.repaint();
            sphereColorPanel.setBackground(color);
            scene.setSphereColor(color);
            scene.setTorusColor(torusColorPanel.getBackground());
            scene.setTriangleColor(triangleColorPanel.getBackground());
        }
    }//GEN-LAST:event_sphereColorPanelMouseClicked

    private void torusColorPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_torusColorPanelMouseClicked
        Color color = JColorChooser.showDialog(this, "Torus color", torusColorPanel.getBackground());
        if (color != null) {
            surfaceColorPanel.setOpaque(false);
            sphereColorPanel.setOpaque(true);
            torusColorPanel.setOpaque(true);
            triangleColorPanel.setOpaque(true);
            transparencyPanel.repaint();
            torusColorPanel.setBackground(color);
            scene.setSphereColor(sphereColorPanel.getBackground());
            scene.setTorusColor(color);
            scene.setTriangleColor(triangleColorPanel.getBackground());
        }
    }//GEN-LAST:event_torusColorPanelMouseClicked

    private void triangleColorPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_triangleColorPanelMouseClicked
        Color color = JColorChooser.showDialog(this, "Triangle color", triangleColorPanel.getBackground());
        if (color != null) {
            surfaceColorPanel.setOpaque(false);
            sphereColorPanel.setOpaque(true);
            torusColorPanel.setOpaque(true);
            triangleColorPanel.setOpaque(true);
            transparencyPanel.repaint();
            triangleColorPanel.setBackground(color);
            scene.setSphereColor(sphereColorPanel.getBackground());
            scene.setTorusColor(torusColorPanel.getBackground());
            scene.setTriangleColor(color);
        }
    }//GEN-LAST:event_triangleColorPanelMouseClicked

    private void phongCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phongCheckBoxActionPerformed
        scene.setPhongLighting(phongCheckBox.isSelected());
    }//GEN-LAST:event_phongCheckBoxActionPerformed

    private void aoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aoCheckBoxActionPerformed
        scene.setAmbientOcclusion(aoCheckBox.isSelected());
    }//GEN-LAST:event_aoCheckBoxActionPerformed

    private void renderPointCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renderPointCheckBoxActionPerformed
        String comps[] = pointText.getText().replace(',', '.').split("\\s+");
        Vector3f point = new Vector3f(Float.parseFloat(comps[0]), Float.parseFloat(comps[1]),
                Float.parseFloat(comps[2]));
        scene.setPoint(point);
        scene.setRenderPoint(renderPointCheckBox.isSelected());
    }//GEN-LAST:event_renderPointCheckBoxActionPerformed

    private void ambientExponentSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ambientExponentSpinnerStateChanged
        scene.setAOExponent((float) ambientExponentSpinner.getValue());
    }//GEN-LAST:event_ambientExponentSpinnerStateChanged

    private void ambientThresholdSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ambientThresholdSpinnerStateChanged
        scene.setAOThreshold((float) ambientThresholdSpinner.getValue());
    }//GEN-LAST:event_ambientThresholdSpinnerStateChanged

    private void silhouettesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_silhouettesActionPerformed
        scene.setSilhouettes(silhouettes.isSelected());
    }//GEN-LAST:event_silhouettesActionPerformed

    private void backfaceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backfaceCheckBoxActionPerformed
        scene.setBackfaceModulation(backfaceCheckBox.isSelected());
    }//GEN-LAST:event_backfaceCheckBoxActionPerformed

    private void surfaceColorPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_surfaceColorPanelMouseClicked
        Color color = JColorChooser.showDialog(this, "Surface color", surfaceColorPanel.getBackground());
        if (color != null) {
            sphereColorPanel.setOpaque(false);
            torusColorPanel.setOpaque(false);
            triangleColorPanel.setOpaque(false);
            surfaceColorPanel.setOpaque(true);
            transparencyPanel.repaint();
            surfaceColorPanel.setBackground(color);
            scene.setSphereColor(color);
            scene.setTorusColor(color);
            scene.setTriangleColor(color);
        }
    }//GEN-LAST:event_surfaceColorPanelMouseClicked

    private void dataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataButtonActionPerformed
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setMultiSelectionEnabled(true);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // get all selected files
            File[] files = chooser.getSelectedFiles();
            if (files.length == 0) {
                files = new File[] { chooser.getSelectedFile() };
            }
            // choose whether to load from PDB or from GROMACS
            if (files.length == 2 && isGromacs(files[0], files[1])) {
                scene.loadDynamicsFromGROMACS(files[0], files[1], 100);
            } else if (files.length == 2 && isGromacs(files[1], files[0])) {
                scene.loadDynamicsFromGROMACS(files[1], files[0], 100);
            } else {
                scene.loadDynamics(files);
            }
            // set data display name
            String data = files[0].getName();
            if (files.length > 1) {
                data += " (" + files.length + ")";
            }
            dataTextField.setText(data);
        }
    }//GEN-LAST:event_dataButtonActionPerformed

    private void testGromacsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testGromacsButtonActionPerformed
        //File home = new File(System.getProperty("user.home"));
        File topology = new File("C:\\Users\\Adam\\caver\\dynamics\\LINB-ACE\\LINB-ACETON.gro");
        File trajectory = new File("C:\\Users\\Adam\\caver\\dynamics\\LINB-ACE\\LINB-ACE-pbc.xtc");
        try {
            //int snapshots = loader.getNumberOfSnapshots(trajectory);
            //JOptionPane.showMessageDialog(this, "Number of snapshots: " + snapshots);
            //Dynamics data = loader.loadDynamics(topology, trajectory, 100);
            //JOptionPane.showMessageDialog(this, "Numbers of drugs: " + data.getDrugCount());
            scene.loadDynamicsFromGROMACS(topology, trajectory, 150, 250);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }//GEN-LAST:event_testGromacsButtonActionPerformed

    private void thresholdSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_thresholdSliderStateChanged
        scene.setThreshold(thresholdSlider.getValue());
    }//GEN-LAST:event_thresholdSliderStateChanged

    private void cavityColorPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cavityColorPanel2MouseClicked
        Color color = JColorChooser.showDialog(this, "Cavity color 2", cavityColorPanel2.getBackground());
        if (color != null) {
            cavityColorPanel2.setBackground(color);
            scene.setCavityColor2(color);
        }
    }//GEN-LAST:event_cavityColorPanel2MouseClicked

    private void cavityColorPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cavityColorPanel1MouseClicked
        Color color = JColorChooser.showDialog(this, "Cavity color 1", cavityColorPanel1.getBackground());
        if (color != null) {
            cavityColorPanel1.setBackground(color);
            scene.setCavityColor1(color);
        }
    }//GEN-LAST:event_cavityColorPanel1MouseClicked

    private void coloringComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coloringComboBoxActionPerformed
        if (coloringComboBox.getSelectedIndex() == 0) {
            scene.setCavityColoring(Coloring.AREA);
        } else {
            scene.setCavityColoring(Coloring.MONO);
        }
    }//GEN-LAST:event_coloringComboBoxActionPerformed

    private void tunnelColorPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tunnelColorPanelMouseClicked
        Color color = JColorChooser.showDialog(this, "Tunnel color", tunnelColorPanel.getBackground());
        if (color != null) {
            tunnelColorPanel.setBackground(color);
            scene.setTunnelColor(color);
        }
    }//GEN-LAST:event_tunnelColorPanelMouseClicked

    private void surfaceVisibleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surfaceVisibleCheckBoxActionPerformed
        scene.setClipSurface(!surfaceVisibleCheckBox.isSelected());
    }//GEN-LAST:event_surfaceVisibleCheckBoxActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        if (scene.isDynamicsRunning()) {
            scene.stopDynamics();
            playButton.setText("Play");
        } else {
            scene.startDynamics();
            playButton.setText("Pause");
        }
    }//GEN-LAST:event_playButtonActionPerformed

    private void speedSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSpinnerStateChanged
        scene.setSpeed((float) speedSpinner.getValue());
    }//GEN-LAST:event_speedSpinnerStateChanged

    private void renderingModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renderingModeComboBoxActionPerformed
        scene.setRenderingMode(renderingModeComboBox.getSelectedIndex());
    }//GEN-LAST:event_renderingModeComboBoxActionPerformed

    private void moleculeVisibleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moleculeVisibleCheckBoxActionPerformed
        scene.setMoleculeVisible(moleculeVisibleCheckBox.isSelected());
    }//GEN-LAST:event_moleculeVisibleCheckBoxActionPerformed

    private void snapshotSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_snapshotSpinnerStateChanged
        scene.setDynamicsSnapshot(((int) snapshotSpinner.getValue()) - 1);
    }//GEN-LAST:event_snapshotSpinnerStateChanged

    private void ligandThresholdSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ligandThresholdSpinnerStateChanged
        scene.setLigandThreshold((float) ligandThresholdSpinner.getValue());
    }//GEN-LAST:event_ligandThresholdSpinnerStateChanged

    private void dynamicsInterpolationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dynamicsInterpolationComboBoxActionPerformed
        if (dynamicsInterpolationComboBox.getSelectedIndex() == 0) {
            scene.setDynamicsInterpolation(Interpolation.NEAREST);
        } else {
            scene.setDynamicsInterpolation(Interpolation.LINEAR);
        }
    }//GEN-LAST:event_dynamicsInterpolationComboBoxActionPerformed

    private void tunnelAOThresholdSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tunnelAOThresholdSliderStateChanged
        scene.setTunnelAOThreshold(tunnelAOThresholdSlider.getValue() / 100.0f);
    }//GEN-LAST:event_tunnelAOThresholdSliderStateChanged

    private void frontOpacityMaxExponentSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_frontOpacityMaxExponentSpinnerStateChanged
        scene.setFrontOpacityMaxExponent((float) frontOpacityMaxExponentSpinner.getValue());
    }//GEN-LAST:event_frontOpacityMaxExponentSpinnerStateChanged

    private void backOpacityExponentSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_backOpacityExponentSpinnerStateChanged
        scene.setBackOpacityExponent((float) backOpacityExponentSpinner.getValue());
    }//GEN-LAST:event_backOpacityExponentSpinnerStateChanged

    private void ligandColorPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ligandColorPanelMouseClicked
        Color color = JColorChooser.showDialog(this, "Ligand color", ligandColorPanel.getBackground());
        if (color != null) {
            ligandColorPanel.setBackground(color);
            scene.setLigandColor(color);
        }
    }//GEN-LAST:event_ligandColorPanelMouseClicked

    private void splatCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_splatCheckBoxActionPerformed
        scene.setOBB(splatCheckBox.isSelected());
    }//GEN-LAST:event_splatCheckBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner ambientExponentSpinner;
    private javax.swing.JSpinner ambientThresholdSpinner;
    private javax.swing.JCheckBox aoCheckBox;
    private javax.swing.JCheckBox autoupdateCheckBox;
    private javax.swing.JSpinner backOpacityExponentSpinner;
    private javax.swing.JCheckBox backfaceCheckBox;
    private javax.swing.JPanel cavitiesPanel;
    private javax.swing.JCheckBox cavitiesVisibleCheckBox;
    private javax.swing.JPanel cavityColorPanel1;
    private javax.swing.JPanel cavityColorPanel2;
    private javax.swing.JSpinner cavitySpinner;
    private javax.swing.JComboBox coloringComboBox;
    private javax.swing.JButton dataButton;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JTextField dataTextField;
    private javax.swing.JPanel debugPanel;
    private javax.swing.JPanel develPanel;
    private javax.swing.JComboBox dynamicsInterpolationComboBox;
    private javax.swing.JPanel dynamicsPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JSpinner frontOpacityMaxExponentSpinner;
    private javax.swing.JTabbedPane guiTabbedPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel ligandColorPanel;
    private javax.swing.JSpinner ligandThresholdSpinner;
    private javax.swing.JCheckBox moleculeVisibleCheckBox;
    private javax.swing.JPanel otherPanel;
    private javax.swing.JPanel paramsPanel;
    private javax.swing.JCheckBox phongCheckBox;
    private javax.swing.JTextField planeEquationText;
    private javax.swing.JButton playButton;
    private javax.swing.JTextField pointText;
    private javax.swing.JSpinner probeSpinner;
    private javax.swing.JCheckBox renderPlaneCheckBox;
    private javax.swing.JCheckBox renderPointCheckBox;
    private javax.swing.JComboBox renderingModeComboBox;
    private javax.swing.JComboBox resolutionComboBox;
    private javax.swing.JCheckBox selectCavityCheckBox;
    private javax.swing.JCheckBox selectSphereCheckBox;
    private javax.swing.JCheckBox selectTorusCheckBox;
    private javax.swing.JCheckBox selectTriangleCheckBox;
    private javax.swing.JCheckBox silhouettes;
    private javax.swing.JLabel snapshotLabel;
    private javax.swing.JSpinner snapshotSpinner;
    private javax.swing.JSpinner speedSpinner;
    private javax.swing.JPanel sphereColorPanel;
    private javax.swing.JSpinner sphereSpinner;
    private javax.swing.JCheckBox spheresCheckBox;
    private javax.swing.JCheckBox splatCheckBox;
    private javax.swing.JPanel surfaceColorPanel;
    private javax.swing.JComboBox surfaceComboBox;
    private javax.swing.JPanel surfacePanel;
    private javax.swing.JCheckBox surfaceVisibleCheckBox;
    private javax.swing.JButton testGromacsButton;
    private javax.swing.JSlider thresholdSlider;
    private javax.swing.JCheckBox toriCheckBox;
    private javax.swing.JPanel torusColorPanel;
    private javax.swing.JSpinner torusSpinner;
    private javax.swing.JPanel transparencyPanel;
    private javax.swing.JSlider transparencySlider;
    private javax.swing.JPanel triangleColorPanel;
    private javax.swing.JSpinner triangleSpinner;
    private javax.swing.JCheckBox trianglesCheckBox;
    private javax.swing.JSlider tunnelAOThresholdSlider;
    private javax.swing.JPanel tunnelColorPanel;
    private javax.swing.JPanel tunnelsPanel;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton updateGraphButton;
    // End of variables declaration//GEN-END:variables

    public void keyPressed(KeyEvent e) {
        float step = 0.5f;
        if (e.isShiftDown()) {
            step *= 0.5f;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
                
            case KeyEvent.VK_SPACE:
                mouseEnabled = !mouseEnabled;
                if (mouseEnabled) {
                    setCursor(blankCursor);
                    Point center = new Point(panel.getWidth() / 2, panel.getHeight() / 2);
                    Point location = panel.getLocationOnScreen();
                    //Linux in A419 has two displays
                    robot.mouseMove(location.x + center.x - gc.getBounds().x, location.y + center.y - gc.getBounds().y);
                    //robot.mouseMove(location.x + center.x, location.y + center.y);
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
                break;
                
            case KeyEvent.VK_F:
                toggleFullScreen();
                break;
                
            case KeyEvent.VK_P:
                scene.togglePolygonMode();
                break;
                
            case KeyEvent.VK_M:
                scene.toggleWholeMolecule();
                break;
                
            case KeyEvent.VK_R:
                scene.writeResults();
                break;
                
            case KeyEvent.VK_I:
                scene.writePerformanceInfo();
                break;
                
            case KeyEvent.VK_W:
                scene.move(step);
                break;
                
            case KeyEvent.VK_S:
                scene.move(-step);
                break;
                
            case KeyEvent.VK_A:
                scene.strafe(-step);
                break;
                
            case KeyEvent.VK_D:
                scene.strafe(step);
                break;
        }
    }
    
    private void mouseMoved(MouseEvent e) {
        if (!mouseEnabled) {
            return;
        }
        
        int diffX = panel.getWidth() / 2 - e.getX();
        int diffY = panel.getHeight() / 2 - e.getY();
        
        scene.tilt(-diffY / 100f);
        scene.pan(diffX / 100f);
        
        if (diffX != 0 || diffY != 0) {
            Point center = new Point(panel.getWidth() / 2, panel.getHeight() / 2);
            Point location = panel.getLocationOnScreen();
            robot.mouseMove(location.x + center.x - gc.getBounds().x, location.y + center.y - gc.getBounds().y);
            //robot.mouseMove(location.x + center.x, location.y + center.y);
        }
    }
    
    private void toggleFullScreen() {
        fullscreen = !fullscreen;
        
        if (animator.isAnimating()) {
            animator.stop();
        }
        
        dispose();
        setUndecorated(fullscreen);
        pack();
        
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();
        
        if (fullscreen) {
            device.setFullScreenWindow(this);
        } else {
            device.setFullScreenWindow(null);
        }
        setVisible(true);
        animator.start();
    }
    
    private static boolean isGromacs(File topology, File trajectory) {
        return topology.getName().endsWith(".gro") && trajectory.getName().endsWith(".xtc");
    }
    
}
