package DB_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ADD_TRANSPORT_Admin_GUI extends JFrame{
    JButton Submit_B, Back_B;
    JLabel REG_Customer, Tname_L, TModel_L, TCapacity_L;
    JTextField Tname_T, T_Model_T;
    JComboBox<String> Capacity_CB;
    String [] Capacity ={"1","2","3","4","5","6","7","8"};

    public ADD_TRANSPORT_Admin_GUI(){
        JPanel ADD_USER_P = new JPanel() {@Override
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

        REG_Customer = new JLabel("ADD TRANSPORT");
        REG_Customer.setFont(new Font("Serif", Font.BOLD,25));
        REG_Customer.setBounds(200,50,500,35);
        REG_Customer.setForeground(new Color(51,51,51));
        ADD_USER_P.add(REG_Customer);

        Tname_L = new JLabel("Transport Name :");
        Tname_L.setFont(new Font("Serif", Font.BOLD,15));
        Tname_L.setBounds(150,150,150,30);
        ADD_USER_P.add(Tname_L);

        Tname_T = new JTextField();
        Tname_T.setBounds(300,150,300,30);
        ADD_USER_P.add(Tname_T);

        TCapacity_L = new JLabel("Transport Capacity :");
        TCapacity_L.setFont(new Font("Serif", Font.BOLD,15));
        TCapacity_L.setBounds(150,200,150,30);
        ADD_USER_P.add(TCapacity_L);

        Capacity_CB = new JComboBox<String>(Capacity);
        Capacity_CB.setBounds(300,200,50,30);
        ADD_USER_P.add(Capacity_CB);

        TModel_L = new JLabel("Transport Model :");
        TModel_L.setFont(new Font("Serif", Font.BOLD,15));
        TModel_L.setBounds(150,250,150,30);
        ADD_USER_P.add(TModel_L);

        T_Model_T = new JTextField(20);
        T_Model_T.setBounds(300,250,300,30);
        ADD_USER_P.add(T_Model_T);


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
            }else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Textfields left empty");
            }
            else if(e.getActionCommand().equalsIgnoreCase("SUBMIT")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO PROJECT.TRANSPORTATION (T_NAME,T_CAPACITY,T_MODEL) VALUES (?,?,?)");

                    String s1 = Tname_T.getText();
                    int s2 = Integer.parseInt(Capacity_CB.getSelectedItem().toString());
                    String s3 = T_Model_T.getText();

                    preparedStatement.setString(1, s1);
                    preparedStatement.setInt(2, s2);
                    preparedStatement.setString(3, s3);
                    preparedStatement.executeQuery();

                    JOptionPane.showMessageDialog(null, "Transport Added Successfully\nPress OK to Return to Create Package Page");
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
        if (Tname_T.getText().isEmpty() || T_Model_T.getText().isEmpty() ){
            return false;
        }
        else{
            return true;
        }
    }
}

