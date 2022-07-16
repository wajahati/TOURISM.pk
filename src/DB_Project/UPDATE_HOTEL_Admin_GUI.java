package DB_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UPDATE_HOTEL_Admin_GUI extends JFrame{
    JButton Submit_B, Back_B;
    JLabel REG_Customer,Hotel_l,Select_Hotel_L, Hname_L, HCity_L,HRooms_L;
    JTextField Hname_T, HCity_T;
    JComboBox<String> Rooms_CB;
    String [] Rooms ={"1","2","3","4","5"};

    ArrayList<String> Hotel_ID_AL;
    String[] Hotel_ID_ARR;

    JTable table_hotel;
    JComboBox<String> Hotel_CB;
    JButton Add_Hotel_B;

    public UPDATE_HOTEL_Admin_GUI(){
        Hotel_ID_AL =new ArrayList<>();

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

        REG_Customer = new JLabel("UPDATE HOTEL");
        REG_Customer.setFont(new Font("Serif", Font.BOLD,25));
        REG_Customer.setBounds(200,50,500,35);
        REG_Customer.setForeground(new Color(51,51,51));
        ADD_USER_P.add(REG_Customer);

        Hotel_l = new JLabel("Hotel Table :");
        Hotel_l.setFont(new Font("Serif", Font.BOLD,15));
        Hotel_l.setBounds(100,80,500,35);
        Hotel_l.setForeground(new Color(51,51,51));
        ADD_USER_P.add(Hotel_l);

        table_hotel = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table_hotel.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
                "ID", "Name", "City", "No. of Rooms"}
        ));
        showData_Hotel();
        table_hotel.setFont(new Font("Serif", Font.PLAIN,15));

        JScrollPane scrollPane_hotel = new JScrollPane(table_hotel);
        scrollPane_hotel.setBounds(100, 120, 500, 200);
        ADD_USER_P.add(scrollPane_hotel);


        Select_Hotel_L = new JLabel("Select ID : ");
        Select_Hotel_L.setFont(new Font("Serif", Font.BOLD,15));
        Select_Hotel_L.setBounds(100,350,100,35);
        Select_Hotel_L.setForeground(new Color(51,51,51));
        ADD_USER_P.add(Select_Hotel_L);

        Hotel_CB = new JComboBox<String>(Hotel_ID_ARR);
        Hotel_CB.setBounds(220,350,50,30);
        ADD_USER_P.add(Hotel_CB);

        Add_Hotel_B = new JButton("UPDATE");
        Add_Hotel_B.setBounds(300,350,80,25);
        Add_Hotel_B.setBackground(Color.BLUE);
        Add_Hotel_B.setForeground(Color.white);
        ADD_USER_P.add(Add_Hotel_B);

        Hname_L = new JLabel("Hotel Name :");
        Hname_L.setFont(new Font("Serif", Font.BOLD,15));
        Hname_L.setBounds(150,390,120,30);
        Hname_L.setVisible(false);
        ADD_USER_P.add(Hname_L);

        Hname_T = new JTextField();
        Hname_T.setBounds(300,390,300,30);
        Hname_T.setVisible(false);
        ADD_USER_P.add(Hname_T);

        HCity_L = new JLabel("Hotel City :");
        HCity_L.setFont(new Font("Serif", Font.BOLD,15));
        HCity_L.setBounds(150,440,120,30);
        HCity_L.setVisible(false);
        ADD_USER_P.add(HCity_L);

        HCity_T = new JTextField(20);
        HCity_T.setBounds(300,440,300,30);
        HCity_T.setVisible(false);
        ADD_USER_P.add(HCity_T);

        HRooms_L = new JLabel("No of Rooms :");
        HRooms_L.setFont(new Font("Serif", Font.BOLD,15));
        HRooms_L .setBounds(150,490,120,30);
        HRooms_L.setVisible(false);
        ADD_USER_P.add(HRooms_L );

        Rooms_CB = new JComboBox<String>(Rooms);
        Rooms_CB.setBounds(300,490,50,30);
        Rooms_CB.setVisible(false);
        ADD_USER_P.add(Rooms_CB);

        Back_B = new JButton("BACK");
        Back_B.setBounds(100,530,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_USER_P.add(Back_B);

        Submit_B = new JButton("SUBMIT");
        Submit_B.setBounds(280,530,80,25);
        Submit_B.setBackground(Color.BLUE);
        Submit_B.setForeground(Color.white);
        Submit_B.setVisible(false);
        ADD_USER_P.add(Submit_B);

        setVisible(true);
        MyListener listener = new MyListener();
        Add_Hotel_B.addActionListener(listener);
        Submit_B.addActionListener(listener);
        Back_B.addActionListener(listener);

    }
    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("UPDATE")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("SELECT H_NAME ,H_CITY, H_NO_OF_ROOMS FROM Hotel WHERE H_ID=?");
                    int s1 = Integer.parseInt(Hotel_CB.getSelectedItem().toString());
                    preparedStatement.setInt(1,s1);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()){
                        Hname_L.setVisible(true);
                        Hname_T.setVisible(true);
                        Hname_T.setText(rs.getString(1));
                        HCity_L.setVisible(true);
                        HCity_T.setVisible(true);
                        HCity_T.setText(rs.getString(2));
                        HRooms_L.setVisible(true);
                        Rooms_CB.setVisible(true);
                        Rooms_CB.setSelectedItem(Rooms[(Integer.parseInt(rs.getString(3)))-1]);
                        System.out.println((Integer.parseInt(rs.getString(3)))-1);
                        Submit_B.setVisible(true);

                    }
                    con.close();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                    ae.printStackTrace();
                }
            }else if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new CREATE_PACKAGE_Admin_GUI();
            }else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Textfields left empty");
            }

            else if (e.getActionCommand().equalsIgnoreCase("SUBMIT")) {

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("UPDATE Hotel SET H_NAME=? ,H_CITY=?, H_NO_OF_ROOMS=? WHERE H_ID=?");

                    String s1 = Hname_T.getText();
                    String s2 = HCity_T.getText();
                    int s3 = Integer.parseInt(Rooms_CB.getSelectedItem().toString());
                    int s4 = Integer.parseInt(Hotel_CB.getSelectedItem().toString());

                    preparedStatement.setString(1, s1);
                    preparedStatement.setString(2, s2);
                    preparedStatement.setInt(3, s3);
                    preparedStatement.setInt(4, s4);

                    preparedStatement.executeQuery();

                    JOptionPane.showMessageDialog(null, "Hotel Updated Successfully\nPress OK to Return to Create Package Page");
                    dispose();
                    con.close();
                    new CREATE_PACKAGE_Admin_GUI();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }

        }
    }

    private void showData_Hotel(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Hotel");
            preparedStatement = con.prepareStatement("SELECT H_ID, H_NAME, H_CITY, H_NO_OF_ROOMS FROM Hotel ORDER BY H_ID");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_hotel.getModel();

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2),
                        rs.getString(3), String.valueOf(rs.getInt(4))};
                System.out.println(departmentData[0]);
                Hotel_ID_AL.add(departmentData[0]);
                defaultTableModel.addRow(departmentData);
            }
            Hotel_ID_ARR = Hotel_ID_AL.toArray(new String[Hotel_ID_AL.size()]);
            con.close();
        }
        catch (Exception ea){
            ea.printStackTrace();
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

