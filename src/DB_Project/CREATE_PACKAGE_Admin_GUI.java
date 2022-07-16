package DB_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CREATE_PACKAGE_Admin_GUI extends JFrame {
    ArrayList<String> Hotel_ID_AL, Transport_ID_AL;
    String[] Hotel_ID_ARR, Transport_ID_ARR;
    String [] Types={"Independent", "Couple", "Family"};

    JTable table_hotel, table_transportation;
    JButton Add_Hotel_B,Update_Hotel_B,Add_Transport_B, Update_Transport_B,Back_B, Submit_B;
    JLabel Transport_L, Create_L, Hotel_L;

    JComboBox<String> Hotel_CB, Transport_CB,Type_CB;

    JLabel Hotel_ID_L, Transport_ID_L ,Name_L,Type_L, Price_L ;
    JTextField Name_T, Price_T ;

    public CREATE_PACKAGE_Admin_GUI(){
        Hotel_ID_AL =new ArrayList<>();
        Transport_ID_AL= new ArrayList<>();

        JPanel Menu_P = new JPanel() {@Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("background.png");
            g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }};
        setSize(750, 750);
        setTitle("TOURISM NETWORK");
        add(Menu_P);
        Menu_P.setLayout(null);
        Menu_P.setBackground(new Color(153,153,153));


        Create_L = new JLabel("CREATE PACKAGE");
        Create_L.setFont(new Font("Serif", Font.BOLD,25));
        Create_L.setBounds(260,50,500,35);
        Create_L.setForeground(new Color(51,51,51));
        Menu_P.add(Create_L);

        Hotel_L = new JLabel("Hotel Table");
        Hotel_L.setFont(new Font("Serif", Font.BOLD,15));
        Hotel_L.setBounds(20,120,200,25);
        Hotel_L.setForeground(new Color(51,51,51));
        Menu_P.add(Hotel_L);

        Transport_L = new JLabel("Transport Table");
        Transport_L.setFont(new Font("Serif", Font.BOLD,15));
        Transport_L.setBounds(370,120,200,25);
        Transport_L.setForeground(new Color(51,51,51));
        Menu_P.add(Transport_L);


        ///// HOTEL
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
        scrollPane_hotel.setBounds(10, 150, 335, 100);
        Menu_P.add(scrollPane_hotel);

        /////Transportation
        table_transportation = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_transportation.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
                "ID", "Name", "Capacity", "Model"}
        ));
        showData_Transportation();
        table_transportation.setFont(new Font("Serif", Font.PLAIN,15));
        JScrollPane scrollPane_transportation = new JScrollPane(table_transportation);
        scrollPane_transportation.setBounds(355, 150, 335, 100);
        Menu_P.add(scrollPane_transportation);

        Add_Hotel_B = new JButton("ADD HOTEL");
        Add_Hotel_B.setBounds(10,255,150,30);
        Add_Hotel_B.setBackground(Color.BLUE);
        Add_Hotel_B.setForeground(Color.white);
        Menu_P.add(Add_Hotel_B);

        Update_Hotel_B = new JButton("UPDATE HOTEL");
        Update_Hotel_B.setBounds(170,255,150,30);
        Update_Hotel_B.setBackground(Color.BLUE);
        Update_Hotel_B.setForeground(Color.white);
        Menu_P.add(Update_Hotel_B);

        Add_Transport_B = new JButton("ADD TRANSPORT");
        Add_Transport_B.setBounds(360,255,150,30);
        Add_Transport_B.setBackground(Color.BLUE);
        Add_Transport_B.setForeground(Color.white);
        Menu_P.add(Add_Transport_B);

        Update_Transport_B = new JButton("UPDATE TRANSPORT");
        Update_Transport_B.setBounds(520,255,160,30);
        Update_Transport_B.setBackground(Color.BLUE);
        Update_Transport_B.setForeground(Color.white);
        Menu_P.add(Update_Transport_B);

        Hotel_ID_L = new JLabel("Select Hotel ID :");
        Hotel_ID_L.setFont(new Font("Serif", Font.BOLD,15));
        Hotel_ID_L.setBounds(10,310,120,30);
        Menu_P.add(Hotel_ID_L);

        Hotel_CB = new JComboBox<String>(Hotel_ID_ARR);
        Hotel_CB.setBounds(150,310,50,30);
        Menu_P.add(Hotel_CB);


        Transport_ID_L = new JLabel("Select Transport ID :");
        Transport_ID_L.setFont(new Font("Serif", Font.BOLD,15));
        Transport_ID_L.setBounds(355,310,170,30);
        Menu_P.add(Transport_ID_L);

        Transport_CB = new JComboBox<String>(Transport_ID_ARR);
        Transport_CB.setBounds(510,310,50,30);
        Menu_P.add(Transport_CB);

        Name_L = new JLabel("Set Name :");
        Name_L.setFont(new Font("Serif", Font.BOLD,15));
        Name_L.setBounds(150,340,120,30);
        Menu_P.add(Name_L);

        Name_T = new JTextField(20);
        Name_T.setBounds(300,340,200,30);
        Menu_P.add(Name_T);

        Type_L = new JLabel("Set Type :");
        Type_L.setFont(new Font("Serif", Font.BOLD,15));
        Type_L.setBounds(150,390,120,30);
        Menu_P.add(Type_L);

        Type_CB = new JComboBox<String>(Types);
        Type_CB.setBounds(300,390,120,30);
        Type_CB.setFont(new Font("Serif", Font.BOLD,15));
        Menu_P.add(Type_CB);

        Price_L = new JLabel("Set Price :");
        Price_L.setFont(new Font("Serif", Font.BOLD,15));
        Price_L.setBounds(150,440,120,30);
        Menu_P.add(Price_L);

        Price_T = new JTextField(20);
        Price_T.setBounds(300,440,200,30);
        Menu_P.add(Price_T);


        Back_B = new JButton("BACK");
        Back_B.setBounds(100,530,80,25);
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        Menu_P.add(Back_B);

        Submit_B = new JButton("SUBMIT");
        Submit_B.setBounds(280,530,80,25);

        Submit_B.setBackground(Color.BLUE);
        Submit_B.setForeground(Color.white);
        Menu_P.add(Submit_B);

        setVisible(true);
        CREATE_PACKAGE_Admin_GUI.MyListener listener = new MyListener();
        Add_Hotel_B.addActionListener(listener);
        Update_Hotel_B.addActionListener(listener);
        Add_Transport_B.addActionListener(listener);
        Update_Transport_B.addActionListener(listener);
        Submit_B.addActionListener(listener);
        Back_B.addActionListener(listener);
        }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("ADD HOTEL")) {
                dispose();
                new ADD_HOTEL_Admin_GUI();
            }
            else if (e.getActionCommand().equalsIgnoreCase("UPDATE HOTEL")) {
                dispose();
                new UPDATE_HOTEL_Admin_GUI();
            }
            else if (e.getActionCommand().equalsIgnoreCase("ADD TRANSPORT")) {
                dispose();
                new ADD_TRANSPORT_Admin_GUI();
            }
            else if (e.getActionCommand().equalsIgnoreCase("UPDATE TRANSPORT")) {
                dispose();
                new UPDATE_TRANSPORT_Admin_GUI();
            }
            else if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Package_GUI();
            }
            else if(!IsNotEmpty()){
                JOptionPane.showMessageDialog(null, "WARNING! Textfields left empty");
            }
            else if(!isNumeric(Price_T.getText())){
                JOptionPane.showMessageDialog(null, "WARNING! enter Price in Digits");
            }
            else if(e.getActionCommand().equalsIgnoreCase("SUBMIT")){

                try {
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                    PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO \"PROJECT\".\"PACKAGE\" (P_NAME, P_TYPE, P_PRICE, P_CITY, ADMIN_USERNAME, HOTEL_H_ID, TRANSPORTATION_T_ID, ORGANIZER_O_ID,PACKAGE_VISIBILITY)" +
                            " VALUES (?, ?, ?, ?, 'Admin', ?, ?, ?,'Y')\n");


                    String s1 = Name_T.getText();
                    String s2 = Type_CB.getSelectedItem().toString();
                    String s3 = Price_T.getText();
                    String s4 = Find_City(Hotel_CB.getSelectedItem().toString(), con);
                    String s5 = Hotel_CB.getSelectedItem().toString();
                    String s6 = Transport_CB.getSelectedItem().toString();



                    if(Exist_Package(s2,s3,s4,s5,s6,con) ){
                        JOptionPane.showMessageDialog(null,"ERROR !\nPackages with same Data Already Exists");
                    }
                    else if(!Exist_Organizer(con)){
                        JOptionPane.showMessageDialog(null,"ERROR !\nNo Organizer to assign Package to");
                    }

                    else{
                        String s7 = Random_Organizer(con);
                        System.out.println(s7);
                        System.out.println("Random Created");
                        preparedStatement.setString(1, s1);
                        preparedStatement.setString(2, s2);
                        preparedStatement.setInt(3, Integer.parseInt(s3));
                        preparedStatement.setString(4, s4);
                        preparedStatement.setInt(5, Integer.parseInt(s5));
                        preparedStatement.setInt(6, Integer.parseInt(s6));
                        preparedStatement.setInt(7, Integer.parseInt(s7));
                        preparedStatement.executeQuery();

                        Increment_Trips_Managed(Integer.parseInt(s7), con);
                        Increase_Slalary(Integer.parseInt(s7),con);
                        JOptionPane.showMessageDialog(null, "Package Created Successfully\nPress OK to Return to Package Main Page");
                        dispose();
                        con.close();
                        new MAIN_Package_GUI();
                    }
                } catch (Exception ae) {
                    System.out.println(ae.toString());
                    ae.printStackTrace();
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


    private void showData_Transportation(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Transportation");
            preparedStatement = con.prepareStatement("SELECT T_ID,T_NAME, T_CAPACITY, T_MODEL FROM Transportation ORDER BY T_ID");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_transportation.getModel();

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2),
                        String.valueOf(rs.getInt(3)), rs.getString(4)};
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


    public String Random_Organizer(Connection con){
        String C_ID="";
        try{

            PreparedStatement preparedStatement =con.prepareStatement("SELECT O_id FROM (SELECT O_id FROM Organizer ORDER BY dbms_random.value) WHERE rownum = 1");
            ResultSet r1=preparedStatement.executeQuery();
            if(r1.next()) {
                C_ID = String.valueOf(r1.getInt(1));
                return C_ID;
            }
        }
        catch (Exception ea){
            ea.printStackTrace();
        }
        return C_ID;
    }

    public String Find_City(String Hotel_id, Connection con) {
        String H_Name="";
        try{
            PreparedStatement st = con.prepareStatement("select H_CITY from HOTEL where H_ID = ?");
            st.setInt(1, Integer.parseInt(Hotel_id));
            ResultSet r1=st.executeQuery();
            if(r1.next()) {
                H_Name = r1.getString(1);
                System.out.println("Hotel_City ->"+H_Name);
            }
        }
        catch (Exception ea){
            ea.printStackTrace();
        }
        return H_Name;
    }


    public boolean Exist_Package(String Type, String Price, String City, String Hotel_ID, String Transport_ID, Connection connection) {
        try{
            PreparedStatement st = connection.prepareStatement("select P_ID from Package where  P_TYPE = ? and P_PRICE = ? and P_CITY = ? and HOTEL_H_ID = ? and TRANSPORTATION_T_ID = ?");
            st.setString(1, Type);
            st.setInt(2, Integer.parseInt(Price));
            st.setString(3, City);
            st.setInt(4, Integer.parseInt(Hotel_ID));
            st.setInt(5, Integer.parseInt(Transport_ID));
            ResultSet r1=st.executeQuery();
            if(r1.next()) {
                return true;
            }
            return false;
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }

    public void Increment_Trips_Managed(int Oid,Connection con){
        try {
            PreparedStatement ps1 = con.prepareStatement("SELECT O_TRIPS_ORGANIZED FROM Organizer Where O_ID = ? ");
            PreparedStatement ps2 = con.prepareStatement("UPDATE Organizer SET  O_TRIPS_ORGANIZED = ? WHERE O_ID= ?");
            int no_of_trips=0;
            ps1.setInt(1, Oid);
            ResultSet r1=ps1.executeQuery();
            if(r1.next()) {
                no_of_trips = r1.getInt(1);
                ps2.setInt(1, (no_of_trips+1));
                ps2.setInt(2, Oid);
                ps2.executeQuery();
            }




        }catch (Exception ea){
            ea.printStackTrace();
        }
    }

    public void Increase_Slalary(int Oid,Connection con){
        try {
            PreparedStatement ps2 = con.prepareStatement("UPDATE SALARY SET  AMOUNT =(SELECT Amount FROM Salary WHERE ORGANIZER_O_ID=? ) + (0.01*(SELECT Amount FROM Salary WHERE ORGANIZER_O_ID=? )) WHERE ORGANIZER_O_ID= ?");
            ps2.setInt(1, Oid);
            ps2.setInt(2, Oid);
            ps2.setInt(3, Oid);
            ps2.executeQuery();

        }catch (Exception ea){
            ea.printStackTrace();
        }
    }


    public boolean Exist_Organizer(Connection connection) {
        try{
            PreparedStatement st = connection.prepareStatement("SELECT count(*) FROM Organizer");
            ResultSet r1=st.executeQuery();
            if(r1.next()) {
                return true;
            }
            return false;
        } catch (Exception ea){
            ea.printStackTrace();
        }
        return false;
    }
    private boolean IsNotEmpty(){
        if (Name_T.getText().isEmpty() || Price_T.getText().isEmpty() ){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
