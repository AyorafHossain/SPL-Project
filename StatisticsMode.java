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

public class StatisticsMode {
    private JPanel mainPanel;
    private JTextArea dataInputArea;
    private JComboBox<String> calculationTypeComboBox;
    private JTextArea resultField;
    private JButton calculateButton;
    private JButton clearButton; // Added clearButton
    private static final int FIELD_ROWS = 12;
    private static final int FIELD_COLUMNS = 40;
    private static final Dimension FIELD_DIMENSION = new Dimension(550, 250);


    //create statistics JPanel
    public void createStatisticsPanel(JPanel buttonPanel) {
        buttonPanel.setLayout(new BorderLayout(10, 10));
        mainPanel = createStyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel settingsPanel = createStyledPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        settingsPanel.setBorder(createStyledTitledBorder("Statistics Settings"));

        settingsPanel.add(createStyledLabel("Calculation:"));
        String[] calculationTypes = {"Mean", "Median", "Mode", "All"};
        calculationTypeComboBox = createStyledComboBox(calculationTypes);
        settingsPanel.add(calculationTypeComboBox);

        mainPanel.add(settingsPanel, BorderLayout.NORTH);

        JPanel inputPanel = createStyledPanel();
        inputPanel.setLayout(new BorderLayout(10, 10));
        inputPanel.setBorder(createStyledTitledBorder("Data Input"));

        JLabel instructionsLabel = createStyledLabel("Enter numbers separated by commas, spaces, or new lines.");
        inputPanel.add(instructionsLabel, BorderLayout.NORTH);

        dataInputArea = createStyledTextArea(FIELD_ROWS, FIELD_COLUMNS);
        dataInputArea.setLineWrap(true);
        dataInputArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = createStyledScrollPane(dataInputArea);
        inputScrollPane.setPreferredSize(FIELD_DIMENSION);
        JPanel inputCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputCenterPanel.setOpaque(false);
        inputCenterPanel.add(inputScrollPane);
        inputPanel.add(inputCenterPanel, BorderLayout.CENTER);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel resultPanel = createStyledPanel();
        resultPanel.setLayout(new BorderLayout(10, 10));
        resultPanel.setBorder(createStyledTitledBorder("Result"));

        resultField = createStyledTextArea(FIELD_ROWS, FIELD_COLUMNS);
        resultField.setEditable(false);
        resultField.setBackground(new Color(35, 35, 45));
        resultField.setLineWrap(true);
        resultField.setWrapStyleWord(true);
        JScrollPane resultScrollPane = createStyledScrollPane(resultField);
        resultScrollPane.setPreferredSize(FIELD_DIMENSION);
        JPanel resultCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultCenterPanel.setOpaque(false);
        resultCenterPanel.add(resultScrollPane);
        resultPanel.add(resultCenterPanel, BorderLayout.CENTER);

        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel2.setOpaque(false);

        calculateButton = createStyledButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(100, calculateButton.getPreferredSize().height));
        calculateButton.addActionListener(new CalculateButtonListener());
        buttonPanel2.add(calculateButton);


        clearButton = createStyledButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, clearButton.getPreferredSize().height));
        clearButton.addActionListener(e -> clearFields());
        buttonPanel2.add(clearButton);

        resultPanel.add(buttonPanel2, BorderLayout.SOUTH);

        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        buttonPanel.add(mainPanel, BorderLayout.CENTER);
    }



    //clear displayfield
    private void clearFields() {
        dataInputArea.setText("");
        resultField.setText("");
    }



    //create style jpanel
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



    //create style JComboBox
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
            @Override
            public void paintCurrentValue(Graphics g, java.awt.Rectangle bounds, boolean hasFocus) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        bounds.x, bounds.y, new Color(60, 60, 70),
                        bounds.x, bounds.y + bounds.height, new Color(80, 80, 90)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 6, 6);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 12));
                String text = "";
                if (comboBox.getSelectedItem() != null)
                {
                    text = comboBox.getSelectedItem().toString();
                }
                java.awt.FontMetrics fm = g2d.getFontMetrics();
                int textX = bounds.x + 8;
                int textY = bounds.y + (bounds.height + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(text, textX, textY);
                g2d.dispose();
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
                            BorderFactory.createEmptyBorder(6, 10, 6, 10)
                    ));
                }
                else
                {
                    setBackground(new Color(45, 45, 55));
                    setForeground(Color.WHITE);
                    setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                }
                setOpaque(true);
                return this;
            }
        });
        // Add border around the entire ComboBox
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        return comboBox;
    }



    //create style TitleBorder
    private javax.swing.border.TitledBorder createStyledTitledBorder(String title) {
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        return border;
    }



    //Create Style TextArea
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


    //create Styled ScrollPane
    private JScrollPane createStyledScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        return scrollPane;
    }


    //create style ActionListener
    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String inputText = dataInputArea.getText().trim();
                if (inputText.isEmpty())
                {
                    resultField.setText("Please enter data");
                    return;
                }
                double[] data = parseInputData(inputText);
                if (data.length == 0)
                {
                    resultField.setText("No valid numbers found");
                    return;
                }
                String calculationType = (String) calculationTypeComboBox.getSelectedItem();
                StringBuilder result = new StringBuilder();
                switch (calculationType)
                {
                    case "Mean":
                        result.append("Mean: ").append(calculateMean(data));
                        break;
                    case "Median":
                        result.append("Median: ").append(calculateMedian(data));
                        break;
                    case "Mode":
                        result.append("Mode: ").append(calculateMode(data));
                        break;
                    case "All":
                        result.append("Mean: ").append(calculateMean(data)).append("\n");
                        result.append("Median: ").append(calculateMedian(data)).append("\n");
                        result.append("Mode: ").append(calculateMode(data));
                        break;
                    default:
                        result.append("Invalid calculation type");
                }
                resultField.setText(result.toString());
            } catch (Exception ex) {
                resultField.setText("Error: " + ex.getMessage());
            }
        }
    }



    //parse input data
    private double[] parseInputData(String inputText) {
        inputText = inputText.replace(",", " ");
        String[] tokens = inputText.split("\\s+");
        int validCount = 0;
        for (String token : tokens)
        {
            if (!token.isEmpty())
            {
                try {
                    Double.parseDouble(token);
                    validCount++;
                } catch (NumberFormatException e) {
                }
            }
        }
        double[] data = new double[validCount];
        int index = 0;
        for (String token : tokens)
        {
            if (!token.isEmpty())
            {
                try {
                    data[index++] = Double.parseDouble(token);
                } catch (NumberFormatException e) {
                }
            }
        }
        return data;
    }


    //calculate mean
    private double calculateMean(double[] data) {
        double sum = 0,mean;
        int n=data.length;
        for (int i = 0; i <n ; i++)
        {
            sum += data[i];
        }
        mean = sum/n;
        return  mean;
    }



    //calculate Median
    private double calculateMedian(double[] data) {
        double[] sortedData = new double[data.length];
        for (int i = 0; i < data.length; i++)
        {
            sortedData[i] = data[i];
        }
        bubbleSort(sortedData);
        int middle = sortedData.length / 2;
        if (sortedData.length % 2 == 0)
        {
            return (sortedData[middle - 1] + sortedData[middle]) / 2.0;
        }
        else
        {
            return sortedData[middle];
        }
    }


    //calculate Mode
    private String calculateMode(double[] data) {
        if (data.length == 0)
        {
            return "No data";
        }
        double[] sortedData = new double[data.length];
        for (int i = 0; i < data.length; i++)
        {
            sortedData[i] = data[i];
        }
        bubbleSort(sortedData);
        double currentValue = sortedData[0];
        int currentCount = 1;
        double modeValue = currentValue;
        int modeCount = currentCount;
        for (int i = 1; i < sortedData.length; i++)
        {
            if (sortedData[i] == currentValue)
            {
                currentCount++;
            }
            else
            {
                if (currentCount > modeCount)
                {
                    modeCount = currentCount;
                    modeValue = currentValue;
                }
                currentValue = sortedData[i];
                currentCount = 1;
            }
        }
        if (currentCount > modeCount)
        {
            modeCount = currentCount;
            modeValue = currentValue;
        }
        if (modeCount == 1)
        {
            return "No mode (all values appear once)";
        }
        return String.valueOf(modeValue) + " (appears " + modeCount + " times)";
    }



    //perform bubblesort algorithm
    private void bubbleSort(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
        {
            for (int j = 0; j < n - i - 1; j++)
            {
                if (arr[j] > arr[j + 1])
                {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}