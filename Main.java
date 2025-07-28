import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Dimension;


public class Main extends JFrame {
    private ImageIcon img;
    private JTextField displayField;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private JMenuBar menuBar;
    private JMenu modeMenu;
    private JMenuItem standardItem, scientificItem, matrixItem, primeAndDivisorItem, conversionItem, statisticsItem, equationSolverItem, bitwiseOperatorItem;
    private ScientificAndStandardMode scientificStandardMode;
    private MatrixMode matrixMode;
    private PrimeAndDivisorMode primeAndDivisorMode;
    private ConversionMode conversionMode;
    private StatisticsMode statisticsMode;
    private EquationSolver equationSolver;
    private BitwiseOperator bitwiseOperator;
    private boolean isScientificMode = false;
    private boolean isMatrixMode = false;
    private boolean isPrimeAndDivisorMode = false;
    private boolean isConversionMode = false;
    private boolean isStatisticsMode = false;
    private boolean isEquationSolverMode = false;
    private boolean isBitwiseOperatorMode = false;

    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Advanced Scientific Calculator");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        img = new ImageIcon(getClass().getResource("cal.png"));
        setIconImage(img.getImage());

        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        createMenuBar();

        displayField = new JTextField("0") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(45, 45, 55));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(70, 130, 180, 50));
                g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 12, 12);
                super.paintComponent(g);
                g2d.dispose();
            }
        };
        displayField.setEditable(false);
        displayField.setFont(new Font("Segoe UI", Font.BOLD, 32));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setForeground(Color.WHITE);
        displayField.setBackground(new Color(45, 45, 55));
        displayField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        displayField.setOpaque(false);
        displayField.setPreferredSize(new Dimension(0, 80));


        displayPanel = new JPanel(new BorderLayout());
        displayPanel.setOpaque(false);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        displayPanel.add(displayField, BorderLayout.CENTER);


        mainPanel.add(displayPanel, BorderLayout.NORTH);
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);


        scientificStandardMode = new ScientificAndStandardMode(displayField);
        matrixMode = new MatrixMode();
        primeAndDivisorMode = new PrimeAndDivisorMode();
        conversionMode = new ConversionMode();
        statisticsMode = new StatisticsMode();
        equationSolver = new EquationSolver();
        bitwiseOperator = new BitwiseOperator();

        updateCalculatorMode(false, false, false, false, false, false, false);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 25, 35),
                    0, getHeight(), new Color(45, 45, 65)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(new Color(70, 130, 180, 30));
            for (int i = 0; i < getWidth(); i += 100) {
                g2d.drawLine(i, 0, i + 50, getHeight());
            }
        }
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(70, 130, 180));
        menuBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        modeMenu = new JMenu("Mode");
        modeMenu.setForeground(Color.BLACK);
        modeMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        modeMenu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        modeMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                modeMenu.setOpaque(true);
                modeMenu.setBackground(new Color(100, 150, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                modeMenu.setOpaque(false);
            }
        });

        standardItem = createStyledMenuItem("Standard");
        standardItem.addActionListener(e -> updateCalculatorMode(false, false, false, false, false, false, false));

        scientificItem = createStyledMenuItem("Scientific");
        scientificItem.addActionListener(e -> updateCalculatorMode(true, false, false, false, false, false, false));

        matrixItem = createStyledMenuItem("Matrix Operations");
        matrixItem.addActionListener(e -> updateCalculatorMode(false, true, false, false, false, false, false));

        primeAndDivisorItem = createStyledMenuItem("Prime and Divisor");
        primeAndDivisorItem.addActionListener(e -> updateCalculatorMode(false, false, true, false, false, false, false));

        conversionItem = createStyledMenuItem("Conversion");
        conversionItem.addActionListener(e -> updateCalculatorMode(false, false, false, true, false, false, false));

        statisticsItem = createStyledMenuItem("Statistics");
        statisticsItem.addActionListener(e -> updateCalculatorMode(false, false, false, false, true, false, false));

        equationSolverItem = createStyledMenuItem("Equation Solver");
        equationSolverItem.addActionListener(e -> updateCalculatorMode(false, false, false, false, false, true, false));

        bitwiseOperatorItem = createStyledMenuItem("Bitwise Operator");
        bitwiseOperatorItem.addActionListener(e -> updateCalculatorMode(false, false, false, false, false, false, true));

        modeMenu.add(standardItem);
        modeMenu.add(scientificItem);
        modeMenu.add(matrixItem);
        modeMenu.add(primeAndDivisorItem);
        modeMenu.add(conversionItem);
        modeMenu.add(statisticsItem);
        modeMenu.add(equationSolverItem);
        modeMenu.add(bitwiseOperatorItem);
        menuBar.add(modeMenu);
        setJMenuBar(menuBar);
    }

    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setBackground(new Color(240, 240, 240));
        item.setForeground(new Color(30, 30, 30));
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item.setBackground(new Color(70, 130, 180));
                item.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item.setBackground(new Color(240, 240, 240));
                item.setForeground(new Color(30, 30, 30));
            }
        });
        return item;
    }


    private void updateCalculatorMode(boolean scientific, boolean matrix, boolean primeAndDivisor,
                                      boolean conversion, boolean statistics, boolean equationSolver, boolean bitwiseOperator) {
        isScientificMode = scientific;
        isMatrixMode = matrix;
        isPrimeAndDivisorMode = primeAndDivisor;
        isConversionMode = conversion;
        isStatisticsMode = statistics;
        isEquationSolverMode = equationSolver;
        isBitwiseOperatorMode = bitwiseOperator;
        buttonPanel.removeAll();


        boolean showDisplayField = !matrix && !primeAndDivisor && !conversion && !statistics && !equationSolver && !bitwiseOperator;
        displayPanel.setVisible(showDisplayField);


        if (!showDisplayField)
        {
            displayField.setText("0");
        }


        if (matrix)
        {
            matrixMode.createMatrixPanel(buttonPanel);
        }
        else if (primeAndDivisor)
        {
            primeAndDivisorMode.createPrimeAndDivisorPanel(buttonPanel);
        }
        else if (conversion)
        {
            conversionMode.createConversionPanel(buttonPanel);
        }
        else if (statistics)
        {
            statisticsMode.createStatisticsPanel(buttonPanel);
        }
        else if (equationSolver)
        {
            this.equationSolver.createEquationSolverPanel(buttonPanel);
        }
        else if (bitwiseOperator)
        {
            this.bitwiseOperator.createBitwiseOperatorPanel(buttonPanel);
        }
        else
        {

            scientificStandardMode.setupButtonPanel(buttonPanel, scientific);
        }


        buttonPanel.revalidate();
        buttonPanel.repaint();


        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static void main(String[] args) {
        Main calculator = new Main();
        calculator.setLocationRelativeTo(null);
        calculator.setVisible(true);
    }
}
