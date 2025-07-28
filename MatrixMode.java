import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
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
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class MatrixMode {
    private JTextField[][] matrixA;
    private JTextField[][] matrixB;
    private JTextField[][] resultMatrix;
    private JPanel matrixPanel, matrixAPanel, matrixBPanel, resultPanel;
    private JSpinner rowsASpinner, colsASpinner, rowsBSpinner, colsBSpinner;
    private JButton calculateButton;
    private String matrixOperation = "addition";
    private JComboBox<String> operationComboBox;

    public void createMatrixPanel(JPanel buttonPanel) {
        buttonPanel.setLayout(new BorderLayout(10, 10));
        matrixPanel = new JPanel(new BorderLayout(10, 10));
        matrixPanel.setOpaque(false);
        matrixPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel dimensionPanel = createStyledPanel();
        dimensionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dimensionPanel.setBorder(createStyledTitledBorder("Matrix Settings"));
        dimensionPanel.add(createStyledLabel("Operation:"));
        String[] operations = {"addition", "subtraction", "multiplication", "transpose"};
        operationComboBox = createStyledComboBox(operations);
        operationComboBox.addActionListener(e -> {
            matrixOperation = (String) operationComboBox.getSelectedItem();
            updateMatrixPanels();
        });
        dimensionPanel.add(operationComboBox);
        dimensionPanel.add(Box.createHorizontalStrut(20));
        dimensionPanel.add(createStyledLabel("Matrix A:"));
        rowsASpinner = createStyledSpinner(3, 1, 10, 1);
        colsASpinner = createStyledSpinner(3, 1, 10, 1);
        dimensionPanel.add(createStyledLabel("Rows:"));
        dimensionPanel.add(rowsASpinner);
        dimensionPanel.add(createStyledLabel("Columns:"));
        dimensionPanel.add(colsASpinner);

        dimensionPanel.add(Box.createHorizontalStrut(20));
        dimensionPanel.add(createStyledLabel("Matrix B:"));
        rowsBSpinner = createStyledSpinner(3, 1, 10, 1);
        colsBSpinner = createStyledSpinner(3, 1, 10, 1);
        dimensionPanel.add(createStyledLabel("Rows:"));
        dimensionPanel.add(rowsBSpinner);
        dimensionPanel.add(createStyledLabel("Columns:"));
        dimensionPanel.add(colsBSpinner);

        rowsASpinner.addChangeListener(e -> updateMatrixConstraints());
        colsASpinner.addChangeListener(e -> updateMatrixConstraints());
        rowsBSpinner.addChangeListener(e -> updateMatrixConstraints());
        colsBSpinner.addChangeListener(e -> updateMatrixConstraints());

        JButton createMatricesButton = createStyledButton("Create Matrices");
        createMatricesButton.addActionListener(e -> createMatrices());
        dimensionPanel.add(createMatricesButton);
        matrixPanel.add(dimensionPanel, BorderLayout.NORTH);

        JPanel matricesPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        matricesPanel.setOpaque(false);
        matricesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        matrixAPanel = createStyledPanel();
        matrixAPanel.setLayout(new BorderLayout());
        matrixAPanel.setBorder(createStyledTitledBorder("Matrix A"));
        matricesPanel.add(matrixAPanel);

        matrixBPanel = createStyledPanel();
        matrixBPanel.setLayout(new BorderLayout());
        matrixBPanel.setBorder(createStyledTitledBorder("Matrix B"));
        matricesPanel.add(matrixBPanel);

        resultPanel = createStyledPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(createStyledTitledBorder("Result"));
        matricesPanel.add(resultPanel);
        matrixPanel.add(matricesPanel, BorderLayout.CENTER);


        JPanel matrixButtonPanel = createStyledPanel();
        matrixButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        calculateButton = createStyledButton("Calculate");
        calculateButton.setEnabled(false);
        calculateButton.addActionListener(e -> performMatrixOperation());
        matrixButtonPanel.add(calculateButton);
        matrixPanel.add(matrixButtonPanel, BorderLayout.SOUTH);
        buttonPanel.add(matrixPanel, BorderLayout.CENTER);
        updateMatrixConstraints();
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

        comboBox.setBackground(new Color(45, 45, 55));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.BOLD, 12));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton() {
                    @Override
                    public void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        GradientPaint gradient = new GradientPaint(
                                0, 0, new Color(70, 130, 180),
                                0, getHeight(), new Color(50, 110, 160)
                        );
                        g2d.setPaint(gradient);
                        g2d.fillRect(0, 0, getWidth(), getHeight());

                        g2d.setColor(Color.WHITE);
                        int centerX = getWidth() / 2;
                        int centerY = getHeight() / 2;
                        int[] xPoints = {centerX - 4, centerX + 4, centerX};
                        int[] yPoints = {centerY - 2, centerY - 2, centerY + 3};
                        g2d.fillPolygon(xPoints, yPoints, 3);

                        g2d.setColor(new Color(255, 255, 255, 50));
                        g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);

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
                        list.setBackground(new Color(35, 35, 45));
                        list.setForeground(Color.WHITE);
                        list.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        list.setSelectionBackground(new Color(70, 130, 180));
                        list.setSelectionForeground(Color.WHITE);
                        list.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                                BorderFactory.createEmptyBorder(2, 2, 2, 2)
                        ));
                    }
                };
            }
        });

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                setFont(new Font("Segoe UI", Font.BOLD, 12));

                if (index == -1) {
                    setBackground(new Color(45, 45, 55));
                    setForeground(new Color(255, 255, 255));
                    setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(100, 150, 200, 100), 1),
                            BorderFactory.createEmptyBorder(3, 7, 3, 7)
                    ));
                } else if (isSelected) {
                    setBackground(new Color(70, 130, 180));
                    setForeground(Color.WHITE);
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(100, 150, 200), 1),
                            BorderFactory.createEmptyBorder(4, 8, 4, 8)
                    ));
                } else {
                    setBackground(new Color(35, 35, 45));
                    setForeground(new Color(220, 220, 220));
                    setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                }

                setOpaque(true);

                if (value != null) {
                    String text = value.toString();
                    text = text.substring(0, 1).toUpperCase() + text.substring(1);
                    setText(text);
                }

                return this;
            }
        });

        return comboBox;
    }


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



    private javax.swing.border.TitledBorder createStyledTitledBorder(String title) {
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        return border;
    }



    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text, 5);
        field.setBackground(new Color(45, 45, 55));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        field.setCaretColor(Color.WHITE);
        field.setSelectionColor(new Color(70, 130, 180));
        field.setSelectedTextColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        return field;
    }



    private void updateMatrixPanels() {
        boolean isTranspose = "transpose".equals(matrixOperation);
        matrixBPanel.setVisible(!isTranspose);
        updateMatrixConstraints();
        matrixPanel.revalidate();
        matrixPanel.repaint();
    }




    private void updateMatrixConstraints() {
        if ("addition".equals(matrixOperation) || "subtraction".equals(matrixOperation))
        {
            rowsBSpinner.setEnabled(true);
            colsBSpinner.setEnabled(true);
        }
        else if ("multiplication".equals(matrixOperation))
        {
            rowsBSpinner.setEnabled(true);
            colsBSpinner.setEnabled(true);
        }
        else
        {
            rowsBSpinner.setEnabled(false);
            colsBSpinner.setEnabled(false);
        }
    }




    private void createMatrices() {
        int rowsA = (Integer) rowsASpinner.getValue();
        int colsA = (Integer) colsASpinner.getValue();
        int rowsB = (Integer) rowsBSpinner.getValue();
        int colsB = (Integer) colsBSpinner.getValue();

        if ("addition".equals(matrixOperation) || "subtraction".equals(matrixOperation))
        {
            if (rowsA != rowsB || colsA != colsB)
            {
                JOptionPane.showMessageDialog(null, "For addition/subtraction, matrices must have the same dimensions.", "Invalid Dimensions", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        else if ("multiplication".equals(matrixOperation))
        {
            if (colsA != rowsB)
            {
                JOptionPane.showMessageDialog(null, "For multiplication, columns of Matrix A (" + colsA + ") must equal rows of Matrix B (" + rowsB + ").", "Invalid Dimensions", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        matrixAPanel.removeAll();
        JPanel gridA = new JPanel(new GridLayout(rowsA, colsA, 5, 5));
        gridA.setOpaque(false);
        matrixA = new JTextField[rowsA][colsA];
        for (int i = 0; i < rowsA; i++)
        {
            for (int j = 0; j < colsA; j++)
            {
                matrixA[i][j] = createStyledTextField("0");
                gridA.add(matrixA[i][j]);
            }
        }
        JScrollPane scrollA = new JScrollPane(gridA);
        scrollA.setOpaque(false);
        scrollA.getViewport().setOpaque(false);
        matrixAPanel.add(scrollA, BorderLayout.CENTER);

        if (!"transpose".equals(matrixOperation))
        {
            matrixBPanel.removeAll();
            JPanel gridB = new JPanel(new GridLayout(rowsB, colsB, 5, 5));
            gridB.setOpaque(false);
            matrixB = new JTextField[rowsB][colsB];
            for (int i = 0; i < rowsB; i++)
            {
                for (int j = 0; j < colsB; j++)
                {
                    matrixB[i][j] = createStyledTextField("0");
                    gridB.add(matrixB[i][j]);
                }
            }
            JScrollPane scrollB = new JScrollPane(gridB);
            scrollB.setOpaque(false);
            scrollB.getViewport().setOpaque(false);
            matrixBPanel.add(scrollB, BorderLayout.CENTER);
        }

        calculateButton.setEnabled(true);
        matrixAPanel.revalidate();
        matrixAPanel.repaint();
        matrixBPanel.revalidate();
        matrixBPanel.repaint();
    }



    private void performMatrixOperation() {
        try {
            switch (matrixOperation)
            {
                case "addition":
                case "subtraction":
                    performAdditionOrSubtraction();
                    break;
                case "multiplication":
                    performMultiplication();
                    break;
                case "transpose":
                    performTranspose();
                    break;
            }
            resultPanel.revalidate();
            resultPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Matrix Operation Error", JOptionPane.ERROR_MESSAGE);
        }
    }







    private void performAdditionOrSubtraction() {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        if (rowsA != rowsB || colsA != colsB)
        {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition/subtraction");
        }

        double[][] a = getMatrixValues(matrixA);
        double[][] b = getMatrixValues(matrixB);
        double[][] result = new double[rowsA][colsA];

        for (int i = 0; i < rowsA; i++)
        {
            for (int j = 0; j < colsA; j++)
            {
                if ("addition".equals(matrixOperation))
                {
                    result[i][j] = a[i][j] + b[i][j];
                }
                else
                {
                    result[i][j] = a[i][j] - b[i][j];
                }
            }
        }

        displayResult(result);
    }






    private void performMultiplication() {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        if (colsA != rowsB)
        {
            throw new IllegalArgumentException("Number of columns in Matrix A  must equal number of rows in Matrix B");
        }

        double[][] a = getMatrixValues(matrixA);
        double[][] b = getMatrixValues(matrixB);
        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++)
        {
            for (int j = 0; j < colsB; j++)
            {
                for (int k = 0; k < colsA; k++)
                {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        displayResult(result);
    }





    private void performTranspose() {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;

        double[][] a = getMatrixValues(matrixA);
        double[][] result = new double[colsA][rowsA];

        for (int i = 0; i < rowsA; i++)
        {
            for (int j = 0; j < colsA; j++)
            {
                result[j][i] = a[i][j];
            }
        }

        displayResult(result);
    }




    private double[][] getMatrixValues(JTextField[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] values = new double[rows][cols];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                try {
                    values[i][j] = Double.parseDouble(matrix[i][j].getText());
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid Input");
                }
            }
        }
        return values;
    }




    private void displayResult(double[][] result) {
        int rows = result.length;
        int cols = result[0].length;

        resultPanel.removeAll();
        JPanel resultGrid = new JPanel(new GridLayout(rows, cols, 5, 5));
        resultGrid.setOpaque(false);

        resultMatrix = new JTextField[rows][cols];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                resultMatrix[i][j] = createStyledTextField(String.format("%.2f", result[i][j]));
                resultMatrix[i][j].setEditable(false);
                resultGrid.add(resultMatrix[i][j]);
            }
        }

        JScrollPane scrollResult = new JScrollPane(resultGrid);
        scrollResult.setOpaque(false);
        scrollResult.getViewport().setOpaque(false);
        resultPanel.add(scrollResult, BorderLayout.CENTER);
    }
}