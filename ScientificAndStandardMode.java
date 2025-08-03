import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GradientPaint;
import java.awt.Dimension;

public class ScientificAndStandardMode {
    private JTextField displayField;
    private StringBuilder currentInput = new StringBuilder();
    private double lastNumber = 0;
    private String lastOperator = "";
    private boolean waitingForPowerInput = false;
    private boolean isDegreeMode = true;
    private boolean isInverseMode = false;
    private JToggleButton degRadButton;
    private JToggleButton invButton;

    public ScientificAndStandardMode(JTextField displayField) {
        this.displayField = displayField;
    }

    public void setupButtonPanel(JPanel buttonPanel, boolean isScientificMode) {
        if (isScientificMode)
        {
            buttonPanel.setLayout(new GridLayout(9, 4, 8, 8));
            createScientificButtons(buttonPanel);
        }
        else
        {
            buttonPanel.setLayout(new GridLayout(5, 4, 8, 8));
            createStandardButtons(buttonPanel);
        }
    }


    //create Scientific Buttons
    private void createScientificButtons(JPanel buttonPanel) {
        String[] buttonLabels = {
                "DEG", "INV", "ln", "e^x",
                "sin", "cos", "tan", "C",
                "cot", "sec", "cosec", "Del",
                "log", "!", "√", "^",
                "π", "(", ")", "*",
                "7", "8", "9", "/",
                "4", "5", "6", "+",
                "1", "2", "3", "-",
                "0", ".", "=", "%"
        };

        for (int i = 0; i < buttonLabels.length; i++)
        {
            String label = buttonLabels[i];
            if ("DEG".equals(label))
            {
                degRadButton = createStyledToggleButton("DEG");
                degRadButton.setSelected(true);
                degRadButton.addActionListener(e -> toggleDegreeRadianMode());
                buttonPanel.add(degRadButton);
            }
            else if ("INV".equals(label))
            {
                invButton = createStyledToggleButton("INV");
                invButton.setSelected(false);
                invButton.addActionListener(e -> toggleInverseMode());
                buttonPanel.add(invButton);
            }
            else
            {
                JButton button = createStyledButton(label);
                buttonPanel.add(button);
            }
        }
    }

    //create Standard Buttons
    private void createStandardButtons(JPanel buttonPanel) {
        String[] buttonLabels = {
                "7", "8", "9", "C",
                "4", "5", "6", "*",
                "1", "2", "3", "/",
                "0", ".", "+", "-",
                "%", "(", ")", "="
        };

        for (String label : buttonLabels)
        {
            JButton button = createStyledButton(label);
            buttonPanel.add(button);
        }
    }


  //create Styled Button
    private JButton createStyledButton(String label) {
        JButton button = new JButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color startColor, endColor;

                if (getModel().isPressed())
                {
                    startColor = new Color(40, 40, 50);
                    endColor = new Color(60, 60, 70);
                }
                else if (getModel().isRollover())
                {
                    startColor = new Color(70, 130, 180);
                    endColor = new Color(100, 150, 200);
                }
                else
                {
                    if (isOperator(label) || "=".equals(label))
                    {
                        startColor = new Color(70, 130, 180);
                        endColor = new Color(50, 110, 160);
                    }
                    else if ("C".equals(label) || "Del".equals(label))
                    {
                        startColor = new Color(220, 80, 80);
                        endColor = new Color(200, 60, 60);
                    }
                    else if (isScientificFunction(label))
                    {
                        startColor = new Color(120, 80, 180);
                        endColor = new Color(100, 60, 160);
                    }
                    else
                    {
                        startColor = new Color(60, 60, 70);
                        endColor = new Color(80, 80, 90);
                    }
                }

                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                super.paintComponent(g);
                g2d.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 25));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(70, 50));
        button.addActionListener(new ButtonClickListener());
        return button;
    }

    //create Styled Toggle Button
    private JToggleButton createStyledToggleButton(String label) {
        JToggleButton button = new JToggleButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color startColor, endColor;

                if (isSelected())
                {
                    startColor = new Color(255, 140, 0);
                    endColor = new Color(255, 165, 0);
                }
                else {
                    startColor = new Color(60, 60, 70);
                    endColor = new Color(80, 80, 90);
                }

                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                super.paintComponent(g);
                g2d.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(70, 50));
        return button;
    }

    private void toggleDegreeRadianMode() {
        isDegreeMode = !isDegreeMode;
        if (isDegreeMode)
        {
            degRadButton.setText("DEG");
        }
        else
        {
            degRadButton.setText("RAD");
        }
        degRadButton.repaint();
    }


    private void toggleInverseMode()
    {
        isInverseMode = !isInverseMode;
        invButton.repaint();
    }

    private boolean isOperator(String label) {
        return "+".equals(label) || "-".equals(label) || "*".equals(label) ||
                "/".equals(label) || "%".equals(label) || "^".equals(label);
    }

    private boolean isScientificFunction(String label) {
        return "sin".equals(label) || "cos".equals(label) || "tan".equals(label) ||
                "cot".equals(label) || "sec".equals(label) || "cosec".equals(label) ||
                "log".equals(label) || "ln".equals(label) || "√".equals(label) ||
                "!".equals(label) || "π".equals(label) || "e^x".equals(label);
    }


    // implements ActionListener
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("=".equals(command))
            {
                evaluateExpression();
            }
            else if ("C".equals(command))
            {
                clearDisplay();
            }
            else if ("Del".equals(command))
            {
                deleteLastCharacter();
            }

            else if (isScientificFunction(command))
            {
                handleScientificFunction(command);
            }
            else if (isOperator(command))
            {
                handleOperator(command);
            }
            else
            {
                appendToInput(command);
            }
        }
    }


   //evaluate Expression method
    private void evaluateExpression() {
        try {
            String expression = currentInput.toString();
            if (expression.isEmpty())
            {
                return;
            }

            if (!areParenthesesBalanced(expression))
            {
                displayField.setText("Error: Unmatched parentheses");
                return;
            }

            expression = handleScientificFunctionsInExpression(expression);
            double result = evaluate(expression);
            currentInput.setLength(0);
            currentInput.append(result);
            displayField.setText(currentInput.toString());
        } catch (Exception e) {
            displayField.setText("Error: " + e.getMessage());
            currentInput.setLength(0);
        }
    }


    //check are Parentheses Balanced
    private boolean areParenthesesBalanced(String expression) {
        int count = 0;
        for (char c : expression.toCharArray())
        {
            if (c == '(')
            {
                count++;
            }
            else if (c == ')')
            {
                count--;
                if (count < 0)
                {
                    return false;
                }
            }
        }
        return count == 0;
    }

    private void clearDisplay() {
        currentInput.setLength(0);
        lastNumber = 0;
        lastOperator = "";
        waitingForPowerInput = false;
        displayField.setText("0");
    }

    private void deleteLastCharacter() {
        if (currentInput.length() > 0)
        {
            currentInput.deleteCharAt(currentInput.length() - 1);
            if (currentInput.length() == 0)
            {
                displayField.setText("0");
            }
            else
            {
                displayField.setText(currentInput.toString());
            }
        }
    }

    private void handleScientificFunction(String function) {
        if ("π".equals(function)) {
            currentInput.append(String.valueOf(Math.PI));
            displayField.setText(currentInput.toString());
            return;
        }


        if (isInverseMode && ("sin".equals(function) || "cos".equals(function) || "tan".equals(function) ||
                "cot".equals(function) || "sec".equals(function) || "cosec".equals(function)))
        {
            currentInput.append("a").append(function).append("(");
        }

        else
        {
            currentInput.append(function).append("(");
        }
        displayField.setText(currentInput.toString());
    }

    private void handleOperator(String operator) {
        currentInput.append(operator);
        displayField.setText(currentInput.toString());
    }

    private void appendToInput(String text) {
        if (currentInput.length() == 0 && displayField.getText().equals("0") && Character.isDigit(text.charAt(0)))
        {
            currentInput.setLength(0);
        }

        if ("π".equals(text))
        {
            currentInput.append(String.valueOf(Math.PI));
        }
        else
        {
            currentInput.append(text);
        }
        displayField.setText(currentInput.toString());
    }

    private double factorial(int n) {
        if (n < 0)
        {
            throw new ArithmeticException("Factorial requires non-negative integer");
        }
        double result = 1;
        for (int i = 2; i <= n; i++)
        {
            result *= i;
        }
        return result;
    }

    private String handleScientificFunctionsInExpression(String expression) {
        expression = expression.replace("π", String.valueOf(Math.PI));
        int maxIterations = 100, iterations = 0;


        String[] functions = {"acosec", "asec", "acot", "atan", "acos", "asin",
                "cosec", "sec", "cot", "tan", "cos", "sin",
                "log", "ln", "e^x", "√", "!"};

        while (containsAnyFunction(expression, functions) && iterations < maxIterations)
        {
            iterations++;
            for (String func : functions)
            {
                int funcStart = expression.indexOf(func + "(");
                if (funcStart >= 0)
                {
                    int openParens = 1;
                    int funcEnd = funcStart + func.length() + 1;

                    while (funcEnd < expression.length() && openParens > 0)
                    {
                        char ch = expression.charAt(funcEnd);
                        if (ch == '(')
                        {
                            openParens++;
                        }
                        else if (ch == ')')
                        {
                            openParens--;
                        }
                        funcEnd++;
                    }

                    if (openParens != 0)
                    {
                        throw new ArithmeticException("Unmatched parentheses in function: " + func);
                    }

                    String arg = expression.substring(funcStart + func.length() + 1, funcEnd - 1);
                    double argValue = evaluate(arg);
                    double result = calculateFunctionResult(func, argValue);

                    expression = expression.substring(0, funcStart) + result + expression.substring(funcEnd);
                    break;
                }
            }
        }

        if (iterations >= maxIterations)
        {
            throw new ArithmeticException("Expression too complex or contains errors");
        }
        return expression;
    }

    private boolean containsAnyFunction(String expr, String[] funcs) {
        for (String func : funcs)
        {
            if (expr.contains(func + "("))
            {
                return true;
            }
        }
        return false;
    }

    private double calculateFunctionResult(String func, double argValue) {
        switch (func) {
            case "sin":
                return isDegreeMode ? Math.sin(Math.toRadians(argValue)) : Math.sin(argValue);
            case "cos":
                return isDegreeMode ? Math.cos(Math.toRadians(argValue)) : Math.cos(argValue);
            case "tan":
                if (isDegreeMode)
                {
                    if (Math.abs(argValue % 180) == 90) {
                        throw new ArithmeticException("Undefined");
                    }
                    return Math.tan(Math.toRadians(argValue));
                }
                else
                {
                    double normalizedValue = argValue % (2 * Math.PI);
                    if (Math.abs(normalizedValue - Math.PI/2) < 1e-10 || Math.abs(normalizedValue - 3*Math.PI/2) < 1e-10)
                    {
                        throw new ArithmeticException("Undefined");
                    }
                    return Math.tan(argValue);
                }


            case "asin":
                if (argValue < -1 || argValue > 1)
                {
                    throw new ArithmeticException("Invalid input for arcsin");
                }
                return isDegreeMode ? Math.toDegrees(Math.asin(argValue)) : Math.asin(argValue);
            case "acos":
                if (argValue < -1 || argValue > 1)
                {
                    throw new ArithmeticException("Invalid input for arccos");
                }
                return isDegreeMode ? Math.toDegrees(Math.acos(argValue)) : Math.acos(argValue);
            case "atan":
                return isDegreeMode ? Math.toDegrees(Math.atan(argValue)) : Math.atan(argValue);


            case "cot":
                if (isDegreeMode)
                {
                    if (argValue % 180 == 0)
                    {
                        throw new ArithmeticException("Undefined");
                    }
                    double sinValue = Math.sin(Math.toRadians(argValue));
                    double cosValue = Math.cos(Math.toRadians(argValue));
                    return cosValue / sinValue;
                }
                else
                {
                    double normalizedValue = argValue % Math.PI;
                    if (Math.abs(normalizedValue) < 1e-10)
                    {
                        throw new ArithmeticException("Undefined");
                    }
                    double sinValue = Math.sin(argValue);
                    double cosValue = Math.cos(argValue);
                    return cosValue / sinValue;
                }
            case "acot":
                return isDegreeMode ? Math.toDegrees(Math.atan(1.0 / argValue)) : Math.atan(1.0 / argValue);

            case "sec":
                if (isDegreeMode)
                {
                    if (Math.abs(argValue % 180) == 90)
                    {
                        throw new ArithmeticException("Undefined");
                    }
                    double cosValue = Math.cos(Math.toRadians(argValue));
                    return 1.0 / cosValue;
                }
                else
                {
                    double normalizedValue = argValue % (2 * Math.PI);
                    if (Math.abs(normalizedValue - Math.PI/2) < 1e-10 || Math.abs(normalizedValue - 3*Math.PI/2) < 1e-10)
                    {
                        throw new ArithmeticException("Undefined");
                    }
                    double cosValue = Math.cos(argValue);
                    return 1.0 / cosValue;
                }
            case "asec":
                if (Math.abs(argValue) < 1)
                {
                    throw new ArithmeticException("Invalid input for arcsec");
                }
                return isDegreeMode ? Math.toDegrees(Math.acos(1.0 / argValue)) : Math.acos(1.0 / argValue);

            case "cosec":
                if (isDegreeMode)
                {
                    if (argValue % 180 == 0)
                    {
                        throw new ArithmeticException("Undefined");
                    }
                    double sinValue = Math.sin(Math.toRadians(argValue));
                    return 1.0 / sinValue;
                }
                else
                {
                    double normalizedValue = argValue % Math.PI;
                    if (Math.abs(normalizedValue) < 1e-10)
                    {
                        throw new ArithmeticException("Undefined");
                    }
                    double sinValue = Math.sin(argValue);
                    return 1.0 / sinValue;
                }
            case "acosec":
                if (Math.abs(argValue) < 1)
                {
                    throw new ArithmeticException("Invalid input for arccosec");
                }
                return isDegreeMode ? Math.toDegrees(Math.asin(1.0 / argValue)) : Math.asin(1.0 / argValue);

            case "log":
                if (argValue <= 0)
                {
                    throw new ArithmeticException("Invalid input for log");
                }
                return Math.log10(argValue);
            case "ln":
                if (argValue <= 0)
                {
                    throw new ArithmeticException("Invalid input for ln");
                }
                return Math.log(argValue);
            case "e^x":
                return Math.exp(argValue);

            case "√":
                if (argValue < 0)
                {
                    throw new ArithmeticException("Invalid input for square root");
                }
                return Math.sqrt(argValue);
            case "!":
                if (argValue < 0)
                {
                    throw new ArithmeticException("Factorial requires non-negative integer");
                }
                return factorial((int) argValue);

            default:
                throw new ArithmeticException("Unknown function: " + func);
        }
    }

    private double evaluate(String expression) {
        try {
            double[] numberStack = new double[100];
            int numberTop = -1;
            char[] operatorStack = new char[100];
            int operatorTop = -1;

            for (int i = 0; i < expression.length(); i++)
            {
                char c = expression.charAt(i);
                if (c == ' ') continue;

                if (Character.isDigit(c) || c == '.')
                {
                    StringBuilder sb = new StringBuilder();
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.'))
                    {
                        sb.append(expression.charAt(i++));
                    }
                    i--;
                    numberTop++;
                    numberStack[numberTop] = Double.parseDouble(sb.toString());
                }
                else if (c == '(')
                {
                    operatorTop++;
                    operatorStack[operatorTop] = c;
                }
                else if (c == ')')
                {
                    while (operatorTop >= 0 && operatorStack[operatorTop] != '(')
                    {
                        if (numberTop < 1)
                        {
                            throw new ArithmeticException("Invalid expression");
                        }
                        char operator = operatorStack[operatorTop];
                        operatorTop--;
                        double b = numberStack[numberTop];
                        numberTop--;
                        double a = numberStack[numberTop];
                        numberTop--;
                        numberTop++;
                        numberStack[numberTop] = applyOperation(operator, b, a);
                    }
                    if (operatorTop >= 0 && operatorStack[operatorTop] == '(')
                    {
                        operatorTop--;
                    }
                    else
                    {
                        throw new ArithmeticException("Mismatched parentheses");
                    }
                }
                else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^')
                {
                    if (c == '-' && (i == 0 || expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-' || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/' || expression.charAt(i - 1) == '%' || expression.charAt(i - 1) == '^'))
                    {
                        numberTop++;
                        numberStack[numberTop] = 0.0;
                    }

                    while (operatorTop >= 0 && hasPrecedence(c, operatorStack[operatorTop]))
                    {
                        if (numberTop < 1)
                        {
                            throw new ArithmeticException("Invalid expression");
                        }
                        char operator = operatorStack[operatorTop];
                        operatorTop--;
                        double b = numberStack[numberTop];
                        numberTop--;
                        double a = numberStack[numberTop];
                        numberTop--;
                        numberTop++;
                        numberStack[numberTop] = applyOperation(operator, b, a);
                    }
                    operatorTop++;
                    operatorStack[operatorTop] = c;
                }
                else
                {
                    throw new ArithmeticException("Invalid character: " + c);
                }
            }

            while (operatorTop >= 0)
            {
                if (numberTop < 1)
                {
                    throw new ArithmeticException("Invalid expression");
                }
                char operator = operatorStack[operatorTop];
                operatorTop--;
                double b = numberStack[numberTop];
                numberTop--;
                double a = numberStack[numberTop];
                numberTop--;
                numberTop++;
                numberStack[numberTop] = applyOperation(operator, b, a);
            }

            if (numberTop != 0)
            {
                throw new ArithmeticException("Invalid expression");
            }
            return numberStack[numberTop];
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Invalid expression");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArithmeticException("Expression too complex");
        }
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if (op1 == '^') return false;
        if (op2 == '^') return true;
        if ((op1 == '*' || op1 == '/' || op1 == '%') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    private double applyOperation(char operator, double b, double a) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            case '%': return a % b;
            case '^': return Math.pow(a, b);
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
