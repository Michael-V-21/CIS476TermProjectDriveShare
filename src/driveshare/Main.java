package driveshare;

import javax.swing.SwingUtilities;
import driveshare.ui.LoginFrame;

// Main entry point DriveShare

public class Main
{

    public static void main(String[] args)
    {
        // Start Swing application
        SwingUtilities.invokeLater(() -> {

            // Open the login screen
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}