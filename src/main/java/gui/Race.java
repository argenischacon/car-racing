
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import logica.BlueCar;
import logica.GreenCar;
import logica.RedCar;


public class Race extends JFrame{
    
    private SelectableCars selectedCar = null;
    public final static int STARTING_POSITION = 680, CAR_WIDTH = 50, CAR_HEIGHT = 100,
          LANE_RED_CAR = 50, LANE_GREEN_CAR = 190, LANE_BLUE_CAR = 330;  
    private JLabel lblRedCar, lblGreenCar, lblBlueCar;
    private RedCar redCar;
    private GreenCar greenCar;
    private BlueCar blueCar;
    
    
    public Race(){
        initComponents();
        redCar = new RedCar(lblRedCar);
        blueCar = new BlueCar(lblBlueCar);
        greenCar = new GreenCar(lblGreenCar);
        initThreads();
        
        //establecemos la configuracion de la ventana
        pack();
        setSize(600, 900);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        JButton btnStart = new JButton("Start");
        btnStart.setPreferredSize(new Dimension(150, 150));
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
        JToggleButton btnRedCar = new JToggleButton();
        btnRedCar.setPreferredSize(new Dimension(150, 150));
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
        JToggleButton btnGreenCar = new JToggleButton();
        btnGreenCar.setPreferredSize(new Dimension(150, 150));
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
        JToggleButton btnBlueCar = new JToggleButton();
        btnBlueCar.setPreferredSize(new Dimension(150, 150));
        btnBlueCar.addActionListener(getActionListener(SelectableCars.BLUE_CAR));
        panelPrincipal.add(btnBlueCar, gbc);
        
        //Grupo de botones (ButtonGroup)
        ButtonGroup group = new ButtonGroup();
        group.add(btnRedCar);
        group.add(btnGreenCar);
        group.add(btnBlueCar);
        
        //Label para la pista
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 1;//Ocupa espacio adicional en x
        gbc.weighty = 1;//Ocupa espacio adicional en y
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel lblTrack = new JLabel();
        lblTrack.setLayout(null);
        lblTrack.setOpaque(true);
        lblTrack.setBackground(Color.BLACK);
        
        //Labels para los autos (JLabel)
        lblRedCar = new JLabel();
        lblRedCar.setBounds(LANE_RED_CAR, STARTING_POSITION, CAR_WIDTH, CAR_HEIGHT);
        lblRedCar.setOpaque(true);
        lblRedCar.setBackground(Color.red);
        lblTrack.add(lblRedCar);
        
        lblGreenCar = new JLabel();
        lblGreenCar.setBounds(LANE_GREEN_CAR, STARTING_POSITION, CAR_WIDTH, CAR_HEIGHT);
        lblGreenCar.setOpaque(true);
        lblGreenCar.setBackground(Color.GREEN);
        lblTrack.add(lblGreenCar);
        
        lblBlueCar = new JLabel();
        lblBlueCar.setBounds(LANE_BLUE_CAR, STARTING_POSITION, CAR_WIDTH, CAR_HEIGHT);
        lblBlueCar.setOpaque(true);
        lblBlueCar.setBackground(Color.BLUE);
        lblTrack.add(lblBlueCar);
        
        panelPrincipal.add(lblTrack, gbc);
        
        //Añadimos el panel principal a la ventana
        this.add(panelPrincipal);
    }
    
    private ActionListener getActionListener(SelectableCars car){
        
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
        redCar.start();
        greenCar.start();
        blueCar.start();
    }
   
}
