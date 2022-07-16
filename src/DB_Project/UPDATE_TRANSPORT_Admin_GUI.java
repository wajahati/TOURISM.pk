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

public class UPDATE_TRANSPORT_Admin_GUI extends JFrame {
    JButton Submit_B, Back_B;
    JLabel REG_Customer, Transport_l, Select_Transport_L, Tname_L, TModel_L, TCapacity_L;
    JTextField Tname_T, TModel_T;
    JComboBox<String> Capacity_CB;
    String [] Capacity ={"1","2","3","4","5","6","7","8"};

    ArrayList<String> Transport_ID_AL;
    String[] Transport_ID_ARR;

    JTable table_Transport;
    JComboBox<String> Transport_CB;
    JButton Update_Transport_B;

    public UPDATE_TRANSPORT_Admin_GUI(){
        Transport_ID_AL =new ArrayList<>();

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

        REG_Customer = new JLabel("UPDATE TRANSPORT");
        REG_Customer.setFont(new Font("Serif", Font.BOLD,25));
        REG_Customer.setBounds(200,50,500,35);
        REG_Customer.setForeground(new Color(51,51,51));
        ADD_USER_P.add(REG_Customer);

        Transport_l = new JLabel("TRANSPORT Table :");
        Transport_l.setFont(new Font("Serif", Font.BOLD,15));
        Transport_l.setBounds(100,80,500,35);
        Transport_l.setForeground(new Color(51,51,51));
        ADD_USER_P.add(Transport_l);

        table_Transport = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table_Transport.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
                "ID", "Name", "Capacity", "Model"}
        ));
        showData_Transport();
        table_Transport.setFont(new Font("Serif", Font.PLAIN,15));

        JScrollPane scrollPane_hotel = new JScrollPane(table_Transport);
        scrollPane_hotel.setBounds(100, 120, 500, 200);
        ADD_USER_P.add(scrollPane_hotel);


        Select_Transport_L = new JLabel("Select ID : ");
        Select_Transport_L.setFont(new Font("Serif", Font.BOLD,15));
        Select_Transport_L.setBounds(100,350,100,35);
        Select_Transport_L.setForeground(new Color(51,51,51));
        ADD_USER_P.add(Select_Transport_L);

        Transport_CB = new JComboBox<String>(Transport_ID_ARR);
        Transport_CB.setBounds(220,350,50,30);
        ADD_USER_P.add(Transport_CB);

        Update_Transport_B = new JButton("UPDATE");
        Update_Transport_B.setBounds(300,350,80,25);
        Update_Transport_B.setBackground(Color.BLUE);
        Update_Transport_B.setForeground(Color.white);
        ADD_USER_P.add(Update_Transport_B);

        Tname_L = new JLabel("Transport Name :");
        Tname_L.setFont(new Font("Serif", Font.BOLD,15));
        Tname_L.setBounds(150,390,150,30);
        Tname_L.setVisible(false);
        ADD_USER_P.add(Tname_L);

        Tname_T = new JTextField();
        Tname_T.setBounds(300,390,300,30);
        Tname_T.setVisible(false);
        ADD_USER_P.add(Tname_T);

        TCapacity_L = new JLabel("Capacity :");
        TCapacity_L.setFont(new Font("Serif", Font.BOLD,15));
        TCapacity_L.setBounds(150,440,120,30);
        TCapacity_L.setVisible(false);
        ADD_USER_P.add(TCapacity_L);

        Capacity_CB = new JComboBox<String>(Capacity);
        Capacity_CB.setBounds(300,440,50,30);
        Capacity_CB.setVisible(false);
        ADD_USER_P.add(Capacity_CB);

        TModel_L = new JLabel("Transport Model :");
        TModel_L.setFont(new Font("Serif", Font.BOLD,15));
        TModel_L.setBounds(150,490,150,30);
        TModel_L.setVisible(false);
        ADD_USER_P.add(TModel_L);

        TModel_T = new JTextField(20);
        TModel_T.setBounds(300,490,300,30);
        TModel_T.setVisible(false);
        ADD_USER_P.add(TModel_T);


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
        Update_Transport_B.addActionListener(listener);
        Submit_B.addActionListener(listener);
        Back_B.addActionListener(listener);

    }
    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("UPDATE")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("SELECT T_NAME,T_CAPACITY,T_MODEL FROM Transportation WHERE T_ID=?");
                    int s1 = Integer.parseInt(Transport_CB.getSelectedItem().toString());
                    preparedStatement.setInt(1,s1);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()){
                        Tname_L.setVisible(true);
                        Tname_T.setVisible(true);
                        Tname_T.setText(rs.getString(1));
                        TModel_L.setVisible(true);
                        TModel_T.setVisible(true);
                        TModel_T.setText(rs.getString(2));
                        TCapacity_L.setVisible(true);
                        Capacity_CB.setVisible(true);
                        Capacity_CB.setSelectedItem(Capacity[(Integer.parseInt(rs.getString(2)))-1]);
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
                    PreparedStatement preparedStatement = con.prepareStatement("UPDATE Transportation SET T_NAME=?,T_CAPACITY=?,T_MODEL=? WHERE T_ID=?");

                    String s1 = Tname_T.getText();
                    String s2 = TModel_T.getText();
                    int s3 = Integer.parseInt(Capacity_CB.getSelectedItem().toString());
                    int s4 = Integer.parseInt(Transport_CB.getSelectedItem().toString());

                    preparedStatement.setString(1, s1);
                    preparedStatement.setInt(2, s3);
                    preparedStatement.setString(3, s2);
                    preparedStatement.setInt(4, s4);

                    preparedStatement.executeQuery();

                    JOptionPane.showMessageDialog(null, "Transport Updated Successfully\nPress OK to Return to Create Package Page");
                    dispose();
                    con.close();
                    new CREATE_PACKAGE_Admin_GUI();
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                }
            }
        }
    }

    private void showData_Transport(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Hotel");
            preparedStatement = con.prepareStatement("SELECT T_ID,T_NAME, T_CAPACITY, T_MODEL FROM Transportation ORDER BY T_ID");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_Transport.getModel();

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2), String.valueOf(rs.getInt(3)),
                        rs.getString(4)};
                System.out.println(departmentData[0]);
                Transport_ID_AL.add(departmentData[0]);
                defaultTableModel.addRow(departmentData);
            }
            Transport_ID_ARR = Transport_ID_AL.toArray(new String[Transport_ID_AL.size()]);
            con.close();
        }
        catch (Exception ea){
            ea.printStackTrace();
        }
    }
    private boolean IsNotEmpty(){

        if (Tname_T.getText().isEmpty() || TModel_T.getText().isEmpty() ){
            return false;
        }
        else{
            return true;
        }
    }
}
