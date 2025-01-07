
package gui;

import java.awt.GridBagLayout;
import javax.swing.JFrame;


public class Race extends JFrame{
    
    public Race(){
        //Establecemos el layout
        setLayout(new GridBagLayout());
        
        
        
        //establecemos la configuracion de la ventana
        pack();
        setSize(600, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
    }
    
    
}
