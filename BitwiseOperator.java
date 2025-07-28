import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GradientPaint;
import java.awt.Component;

public class BitwiseOperator {
    private JPanel mainPanel;
    private JComboBox<String> operationComboBox;
    private JTextField operand1Field, operand2Field, shiftAmountField;
    private JTextArea resultArea;
    private JButton calculateButton, clearButton;
    private JLabel operand2Label, shiftLabel;

    public void createBitwiseOperatorPanel(JPanel buttonPanel) {
        buttonPanel.setLayout(new BorderLayout(10, 10));
        mainPanel = createStyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Settings Panel
        JPanel settingsPanel = createStyledPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        settingsPanel.setBorder(createStyledTitledBorder("Bitwise Operation"));
        settingsPanel.add(createStyledLabel("Operation:"));
        String[] operations = {"Bitwise AND (&)", "Bitwise OR (|)", "Bitwise XOR (^)", "Bitwise NOT (~)", "Left Shift (<<)", "Right Shift (>>)"};
        operationComboBox = createStyledComboBox(operations);
        operationComboBox.addActionListener(e -> updateOperationFields());
        settingsPanel.add(operationComboBox);
        mainPanel.add(settingsPanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = createStyledPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(createStyledTitledBorder("Input Values"));
        inputPanel.add(createStyledLabel("First Number:"));
        operand1Field = createStyledTextField("0", 15);
        inputPanel.add(operand1Field);
        operand2Label = createStyledLabel("Second Number:");
        operand2Field = createStyledTextField("0", 15);
        inputPanel.add(operand2Label);
        inputPanel.add(operand2Field);
        shiftLabel = createStyledLabel("Shift Amount:");
        shiftAmountField = createStyledTextField("1", 15);
        inputPanel.add(shiftLabel);
        inputPanel.add(shiftAmountField);

        JPanel buttonPanelInner = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanelInner.setOpaque(false);
        calculateButton = createStyledButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());
        buttonPanelInner.add(calculateButton);
        clearButton = createStyledButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        buttonPanelInner.add(clearButton);
        inputPanel.add(new JLabel());
        inputPanel.add(buttonPanelInner);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Result Panel
        JPanel resultPanel = createStyledPanel();
        resultPanel.setLayout(new BorderLayout(10, 10));
        resultPanel.setBorder(createStyledTitledBorder("Result"));
        resultArea = createStyledTextArea(8, 50);
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(35, 35, 45));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Select operation and enter values to see the bitwise calculation result.");
        JScrollPane resultScrollPane = createStyledScrollPane(resultArea);
        resultScrollPane.setPreferredSize(new Dimension(550, 150));
        JPanel resultCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultCenterPanel.setOpaque(false);
        resultCenterPanel.add(resultScrollPane);
        resultPanel.add(resultCenterPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);
        buttonPanel.add(mainPanel, BorderLayout.CENTER);
        updateOperationFields();
    }

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

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return label;
    }

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
                        g2d.setColor(new Color(70, 130, 180));
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
                if (isSelected)
                {
                    setBackground(new Color(70, 130, 180));
                    setForeground(Color.WHITE);
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(100, 150, 200), 1),
                            BorderFactory.createEmptyBorder(4, 8, 4, 8)
                    ));
                }
                else
                {
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

    private javax.swing.border.TitledBorder createStyledTitledBorder(String title) {
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        return border;
    }

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

    private JTextArea createStyledTextArea(int rows, int columns) {
        JTextArea textArea = new JTextArea(rows, columns);
        textArea.setBackground(new Color(45, 45, 55));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setCaretColor(Color.WHITE);
        textArea.setSelectionColor(new Color(70, 130, 180));
        textArea.setSelectedTextColor(Color.WHITE);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return textArea;
    }

    private JScrollPane createStyledScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        return scrollPane;
    }

    private void updateOperationFields() {
        String selectedOperation = (String) operationComboBox.getSelectedItem();
        boolean isShiftOperation = selectedOperation.contains("Shift");
        boolean isNotOperation = selectedOperation.contains("NOT");

        operand2Label.setVisible(!isShiftOperation && !isNotOperation);
        operand2Field.setVisible(!isShiftOperation && !isNotOperation);

        shiftLabel.setVisible(isShiftOperation);
        shiftAmountField.setVisible(isShiftOperation);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void clearFields() {
        operand1Field.setText("0");
        operand2Field.setText("0");
        shiftAmountField.setText("1");
        resultArea.setText("Select operation and enter values to see the bitwise calculation result.");
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedOperation = (String) operationComboBox.getSelectedItem();
                performBitwiseOperation(selectedOperation);
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Please enter valid integer numbers.");
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    private void performBitwiseOperation(String operation) {
        long operand1 = Long.parseLong(operand1Field.getText().trim());
        long operand2 = 0;
        int shiftAmount = 0;

        if (operation.contains("Shift"))
        {
            shiftAmount = Integer.parseInt(shiftAmountField.getText().trim());
            if (shiftAmount < 0 || shiftAmount > 63)
            {
                throw new IllegalArgumentException("Shift amount must be between 0 and 63");
            }
        }
        else if (!operation.contains("NOT"))
        {
            operand2 = Long.parseLong(operand2Field.getText().trim());
        }

        long result = 0;
        StringBuilder resultText = new StringBuilder();

        switch (operation)
        {
            case "Bitwise AND (&)":
                result = operand1 & operand2;
                resultText.append(operand1).append(" & ").append(operand2).append(" = ");
                break;
            case "Bitwise OR (|)":
                result = operand1 | operand2;
                resultText.append(operand1).append(" | ").append(operand2).append(" = ");
                break;
            case "Bitwise XOR (^)":
                result = operand1 ^ operand2;
                resultText.append(operand1).append(" ^ ").append(operand2).append(" = ");
                break;
            case "Bitwise NOT (~)":
                result = ~operand1;
                resultText.append("~").append(operand1).append(" = ");
                break;
            case "Left Shift (<<)":
                result = operand1 << shiftAmount;
                resultText.append(operand1).append(" << ").append(shiftAmount).append(" = ");
                break;
            case "Right Shift (>>)":
                result = operand1 >> shiftAmount;
                resultText.append(operand1).append(" >> ").append(shiftAmount).append(" = ");
                break;
        }

        resultText.append("\n\nDecimal: ").append(result);
        resultText.append("\nBinary:  ").append(formatBinary(result));
        resultArea.setText(resultText.toString());
    }

    private String formatBinary(long value) {
        String binary = Long.toBinaryString(value);
        while (binary.length() < 16)
        {
            binary = "0" + binary;
        }
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < binary.length(); i++)
        {
            if (i > 0 && i % 4 == 0)
            {
                formatted.append(" ");
            }
            formatted.append(binary.charAt(i));
        }
        return formatted.toString();
    }
}