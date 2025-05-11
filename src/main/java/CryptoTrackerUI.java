import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class CryptoTrackerUI {

    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel statusLabel;
    private JLabel marketCapLabel;
    private JLabel volumeLabel;

    JFrame frame;
    JPanel mainPanel;



    public void FrameSetup(){

        frame = new JFrame("Crypto Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 500);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#f2f2f2"));

    }



    public void CreateCryptoIcon(){

        ImageIcon cryptoIcon = new ImageIcon("C:\\Users\\Mateo\\IdeaProjects\\CryptoTracker\\src\\cryptocurrency.png");
        Image scaledCrypto = cryptoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel cryptoImage = new JLabel(new ImageIcon(scaledCrypto));
        cryptoImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(cryptoImage);
    }






    public void createAndShowGUI() {

        FrameSetup();
        CreateCryptoIcon();

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setMaximumSize(new Dimension(280, 40));
        searchPanel.setBackground(Color.decode("#f2f2f2"));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton searchButton = new JButton("🔍");

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Crypto Info Labels
        nameLabel = new JLabel("Crypto Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        priceLabel = new JLabel("Price: Loading...");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("Status: Waiting...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(nameLabel);
        mainPanel.add(priceLabel);
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createVerticalGlue());

        // Info Panel (Market Cap & Volume)
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setMaximumSize(new Dimension(250, 100));
        infoPanel.setBackground(Color.decode("#f2f2f2"));

        marketCapLabel = new JLabel("<html>Market Cap:<br>Loading...</html>");
        marketCapLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        volumeLabel = new JLabel("<html>24h Volume:<br>Loading...</html>");
        volumeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel capPanel = new JPanel();
        capPanel.setBackground(Color.decode("#f2f2f2"));
        capPanel.add(marketCapLabel);

        JPanel volPanel = new JPanel();
        volPanel.setBackground(Color.decode("#f2f2f2"));
        volPanel.add(volumeLabel);

        infoPanel.add(capPanel);
        infoPanel.add(volPanel);
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

//-------------------------------------------------------------------------------------------------


        // Action: Search Button
        searchButton.addActionListener(e -> {
            String cryptoName = searchField.getText().toLowerCase();
            if (!cryptoName.isEmpty()) {
                JSONObject data = CryptoAPI.getCryptoData(cryptoName);
                if (data != null) {
                    double price = data.optDouble("usd", 0.0);
                    double marketCap = data.optDouble("usd_market_cap", 0.0);
                    double volume = data.optDouble("usd_24h_vol", 0.0);

                    SwingUtilities.invokeLater(() -> {
                        nameLabel.setText(Character.toUpperCase(cryptoName.charAt(0)) + cryptoName.substring(1));
                        priceLabel.setText(String.format("Price: $%,.2f", price));
                        marketCapLabel.setText(String.format("<html>Market Cap:<br>$%,.0f</html>", marketCap));
                        volumeLabel.setText(String.format("<html>24h Volume:<br>$%,.0f</html>", volume));
                        statusLabel.setText("Status: Updated");
                        statusLabel.setForeground(Color.GREEN);
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        statusLabel.setText("Status: Failed to fetch");
                        statusLabel.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(frame, "Error fetching data. Try another coin.", "Error", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }
        });

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }//end of method
}