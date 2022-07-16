package DB_Project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class COMPLAIN_RESONSE_Admin_GUI extends JFrame{

    JButton DIS_COMPLAIN_B, WRITE_RESPONSE_B, SUBMIT_B, Back_B;
    JLabel MANAGE_BILL_L, Remaining_Char_L1,BILL_L,COMPLAIN_L,Respond_L;
    JTextArea Complain_Details_T, Complain_RESPONSE_T;
    JComboBox<String> Organizer_CB;
    ArrayList<String> Organizer_ID_AL;
    String[] Organizer_ID_ARR;
    DefaultStyledDocument doc;
    JScrollPane COMPLAIN,DETAILS;
    JPanel ADD_EMP_P;
    JTable table_BILL;

    public COMPLAIN_RESONSE_Admin_GUI(){
        Organizer_ID_AL= new ArrayList<>();
        ADD_EMP_P = new JPanel() {@Override
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

        MANAGE_BILL_L = new JLabel("COMPLAINS");
        MANAGE_BILL_L.setFont(new Font("Serif", Font.BOLD,25));
        MANAGE_BILL_L.setBounds(170,20,500,35);
        MANAGE_BILL_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(MANAGE_BILL_L);

        BILL_L = new JLabel("PENDING COMPLAINS : "+Count_Pending_Complaint());
        BILL_L.setFont(new Font("Serif", Font.BOLD,15));
        BILL_L.setBounds(33,100,700,25);
        BILL_L.setForeground(new Color(51,51,51));
        ADD_EMP_P.add(BILL_L);

        COMPLAIN_L = new JLabel("COMPLAIN :");
        COMPLAIN_L.setFont(new Font("Serif", Font.BOLD,15));
        COMPLAIN_L.setBounds(366,100,700,25);
        COMPLAIN_L.setForeground(new Color(51,51,51));
        COMPLAIN_L.setVisible(false);
        ADD_EMP_P.add(COMPLAIN_L);



        table_BILL = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table_BILL.setModel(new DefaultTableModel(
                new Object [][] {}, new String [] {
                ".", ".", ".", ".", "."
        }
        ));
        showData_Organizer();
        table_BILL.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(table_BILL);
        table_BILL.setFont(new Font("Serif", Font.PLAIN,15));

        JScrollPane scrollPane_transportation = new JScrollPane(table_BILL);
        scrollPane_transportation.setBounds(33, 130, 300, 200);
        ADD_EMP_P.add(scrollPane_transportation);

        Complain_Details_T = new JTextArea();
        Complain_Details_T.setEditable(false);
        Complain_Details_T.setLineWrap(true);
        Complain_Details_T.setWrapStyleWord(true);
        Complain_Details_T.setFont(new Font("Serif", Font.PLAIN,15));
        COMPLAIN = new JScrollPane(Complain_Details_T);
        COMPLAIN.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        COMPLAIN.setBounds(366, 130, 300, 200);
        COMPLAIN.setVisible(false);
        ADD_EMP_P.add(COMPLAIN);

        Complain_RESPONSE_T = new JTextArea();
        doc = new DefaultStyledDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(700));
        doc.addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e) { updateCount();}
            @Override
            public void insertUpdate(DocumentEvent e) { updateCount();}
            @Override
            public void removeUpdate(DocumentEvent e) { updateCount();}
        });
        Complain_RESPONSE_T.setDocument(doc);
        Complain_RESPONSE_T.setLineWrap(true);
        Complain_RESPONSE_T.setWrapStyleWord(true);

        Complain_RESPONSE_T.setFont(new Font("Serif", Font.PLAIN,15));
        DETAILS = new JScrollPane(Complain_RESPONSE_T);
        DETAILS.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        DETAILS.setBounds(100, 420, 500, 200);
        DETAILS.setVisible(false);
        ADD_EMP_P.add(DETAILS);


        Remaining_Char_L1 = new JLabel("");
        Remaining_Char_L1.setFont(new Font("Serif", Font.BOLD,15));
        Remaining_Char_L1.setBounds(420,620,250,30);
        ADD_EMP_P.add(Remaining_Char_L1);

        Respond_L = new JLabel("Enter Response here :");
        Respond_L.setFont(new Font("Serif", Font.BOLD,15));
        Respond_L.setBounds(100,390,250,30);
        Respond_L.setVisible(false);
        ADD_EMP_P.add(Respond_L);

        DIS_COMPLAIN_B = new JButton("SHOW COMPLAIN");
        DIS_COMPLAIN_B.setBounds(100,360,200,35);
        DIS_COMPLAIN_B.setFont(new Font("Serif", Font.BOLD,15));
        DIS_COMPLAIN_B.setBackground(Color.BLUE);
        DIS_COMPLAIN_B.setForeground(Color.white);
        ADD_EMP_P.add(DIS_COMPLAIN_B);

        Organizer_CB = new JComboBox<String>(Organizer_ID_ARR);
        Organizer_CB.setBounds(40,360,50,30);
        ADD_EMP_P.add(Organizer_CB);

        WRITE_RESPONSE_B = new JButton("RESPOND");
        WRITE_RESPONSE_B.setBounds(366,360,160,35);
        WRITE_RESPONSE_B.setFont(new Font("Serif", Font.BOLD,15));
        WRITE_RESPONSE_B.setBackground(Color.BLUE);
        WRITE_RESPONSE_B.setForeground(Color.white);
        WRITE_RESPONSE_B.setVisible(false);
        ADD_EMP_P.add(WRITE_RESPONSE_B);


        SUBMIT_B = new JButton("SUBMIT");
        SUBMIT_B.setBounds(300,650,100,30);
        SUBMIT_B.setFont(new Font("Serif", Font.BOLD,15));
        SUBMIT_B.setBackground(Color.BLUE);
        SUBMIT_B.setForeground(Color.white);
        SUBMIT_B.setVisible(false);
        ADD_EMP_P.add(SUBMIT_B);


        Back_B = new JButton("BACK");
        Back_B.setBounds(150,650,100,30);
        Back_B.setFont(new Font("Serif", Font.BOLD,15));
        Back_B.setBackground(Color.BLUE);
        Back_B.setForeground(Color.white);
        ADD_EMP_P.add(Back_B);

        setVisible(true);
        MyListener listener = new MyListener();
        DIS_COMPLAIN_B.addActionListener(listener);
        WRITE_RESPONSE_B.addActionListener(listener);
        SUBMIT_B.addActionListener(listener);
        Back_B.addActionListener(listener);
    }

    public class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
             if (e.getActionCommand().equalsIgnoreCase("BACK")) {
                dispose();
                new MAIN_Admin_GUI();
            }
            if (Count_Pending_Complaint()!=0) {
                if (e.getActionCommand().equalsIgnoreCase("SHOW COMPLAIN")) {

                    try {
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                        PreparedStatement preparedStatement = con.prepareStatement("SELECT COMPLAIN_DETAILS FROM Complain WHERE COMPLAIN_ID=?");

                        String s1 = Organizer_CB.getSelectedItem().toString();
                        preparedStatement.setInt(1, Integer.parseInt(s1));

                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            COMPLAIN_L.setVisible(true);
                            COMPLAIN.setVisible(true);
                            WRITE_RESPONSE_B.setVisible(true);
                            Complain_Details_T.setText(rs.getString(1));
                            con.close();
                        }
                    } catch (Exception ae) {
                        System.out.println(ae.toString());
                    }

                }
                else if (e.getActionCommand().equalsIgnoreCase("RESPOND")) {
                    DETAILS.setVisible(true);
                    Respond_L.setVisible(true);
                    Complain_RESPONSE_T.setText("");
                    SUBMIT_B.setVisible(true);
                    setVisible(true);

                }
                else if (Count_Pending_Complaint()==0){
                    JOptionPane.showMessageDialog(new JFrame(),"WARNING! There are no Pending Complaints to respond to");
                }
                else if (!IsNotEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),"WARNING! Respond left empty");
                }

                else if (e.getActionCommand().equalsIgnoreCase("SUBMIT")) {
                    try {
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
                        PreparedStatement preparedStatement = con.prepareStatement("UPDATE Complain SET RESPONSE=?, RESPONSE_STATUS='Y'  WHERE COMPLAIN_ID=?");

                        String s1 = Organizer_CB.getSelectedItem().toString();
                        String s2 = Complain_RESPONSE_T.getText();
                        preparedStatement.setString(1, s2);
                        preparedStatement.setInt(2, Integer.parseInt(s1));
                        preparedStatement.executeQuery();
                        JOptionPane.showMessageDialog(new JFrame(),"Response Delivered Successfully");
                        dispose();
                        con.close();
                        new COMPLAIN_RESONSE_Admin_GUI();
                    } catch (Exception ae) {
                        System.out.println(ae.toString());
                    }
                }
            }

        }
    }
    private void showData_Organizer(){
        try{
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement;

            System.out.println("Organizer");
            preparedStatement = con.prepareStatement("SELECT COMPLAIN_ID,COMPLAIN_SUBJECT,to_char(COMPLAIN_DATE,'dd-MON-yyyy') FROM Complain WHERE RESPONSE_STATUS='N' ORDER BY COMPLAIN_ID");
            DefaultTableModel defaultTableModel = (DefaultTableModel) table_BILL.getModel();
            String [] data={
                    "Complain ID","SUBJECT","STATUS","DATED"};

            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0); }

            ResultSet rs = preparedStatement.executeQuery();
            defaultTableModel.addRow(data);
            while (rs.next()){
                String[] departmentData = {String.valueOf(rs.getInt(1)), rs.getString(2),
                        "Not Responded", String.valueOf(rs.getString(3))};
                System.out.println(departmentData[0]);
                Organizer_ID_AL.add(departmentData[0]);
                defaultTableModel.addRow(departmentData);
            }
            Organizer_ID_ARR = Organizer_ID_AL.toArray(new String[Organizer_ID_AL.size()]);
            con.close();
        }
        catch (Exception ea){
            ea.printStackTrace();
        }
    }

    private void updateCount() {
        Remaining_Char_L1.setText((700 - doc.getLength()) + " characters remaining"); }

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

    public int Count_Pending_Complaint(){
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "Project", "123456");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT count(*) FROM Complain WHERE RESPONSE_STATUS='N'");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
              return rs.getInt(1);
            }
            con.close();
        } catch (Exception ae) {
            System.out.println(ae.toString());
        }
        return 0;
    }

    private boolean IsNotEmpty(){

        if (Complain_RESPONSE_T.getText().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}
