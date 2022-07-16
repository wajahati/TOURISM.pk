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

public class DELETE_PACKAGE_Admin_GUI extends JFrame{
    JButton Delete_B, Back_B;
    JLabel DELETE_PACKAGE_L, PACKAGE_ID_L, Package_L;
    JComboBox<String> Package_CB;
    ArrayList<String> Package_ID_AL;
    String[] Package_ID_ARR;
    JTable table_Package;

    DELETE_PACKAGE_Admin_GUI(){
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

        DELETE_PACKAGE_L = new JLabel("DELETE PACKAGE");
        DELETE_PACKAGE_L.setFont(new Font("Serif", Font.BOLD,25));
        DELETE_PACKAGE_L.setBounds(170,20,500,35);
        DELETE_PACKAGE_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(DELETE_PACKAGE_L);

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
                ".", ".", ".", ".", ".",".", ".", "."
                }
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
        PACKAGE_ID_L.setBounds(150,480,120,30);
        ADD_EMP_P.add(PACKAGE_ID_L);

        Package_CB = new JComboBox<String>(Package_ID_ARR);
        Package_CB.setBounds(300,480,50,30);
        ADD_EMP_P.add(Package_CB);

        Delete_B = new JButton("DELETE");
        Delete_B.setBounds(360,540,100,25);
        Delete_B.setBackground(Color.BLUE);
        Delete_B.setForeground(Color.white);
        ADD_EMP_P.add(Delete_B);

        Back_B = new JButton("BACK");
        Back_B.setBounds(200,540,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);


        setVisible(true);
        DELETE_PACKAGE_Admin_GUI.MyListener listener = new MyListener();
        Delete_B.addActionListener(listener);
        Back_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equalsIgnoreCase("DELETE")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    String s1 = Package_CB.getSelectedItem().toString();
                    if (Package_NotUsed(Integer.parseInt(s1),con)) {
                        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM package WHERE P_ID=? ");
                        preparedStatement.setInt(1, Integer.parseInt(s1));
                        preparedStatement.executeQuery();
                        JOptionPane.showMessageDialog(new JFrame(), "Deleted Successfully Successfully");
                        dispose();
                        new DELETE_PACKAGE_Admin_GUI();
                    }
                    else{
                        JOptionPane.showMessageDialog(new JFrame(), "Package is set Invisible!\nOnly Customers who selected the package before its deletion can use it \n ");
                        PreparedStatement preparedStatement = con.prepareStatement("UPDATE Package SET PACKAGE_VISIBILITY='N' WHERE P_ID=? ");
                        preparedStatement.setInt(1, Integer.parseInt(s1));
                        preparedStatement.executeQuery();
                        dispose();
                        con.close();
                        new DELETE_PACKAGE_Admin_GUI();
                    }
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Package_GUI();
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

    public boolean Package_NotUsed(int P_ID ,Connection con){
        try{

            PreparedStatement ps1 =con.prepareStatement("Select * FROM Package WHERE P_ID=? AND P_ID NOT IN (SELECT PACKAGE_P_ID FROM Confirmation )");
            ps1.setInt(1,P_ID);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()){
                return true;
            }

        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }
}


