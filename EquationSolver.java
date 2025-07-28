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


public class EquationSolver {
    private JPanel mainPanel;
    private JComboBox<String> equationTypeComboBox;
    private JTextField[] coefficientFields;
    private JTextArea resultArea;
    private JButton solveButton, clearButton;
    private JLabel[] coefficientLabels;

    public void createEquationSolverPanel(JPanel buttonPanel) {
        buttonPanel.setLayout(new BorderLayout(10, 10));
        mainPanel = createStyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel settingsPanel = createStyledPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        settingsPanel.setBorder(createStyledTitledBorder("Equation Type"));
        settingsPanel.add(createStyledLabel("Select Equation:"));
        String[] equationTypes = {"Quadratic (ax² + bx + c = 0)", "Cubic (ax³ + bx² + cx + d = 0)"};
        equationTypeComboBox = createStyledComboBox(equationTypes);
        equationTypeComboBox.addActionListener(e -> updateCoefficientFields());
        settingsPanel.add(equationTypeComboBox);
        mainPanel.add(settingsPanel, BorderLayout.NORTH);


        JPanel inputPanel = createStyledPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(createStyledTitledBorder("Coefficients"));
        coefficientLabels = new JLabel[4];
        coefficientFields = new JTextField[4];

        coefficientLabels[0] = createStyledLabel("Coefficient a:");
        coefficientFields[0] = createStyledTextField("1", 10);
        inputPanel.add(coefficientLabels[0]);
        inputPanel.add(coefficientFields[0]);

        coefficientLabels[1] = createStyledLabel("Coefficient b:");
        coefficientFields[1] = createStyledTextField("0", 10);
        inputPanel.add(coefficientLabels[1]);
        inputPanel.add(coefficientFields[1]);

        coefficientLabels[2] = createStyledLabel("Coefficient c:");
        coefficientFields[2] = createStyledTextField("0", 10);
        inputPanel.add(coefficientLabels[2]);
        inputPanel.add(coefficientFields[2]);

        coefficientLabels[3] = createStyledLabel("Coefficient d:");
        coefficientFields[3] = createStyledTextField("0", 10);
        inputPanel.add(coefficientLabels[3]);
        inputPanel.add(coefficientFields[3]);

        JPanel buttonPanelInner = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanelInner.setOpaque(false);
        solveButton = createStyledButton("Solve Equation");
        solveButton.addActionListener(new SolveButtonListener());
        buttonPanelInner.add(solveButton);

        clearButton = createStyledButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        buttonPanelInner.add(clearButton);

        inputPanel.add(new JLabel());
        inputPanel.add(buttonPanelInner);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel resultPanel = createStyledPanel();
        resultPanel.setLayout(new BorderLayout(10, 10));
        resultPanel.setBorder(createStyledTitledBorder("Solution"));
        resultArea = createStyledTextArea(12, 50);
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(35, 35, 45));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Enter coefficients and click 'Solve Equation' to see the solution.");
        JScrollPane resultScrollPane = createStyledScrollPane(resultArea);
        resultScrollPane.setPreferredSize(new Dimension(550, 200));
        JPanel resultCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultCenterPanel.setOpaque(false);
        resultCenterPanel.add(resultScrollPane);
        resultPanel.add(resultCenterPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);
        buttonPanel.add(mainPanel, BorderLayout.CENTER);
        updateCoefficientFields();
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
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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



    private void updateCoefficientFields() {
        String selectedType = (String) equationTypeComboBox.getSelectedItem();
        boolean isCubic = selectedType.contains("Cubic");

        coefficientLabels[3].setVisible(isCubic);
        coefficientFields[3].setVisible(isCubic);

        if (isCubic)
        {
            coefficientLabels[0].setText("Coefficient a (x³):");
            coefficientLabels[1].setText("Coefficient b (x²):");
            coefficientLabels[2].setText("Coefficient c (x):");
            coefficientLabels[3].setText("Coefficient d (constant):");
        }
        else
        {
            coefficientLabels[0].setText("Coefficient a (x²):");
            coefficientLabels[1].setText("Coefficient b (x):");
            coefficientLabels[2].setText("Coefficient c (constant):");
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }



    private void clearFields() {
        coefficientFields[0].setText("1");
        coefficientFields[1].setText("0");
        coefficientFields[2].setText("0");
        coefficientFields[3].setText("0");
        resultArea.setText("Enter coefficients and click 'Solve Equation' to see the solution.");
    }

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedType = (String) equationTypeComboBox.getSelectedItem();
                boolean isCubic = selectedType.contains("Cubic");

                if (isCubic)
                {
                    solveCubicEquation();
                }
                else
                {
                    solveQuadraticEquation();
                }

            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Please enter valid numbers for all coefficients.");
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        }
    }



    private void solveQuadraticEquation() {
        double a = Double.parseDouble(coefficientFields[0].getText());
        double b = Double.parseDouble(coefficientFields[1].getText());
        double c = Double.parseDouble(coefficientFields[2].getText());

        StringBuilder result = new StringBuilder();
        result.append("Quadratic Equation: ").append(formatCoefficient(a, "x²")).append(formatCoefficient(b, "x")).append(formatConstant(c)).append(" = 0\n\n");

        if (a == 0)
        {
            resultArea.setText("Error: Coefficient 'a' cannot be zero for a quadratic equation.");
            return;
        }

        else
        {
            double discriminant = b * b - 4 * a * c;
            result.append("Discriminant (Δ) = b² - 4ac = ").append(String.format("%.6f", discriminant)).append("\n\n");

            if (discriminant > 0)
            {
                double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                result.append("Two Real Solutions:\n");
                result.append("x₁ = ").append(String.format("%.6f", x1)).append("\n");
                result.append("x₂ = ").append(String.format("%.6f", x2));
            }

            else if (discriminant == 0)
            {
                double x = -b / (2 * a);
                result.append("One Real Solution (Repeated Root):\n");
                result.append("x = ").append(String.format("%.6f", x));
            }

            else
            {
                double realPart = -b / (2 * a);
                double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
                result.append("Two Complex Solutions:\n");
                result.append("x₁ = ").append(String.format("%.6f", realPart)).append(" + ").append(String.format("%.6f", imaginaryPart)).append("i\n");
                result.append("x₂ = ").append(String.format("%.6f", realPart)).append(" - ").append(String.format("%.6f", imaginaryPart)).append("i");
            }
        }

        resultArea.setText(result.toString());
    }




    private void solveCubicEquation() {
        double a = Double.parseDouble(coefficientFields[0].getText());
        double b = Double.parseDouble(coefficientFields[1].getText());
        double c = Double.parseDouble(coefficientFields[2].getText());
        double d = Double.parseDouble(coefficientFields[3].getText());

        StringBuilder result = new StringBuilder();
        result.append("Cubic Equation: ").append(formatCoefficient(a, "x³")).append(formatCoefficient(b, "x²")).append(formatCoefficient(c, "x")).append(formatConstant(d)).append(" = 0\n\n");
        if (a == 0)
        {
            resultArea.setText("Error: Coefficient 'a' cannot be zero for a cubic equation.");
            return;
        }

        b /= a;
        c /= a;
        d /= a;

        double p = c - (b * b) / 3;
        double q = (2 * b * b * b - 9 * b * c + 27 * d) / 27;
        double discriminant = (q * q) / 4 + (p * p * p) / 27;

        result.append("Using Cardano's Method:\n");
        result.append("p = ").append(String.format("%.6f", p)).append("\n");
        result.append("q = ").append(String.format("%.6f", q)).append("\n");
        result.append("Discriminant = ").append(String.format("%.6f", discriminant)).append("\n\n");

        if (discriminant > 0)
        {
            double sqrtDisc = Math.sqrt(discriminant);
            double u = Math.cbrt(-q / 2 + sqrtDisc);
            double v = Math.cbrt(-q / 2 - sqrtDisc);
            double x1 = u + v - b / 3;

            result.append("One Real Solution:\n");
            result.append("x₁ = ").append(String.format("%.6f", x1)).append("\n\n");
            result.append("Two Complex Solutions:\n");
            double realPart = -(u + v) / 2 - b / 3;
            double imagPart = Math.sqrt(3) * (u - v) / 2;
            result.append("x₂ = ").append(String.format("%.6f", realPart))
                    .append(" + ").append(String.format("%.6f", imagPart)).append("i\n");
            result.append("x₃ = ").append(String.format("%.6f", realPart))
                    .append(" - ").append(String.format("%.6f", imagPart)).append("i");
        }


        else if (discriminant == 0)
        {
            if (q == 0)
            {
                double x = -b / 3;
                result.append("Triple Root:\n");
                result.append("x = ").append(String.format("%.6f", x));
            }

            else
            {
                double x1 = 3 * q / p - b / 3;
                double x2 = -3 * q / (2 * p) - b / 3;
                result.append("One Single Root and One Double Root:\n");
                result.append("x₁ = ").append(String.format("%.6f", x1)).append("\n");
                result.append("x₂ = x₃ = ").append(String.format("%.6f", x2));
            }
        }


        else
        {
            double m = Math.sqrt(-(p * p * p) / 27);
            double theta = Math.acos(-q / (2 * m));
            double x1 = 2 * Math.cbrt(m) * Math.cos(theta / 3) - b / 3;
            double x2 = 2 * Math.cbrt(m) * Math.cos((theta + 2 * Math.PI) / 3) - b / 3;
            double x3 = 2 * Math.cbrt(m) * Math.cos((theta + 4 * Math.PI) / 3) - b / 3;

            result.append("Three Real Solutions:\n");
            result.append("x₁ = ").append(String.format("%.6f", x1)).append("\n");
            result.append("x₂ = ").append(String.format("%.6f", x2)).append("\n");
            result.append("x₃ = ").append(String.format("%.6f", x3));
        }

        resultArea.setText(result.toString());
    }


    private String formatCoefficient(double coeff, String variable) {
        if (coeff == 0)
            return "";
        String sign = coeff > 0 ? " + " : " - ";
        double absCoeff = Math.abs(coeff);
        if (absCoeff == 1)
        {
            return sign + variable;
        }
        else
        {
            return sign + String.format("%.3g", absCoeff) + variable;
        }
    }


    private String formatConstant(double constant) {
        if (constant == 0)
            return "";
        String sign = constant > 0 ? " + " : " - ";
        return sign + String.format("%.3g", Math.abs(constant));
    }

}
