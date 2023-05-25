package currencyconverter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.http.*;
import java.net.URI;


public class CurrencyConverter {
    private JLabel l1, l2, l3, l4, l5;
    private JTextField t1, t2;
    private JComboBox<String> cb1, cb2;
    private JButton b1;

    public CurrencyConverter() {
        Icon icon1 = new ImageIcon("a10.png");

        JFrame f=new JFrame("Java Currency Converter App");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(0,0,1080,720);
        f.getContentPane().setBackground(new Color(112, 55, 255, 255));


        JLabel l1=new JLabel("Currency Converter");
        l1.setBounds(500,130,400,50);
        l1.setFont(new Font("Script MT Bold", Font.BOLD, 35));
        f.add(l1);

        JLabel l2=new JLabel("Enter Amount");
        l2.setBounds(480,215,150,30);
        l2.setFont(new Font("Verdana", Font.PLAIN, 15));
        f.add(l2);

        JTextField t1=new JTextField();
        t1.setBounds(480,250,325,50);
        t1.setFont(new Font("Verdana", Font.PLAIN, 20));
        f.add(t1);

        JLabel l3=new JLabel("From");
        l3.setBounds(480,315,100,30);
        l3.setFont(new Font("Verdana", Font.PLAIN, 15));
        f.add(l3);

        JLabel l4=new JLabel("To");
        l4.setBounds(700,315,100,30);
        l4.setFont(new Font("Verdana", Font.PLAIN, 15));
        f.add(l4);

        JComboBox cb1= new JComboBox(new String[]{  "AFN",  "USD",  "EUR",  "AUD", 
         "AZN",  "BHD",  "BDT",  "INR",  "NOK",  "BRL",  "CAD",  "CNY",  "NZD", 
          "EGP",  "IDR",  "IRR",  "IQD",  "GBP",  "ILS",  "JPY",  "KZT",  "KWD",  
          "KGS",  "LBP",  "MYR",  "MAD",  "NGN",  "KPW",  "OMR",  "PKR",  "PHP",  
          "QAR",  "RUB",  "SAR",  "SGD",  "ANG",  "KRW",  "LKR",  "SEK",  "CHF",  
          "SYP",  "TJS",  "THB",  "TRY",  "TMT",  "AED",  "YER"  });
        
        cb1.setBounds(480,350,100,40);
        cb1.setFont(new Font("Verdana", Font.PLAIN, 15));
        cb1.setBackground(new Color(255,255,255));
        f.add(cb1);

        JLabel l5 = new JLabel();
        l5.setIcon(icon1);
        l5.setBounds(620,350,40,40);
        f.add(l5);

        JComboBox cb2= new JComboBox(new String[]{  "AFN",  "USD",  "EUR",  "AUD", 
         "AZN",  "BHD",  "BDT",  "INR",  "NOK", 
          "BRL",  "CAD",  "CNY",  "NZD",  "EGP",  
          "IDR",  "IRR",  "IQD",  "GBP",  "ILS",  "JPY",  
          "KZT",  "KWD",  "KGS",  "LBP",  "MYR",  "MAD", 
           "NGN",  "KPW",  "OMR",  "PKR",  "PHP",  "QAR", 
            "RUB",  "SAR",  "SGD",  "ANG",  "KRW",  "LKR",  
            "SEK",  "CHF",  "SYP",  "TJS",  "THB",  "TRY", 
             "TMT",  "AED",  "YER"  }
);
       
        cb2.setBounds(700,350,100,40);
        cb2.setFont(new Font("Verdana", Font.PLAIN, 15));
        cb2.setBackground(new Color(255,255,255));
        f.add(cb2);

        JTextField t2=new JTextField();
        t2.setBounds(480,410,325,40);
        t2.setFont(new Font("Verdana", Font.PLAIN, 17));
        t2.setBorder(null);
        f.add(t2);

        JButton b1=new JButton("Get Exchange Rate");
        b1.setBounds(480,470,325,50);
        b1.setFont(new Font("Verdana", Font.BOLD, 18));
        b1.setBackground(new Color(112, 55, 255, 255));
        b1.setForeground(new Color(255,255,255));
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.setFocusPainted(false);
        f.add(b1);

        JPanel p=new JPanel();
        p.setBounds(450,100,380,465);
        p.setBackground(new Color(255,255,255));
        f.add(p);
        f.setLayout(null);
        f.setVisible(true);

        // Add action listener to button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountText = t1.getText().trim(); // Get the amount input text and remove leading/trailing whitespace

        if (amountText.isEmpty()) {
            t2.setText("Please Enter an Amount");
            return; // Exit the method if the amount is empty
        }
        
                double rate;
                // Retrieve conversion rate from API
                if (cb1.getSelectedItem()==cb2.getSelectedItem()){
                    rate = 1;
                }
                else{
                rate = getConversionRate((String) cb1.getSelectedItem(), (String) cb2.getSelectedItem());
                }
                // Convert amount
                String amount_=t1.getText();
                double amount = 0;
                try{
                amount = Double.parseDouble(amount_);
                }
                catch(NumberFormatException nfe){
                t2.setText("Please Enter a Number");
                return;
                }
                
                double convertedAmount = amount * rate;

                // Display result
                if (convertedAmount<0){
                t2.setText("Connection Failed ");
                
                }
                else{
                
                
                t2.setText(String.format("%.2f %s = %.2f %s", amount, cb1.getSelectedItem(), convertedAmount, cb2.getSelectedItem()));
            }}
        });
    }

    // Retrieve conversion rate from API
    private double getConversionRate(String from, String to) {
        double rate = 0;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://currency-exchange.p.rapidapi.com/exchange?from=" + from + "&to=" + to + "&q=1.0"))
                .header("X-RapidAPI-Key", "c097213fefmsh34abbc5aa54460dp15a535jsne73e3a6bec37")
                .header("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            rate = Double.parseDouble(responseBody);
        } 
           
        catch (Exception e) {
            e.printStackTrace();
            rate = -1;
        }

        return rate;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CurrencyConverter converter = new CurrencyConverter();
        });
    }
}