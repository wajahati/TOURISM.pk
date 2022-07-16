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

public class PAY_CHARGES_Customer_GUI extends JFrame {


    JButton PAY_B, Back_B;
    JLabel MANAGE_BILL_L, BILL_ID_L, BILL_L;
    JComboBox<String> CHARGES_CB;
    ArrayList<String> UNPAID_BILL_ID_AL;
    String[] UNPAID_BILL_ID_ARR;

    JTable table_BILL;

    int C_ID;

    PAY_CHARGES_Customer_GUI(int C_ID){
        this.C_ID=C_ID;
        UNPAID_BILL_ID_AL = new ArrayList<>();
        JPanel ADD_EMP_P = new JPanel() {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        setSize(700, 750);
        setTitle("Tourism Network");
        add(ADD_EMP_P);
        ADD_EMP_P.setLayout(null);
        ADD_EMP_P.setBackground(new Color(153,153,153));

        MANAGE_BILL_L = new JLabel("MANAGE Pending Bills");
        MANAGE_BILL_L.setFont(new Font("Serif", Font.BOLD,25));
        MANAGE_BILL_L.setBounds(170,20,500,35);
        MANAGE_BILL_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(MANAGE_BILL_L);

        BILL_L = new JLabel("Confirmed Bookings to be Paid");
        BILL_L.setFont(new Font("Serif", Font.BOLD,15));
        BILL_L.setBounds(20,100,400,25);
        BILL_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(BILL_L);

        table_BILL = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_BILL.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
                ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."
        }
        ));
        showData_Package();
        table_BILL.setFont(new Font("Serif", Font.PLAIN,15));
        table_BILL.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_BILL);
        JScrollPane scrollPane_transportation = new JScrollPane(table_BILL);
        scrollPane_transportation.setBounds(10, 130, 660, 300);
        ADD_EMP_P.add(scrollPane_transportation);

        BILL_ID_L = new JLabel("SELECT ID : ");
        BILL_ID_L.setFont(new Font("Serif", Font.BOLD,15));
        BILL_ID_L.setBounds(150,480,120,30);
        ADD_EMP_P.add(BILL_ID_L);

        CHARGES_CB = new JComboBox<String>(UNPAID_BILL_ID_ARR);
        CHARGES_CB.setBounds(300,480,50,30);
        ADD_EMP_P.add(CHARGES_CB);

        PAY_B = new JButton("PAY BILL");
        PAY_B.setBounds(360,540,160,35);
        PAY_B.setBackground(Color.BLUE);
        PAY_B.setForeground(Color.white);
        ADD_EMP_P.add(PAY_B);

        Back_B = new JButton("BACK");
        Back_B.setBounds(200,545,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);

        setVisible(true);
        MyListener listener = new MyListener();
        PAY_B.addActionListener(listener);
        Back_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equalsIgnoreCase("PAY BILL")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("UPDATE Charges SET CHARGES_STATUS = 'Y' WHERE CHARGES_ID = ? ");

                    String s1 = CHARGES_CB.getSelectedItem().toString();
                    preparedStatement.setInt(1, Integer.parseInt(s1));
                    preparedStatement.executeQuery();

                    Populate_History(Integer.parseInt(s1),con);

                    JOptionPane.showMessageDialog(new JFrame(), "PAID!");

                    dispose();
                    con.close();
                    new MAIN_Customer_GUI(C_ID);
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Customer_GUI(C_ID);
            }
        }
    }

    private void showData_Package(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Package");
            preparedStatement = con.prepareStatement("" +
                    "SELECT CHARGES_ID, TOTAL_PRICE, CHARGES_STATUS, BOOKING_C_STATUS,C_FIRSTNAME,C_LASTNAME, NO_OF_DAYS,to_char(FROM_DATE,'dd-MON-yyyy'), P_NAME, P_TYPE, P_PRICE, P_CITY,\n" +
                    "                 H_NAME, H_NO_OF_ROOMS, T_CAPACITY\n" +
                    "                 FROM Charges\n" +
                    "                 INNER JOIN Confirmation ON CONFIRMATION_BOOKING_C_ID = BOOKING_C_ID\n" +
                    "                 INNER JOIN package ON PACKAGE_P_ID=P_ID\n" +
                    "                 INNER JOIN Customer ON Charges.CUSTOMER_C_ID=C_ID\n" +
                    "                 INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                    "                 INNER JOIN hotel ON  HOTEL_H_ID= H_ID\n" +
                    "                 WHERE BOOKING_C_STATUS = 'Y' and Charges.CUSTOMER_C_ID=? \n" +
                    "                 ORDER BY Confirmation.BOOKING_C_ID ASC");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_BILL.getModel();
            preparedStatement.setInt(1,C_ID);
            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }
            String[] TableData  = {
                    "CHARGES ID","TOTAL COST","CHARGES STATUS", "CONFIRMATION STATUS", "CUSTOMER NAME", "NO OF DAYS","FROM DATE",  "PACKAGE NAME", "PACKAGE TYPE","PACKAGE PRICE","HOTEL CITY","HOTEL NAME","NO OF ROOMS","TRANSPORT CAPACITY"};
            defaultTableModel.addRow(TableData);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)) ,String.valueOf(rs.getInt(2)),Bill_Status(rs.getString(3)),Confirmation_Status(rs.getString(4)),
                        (rs.getString(5)+" "+rs.getString(6)),String.valueOf(rs.getInt(7)),
                        String.valueOf(rs.getString(8)), rs.getString(9),
                        rs.getString(10), String.valueOf(rs.getInt(11)), rs.getString(12),
                        rs.getString(13),String.valueOf(rs.getInt(14)),String.valueOf(rs.getInt(15))};
                if (!Bill_Status(rs.getString(3)).equals("Paid")){
                    UNPAID_BILL_ID_AL.add(departmentData[0]);
                }
                defaultTableModel.addRow(departmentData);
            }
            UNPAID_BILL_ID_ARR = UNPAID_BILL_ID_AL.toArray(new String[UNPAID_BILL_ID_AL.size()]);
            con.close();
        }
        catch (Exception ea){
            ea.printStackTrace();
        }
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

    public String Confirmation_Status(String status){
        if (status.equals("Y")){
        return "Confirmed";
    }else{
        return "Not Confirmed";
    }}

    public String Bill_Status(String status){
        if (status.equals("Y")){
            return "Paid";
        }else{
            return "Unpaid";
        }
    }

    public void Populate_History(int Bill_ID,Connection con){
        try {
            PreparedStatement preparedStatement1 = con.prepareStatement("" +
                    "SELECT P_NAME, P_TYPE,TOTAL_PRICE, P_CITY, H_NAME, H_NO_OF_ROOMS,P_PRICE, T_CAPACITY,to_char(FROM_DATE,'dd-MON-yyyy')\n" +
                    "                     FROM Charges\n" +
                    "                     INNER JOIN Confirmation ON CONFIRMATION_BOOKING_C_ID = BOOKING_C_ID\n" +
                    "                     INNER JOIN package ON PACKAGE_P_ID=P_ID\n" +
                    "                     INNER JOIN Customer ON Charges.CUSTOMER_C_ID=C_ID\n" +
                    "                     INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                    "                     INNER JOIN hotel ON  HOTEL_H_ID= H_ID\n" +
                    "                     WHERE BOOKING_C_STATUS = 'Y' and CHARGES_STATUS='Y'   and CHARGES_ID = ?\n" +
                    "                     ORDER BY Confirmation.BOOKING_C_ID ASC");
            PreparedStatement preparedStatement2 = con.prepareStatement("" +
                    "INSERT INTO \"PROJECT\".\"BOOKING_HISTORY\" " +
                    "(CUSTOMER_C_ID, PACKAGE_NAME, PACKAGE_TYPE, TOTAL_PRICE, PACKAGE_CITY, HOTEL_NAME, NO_OF_ROOMS, PACKAGE_PRICE, TRANNSPORT_CAPACITY, CHARGES_CHARGES_ID, FROM_DATE) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?,?, ?,?, TO_DATE(?, 'dd-MON-yyyy'))\n");
            preparedStatement1.setInt(1, Bill_ID);
            ResultSet rs = preparedStatement1.executeQuery();
            if (rs.next()) {
                preparedStatement2.setInt(1, C_ID);
                preparedStatement2.setString(2, rs.getString(1));
                preparedStatement2.setString(3, rs.getString(2));
                preparedStatement2.setInt(4, rs.getInt(3));
                preparedStatement2.setString(5, rs.getString(4));
                preparedStatement2.setString(6, rs.getString(5));
                preparedStatement2.setInt(7, rs.getInt(6));
                preparedStatement2.setInt(8, rs.getInt(7));
                preparedStatement2.setInt(9, rs.getInt(8));
                preparedStatement2.setInt(10, Bill_ID);
                preparedStatement2.setString(11, (rs.getString(9)));
                preparedStatement2.executeQuery();
            }
        } catch (Exception ea){
            ea.printStackTrace();
        }
    }
}

