package DB_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MAIN_Bookings_GUI extends JFrame{
    JButton CONFIRM_B, Back_B;
    JLabel MANAGE_BOOKINGS_L, BOOKING_ID_L, BOOKING_L;
    JComboBox<String> BOOKING_CB;
    ArrayList<String> BOOKING_ID_AL;
    String[] BOOKING_ID_ARR;

    JTable table_BOOKING;

    MAIN_Bookings_GUI(){
        BOOKING_ID_AL = new ArrayList<>();
        JPanel ADD_EMP_P = new JPanel();
        setSize(700, 750);
        setTitle("Tourism Network");
        add(ADD_EMP_P);
        ADD_EMP_P.setLayout(null);
        ADD_EMP_P.setBackground(new Color(153,153,153));

        MANAGE_BOOKINGS_L = new JLabel("MANAGE BOOKINGS");
        MANAGE_BOOKINGS_L.setFont(new Font("Serif", Font.BOLD,25));
        MANAGE_BOOKINGS_L.setBounds(170,20,500,35);
        MANAGE_BOOKINGS_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(MANAGE_BOOKINGS_L);

        BOOKING_L = new JLabel("BOOKINGS TO BE CONFIRMED");
        BOOKING_L.setFont(new Font("Serif", Font.BOLD,15));
        BOOKING_L.setBounds(20,100,400,25);
        BOOKING_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(BOOKING_L);

        table_BOOKING = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_BOOKING.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {

                ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."
                }
        ));
        showData_BOOKING();
        table_BOOKING.setFont(new Font("Serif", Font.PLAIN,15));
        table_BOOKING.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_BOOKING);
        JScrollPane scrollPane_transportation = new JScrollPane(table_BOOKING);
        scrollPane_transportation.setBounds(10, 130, 660, 300);
        ADD_EMP_P.add(scrollPane_transportation);

        BOOKING_ID_L = new JLabel("SELECT ID : ");
        BOOKING_ID_L.setFont(new Font("Serif", Font.BOLD,15));
        BOOKING_ID_L.setBounds(150,480,120,30);
        ADD_EMP_P.add(BOOKING_ID_L);

        BOOKING_CB = new JComboBox<String>(BOOKING_ID_ARR);
        BOOKING_CB.setBounds(300,480,50,30);
        ADD_EMP_P.add(BOOKING_CB);

        CONFIRM_B = new JButton("CONFIRM BOOKING");
        CONFIRM_B.setBounds(360,540,160,35);
        CONFIRM_B.setBackground(Color.BLUE);
        CONFIRM_B.setForeground(Color.white);
        ADD_EMP_P.add(CONFIRM_B);

        Back_B = new JButton("BACK");
        Back_B.setBounds(200,545,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);

        setVisible(true);
        MyListener listener = new MyListener();
        CONFIRM_B.addActionListener(listener);
        Back_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equalsIgnoreCase("CONFIRM BOOKING")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("UPDATE Confirmation SET BOOKING_C_STATUS = 'Y' WHERE BOOKING_C_ID = ? ");

                    String s1 = BOOKING_CB.getSelectedItem().toString();
                    preparedStatement.setInt(1, Integer.parseInt(s1));
                    preparedStatement.executeQuery();
                    int CONFIRMATION_ID = Integer.parseInt(s1);
                    Populate_Charges_Table(CONFIRMATION_ID, con);
                    JOptionPane.showMessageDialog(new JFrame(), "BOOKING CONFIRMED!");

                    dispose();
                    con.close();
                    new MAIN_Bookings_GUI();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Admin_GUI();
            }
        }
    }

    private void showData_BOOKING(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Package");
            preparedStatement = con.prepareStatement("SELECT BOOKING_C_ID, BOOKING_C_STATUS, C_ID,C_FIRSTNAME,C_LASTNAME, NO_OF_DAYS, to_char(FROM_DATE,'dd-MON-yyyy'),P_ID, P_NAME, P_TYPE, P_PRICE, P_CITY,\n" +
                    "                    H_NAME, H_NO_OF_ROOMS, T_CAPACITY\n" +
                    "                    FROM Confirmation\n" +
                    "                    INNER JOIN package ON PACKAGE_P_ID=P_ID\n" +
                    "                    INNER JOIN Customer ON CUSTOMER_C_ID=C_ID\n" +
                    "                    INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                    "                    INNER JOIN hotel ON  HOTEL_H_ID= H_ID\n" +
                    "                    WHERE BOOKING_C_STATUS = 'N'\n" +
                    "                    ORDER BY Confirmation.BOOKING_C_ID ASC");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_BOOKING.getModel();

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }
            String[] TableData  = {
                    "BOOKING ID", "STATUS", "CUSTOMER ID", "CUSTOMER NAME", "NO OF DAYS","FROM DATE", "PACKAGE ID", "PACKAGE NAME", "PACKAGE TYPE","PACKAGE PRICE","HOTEL CITY","HOTEL NAME","NO OF ROOMS","TRANSPORT CAPACITY"};
            defaultTableModel.addRow(TableData);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)),Confirmation_Status(rs.getString(2)), String.valueOf(rs.getInt(3)),
                        (rs.getString(4)+" "+rs.getString(5)),String.valueOf(rs.getInt(6)),
                        (rs.getString(7)),String.valueOf(rs.getInt(8)), rs.getString(9),
                        rs.getString(10), String.valueOf(rs.getInt(11)), rs.getString(12),
                        rs.getString(13),String.valueOf(rs.getInt(14)),String.valueOf(rs.getInt(15))};
                BOOKING_ID_AL.add(departmentData[0]);
                defaultTableModel.addRow(departmentData);
            }
            BOOKING_ID_ARR = BOOKING_ID_AL.toArray(new String[BOOKING_ID_AL.size()]);
            con.close();
        }
        catch (Exception ea){
            ea.printStackTrace();
        }
    }


    public String Confirmation_Status(String status){
        if (status.equals("Y")){
            return "Confirmed";
        }else{
            return "Not Confirmed";
        }}

    public void Populate_Charges_Table(int CONF_ID, Connection con){
        try {
            PreparedStatement preparedStatement1 = con.prepareStatement("SELECT CUSTOMER_C_ID,NO_OF_DAYS FROM Confirmation WHERE  BOOKING_C_ID =?");
            PreparedStatement preparedStatement2 = con.prepareStatement("INSERT INTO \"PROJECT\".\"CHARGES\" (TOTAL_PRICE, CHARGES_STATUS, CUSTOMER_C_ID, CONFIRMATION_BOOKING_C_ID) VALUES (?, 'N', ?, ?)");
            preparedStatement1.setInt(1, CONF_ID);
            ResultSet rs = preparedStatement1.executeQuery();
            if (rs.next()) {
                int C_ID = rs.getInt(1);
                int no_of_days = rs.getInt(2);
                int total_cost = Get_Charges(CONF_ID, no_of_days, con);

                preparedStatement2.setInt(1, total_cost);
                preparedStatement2.setInt(2, C_ID);
                preparedStatement2.setInt(3, CONF_ID);
                preparedStatement2.executeQuery();
            }
        } catch (Exception ea){
            ea.printStackTrace();
        }
    }

    public int Get_Charges(int CONF_ID, int no_of_days , Connection con){
        int total_Cost=0;
        try{
            PreparedStatement preparedStatement1 = con.prepareStatement("SELECT P_PRICE FROM Package WHERE  P_ID =(SELECT PACKAGE_P_ID FROM CONFIRMATION WHERE  BOOKING_C_ID = ?)");
            preparedStatement1.setInt(1,CONF_ID);
            ResultSet rs = preparedStatement1.executeQuery();
            if (rs.next()){
                int cost_per_day= rs.getInt(1);
                total_Cost= cost_per_day*no_of_days;
                if (no_of_days>=7){
                    total_Cost = total_Cost - (total_Cost*15)/100;
                }
            }
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return total_Cost;
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 30;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +15 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}


