package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import logica.BlueCar;
import logica.GreenCar;
import logica.RedCar;

public class Race extends JFrame {

    private SelectableCars selectedCar = null;
    public final static int STARTING_POSITION = 640, CAR_WIDTH = 50, CAR_HEIGHT = 100,
            LANE_RED_CAR = 50, LANE_GREEN_CAR = 190, LANE_BLUE_CAR = 330;
    private JLabel lblRedCar, lblGreenCar, lblBlueCar, lblTrack;
    private JPanel panelPrincipal;
    private JButton btnStart;
    private RedCar redCar;
    private GreenCar greenCar;
    private BlueCar blueCar;
    private ButtonGroup group;
    private JToggleButton btnRedCar, btnGreenCar, btnBlueCar;
    private ImageIcon iconTrackGif, iconTrackJpg, iconRedCar, iconGreenCar, iconBlueCar, iconFlag,
            iconFirstPlace, iconSecondPlace, iconThirdPlace;

    public Race() {
        initIcons();
        initComponents();

        //establecemos la configuracion de la ventana
        pack();
        setSize(600, 900);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Boton de start (JButton)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;// No ocupa espacio adicional en X
        gbc.weighty = 0;// No ocupa espacio adicional en Y
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        btnStart = new JButton();
        btnStart.setPreferredSize(new Dimension(150, 150));
        btnStart.setIcon(new ImageIcon(iconFlag.getImage().getScaledInstance
            ((int)btnStart.getPreferredSize().getWidth(),
                    (int)btnStart.getPreferredSize().getHeight(), Image.SCALE_SMOOTH)));
        btnStart.addActionListener(ActionListenerForStartButton());
        panelPrincipal.add(btnStart, gbc);

        //Etiqueta para indicar seleccion de auto (JLabel)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 10);
        JLabel lblIndicarSeleccion = new JLabel("Seleccione su auto: ");
        lblIndicarSeleccion.setFont(new Font("Product Sans", Font.BOLD, 15));
        lblIndicarSeleccion.setPreferredSize(new Dimension(150, 30));
        lblIndicarSeleccion.setHorizontalAlignment(JLabel.CENTER);
        panelPrincipal.add(lblIndicarSeleccion, gbc);

        //Botones para seleccionar auto (JToggleButton)
        //Boton del auto rojo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        btnRedCar = new JToggleButton();
        btnRedCar.setPreferredSize(new Dimension(150, 150));
        btnRedCar.setIcon(new ImageIcon(iconRedCar.getImage().getScaledInstance(70, 130, Image.SCALE_SMOOTH)));
        btnRedCar.addActionListener(getActionListener(SelectableCars.RED_CAR));
        panelPrincipal.add(btnRedCar, gbc);

        //Boton del auto verde
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        btnGreenCar = new JToggleButton();
        btnGreenCar.setPreferredSize(new Dimension(150, 150));
        btnGreenCar.setIcon(new ImageIcon(iconGreenCar.getImage().getScaledInstance(70, 130, Image.SCALE_SMOOTH)));
        btnGreenCar.addActionListener(getActionListener(SelectableCars.GREEN_CAR));
        panelPrincipal.add(btnGreenCar, gbc);

        //Boton del auto azul
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        btnBlueCar = new JToggleButton();
        btnBlueCar.setPreferredSize(new Dimension(150, 150));
        btnBlueCar.setIcon(new ImageIcon(iconBlueCar.getImage().getScaledInstance(70, 130, Image.SCALE_SMOOTH)));
        btnBlueCar.addActionListener(getActionListener(SelectableCars.BLUE_CAR));
        panelPrincipal.add(btnBlueCar, gbc);

        //Grupo de botones (ButtonGroup)
        group = new ButtonGroup();
        group.add(btnRedCar);
        group.add(btnGreenCar);
        group.add(btnBlueCar);

        //Panel derecho
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 1;//Ocupa espacio adicional en x
        gbc.weighty = 1;//Ocupa espacio adicional en y
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelPrincipal.add(panelDerecho, gbc);

        //Panel para las posiciones
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel panelRacePositions = new JPanel(new GridBagLayout());
        panelRacePositions.setBackground(Color.PINK);
        panelRacePositions.setPreferredSize(new Dimension(300, 50));
        panelDerecho.add(panelRacePositions, gbc);

        //Labels para las posiciones
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblRedCarPosition = new JLabel("RC", SwingConstants.CENTER);
        lblRedCarPosition.setOpaque(true);
        lblRedCarPosition.setBackground(Color.ORANGE);
        lblRedCarPosition.setPreferredSize(new Dimension(50, 50));
        panelRacePositions.add(lblRedCarPosition, gbc);

        gbc.gridx = 1;
        JLabel lblGreenCarPosition = new JLabel("GC", SwingConstants.CENTER);
        lblGreenCarPosition.setOpaque(true);
        lblGreenCarPosition.setBackground(Color.YELLOW);
        lblGreenCarPosition.setPreferredSize(new Dimension(50, 50));
        panelRacePositions.add(lblGreenCarPosition, gbc);

        gbc.gridx = 2;
        JLabel lblBlueCarPosition = new JLabel("BC", SwingConstants.CENTER);
        lblBlueCarPosition.setOpaque(true);
        lblBlueCarPosition.setBackground(Color.WHITE);
        lblBlueCarPosition.setPreferredSize(new Dimension(50, 50));
        panelRacePositions.add(lblBlueCarPosition, gbc);

        //Label para la pista
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;//Ocupa espacio adicional en x
        gbc.weighty = 1;//Ocupa espacio adicional en y
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        lblTrack = new JLabel();
        lblTrack.setLayout(null);
        lblTrack.setIcon(iconTrackJpg);
        lblTrack.setOpaque(true);
        lblTrack.setBackground(Color.BLACK);

        //Labels para los autos (JLabel)
        lblRedCar = new JLabel();
        lblRedCar.setBounds(LANE_RED_CAR, STARTING_POSITION, CAR_WIDTH, CAR_HEIGHT);
        lblRedCar.setIcon(new ImageIcon(iconRedCar.getImage().getScaledInstance(CAR_WIDTH, CAR_HEIGHT, Image.SCALE_SMOOTH)));
        lblTrack.add(lblRedCar);

        lblGreenCar = new JLabel();
        lblGreenCar.setBounds(LANE_GREEN_CAR, STARTING_POSITION, CAR_WIDTH, CAR_HEIGHT);
        lblGreenCar.setIcon(new ImageIcon(iconGreenCar.getImage().getScaledInstance(CAR_WIDTH, CAR_HEIGHT, Image.SCALE_SMOOTH)));
        lblTrack.add(lblGreenCar);

        lblBlueCar = new JLabel();
        lblBlueCar.setBounds(LANE_BLUE_CAR, STARTING_POSITION, CAR_WIDTH, CAR_HEIGHT);
        lblBlueCar.setIcon(new ImageIcon(iconBlueCar.getImage().getScaledInstance(CAR_WIDTH, CAR_HEIGHT, Image.SCALE_SMOOTH)));
        lblTrack.add(lblBlueCar);

        panelDerecho.add(lblTrack, gbc);

        //Añadimos el panel principal a la ventana
        this.add(panelPrincipal);
    }

    private ActionListener getActionListener(SelectableCars car) {

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCar = car;
            }
        };

        return actionListener;
    }

    private void initThreads() {
        //Instanciar los hilos
        redCar = new RedCar(lblRedCar);
        greenCar = new GreenCar(lblGreenCar);
        blueCar = new BlueCar(lblBlueCar);
        redCar.start();
        greenCar.start();
        blueCar.start();
    }

    public ActionListener ActionListenerForStartButton() {

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedCar != null) { //se ha seleccionado una auto
                    initThreads();
                    disabledNewRace();

                    //Referencia para el hilo que termino primero
                    final AtomicReference<Thread> hiloTerminadoPrimero = new AtomicReference<>(null);

                    //Contador de hilos terminados
                    final AtomicInteger numeroHilosTerminados = new AtomicInteger(0);

                    //conjunto que almacene los hilos terminados
                    final Set<Thread> hilosTerminados = ConcurrentHashMap.newKeySet();

                    Thread hilos[] = {redCar, greenCar, blueCar};

                    Timer timer = new Timer(50, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            for (Thread hilo : hilos) {

                                if (hilo.getState() == Thread.State.TERMINATED && !hilosTerminados.contains(hilo)) {
                                    //registramos el hilo terminado primero
                                    if (hiloTerminadoPrimero.get() == null) {
                                        System.out.println(hilo.getName());
                                        hiloTerminadoPrimero.set(hilo);
                                    }
                                    //si el hilo termino lo agrego al conjunto de hilos terminados
                                    hilosTerminados.add(hilo);

                                    //aumento el contador
                                    numeroHilosTerminados.incrementAndGet();
                                    System.out.println(numeroHilosTerminados.get());
                                    //Verificamos si se gano
                                    if (numeroHilosTerminados.get() == hilos.length) {
                                        //Si los 3 hilos terminaron
                                        System.out.println("3 hilos terminados");
                                        //Detemos el timer
                                        ((Timer) e.getSource()).stop();

                                        if (!(hiloTerminadoPrimero.get().getName().equals(selectedCar.name()))) {
                                            JOptionPane.showMessageDialog(null, "PERDISTE");
                                        } else {
                                            JOptionPane.showMessageDialog(null, "GANASTE");

                                        }

                                        enableNewRace();

                                    }

                                }

                            }

                        }

                    });
                    timer.start();

                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Seleccione un auto", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        };

        return actionListener;
    }

    private void disabledNewRace() {
        
        lblTrack.setIcon(iconTrackGif);
        btnStart.setEnabled(false);
        btnRedCar.setEnabled(false);
        btnGreenCar.setEnabled(false);
        btnBlueCar.setEnabled(false);
        
    }

    private void enableNewRace() {
        //colocamos la pista estatica
        lblTrack.setIcon(iconTrackJpg);
        //Habilitamos el boton start
        btnStart.setEnabled(true);
        //selectedCar
        selectedCar = null;
        //Posicionamos los autos
        lblRedCar.setLocation(LANE_RED_CAR, STARTING_POSITION);
        lblGreenCar.setLocation(LANE_GREEN_CAR, STARTING_POSITION);
        lblBlueCar.setLocation(LANE_BLUE_CAR, STARTING_POSITION);
        //Deseleccionar
        group.clearSelection();
        //activar toggle buttons
        btnRedCar.setEnabled(true);
        btnGreenCar.setEnabled(true);
        btnBlueCar.setEnabled(true);
    }

    private void initIcons() {
        iconFlag = new ImageIcon(getClass().getResource("/images/Bandera.png"));
        iconTrackJpg = new ImageIcon(getClass().getResource("/images/pista_estatica.jpg"));
        iconTrackGif = new ImageIcon(getClass().getResource("/images/pista.gif"));
        iconRedCar = new ImageIcon(getClass().getResource("/images/auto_rojo.png"));
        iconGreenCar = new ImageIcon(getClass().getResource("/images/auto_verde.png"));
        iconBlueCar = new ImageIcon(getClass().getResource("/images/auto_azul.png"));
        iconFirstPlace = new ImageIcon(getClass().getResource("/images/first_place.png"));
        iconSecondPlace = new ImageIcon(getClass().getResource("/images/second_place.png"));
        iconThirdPlace = new ImageIcon(getClass().getResource("/images/third_place.png"));
    }

}
