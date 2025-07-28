import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
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

public class ConversionMode {
    private JPanel mainPanel;
    private JComboBox<String> fromBaseComboBox;
    private JComboBox<String> toBaseComboBox;
    private JTextField inputField;
    private JTextField resultField;
    private JButton convertButton;

    public void createConversionPanel(JPanel buttonPanel) {
        buttonPanel.setLayout(new BorderLayout(10, 10));
        mainPanel = createStyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel settingsPanel = createStyledPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        settingsPanel.setBorder(createStyledTitledBorder("Number System Conversion"));

        settingsPanel.add(createStyledLabel("From Base:"));
        String[] bases = {"Binary", "Octal", "Decimal", "Hexadecimal"};
        fromBaseComboBox = createStyledComboBox(bases);
        settingsPanel.add(fromBaseComboBox);

        settingsPanel.add(createStyledLabel("To Base:"));
        toBaseComboBox = createStyledComboBox(bases);
        toBaseComboBox.setSelectedIndex(2);
        settingsPanel.add(toBaseComboBox);

        mainPanel.add(settingsPanel, BorderLayout.NORTH);

        JPanel inputPanel = createStyledPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(createStyledTitledBorder("Input and Result"));

        JLabel inputLabel = createStyledLabel("Input Value:");
        inputField = createStyledTextField("", 20);
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        JLabel resultLabel = createStyledLabel("Result:");
        resultField = createStyledTextField("", 20);
        resultField.setEditable(false);
        resultField.setBackground(new Color(35, 35, 45));
        inputPanel.add(resultLabel);
        inputPanel.add(resultField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel actionPanel = createStyledPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        convertButton = createStyledButton("Convert");
        convertButton.addActionListener(new ConvertButtonListener());
        actionPanel.add(convertButton);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        buttonPanel.add(mainPanel, BorderLayout.CENTER);
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

    private class ConvertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String input = inputField.getText().trim();
                if (input.isEmpty())
                {
                    resultField.setText("Please enter a value");
                    return;
                }

                String fromBase = (String) fromBaseComboBox.getSelectedItem();
                String toBase = (String) toBaseComboBox.getSelectedItem();

                double decimalValue = convertToDecimal(input, fromBase);
                String result = convertFromDecimal(decimalValue, toBase);

                resultField.setText(result);
            } catch (NumberFormatException ex) {
                resultField.setText("Invalid input for selected base");
            } catch (Exception ex) {
                resultField.setText("Error: " + ex.getMessage());
            }
        }
    }

    private double convertToDecimal(String input, String baseStr) {
        int base;
        switch (baseStr) {
            case "Binary":
                base = 2;
                validateInput(input, base);
                break;
            case "Octal":
                base = 8;
                validateInput(input, base);
                break;
            case "Decimal":
                base = 10;
                validateInput(input, base);
                return Double.parseDouble(input);
            case "Hexadecimal":
                base = 16;
                validateInput(input, base);
                break;
            default:
                throw new IllegalArgumentException("Invalid base");
        }

        // Split input into integer and fractional parts
        boolean isNegative = input.startsWith("-");
        if (isNegative)
        {
            input = input.substring(1);
        }


        String[] parts = input.toUpperCase().split("\\.");
        String integerPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : "";

        // Convert integer part
        double result = 0;
        for (int i = 0; i < integerPart.length(); i++)
        {
            char c = integerPart.charAt(i);
            int digit = getDigitValue(c);
            if (digit >= base)
            {
                throw new NumberFormatException("Invalid digit for base " + base);
            }
            result = result * base + digit;
        }

        // Convert fractional part
        if (!fractionalPart.isEmpty())
        {
            double fractionalValue = 0;
            double basePower = 1.0 / base;
            for (int i = 0; i < fractionalPart.length(); i++)
            {
                char c = fractionalPart.charAt(i);
                int digit = getDigitValue(c);
                if (digit >= base)
                {
                    throw new NumberFormatException("Invalid digit for base " + base);
                }
                fractionalValue += digit * basePower;
                basePower /= base;
            }
            result += fractionalValue;
        }

        return isNegative ? -result : result;
    }


    private String convertFromDecimal(double decimal, String baseStr) {
        int base;
        switch (baseStr) {
            case "Binary":
                base = 2;
                break;
            case "Octal":
                base = 8;
                break;
            case "Decimal":
                base = 10;
                return String.valueOf(decimal);
            case "Hexadecimal":
                base = 16;
                break;
            default:
                throw new IllegalArgumentException("Invalid base");
        }


        boolean isNegative = decimal < 0;
        if (isNegative) {
            decimal = -decimal;
        }


        if (decimal == 0)
        {
            return "0";
        }

        // Split into integer and fractional parts
        long integerPart = (long) decimal;
        double fractionalPart = decimal - integerPart;

        // Convert integer part
        StringBuilder result = new StringBuilder();
        if (integerPart == 0)
        {
            result.append("0");
        }
        else
        {
            long value = integerPart;
            while (value > 0)
            {
                int remainder = (int) (value % base);
                result.insert(0, getDigitChar(remainder));
                value /= base;
            }
        }

        // Convert fractional part
        if (fractionalPart > 0)
        {
            result.append(".");
            int maxPrecision = 10;
            int precision = 0;
            while (fractionalPart > 0 && precision < maxPrecision)
            {
                fractionalPart *= base;
                int digit = (int) fractionalPart;
                result.append(getDigitChar(digit));
                fractionalPart -= digit;
                precision++;
            }
        }



        return isNegative ? "-" + result.toString() : result.toString();

    }


    private int getDigitValue(char c) {
        if (Character.isDigit(c))
        {
            return c - '0';
        }
        else
        {
            return c - 'A' + 10;
        }
    }

    private char getDigitChar(int digit) {
        if (digit < 10)
        {
            return (char) ('0' + digit);
        }
        else
        {
            return (char) ('A' + digit - 10);
        }
    }

    private void validateInput(String input, int base)
    {
        if (input.contains("."))
        {
            String[] parts = input.split("\\.");
            if (parts.length > 2)
            {
                throw new NumberFormatException("Invalid decimal format");
            }
            validatePart(parts[0], base);
            if (parts.length > 1)
            {
                validatePart(parts[1], base);
            }
        }
        else
        {
            validatePart(input, base);
        }
    }

    private void validatePart(String part, int base) {
        part = part.toUpperCase();
        for (char c : part.toCharArray())
        {
            int digit = getDigitValue(c);
            if (digit >= base)
            {
                throw new NumberFormatException("Invalid digit '" + c + "' for base " + base);
            }
        }
    }
}
