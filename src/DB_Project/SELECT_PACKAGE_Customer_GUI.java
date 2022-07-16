package DB_Project;

import com.toedter.calendar.JDateChooser;

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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class SELECT_PACKAGE_Customer_GUI extends JFrame {
    JDateChooser From_Date_DC;
    JButton CONFIRM_B, Back_B;
    JLabel SELECT_PACKAGE_L, PACKAGE_ID_L, Package_L, No_of_Days_L, From_Date_L;
//    JTextPane To_Date_T;
    JComboBox<String> Package_CB, No_of_Days_CB;
    ArrayList<String> Package_ID_AL;
    String[] Package_ID_ARR, No_OF_Days={"1","2","3","4","5","6","7","8","9","10"};
    JTable table_Package;

    public int C_ID;

    SELECT_PACKAGE_Customer_GUI(int C_id){
        this.C_ID = C_id;

        Package_ID_AL = new ArrayList<>();
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

        SELECT_PACKAGE_L = new JLabel("SELECT PACKAGE");
        SELECT_PACKAGE_L.setFont(new Font("Serif", Font.BOLD,25));
        SELECT_PACKAGE_L.setBounds(170,20,500,35);
        SELECT_PACKAGE_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(SELECT_PACKAGE_L);

        Package_L = new JLabel("Available Packages List");
        Package_L.setFont(new Font("Serif", Font.BOLD,15));
        Package_L.setBounds(20,100,400,25);
        Package_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(Package_L);

        table_Package = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_Package.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
                ".", ".", ".", ".", ".",".", ".", "."}
        ));
        showData_Package();
        table_Package.setFont(new Font("Serif", Font.PLAIN,15));
        table_Package.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_Package);
        JScrollPane scrollPane_transportation = new JScrollPane(table_Package);
        scrollPane_transportation.setBounds(10, 130, 660, 300);
        ADD_EMP_P.add(scrollPane_transportation);

        PACKAGE_ID_L = new JLabel("Enter ID : ");
        PACKAGE_ID_L.setFont(new Font("Serif", Font.BOLD,15));
        PACKAGE_ID_L.setBounds(100,440,120,30);
        ADD_EMP_P.add(PACKAGE_ID_L);

        Package_CB = new JComboBox<String>(Package_ID_ARR);
        Package_CB.setBounds(250,440,50,30);
        ADD_EMP_P.add(Package_CB);

        From_Date_L = new JLabel("From Date : ");
        From_Date_L.setFont(new Font("Serif", Font.BOLD,15));
        From_Date_L.setBounds(350,440,120,30);
        ADD_EMP_P.add(From_Date_L);

        From_Date_DC = new JDateChooser();
        From_Date_DC.setFont(new Font("Serif", Font.BOLD,15));
        From_Date_DC.setBounds(450,440,120,30);
        From_Date_DC.setDateFormatString("dd.MM.yyyy");
        ///
        LocalDate dateOfBirth = LocalDate.of(2022, Month.DECEMBER, 30);
        Instant min = dateOfBirth.plusYears(15).atStartOfDay(ZoneId.systemDefault()).toInstant();
        From_Date_DC.setSelectableDateRange(new Date(), Date.from(min));
        ///
        ADD_EMP_P.add(From_Date_DC);
        From_Date_DC.setDate(new Date());

        No_of_Days_L = new JLabel("Trip for Days? : ");
        No_of_Days_L.setFont(new Font("Serif", Font.BOLD,15));
        No_of_Days_L.setBounds(100,490,120,30);
        ADD_EMP_P.add(No_of_Days_L);

        No_of_Days_CB = new JComboBox<String>(No_OF_Days);
        No_of_Days_CB.setBounds(250,490,50,30);
        ADD_EMP_P.add(No_of_Days_CB);

        CONFIRM_B = new JButton("CONFIRM");
        CONFIRM_B.setBounds(360,600,100,25);
        CONFIRM_B.setBackground(Color.BLUE);
        CONFIRM_B.setForeground(Color.white);
        ADD_EMP_P.add(CONFIRM_B);

        Back_B = new JButton("BACK");
        Back_B.setBounds(200,600,80,25);
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

            if(e.getActionCommand().equalsIgnoreCase("CONFIRM")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO PROJECT.CONFIRMATION (BOOKING_C_STATUS, PACKAGE_P_ID, CUSTOMER_C_ID, NO_OF_DAYS, FROM_DATE) " +
                            "VALUES ('N', ?, ?, ?, TO_DATE(?,'DD.MM.YYYY'))");
                    ///
                    String P_ID = Package_CB.getSelectedItem().toString();
                    String No_Days = No_of_Days_CB.getSelectedItem().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    String date = dateFormat.format(From_Date_DC.getDate());


                    preparedStatement.setInt(1, Integer.parseInt(P_ID));
                    preparedStatement.setInt(2, C_ID);
                    preparedStatement.setInt(3, Integer.parseInt(No_Days));
                    preparedStatement.setString(4, date);
                    preparedStatement.executeQuery();

                    JOptionPane.showMessageDialog(new JFrame(), "PACKAGE SELECTED Successfully and Forwarded to Admin");
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
                    "SELECT P_ID, P_NAME, P_TYPE, P_PRICE, P_CITY,H_NAME, H_NO_OF_ROOMS, T_CAPACITY\n" +
                    "                    FROM package \n" +
                    "                    INNER JOIN TRANSPORTATION ON TRANSPORTATION_T_ID =T_ID\n" +
                    "                    INNER JOIN hotel ON  HOTEL_H_ID= H_ID  \n" +
                    "                    WHERE PACKAGE_VISIBILITY='Y'\n" +
                    "                    ORDER BY Package.P_ID ASC");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_Package.getModel();

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();
            String[] TableData  = {
                    "ID", "NAME", "TYPE", "PRICE", "CITY","HOTEL NAME", "NO OF ROOMS", "TRANSPORT CAPACITY"};
            defaultTableModel.addRow(TableData);
            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2),
                        rs.getString(3), String.valueOf(rs.getInt(4)),rs.getString(5),
                        rs.getString(6),String.valueOf(rs.getInt(7)),String.valueOf(rs.getInt(8))};
                Package_ID_AL.add(departmentData[0]);
                defaultTableModel.addRow(departmentData);
            }
            Package_ID_ARR = Package_ID_AL.toArray(new String[Package_ID_AL.size()]);
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
}
