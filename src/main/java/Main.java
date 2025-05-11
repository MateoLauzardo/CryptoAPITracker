import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            CryptoTrackerUI ui = new CryptoTrackerUI();
            ui.createAndShowGUI();


        });

    }

}