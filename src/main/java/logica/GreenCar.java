package logica;

import gui.Race;
import gui.SelectableCars;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GreenCar extends Thread {

    private int positionY;
    private JLabel lblGreenCar;

    public GreenCar(JLabel lblGreenCar) {
        super(SelectableCars.GREEN_CAR.name());
        this.lblGreenCar = lblGreenCar;
        positionY = Race.STARTING_POSITION;
    }

    @Override
    public void run() {
        int numeroRandom = numeroRandom();

        for (int i = Race.STARTING_POSITION; i > 0; i--) {
            final int currentPosition = i;
            SwingUtilities.invokeLater(() -> {
                lblGreenCar.setLocation(Race.LANE_GREEN_CAR, currentPosition);
            });
            positionY--;

            if (i == Race.STARTING_POSITION / 2) {
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
        int numeroRandom = random.nextInt(30 - 10 + 1) + 10;
        return numeroRandom;
    }

}
