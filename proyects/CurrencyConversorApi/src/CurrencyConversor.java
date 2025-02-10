import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class CurrencyConversor implements ActionListener {

    String apiConversor;

    Font myFont = new Font("Calibri", Font.PLAIN, 14);

    String[] Currencies = {"USD","EUR","JPY","AUD","MXN","GBP"};
    JComboBox<String> InputCurrency;
    JComboBox<String> OutputCurrency;
    JFrame frame;
    JLabel label;
    JTextField textFieldInput;
    JTextField textFieldOutput;
    JButton convertButton;
    String ApiKey = "5c7a3b51d400ced4bcc336ffbd809e94";

    CurrencyConversor(){

        frame = new JFrame("Currency Conversor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(null);

        label = new JLabel("Select the currencies to exchange.");
        label.setBounds(200, 10, 300, 30);
        label.setFont(myFont);

        textFieldInput = new JTextField();
        textFieldInput.setBounds(50, 50, 200, 30);
        textFieldInput.setFont(myFont);

        textFieldOutput = new JTextField("Output Currency");
        textFieldOutput.setBounds(350, 50, 200, 30);
        textFieldOutput.setFocusable(false);
        textFieldOutput.setEditable(false);
        textFieldOutput.setFont(myFont);

        InputCurrency = new JComboBox<>(Currencies);
        InputCurrency.setBounds(100, 100, 100, 30);
        InputCurrency.setFont(myFont);

        OutputCurrency = new JComboBox<>(Currencies);
        OutputCurrency.setBounds(400, 100, 100, 30);
        OutputCurrency.setFont(myFont);

        convertButton = new JButton("Convert currency");
        convertButton.setBounds(200, 200, 200, 40);
        convertButton.addActionListener(this);
        convertButton.setFont(myFont);

        // Aggregate los componentes al frame
        frame.add(label);
        frame.add(textFieldInput);
        frame.add(textFieldOutput);
        frame.add(InputCurrency);
        frame.add(OutputCurrency);
        frame.add(convertButton);

        frame.setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new CurrencyConversor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convertButton){
            try {
                String from = InputCurrency.toString();
                String to = OutputCurrency.toString();
                double InputValue = Double.parseDouble(textFieldInput.getText());
                double resultValue = 0;
                // "convert" - convert one currency to another
                // https://api.currencylayer.com/convert?from=EUR&to=GBP&amount=100
//                https://api.currencylayer.com/convert?access_key=5c7a3b51d400ced4bcc336ffbd809e94&from=USD&to=GBP&amount=10
                var client = HttpClient.newHttpClient();
                var request = HttpRequest.newBuilder(
                        URI.create("https://api.currencylayer.com/convert?" +
                                "access_key=5c7a3b51d400ced4bcc336ffbd809e94&" +
                                "from="+from+"&to="+to)
                );
                textFieldOutput.setText(String.valueOf(resultValue));

            }catch (Exception exception){

            }
        }


    }
}
