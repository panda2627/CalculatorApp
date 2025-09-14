import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This program is a GUI calculator that can proform simple arithmetic operations
 * 
 */
public class GUICalculator implements ActionListener {

    private JFrame frame;
    private JTextField textField;
    private JLabel equationLabel;
    private JButton[] numberButtons = new JButton[10];
    private JButton[] functionButtons = new JButton[9];
    private JButton addButton, subButton, mulButton, divButton;
    private JButton decButton, equButton, delButton, clrButton, perButton;
    private JPanel panel;

    private Font myFont = new Font("Inter", Font.BOLD, 45);
    private Color colourBackground = new Color(44, 44, 44);
    private Color colourButton = new Color(66, 66, 66);
    private Color orange = new Color(221, 149, 14);

    private double num1 = 0, num2 = 0, result = 0;
    private char operator = '\0';

    /**
     * Constructor to set up the GUI and initialize components.
     */
    public GUICalculator() {
        setupFrameAndDisplay();
        setupButtons();
        setupPanel();

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Initializes the main JFrame, text field, and equation label.
     */
    private void setupFrameAndDisplay() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(330, 550);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(colourBackground);

        equationLabel = new JLabel();
        equationLabel.setBounds(10, 0, 300, 25);
        equationLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        equationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        equationLabel.setForeground(Color.LIGHT_GRAY);
        frame.add(equationLabel);

        textField = new JTextField("0");
        textField.setBounds(10, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setBackground(colourBackground);
        textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, colourButton));
        frame.add(textField);
    }

    /**
     * Initializes all number and function buttons.
     */
    private void setupButtons() {
        addButton = new RoundedButton("+");
        subButton = new RoundedButton("\u2212");
        mulButton = new RoundedButton("\u00D7");
        divButton = new RoundedButton("\u00F7");
        decButton = new RoundedButton(".");
        equButton = new RoundedButton("=");
        delButton = new RoundedButton("\u2190"); 
        clrButton = new RoundedButton("C");
        perButton = new RoundedButton("%");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = perButton;

        for (int i = 0; i < 9; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false);
            functionButtons[i].setBackground(colourButton);
            functionButtons[i].setForeground(orange);
        }

        // Special styling for buttons that dont fit exactly
        equButton.setBackground(orange);
        equButton.setForeground(Color.WHITE);
        delButton.setFont(new Font("Dialog", Font.BOLD, 35));
        perButton.setFont(new Font("Dialog", Font.BOLD, 35));

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new RoundedButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(colourButton);
            numberButtons[i].setForeground(Color.WHITE);
        }
    }

    /**
     * Initializes the main button panel and adds buttons to it.
     */
    private void setupPanel() {
        panel = new JPanel();
        panel.setBounds(10, 100, 300, 380);
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBackground(colourBackground);

        // Add buttons to the panel
        panel.add(clrButton);
        panel.add(delButton);
        panel.add(perButton);
        panel.add(divButton);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(mulButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(addButton);

        panel.add(new JLabel()); // Blank spot 
        panel.add(numberButtons[0]);
        panel.add(decButton);
        panel.add(equButton);
    }


    public static void main(String[] args) {
        GUICalculator calc = new GUICalculator();
    }

    /**
     * Handles actions performed when a button is clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handles number of button presses
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (textField.getText().equals("0")) {
                    textField.setText(String.valueOf(i));
                } else {
                    textField.setText(textField.getText().concat(String.valueOf(i)));
                }
                return; 
            }
        }

        // Decimal button click
        if (e.getSource() == decButton) {
            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText().concat("."));
            }
            return;
        }

        // Arithmetic buttons
        if (e.getSource() == addButton || e.getSource() == subButton || e.getSource() == mulButton || e.getSource() == divButton) {
            handleOperator(e.getSource());
            return;
        }

        // Equals button click
        if (e.getSource() == equButton) {
            handleEquals();
            return;
        }

        // Clear button click
        if (e.getSource() == clrButton) {
            textField.setText("0");
            equationLabel.setText("");
            num1 = 0;
            num2 = 0;
            operator = '\0';
            return;
        }

        // Delete button click
        if (e.getSource() == delButton) {
            String string = textField.getText();
            if (string.length() > 0) {
                string = string.substring(0, string.length() - 1);
                textField.setText(string.isEmpty() ? "0" : string);
            }
            return;
        }

        // Percentage button click
        if (e.getSource() == perButton) {
            if (!textField.getText().isEmpty()) {
                double tempValue = Double.parseDouble(textField.getText()) / 100.0;
                textField.setText(formatDisplayValue(tempValue));
            }
        }
    }

     // Logic for processing operator button presses 
    private void handleOperator(Object source) {
        String currentText = textField.getText();
        
        // If no number has been entered
        if (currentText.isEmpty() && operator == '\0') {
            return;
        }

        if (!currentText.isEmpty() && !currentText.equals("0")) {
            if (operator != '\0') {
                num2 = Double.parseDouble(currentText);
                if (calculateResult()) { 
                    num1 = result;
                } else {
                    return; // Stop processing on error 
                }
            } else {
                num1 = Double.parseDouble(currentText);
            }
        }

        // Set the new operator based on which button was pressed
        if (source == addButton) operator = '+';
        else if (source == subButton) operator = '-';
        else if (source == mulButton) operator = '*';
        else if (source == divButton) operator = '/';

        equationLabel.setText(formatDisplayValue(num1) + " " + operator);
        textField.setText("0");
    }


    // Processing the equals button press
     
    private void handleEquals() {
        String currentText = textField.getText();
        
        // Checking for operator and a second number
        if (operator == '\0' || currentText.isEmpty() || currentText.equals("0")) {
            return;
        }

        num2 = Double.parseDouble(currentText);
        equationLabel.setText("");

        if (calculateResult()) {
            textField.setText(formatDisplayValue(result));
            num1 = result; // The result becomes the new first number
        }
        
        operator = '\0'; // Reset operator for the next calculation
    }

    /**
     * Performs the calculation based on the stored number and operator
     * sets the main display to "Error" and returns false if division by zero occurs
     * returns true if calculation was successful otherwise false 
     */
    private boolean calculateResult() {
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    textField.setText("Error");
                    equationLabel.setText("");
                    operator = '\0';
                    return false;
                }
                result = num1 / num2;
                break;
        }
        return true;
    }

    // Formats a double to a String removing ".0" if its a whole number
    private String formatDisplayValue(double value) {
        if (value % 1 == 0) {
            return String.valueOf((long) value);
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * JButton class for the rounded rectangle edges
     */
    private class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false); 
            setFocusPainted(false);      
            setBorderPainted(false);     
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // On click it will go darker
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else {
                g2.setColor(getBackground());
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

            super.paintComponent(g);
            g2.dispose();
        }
    }
}