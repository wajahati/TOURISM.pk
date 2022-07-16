package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ADD_HOTEL_Admin_GUI extends JFrame {
    JButton Submit_B, Back_B;
    JLabel REG_Customer, Hname_L, HCity_L,HRooms_L;
    JTextField Hname_T, HCity_T;
    JComboBox<String> Rooms_CB;
    String [] Rooms ={"1","2","3","4","5"};

    public ADD_HOTEL_Admin_GUI(){
        JPanel ADD_USER_P = new JPanel()
        {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        setSize(700, 750);
        setTitle("Tourism Network");
        add(ADD_USER_P);
        ADD_USER_P.setLayout(null);
        ADD_USER_P.setBackground(new Color(153,153,153));

        REG_Customer = new JLabel("ADD HOTEL");
        REG_Customer.setFont(new Font("Serif", Font.BOLD,25));
        REG_Customer.setBounds(200,50,500,35);
        REG_Customer.setForeground(new Color(51,51,51));
        ADD_USER_P.add(REG_Customer);

        Hname_L = new JLabel("Hotel Name :");
        Hname_L.setFont(new Font("Serif", Font.BOLD,15));
        Hname_L.setBounds(150,150,120,30);
        ADD_USER_P.add(Hname_L);

        Hname_T = new JTextField();
        Hname_T.setBounds(300,150,300,30);
        ADD_USER_P.add(Hname_T);

        HCity_L = new JLabel("Hotel City :");
        HCity_L.setFont(new Font("Serif", Font.BOLD,15));
        HCity_L.setBounds(150,200,120,30);
        ADD_USER_P.add(HCity_L);

        HCity_T = new JTextField(20);
        HCity_T.setBounds(300,200,300,30);
        ADD_USER_P.add(HCity_T);

        HRooms_L = new JLabel("No of Rooms :");
        HRooms_L.setFont(new Font("Serif", Font.BOLD,15));
        HRooms_L .setBounds(150,250,120,30);
        ADD_USER_P.add(HRooms_L );

        Rooms_CB = new JComboBox<String>(Rooms);
        Rooms_CB.setBounds(300,250,50,30);
        ADD_USER_P.add(Rooms_CB);


        Back_B = new JButton("BACK");
        Back_B.setBounds(100,460,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_USER_P.add(Back_B);

        Submit_B = new JButton("SUBMIT");
        Submit_B.setBounds(280,460,80,25);

        Submit_B.setBackground(Color.BLUE);
        Submit_B.setForeground(Color.white);
        ADD_USER_P.add(Submit_B);

        setVisible(true);
        MyListener listener = new MyListener();
        Submit_B.addActionListener(listener);
        Back_B.addActionListener(listener);

    }
    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new CREATE_PACKAGE_Admin_GUI();
            }
            else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Textfields left empty");
            }
            else if(e.getActionCommand().equalsIgnoreCase("SUBMIT")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO PROJECT.Hotel (H_NAME,H_CITY,H_NO_OF_ROOMS) VALUES (?,?,?)");

                    String s1 = Hname_T.getText();
                    String s2 = HCity_T.getText();
                    int s3 = Integer.parseInt(Rooms_CB.getSelectedItem().toString());

                    preparedStatement.setString(1, s1);
                    preparedStatement.setString(2, s2);
                    preparedStatement.setInt(3, s3);

                    preparedStatement.executeQuery();

                    JOptionPane.showMessageDialog(null, "Hotel Added Successfully\nPress OK to Return to Create Package Page");
                    dispose();
                    con.close();
                    new CREATE_PACKAGE_Admin_GUI();

                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }

        }
    }
    private boolean IsNotEmpty(){
        if (Hname_T.getText().isEmpty() || HCity_T.getText().isEmpty() ){
            return false;
        }
        else{
            return true;
        }
    }
}
