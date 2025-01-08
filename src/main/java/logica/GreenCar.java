
package logica;

import gui.LocationListener;
import gui.Race;
import gui.SelectableCars;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;

public class GreenCar extends Thread{

    private List<LocationListener> listeners = new ArrayList<>();
    private int positionY;
    private JLabel lblGreenCar;
    
    public GreenCar(JLabel lblGreenCar) {
        super(SelectableCars.RED_CAR.name());
        this.lblGreenCar = lblGreenCar;
        positionY = Race.STARTING_POSITION;
    }

    public void addLocationListener(LocationListener locationListener){
        listeners.add(locationListener);
    }

    public void launchEvent(){
        for(LocationListener l: listeners){
            l.onLocationEvent(positionY);
        }
    }

    @Override
    public void run() {
        int numeroRandom = numeroRandom();
        
        for (int i = Race.STARTING_POSITION; i < 0; i--) {
            lblGreenCar.setLocation(Race.LANE_GREEN_CAR, i);
            positionY--;
            launchEvent();
            
            if(i == Race.STARTING_POSITION/2){
                numeroRandom = numeroRandom();
            }
            
            try {
                sleep(numeroRandom);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
        }
    }

    private int numeroRandom() {
        Random random = new Random();
        int numeroRandom = random.nextInt(30-10+1)+10;
        return numeroRandom;
    }
    
    
    
    
    
}
