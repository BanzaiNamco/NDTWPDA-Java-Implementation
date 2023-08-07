package STALGCMMP.GUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeafView extends JPanel{
    public String currState;
    public String input;
    public ArrayList<String> stack;
    public int head;

    private JLabel currStateLabel;
    private Label inputLabel;
    private JLabel stackLabel;
    
    public LeafView(String currState, String input, ArrayList<String> stack, int head) {
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.setLayout(new GridLayout(3,1));
    
        this.currState = currState;
        this.input = input;
        this.stack = stack;
        this.head = head+3;

        currStateLabel = new JLabel("Q: " + currState);
        
        inputLabel = new Label();
        inputLabel.setText("Σ: " + input);

        if(head != input.length())
            inputLabel.highlightRegion(this.head, this.head + 1);

        stackLabel = new JLabel("Stack:" + stack);
        this.add(currStateLabel);
        this.add(inputLabel);
        this.add(stackLabel);
    }
   
}

class Label extends JLabel
    {
        private static final long serialVersionUID = 1L;
        private int start;
        private int end;

        @Override
        public void paint(Graphics g)
        {
            FontMetrics fontMetrics = g.getFontMetrics();

            String startString = getText().substring(0, start);
            String text = getText().substring(start, end);

            int startX = fontMetrics.stringWidth(startString);
            int startY = 0;

            int length = fontMetrics.stringWidth(text);
            int height = this.getHeight();

            g.setColor(Color.green);
            g.fillRect(startX, startY, length, height);

            super.paint(g);
        }

        public void highlightRegion(int start, int end)
        {
            this.start = start;
            this.end = end;
        }
    }
