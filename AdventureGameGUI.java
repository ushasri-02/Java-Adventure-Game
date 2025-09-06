package AdventureGame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class AdventureGameGUI {
    private JFrame frame;
    private JTextArea storyArea;
    private JPanel buttonPanel;
    private JLabel imageLabel;
    private String playerName = "";
    private ArrayList<String> pocket = new ArrayList<>();
    private int step = 0;
    private JPanel imagePanel;
    private JPanel rightPanel;
    public AdventureGameGUI() {
        frame = new JFrame("Adventure Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new GridLayout(1, 2)); // Split frame into two equal halves
        // Left panel for image
        imagePanel = new JPanel();
        imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.addComponentListener(new java.awt.event.ComponentAdapter() {
        public void componentResized(java.awt.event.ComponentEvent evt) {
            resizeImage(); // Rescale image when panel size changes
        }
    });
        // Right panel for text + buttons
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        storyArea = new JTextArea();
        storyArea.setEditable(false);
        storyArea.setFont(new Font("Serif", Font.PLAIN, 25));
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(storyArea);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        // Add panels to frame
        frame.add(imagePanel);
        frame.add(rightPanel);
        // Start game
        askName();
        frame.setVisible(true);
    }
    private void showStory(String text, String imageName, String... options) {
    storyArea.setText(text);
    // Load the image
    if (imageName != null && !imageName.isEmpty()) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("C:\\usha\\java_assig\\IMP\\AdventureGame\\images\\" + imageName));
            imageLabel.setIcon(icon); // Set original icon
            resizeImage(); // Scale to panel size
        } catch (Exception e) {
            System.out.println("Image not found: " + imageName);
            imageLabel.setIcon(null);
        }
    } else {
        imageLabel.setIcon(null);
    }
    // Buttons
    buttonPanel.removeAll();
    for (String option : options) {
        JButton btn = new JButton(option);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setPreferredSize(new Dimension(150, 50));
        btn.addActionListener(e -> handleChoice(option));
        buttonPanel.add(btn);
    }
    buttonPanel.revalidate();
    buttonPanel.repaint();
    }
    // Resize image method
    private void resizeImage() {
        if (imageLabel.getIcon() != null) {
            ImageIcon icon = (ImageIcon) imageLabel.getIcon();
            Image img = icon.getImage().getScaledInstance(
                    imagePanel.getWidth(), imagePanel.getHeight(), Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        }
}
    private void askName() {
        playerName = JOptionPane.showInputDialog(frame, "Enter your name:");
        if (playerName == null || playerName.isEmpty()) playerName = "Player";

        showStory(playerName + ", welcome to the Adventure Game!\nYou are entering into a jungle. Choose a direction:",
                "jungle.jpeg", "North", "South", "East", "West");
        step = 1;
    }
    private void handleChoice(String choice) {
        if (choice.equals("Quit")) {
            JOptionPane.showMessageDialog(frame, "Thanks for playing!");
            System.exit(0);
        }
        switch (step) {
            // Step 1: choose direction
            case 1:
                switch (choice) {
                    case "North":
                        showStory("You are moving North...\nYou just witnessed a dead man!\nDo you still want to enter jungle?",
                                "deadman.png", "Yes", "No");
                        step = 2;
                        break;
                    case "South":
                        showStory("You are moving South...\nYou reached the main road.\nYou go home safely without treasure.",
                                "road.jpeg", "Restart", "Quit");
                        step = 99;
                        break;
                    case "East":
                        showStory("You are moving East...\nYou arrive at a wide river. A boat is waiting on the bank.\nBut a huge crocodile is sleeping nearby, just a few feet from the path.\nDo you want to RUN or MOVE SLOW?",
                                "crocodile.jpeg", "Run", "Slow");
                        step = 10;
                        break;
                    case "West":
                        showStory("You are moving West...\nYou just witnessed a dead man!\nDo you still want to enter jungle?",
                                "deadman.png", "Yes", "No");
                        step = 20;
                        break;
                }
                break;
            // North Path
            case 2:
                if (choice.equals("Yes")) {
                    showStory("Beside the dead man, you see a Gun. Pick it up?", "gun.png", "Yes", "No");
                    step = 3;
                } else {
                    showStory("You quit. Game Over.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            case 3:
                if (choice.equals("Yes")) pocket.add("Gun");
                showStory("A wolf appears! Attack it?", "wolf.jpeg", "Yes", "No");
                step = 4;
                break;
            case 4:
                if (choice.equals("Yes")) {
                    if (pocket.contains("Gun")) {
                        showStory("You shoot the wolf. You're safe!\n You arrive at a wide river. A boat is waiting on the bank.\n But a huge crocodile is sleeping nearby, just a few feet from the path.\nDo you RUN or go SLOW?",
                                "crocodile.jpeg", "Run", "Slow");
                        step = 5;
                    } else {
                        showStory("You had no weapon. The wolf killed you.\nGame Over.",
                                "gameover.jpeg", "Restart");
                        step = 99;
                    }
                } else {
                    showStory(" you Hesitated! The wolf attacks you.\n You lost the game.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            case 5:
                if (choice.equals("Run")) {
                    showStory("The crocodile wakes up and eats you.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                } else if (choice.equals("Slow")) {
                    showStory("You move slowly, the crocodile stays asleep.\nIn the boat, you see Food and Keys.\nPick one:",
                            "boat.jpeg", "Food", "Keys");
                    step = 6;
                } else {
                    showStory("You hesitated too long! The crocodile attacks.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            case 6:
                if (choice.equals("Food")) pocket.add("Food");
                else pocket.add("Keys");
                showStory("You found a treasure box.\n Do you want to Open it?", "treasure.jpeg", "Yes", "No");
                step = 7;
                break;
            case 7:
                if (choice.equals("Yes")) {
                    if (pocket.contains("Keys")) {
                        showStory("You used the keys and opened the treasure! You win!", "treasure_opened.jpeg", "Restart", "Quit");
                    } else {
                        showStory("You don’t have the keys. You lost.\nGame Over.", "gameover.jpeg", "Restart", "Quit");
                    }
                } else {
                    showStory("You ignored the treasure. Game Over.", "gameover.jpeg", "Restart", "Quit");
                }
                step = 99;
                break;
            // East Path
            case 10:
                if (choice.equals("Run")) {
                    showStory("The crocodile wakes up and attacks you.\n Game Over.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                } else if (choice.equals("Slow")) {
                    showStory("You move slowly, the crocodile stays asleep.\nIn the boat, you see Food and Keys.\nPick one:",
 "boat.jpeg", "Food", "Keys");
                    step = 11;
                } else {
                    showStory("You hesitated too long! The crocodile attacks.\nGame Over.","gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            case 11:
                if (choice.equals("Food")) pocket.add("Food");
                else pocket.add("Keys");
                showStory("After leaving the boat, you discover a dead man in the jungle.\nDo you  still want to go?",
 "deadman.png", "Yes", "No");
                step = 12;
                break;
            case 12:
                if (choice.equals("Yes")) {
                    showStory("Beside the dead man, you see a Gun. Pick it up?", "gun.png", "Yes", "No");
                    step = 13;
                } else {
                    showStory("You quit. Game Over.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            case 13:
                if (choice.equals("Yes")) pocket.add("Gun");
                showStory("A wolf appears! Attack it?", "wolf.jpeg", "Yes", "No");
                step = 14;
                break;
            case 14:
                if (choice.equals("Yes")) {
                    if (pocket.contains("Gun")) {
                        showStory("You shoot the wolf. You're safe!\nYou found a treasure box!\nDo you want to open it?",
 "treasure.jpeg", "Yes", "No");
                        step = 7;
                    } else {
                        showStory("You had no weapon. The wolf killed you.\nGame Over.",
 "gameover.jpeg", "Restart", "Quit");
                        step = 99;
                    }
                } else {
                    showStory("You hesitated. The wolf attacks you.\nYou lost the game.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            // West Path (Dead Man → Gun → Crocodile → Boat → Wolf → Treasure)
            case 20:
                if (choice.equals("Yes")) {
                    showStory("Beside the dead man, you see a Gun. Pick it up?", "gun.png", "Yes", "No");
                    step = 21;
                } else {
                    showStory("You quit. Game Over.", "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            case 21:
                if (choice.equals("Yes")) pocket.add("Gun");
                showStory("You arrive at a wide river. A boat is waiting on the bank.\n But a huge crocodile is sleeping nearby, just a few feet from the path.\nDo you RUN or go SLOW?",
 "crocodile.jpeg", "Run", "Slow");
                step = 22;
                break;
            case 22:
                if (choice.equals("Run")) {
                    showStory("The crocodile wakes up and attacks you.\nGame Over.",
 "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                } else if (choice.equals("Slow")) {
                    showStory("You carefully move past the crocodile and find a boat.\nIn the boat, you see Food and Keys.\nPick one:",
 "boat.jpeg", "Food", "Keys");
                    step = 23;
                } else {
                    showStory("You hesitated too long! The crocodile attacks.\nGame Over.",
 "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            case 23:
                if (choice.equals("Food")) pocket.add("Food");
                else pocket.add("Keys");
                showStory("After leaving the boat, a wolf appears! Attack it?",
 "wolf.jpeg", "Yes", "No");
                step = 24;
                break;
            case 24:
                if (choice.equals("Yes")) {
                    if (pocket.contains("Gun")) {
                        showStory("You shoot the wolf. You're safe!\nYou found a treasure box!\nDo you want to open it?",
 "treasure.jpeg", "Yes", "No");
                        step = 7;
                    } else {
                        showStory("You had no weapon. The wolf killed you.\nGame Over.",
"gameover.jpeg", "Restart", "Quit");
                        step = 99;
                    }
                } else {
                    showStory("You hesitated. The wolf attacks you.\nYou lost the game.",
 "gameover.jpeg", "Restart", "Quit");
                    step = 99;
                }
                break;
            // Restart
            case 99:
                pocket.clear();
                askName();
                break;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdventureGameGUI::new);
    }
}















