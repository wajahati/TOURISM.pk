package DB_Project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class ADD_COMPLAIN_Customer_GUI extends JFrame {

    JButton Add_Complain_B, Back_B;
    JLabel ADD_Complain_L , Remaining_L1,Remaining_L2, Complain_Subject_L , Complain_Details_L;
    JTextField Complain_Subject_T;
    JTextArea Complain_Details_T;

    DefaultStyledDocument doc1,doc2;
    int C_ID;

    public ADD_COMPLAIN_Customer_GUI(int C_ID){
        this.C_ID = C_ID;
        JPanel ADD_USER_P = new JPanel()
                        {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }}
                ;
        setSize(700, 750);
        setTitle("Tourism Network");
        add(ADD_USER_P);
        ADD_USER_P.setLayout(null);
        ADD_USER_P.setBackground(new Color(153,153,153));

        ADD_Complain_L = new JLabel("ADD COMPLAIN");
        ADD_Complain_L.setFont(new Font("Serif", Font.BOLD,25));
        ADD_Complain_L.setBounds(160,50,500,35);
        ADD_Complain_L.setForeground(new Color(51,51,51));
        ADD_USER_P.add(ADD_Complain_L);

        Complain_Subject_L = new JLabel("Complain Subject :");
        Complain_Subject_L.setFont(new Font("Serif", Font.BOLD,15));
        Complain_Subject_L.setBounds(100,130,200,30);
        ADD_USER_P.add(Complain_Subject_L);

        Complain_Subject_T = new JTextField();
        Complain_Subject_T.setFont(new Font("Serif", Font.PLAIN,15));
        Complain_Subject_T.setBounds(100,170,500,30);
        ADD_USER_P.add(Complain_Subject_T);
        doc1 = new DefaultStyledDocument();
        doc1.setDocumentFilter(new DocumentSizeFilter(30));
        doc1.addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e) { updateCount1();}
            @Override
            public void insertUpdate(DocumentEvent e) { updateCount1();}
            @Override
            public void removeUpdate(DocumentEvent e) { updateCount1();}
        });
        Complain_Subject_T.setDocument(doc1);

        Remaining_L1 = new JLabel("");
        Remaining_L1.setFont(new Font("Serif", Font.BOLD,15));
        Remaining_L1.setBounds(420,210,250,30);
        ADD_USER_P.add(Remaining_L1);

        Complain_Details_L = new JLabel("Complain Details :");
        Complain_Details_L.setFont(new Font("Serif", Font.BOLD,15));
        Complain_Details_L.setBounds(100,230,200,30);
        ADD_USER_P.add(Complain_Details_L);


        Complain_Details_T = new JTextArea();
        doc2 = new DefaultStyledDocument();
        doc2.setDocumentFilter(new DocumentSizeFilter(700));
        doc2.addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e) { updateCount2();}
            @Override
            public void insertUpdate(DocumentEvent e) { updateCount2();}
            @Override
            public void removeUpdate(DocumentEvent e) { updateCount2();}
        });
        Complain_Details_T.setDocument(doc2);
        Complain_Details_T.setLineWrap(true);
        Complain_Details_T.setWrapStyleWord(true);

        Complain_Details_T.setFont(new Font("Serif", Font.PLAIN,15));
        JScrollPane scrollPane_transportation = new JScrollPane(Complain_Details_T);
        scrollPane_transportation.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_transportation.setBounds(100, 270, 500, 200);
        ADD_USER_P.add(scrollPane_transportation);


        Remaining_L2 = new JLabel("");
        Remaining_L2.setFont(new Font("Serif", Font.BOLD,15));
        Remaining_L2.setBounds(420,470,250,30);
        ADD_USER_P.add(Remaining_L2);

        Back_B = new JButton("BACK");
        Back_B.setBounds(100,500,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_USER_P.add(Back_B);

        Add_Complain_B = new JButton("SUBMIT");
        Add_Complain_B.setBounds(280,500,80,25);

        Add_Complain_B.setBackground(Color.BLUE);
        Add_Complain_B.setForeground(Color.white);
        ADD_USER_P.add(Add_Complain_B);

        setVisible(true);
        MyListener listener = new MyListener();
        Add_Complain_B.addActionListener(listener);
        Back_B.addActionListener(listener);

    }
    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_COMPLAIN_Customer_GUI(C_ID);
            }
            else if (!IsNotEmpty()) {
                JOptionPane.showMessageDialog(null, "WARNING! Subject or Complain Details left empty");
            }
            else if(e.getActionCommand().equalsIgnoreCase("SUBMIT")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO \"PROJECT\".\"COMPLAIN\" (CUSTOMER_C_ID, COMPLAIN_DATE, COMPLAIN_SUBJECT, COMPLAIN_DETAILS, RESPONSE_STATUS) VALUES (?, TO_DATE(?, 'dd.MM.yyyy'), ?, ?, 'N')");
                    String s3 = Complain_Subject_T.getText();
                    String s4 = Complain_Details_T.getText();
                    preparedStatement.setInt(1, C_ID);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    String date = dateFormat.format(new Date());
                    System.out.println(date);

                    preparedStatement.setString(2,date );
                    System.out.println("Working DATE");
                    preparedStatement.setString(3, s3);
                    preparedStatement.setString(4, s4);
                    preparedStatement.executeQuery();

                    JOptionPane.showMessageDialog(new JFrame(), "Complain Forwarded to Customer Support\nYou will see the response in the View Response section\nOnce the Response is given by Customer Support team");
                    dispose();
                    con.close();
                    new MAIN_COMPLAIN_Customer_GUI(C_ID);

                    } catch (Exception ae) {
                        System.out.println(ae.toString());
                }
            }
        }
    }
    private void updateCount1() {
        Remaining_L1.setText((30 - doc1.getLength()) + " characters remaining"); }
    private void updateCount2() {
        Remaining_L2.setText((700 - doc2.getLength()) + " characters remaining"); }

    private boolean IsNotEmpty(){
        if (Complain_Subject_T.getText().isEmpty() || Complain_Details_T.getText().isEmpty() ){
            return false;
        }
        else{
            return true;
        }
    }
}
