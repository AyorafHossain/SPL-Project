import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GradientPaint;
import java.awt.Component;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PrimeAndDivisorMode {
    private JPanel mainPanel;
    private JComboBox<String> operationComboBox;
    private JSpinner numberCountSpinner;
    private JTextField[] numberFields;
    private JTextField resultField;
    private JButton calculateButton;
    private JButton clearButton;


    //create Prime And Divisor Panel
    public void createPrimeAndDivisorPanel(JPanel buttonPanel) {
        buttonPanel.setLayout(new BorderLayout(10, 10));
        mainPanel = createStyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel settingsPanel = createStyledPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        settingsPanel.setBorder(createStyledTitledBorder("Prime and Divisor Settings"));

        settingsPanel.add(createStyledLabel("Operation:"));
        String[] operations = {"LCM", "GCD", "Prime Check"};
        operationComboBox = createStyledComboBox(operations);
        operationComboBox.addActionListener(e -> updatePanelLayout());
        settingsPanel.add(operationComboBox);


        settingsPanel.add(createStyledLabel("Number Count:"));
        numberCountSpinner = createStyledSpinner(2, 2, 4, 1);
        numberCountSpinner.addChangeListener(e -> updateNumberFields());
        settingsPanel.add(numberCountSpinner);

        mainPanel.add(settingsPanel, BorderLayout.NORTH);

        JPanel inputPanel = createStyledPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));//grid
        inputPanel.setBorder(createStyledTitledBorder("Input"));

        // create jtexfield
        numberFields = new JTextField[4];
        for (int i = 0; i < 4; i++)
        {
            JLabel label = createStyledLabel("Number " + (i + 1) + ":");
            numberFields[i] = createStyledTextField("", 10);
            inputPanel.add(label);
            inputPanel.add(numberFields[i]);
        }

        JLabel resultLabel = createStyledLabel("Result:");
        resultField = createStyledTextField("", 10);
        resultField.setEditable(false);
        resultField.setBackground(new Color(35, 35, 45));
        inputPanel.add(resultLabel);
        inputPanel.add(resultField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel actionPanel = createStyledPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        calculateButton = createStyledButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());
        actionPanel.add(calculateButton);


        clearButton = createStyledButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        actionPanel.add(clearButton);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        buttonPanel.add(mainPanel, BorderLayout.CENTER);
        updatePanelLayout();
    }


    // clear input and displayfield
    private void clearFields() {
        for (JTextField field : numberFields)
        {
            field.setText("");
        }
        resultField.setText("");
    }


    //crete style jpanel
    private JPanel createStyledPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(60, 60, 70, 180),
                        0, getHeight(), new Color(80, 80, 90, 180)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2d.dispose();
            }
        };
    }


    //create style jlavel
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return label;
    }


    //create style jbutton
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color startColor, endColor;
                if (getModel().isPressed())
                {
                    startColor = new Color(50, 110, 160);
                    endColor = new Color(70, 130, 180);
                }
                else if (getModel().isRollover())
                {
                    startColor = new Color(90, 150, 200);
                    endColor = new Color(70, 130, 180);
                }
                else
                {
                    startColor = new Color(70, 130, 180);
                    endColor = new Color(50, 110, 160);
                }
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                super.paintComponent(g);
                g2d.dispose();
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }


    //create style combobox
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(new Color(60, 60, 70));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.BOLD, 12));
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton() {
                    @Override
                    public void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setColor(new Color(90, 130, 180));
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                        g2d.setColor(Color.WHITE);
                        int[] xPoints = {getWidth()/2 - 3, getWidth()/2 + 3, getWidth()/2};
                        int[] yPoints = {getHeight()/2 - 2, getHeight()/2 - 2, getHeight()/2 + 2};
                        g2d.fillPolygon(xPoints, yPoints, 3);
                        g2d.dispose();
                    }
                };
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setFocusPainted(false);
                return button;
            }
            @Override
            protected ComboPopup createPopup() {
                return new BasicComboPopup(comboBox) {
                    @Override
                    protected void configureList() {
                        super.configureList();
                        list.setBackground(new Color(45, 45, 55));
                        list.setForeground(Color.WHITE);
                        list.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        list.setSelectionBackground(new Color(70, 130, 180));
                        list.setSelectionForeground(Color.WHITE);
                        list.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
                    }
                };
            }
        });
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(new Font("Segoe UI", Font.PLAIN, 12));
                if (isSelected) {
                    setBackground(new Color(70, 130, 180));
                    setForeground(Color.WHITE);
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(100, 150, 200), 1),
                            BorderFactory.createEmptyBorder(4, 8, 4, 8)
                    ));
                } else {
                    setBackground(new Color(45, 45, 55));
                    setForeground(Color.WHITE);
                    setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                }
                setOpaque(true);
                return this;
            }
        });
        return comboBox;
    }


    //create style Jspainer
    private JSpinner createStyledSpinner(int value, int min, int max, int step) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.getEditor().getComponent(0).setBackground(new Color(60, 60, 70));
        spinner.getEditor().getComponent(0).setForeground(Color.WHITE);
        spinner.getEditor().getComponent(0).setFont(new Font("Segoe UI", Font.PLAIN, 12));
        Component[] components = spinner.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(new Color(70, 130, 180));
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)));
            }
        }
        spinner.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        return spinner;
    }


    //create style titleborder
    private javax.swing.border.TitledBorder createStyledTitledBorder(String title) {
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        return border;
    }


    //create style jtextfield
    private JTextField createStyledTextField(String text, int columns) {
        JTextField field = new JTextField(text, columns);
        field.setBackground(new Color(45, 45, 55));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setCaretColor(Color.WHITE);
        field.setSelectionColor(new Color(70, 130, 180));
        field.setSelectedTextColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    private void updatePanelLayout() {
        String selectedOperation = (String) operationComboBox.getSelectedItem();
        boolean isLcmOrGcd = "LCM".equals(selectedOperation) || "GCD".equals(selectedOperation);
        numberCountSpinner.setEnabled(isLcmOrGcd);

        //prime check
        if ("Prime Check".equals(selectedOperation))
        {
            numberFields[0].setEnabled(true);
            numberFields[1].setEnabled(false);
            numberFields[2].setEnabled(false);
            numberFields[3].setEnabled(false);
        }
        else
        {
            updateNumberFields();
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void updateNumberFields() {
        if (!"Prime Check".equals(operationComboBox.getSelectedItem()))
        {
            int count = (Integer) numberCountSpinner.getValue();
            numberFields[0].setEnabled(true);
            numberFields[1].setEnabled(true);
            numberFields[2].setEnabled(count >= 3);
            numberFields[3].setEnabled(count >= 4);
        }
    }


    //implements ActionListener
    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String operation = (String) operationComboBox.getSelectedItem();
                if ("Prime Check".equals(operation))
                {
                    int number = Integer.parseInt(numberFields[0].getText());
                    boolean isPrime = isPrimeNumber(number);
                    resultField.setText(isPrime ? "Prime" : "Not Prime");
                }
                else {
                    int count = (Integer) numberCountSpinner.getValue();
                    int[] numbers = new int[count];
                    for (int i = 0; i < count; i++)
                    {
                        numbers[i] = Integer.parseInt(numberFields[i].getText());
                    }
                    int result;
                    if ("LCM".equals(operation))
                    {
                        result = calculateLCM(numbers);
                    }
                    else
                    {
                        result = calculateGCD(numbers);
                    }
                    resultField.setText(String.valueOf(result));
                }
            } catch (NumberFormatException ex) {
                resultField.setText("Invalid input");
            }
        }
    }


   //check whether a number is prime or not
    private boolean isPrimeNumber(int number) {
        if (number <= 1)
        {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++)
        {
            if (number % i == 0)
            {
                return false;
            }
        }
        return true;
    }

    //determine Gcd
    private int gcd(int a, int b) {
        while (b != 0)
        {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


    //determine gcd if number is more than two
    private int calculateGCD(int[] numbers) {
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++)
        {
            result = gcd(result, numbers[i]);
        }
        return result;
    }

    //calculate lcm by Eucledian algorithm
    private int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    //find lcm more than 2 number
    private int calculateLCM(int[] numbers) {
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++)
        {
            result = lcm(result, numbers[i]);
        }
        return result;
    }
}