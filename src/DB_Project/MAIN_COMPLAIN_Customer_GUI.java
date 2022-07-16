package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MAIN_COMPLAIN_Customer_GUI extends JFrame {
    JButton Add_Complain_B, View_Response_B, Back_B;
    JLabel Menu_Complain_L,Company_L;

    int C_ID;
    public MAIN_COMPLAIN_Customer_GUI (int C_ID){
        this.C_ID=C_ID;
        
        JPanel Menu_P = new JPanel() {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};


        setSize(700, 750);
        setTitle("TOURISM NETWORK");
        add(Menu_P);
        Menu_P.setLayout(null);
        Menu_P.setBackground(new Color(153,153,153));


        Company_L = new JLabel("COMPLAIN");
        Company_L.setFont(new Font("Serif", Font.BOLD,35));
        Company_L.setBounds(160,10,400,35);
        Company_L.setForeground(new Color(51,51,51));
        Menu_P.add(Company_L);

        Menu_Complain_L = new JLabel("MAIN MENU");
        Menu_Complain_L.setFont(new Font("Serif", Font.BOLD,30));
        Menu_Complain_L.setBounds(250,60,300,30);
        Menu_Complain_L.setForeground(new Color(51,51,51));
        Menu_P.add(Menu_Complain_L);


        Add_Complain_B = new JButton("ADD COMPLAIN");
        Add_Complain_B.setBounds(140,160,420,80);
        Add_Complain_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Add_Complain_B);



        View_Response_B = new JButton("VIEW RESPONSE");
        View_Response_B.setBounds(140,280,420,80);
        View_Response_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(View_Response_B);


        Back_B = new JButton("BACK");
        Back_B.setBounds(140,400,100,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Back_B);

        setVisible(true);

        MyListener listener = new MyListener();
        Add_Complain_B.addActionListener(listener);
        View_Response_B.addActionListener(listener);
        Back_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("BACK")){
                dispose();
                new MAIN_Customer_GUI(C_ID);
            }
            else if (e.getActionCommand().equalsIgnoreCase("ADD COMPLAIN")) {
                dispose();
                new ADD_COMPLAIN_Customer_GUI(C_ID);
            }
            else if (!Complain_Available()){
                JOptionPane.showMessageDialog(new JFrame(), "No Responds Available");
            }
            else if (e.getActionCommand().equalsIgnoreCase("VIEW RESPONSE")) {
                dispose();
                new VIEW_RESPONSE_GUI(C_ID);
            }
        }
    }

    public boolean Complain_Available(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement st = con.prepareStatement("Select * FROM Complain WHERE CUSTOMER_C_ID=?");
            st.setInt(1,C_ID);
            ResultSet r1=st.executeQuery();
            if (r1.next()) {
                return true;
            }
            con.close();
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }
}
