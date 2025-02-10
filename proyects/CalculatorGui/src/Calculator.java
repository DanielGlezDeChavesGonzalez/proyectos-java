import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Calculator implements ActionListener {

    JFrame frame;
    JPanel panel;
    JTextField textField;
    Font myFont = new Font("Calibri", Font.PLAIN, 24);
    JButton[] numberButtons = new JButton[10];
    JButton logo = new JButton("DD");

    JButton[] operationButtons = new JButton[9];
    JButton badd,bmultiplication,bsubstracction,bmodule,bdivision,bequal,bcleanall,bcomma,bdelete;

    Calculator(){
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450,600);
        frame.setLayout(null);

        textField = new JTextField();
        textField.setBounds(45,50,350,50);
        textField.setFont(myFont);
        textField.setFocusable(false);
        textField.setEditable(false);

        badd = new JButton("+");
        bmultiplication = new JButton("*");
        bsubstracction = new JButton("-");
        bmodule = new JButton("%");
        bdivision = new JButton("/");
        bequal = new JButton("=");
        bcleanall = new JButton("CA");
        bcomma = new JButton(",");
        bdelete = new JButton("<-");

        operationButtons[0] = badd;
        operationButtons[1] = bmultiplication;
        operationButtons[2] = bsubstracction;
        operationButtons[3] = bmodule;
        operationButtons[4] = bdivision;
        operationButtons[5] = bequal;
        operationButtons[6] = bcleanall;
        operationButtons[7] = bcomma;
        operationButtons[8] = bdelete;

        for (int i = 0; i < 9; i++){
            operationButtons[i].addActionListener(this);
            operationButtons[i].setFont(myFont);
            operationButtons[i].setFocusable(false);
        }

        for (int i = 0; i < 10; i++){
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        panel = new JPanel();
        panel.setBounds(70,130,300,300);
        panel.setLayout(new GridLayout(5,4,15,15));
//        panel.setBackground(Color.DARK_GRAY);

        panel.add(operationButtons[6]);
        panel.add(operationButtons[8]);
        panel.add(operationButtons[3]);
        panel.add(operationButtons[4]);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(operationButtons[1]);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(operationButtons[2]);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(operationButtons[0]);
        panel.add(logo);
        panel.add(numberButtons[0]);
        panel.add(operationButtons[7]);
        panel.add(operationButtons[5]);

        frame.add(panel);
        frame.add(textField);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i=0; i < 10; i++){
            if(e.getSource() == numberButtons[i]){
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }
        if (e.getSource() == bcomma){
            if (textField.getText().charAt(textField.getText().length()-1) != ','){
            textField.setText(textField.getText().concat(","));
            }
        }
        if (e.getSource() == badd){
            if (textField.getText().charAt(textField.getText().length()-1) != '+') {
                textField.setText(textField.getText().concat("+"));
            }
        }
        if (e.getSource() == bsubstracction){
            if (textField.getText().isEmpty()){
                textField.setText(textField.getText().concat("-"));
            } else if (textField.getText().charAt(textField.getText().length()-1) != '-') {
                textField.setText(textField.getText().concat("-"));
            }
        }
        if (e.getSource() == bdivision){
            if (textField.getText().charAt(textField.getText().length()-1) != '/'){
                textField.setText(textField.getText().concat("/"));
            }
        }
        if (e.getSource() == bmodule){
            if (textField.getText().charAt(textField.getText().length()-1) != '%') {
                textField.setText(textField.getText().concat("%"));
            }
        }
        if (e.getSource() == bmultiplication){
            if (textField.getText().charAt(textField.getText().length()-1) != '*') {
                textField.setText(textField.getText().concat("*"));
            }
        }
        if (e.getSource() == bcleanall){
            textField.setText("");
        }
        if (e.getSource() == bdelete){
            textField.setText(textField.getText().substring(0, textField.getText().length() -1));
        }
        if (e.getSource() == bequal){
            String operation = textField.getText().replace(',','.');
            List<String> postFix = convertToPostFix(operation);
            double result = evaluatePostFix(postFix);
            textField.setText(String.valueOf(result));
        }

    }

    private static List<String> convertToPostFix(String operation) {
        Map<Character, Integer> prevalence = Map.of(
                '+', 1, '-', 1, '*', 2, '/', 2, '%', 2
        );

        List<String> output = new ArrayList<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder number = new StringBuilder();
        boolean isNegative = true;

        for (char c : operation.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
                isNegative = false;
            } else {
                if (!number.isEmpty()) {
                    output.add(number.toString());
                    number.setLength(0);
                }

                if (c == '-' && isNegative) {
                    number.append(c);
                } else if (prevalence.containsKey(c)) {
                    while (!operators.isEmpty() && prevalence.getOrDefault(operators.peek(), 0) >= prevalence.get(c)) {
                        output.add(String.valueOf(operators.pop()));
                    }
                    operators.push(c);
                    isNegative = true;
                } else if (c == '(') {
                    operators.push(c);
                    isNegative = true;
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        output.add(String.valueOf(operators.pop()));
                    }
                    if (!operators.isEmpty()) {
                        operators.pop();
                    }
                    isNegative = false;
                }
            }
        }

        if (!number.isEmpty()) {
            output.add(number.toString());
        }

        while (!operators.isEmpty()) {
            output.add(String.valueOf(operators.pop()));
        }

        return output;
    }

    private static double evaluatePostFix(List<String> postFix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postFix) {
            if (token.matches("[-+]?[0-9]*\\.?[0-9]+")) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();

                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                    case "%": stack.push(a % b); break;
                }
            }
        }

        return stack.pop();
    }


    public static void main(String[] args) throws Exception {

        new Calculator();

    }
}
