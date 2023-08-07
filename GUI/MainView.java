package STALGCMMP.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainView {
    private JFrame mainFrame = new JFrame("2 Way PDA");

    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel timelinesPanel;
    private JPanel buttonsPanel;
    private JPanel inputPanel;
    private JScrollPane scrollPane;

    public JButton nextButton = new JButton("Next");
    public JButton autoButton = new JButton("Auto");
    public JButton resetButton = new JButton("Reset");

    public JButton inputButton = new JButton("Set Input");
    public JButton lambda = new JButton("λ");

    public JTextField inputArea = new JTextField();

    private JPanel inputLabelPanel = new JPanel(new GridLayout(2, 1));
    public JLabel inputLabel = new JLabel("Input: N/A");
    public JLabel statusLabel = new JLabel("Status: N/A");

    public ArrayList<LeafView> leafViews = new ArrayList<>();
    public MainView(){
        //Set up the main frame
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(500, 500));
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        
        nextButton.setFocusable(false);
        autoButton.setFocusable(false);
        resetButton.setFocusable(false);
        inputButton.setFocusable(false);
        lambda.setFocusable(false);

        //Panels to add to mainFrame
        timelinesPanel = new JPanel();
        buttonsPanel = new JPanel(new GridLayout(1, 3));
        inputPanel = new JPanel(new GridLayout(1, 4));
        
        //inputlabel and status label to center
        inputLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        //This is for the top left area with the current input and accepted status
        inputLabelPanel.add(inputLabel);
        inputLabelPanel.add(statusLabel);
        
        //For all the timelines
        timelinesPanel.setLayout(new BoxLayout(timelinesPanel, BoxLayout.Y_AXIS));
        
        Dimension minSize = new Dimension(10, 100);
        Dimension prefSize = new Dimension(10, 100);
        Dimension maxSize = new Dimension(10, 100);
        timelinesPanel.add(new Box.Filler(minSize, prefSize, maxSize));

        //Make the timelines scrollable
        scrollPane = new JScrollPane(timelinesPanel);

        //User input and buttons and the machine status
        inputPanel.add(inputLabelPanel);
        inputPanel.add(inputArea);
        inputPanel.add(inputButton);
        inputPanel.add(lambda);

        //Buttons at the bottom
        buttonsPanel.add(nextButton);
        buttonsPanel.add(autoButton);
        buttonsPanel.add(resetButton);

        //Add all the panels to the main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        //Add the main panel to the main frame
        mainFrame.add(mainPanel);

        mainFrame.setVisible(true);
    }

    public void updateTimelines(ArrayList<LeafView> leafViews){
        timelinesPanel.removeAll();
        this.leafViews = leafViews;
        for(int i = 0; i < leafViews.size(); i++){
            timelinesPanel.add(this.leafViews.get(i));
        }
        timelinesPanel.revalidate();
        timelinesPanel.repaint();
    }
}
