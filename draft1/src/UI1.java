import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UI1{

    private JFrame Frame;
    private JLabel Label;
    private JButton register;
    private JButton login;
    private JPanel panel;
    private JTextField inpID, inpPassword;
    public String ID, password, BookID;
    private String BookSelectedForRent, BookSelectedForbBorrow;
    private int BookSelectedForRentInt, BookSelectedForBorrowInt;
    public static int pointsInt;

    void IntroScreen(){

        panel = new JPanel();
        panel.setLayout(new GridLayout(3,0));
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.add(panel, BorderLayout.CENTER);

        Label = new JLabel("Welcome");
        Label.setBounds(200, 100, 80, 50);
        panel.add(Label);
        login = new JButton("Login");
        panel.add(login);
        register = new JButton("Register");
        loginClick();
        registerClick();
        panel.add(register);
        Frame.setSize(400, 500);
        //Frame.pack();
        Frame.setVisible(true);

    }

    void loginScreen(){

        panel = new JPanel();
        panel.setLayout(new GridLayout(8,0));
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.add(panel, BorderLayout.CENTER);

        Label = new JLabel("Login Here");
        Label.setBounds(200, 100, 80, 50);
        panel.add(Label);
        
        inpID = new JTextField();
        inpPassword = new JTextField();
        JLabel enterIDLabel = new JLabel("Enter ID");
        JLabel enterPasswordLabel = new JLabel("Enter Password");
        JLabel status = new JLabel();
        login = new JButton("Login");
        login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ID = inpID.getText();
                password = inpPassword.getText();
                try {
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
                    Statement stmt=con.createStatement();  
                    System.out.print("SELECT * FROM univlib.user_details WHERE ID = \""+ ID+"\" AND password =  \""+password+"\"\n");
                    ResultSet rs = stmt.executeQuery("SELECT * FROM univlib.user_details WHERE ID = "+ID+" AND password =  \""+password+"\"");
                    if (rs.next() == true){
                        status.setText("Login Successfull");
                        userScreen();
                    }
                    else{
                        status.setText("Login Unsuccessfull");
                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        panel.add(enterIDLabel);
        panel.add(inpID);
        panel.add(enterPasswordLabel);
        panel.add(inpPassword);
        panel.add(login);
        panel.add(status);

        Frame.setSize(400, 500);
        //Frame.pack();
        Frame.setVisible(true);
    }

    void registerScreen(){

        panel = new JPanel();
        panel.setLayout(new GridLayout(20,0));
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.add(panel, BorderLayout.CENTER);

        JTextField fNameJTextField = new JTextField();
        JTextField lNameJTextField = new JTextField();
        JTextField areaLivingInJTextField = new JTextField();
        JTextField passwordJTextField = new JTextField();

        register = new JButton("Register");
        register.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Connection con;
                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
                    PreparedStatement stmt=con.prepareStatement("INSERT INTO user_details (FirstName, LastName, AreaLiving, password) VALUES (?,?,?,?)");
                    

                    String FName = fNameJTextField.getText();
                    String LName = lNameJTextField.getText();
                    String AreaLivingIn = areaLivingInJTextField.getText();
                    password = passwordJTextField.getText();
                    
                    stmt.setString(1, FName);
                    stmt.setString(2, LName);
                    stmt.setString(3, AreaLivingIn);
                    stmt.setString(4, password);
                    
                    stmt.executeUpdate();

                    panel.add(new JLabel("Register Successfull"));
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery("SELECT ID FROM user_details WHERE id=(SELECT max(id) FROM user_details)");
                    rs.next();
                    ID = rs.getString("ID");
                    panel.add(new JLabel("Your ID is " + ID));
                    
                    
                } catch (Exception e1) {
                    panel.add(new JLabel("Unsucessfull"));
                    e1.printStackTrace();
                }
            }
        });

        JButton continuetoLogin = new JButton("Continue");
        continuetoLogin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                loginScreen();
            }
        });


        Label = new JLabel("Register here");
        Label.setBounds(200, 100, 80, 50);
        panel.add(Label);

        panel.add(new JLabel("Enter First Name"));
        panel.add(fNameJTextField);
        panel.add(new JLabel("Enter Last Name"));
        panel.add(lNameJTextField);
        panel.add(new JLabel("Enter Area you live in"));
        panel.add(areaLivingInJTextField);
        panel.add(new JLabel("Enter Password"));
        panel.add(passwordJTextField);
        panel.add(register);
        panel.add(continuetoLogin);

        Frame.setSize(500, 900);
        //Frame.pack();
        Frame.setVisible(true);

    }

    void userScreen(){

        //set panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(9,0));
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.add(panel, BorderLayout.CENTER);

        Label = new JLabel("User Screen");
        Label.setBounds(200, 100, 80, 50);
        //panel.add(Label);

        JLabel points = new JLabel();
        JLabel Userdetails = new JLabel();

        JButton rent = new JButton("Rent a book");
        rent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    rentScreen();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        JButton borrow = new JButton("Borrow a Book");
        borrow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    borrowScreen();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        JLabel booksborrowed = new JLabel();
        JLabel booksrented = new JLabel();


        try {
            //create connection and statement
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
            Statement stmt=con.createStatement();

            //points
            ResultSet rs = stmt.executeQuery("SELECT Points FROM univlib.user_details WHERE ID = " + ID);
            rs.next();
            pointsInt = Integer.parseInt(rs.getString("Points"));
            points.setText("Points : " + pointsInt);
            

            //userdetails
            ResultSet readuser = stmt.executeQuery("SELECT * FROM univlib.user_details WHERE ID = " + ID);
            readuser.next();
            Userdetails.setText("<html>Name : "+readuser.getString("FirstName")+" "+readuser.getString("LastName")+"<br/>User ID : "+readuser.getString("ID")+"</html>");
            
            
            //show books rented out
            String queryBooksrented = "SELECT user_details.FirstName, user_details.LastName, books.name FROM trade INNER JOIN user_details ON trade.BorrowedFrom = user_details.ID INNER JOIN books ON trade.BookID = books.ID WHERE user_details.ID = "+ID;
            ResultSet booksrentedQueryStatement = stmt.executeQuery(queryBooksrented);
            int i =1;
            String booksrentedLabelString = "";
            while(booksrentedQueryStatement.next() != false){
                booksrentedLabelString = booksrentedLabelString+"<br/>"+booksrentedQueryStatement.getString("books.name");
                booksrented.setText("<html>"+booksrentedLabelString+"</html>");
                i++;
            }


            //show books borrowed
            String queryBooksBorrowed = "SELECT user_details.FirstName, user_details.LastName, books.name FROM trade INNER JOIN user_details ON trade.RentedTo = user_details.ID INNER JOIN books ON trade.BookID = books.ID WHERE user_details.ID = "+ID;
            ResultSet booksBorrowedQueryStatement = stmt.executeQuery(queryBooksBorrowed);
            String booksBorrowedLabel = "";
            while(booksBorrowedQueryStatement.next() != false){
                booksBorrowedLabel = booksBorrowedLabel+"<br/>"+booksBorrowedQueryStatement.getString("books.name");
                booksborrowed.setText("<html>"+booksBorrowedLabel+"</html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        

        panel.add(Label);
        panel.add(points);
        panel.add(Userdetails);
        panel.add(new JLabel("Books Rented"));
        panel.add(booksrented);
        panel.add(new JLabel("Books Borrowed"));
        panel.add(booksborrowed);
        panel.add(rent);
        panel.add(borrow);

        

        Frame.setSize(400, 500);
        //Frame.pack();
        Frame.setVisible(true);

    }

    public void borrowScreen() throws Exception{
        panel = new JPanel();
        panel.setLayout(new GridLayout(9,0));
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.add(panel, BorderLayout.CENTER);
        
        panel.add(new JLabel("Books for rent"));

        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
        Statement stmt=con.createStatement();

        ResultSet booksAvailable = stmt.executeQuery("SELECT BookID, books.name FROM univlib.trade INNER JOIN books ON trade.BookID = books.ID WHERE trade.RentedTo IS NULL");
        String bookNames[] = new String[100];
        int bookIDs[] = new int[100];
        int i = 0;
        while(booksAvailable.next() == true){
            bookNames[i] = booksAvailable.getString("name");
            bookIDs[i] = booksAvailable.getInt("BookID");
            i++;
        }


        JComboBox booksavailablechooser = new JComboBox<>(bookNames);
        booksavailablechooser.setBounds(50, 50,90,20);
        booksavailablechooser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                BookSelectedForbBorrow = bookNames[booksavailablechooser.getSelectedIndex()];
                BookSelectedForBorrowInt = bookIDs[booksavailablechooser.getSelectedIndex()];
            }
        });

        //PreparedStatement rentBookPS = con.prepareStatement("UPDATE trade SET RentedTo = "+ID+" WHERE BookID = "+BookSelectedForRentInt);
        

        JButton RentExecuter = new JButton("Borrow");
        RentExecuter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    if(pointsInt>0){
                        int result = stmt.executeUpdate("UPDATE trade SET RentedTo = '"+ID+"' WHERE BookID = '"+BookSelectedForBorrowInt+"' AND RentedTo IS NULL");   
                        pointsInt--;
                        if(result == 1){
                            System.out.println("Success");
                            panel.add(new JLabel("Book Borrowed SuccessFully"));
                        }
                        else{
                            System.out.println("Failed");
                            System.out.println("Failed");
                        }
                        
                    }
                    else if(pointsInt<=0){
                        panel.add(new JLabel("Low Points!!!!!"));
                        userScreen();
                    }
                    int result2 = stmt.executeUpdate("UPDATE user_details SET Points = "+pointsInt+" WHERE ID = "+ID);
                    
                    if(result2 == 1){
                        System.out.println("Success");
                        panel.add(new JLabel("Points Updated Sucessfully"));
                    }
                    else{
                        System.out.println("Failed");
                        System.out.println("Failed");
                    }

                    userScreen();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        panel.add(new JLabel("Books Available for Rent"));
        panel.add(booksavailablechooser);
        panel.add(RentExecuter);

        Frame.setSize(400, 500);
        //Frame.pack();
        Frame.setVisible(true);

    }

    public void rentScreen() throws Exception{
        panel = new JPanel();
        panel.setLayout(new GridLayout(9,0));
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.add(panel, BorderLayout.CENTER);
        
        panel.add(new JLabel("Books to Rent"));

        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
        Statement stmt=con.createStatement();

        ResultSet booksAvailable = stmt.executeQuery("SELECT * FROM books");
        
        String bookNames[] = new String[100];
        int bookIDs[] = new int[100];
        int i = 0;
        while(booksAvailable.next() == true){
            bookNames[i] = booksAvailable.getString("name");
            bookIDs[i] = booksAvailable.getInt("ID");
            i++;
        }

        JComboBox booksavailablechooser = new JComboBox<>(bookNames);
        booksavailablechooser.setBounds(50, 50,90,20);
        booksavailablechooser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                BookSelectedForRent = bookNames[booksavailablechooser.getSelectedIndex()];
                BookSelectedForRentInt = bookIDs[booksavailablechooser.getSelectedIndex()];
            }
        });


        JButton RentExecuter = new JButton("Rent Out");
        RentExecuter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    //int result = rentBookPS.executeUpdate();
                    int result = stmt.executeUpdate("INSERT INTO trade(BorrowedFrom, BookID) VALUES("+ID+","+BookSelectedForRentInt+")");
                    pointsInt++;
                    int result2 = stmt.executeUpdate("UPDATE user_details SET Points = "+pointsInt+" WHERE ID = "+ID);
                    if(result == 1){
                        System.out.println("Success");
                        panel.add(new JLabel("Book Rented SuccessFully"));
                    }
                    else{
                        System.out.println("Failed");
                        System.out.println("Failed");
                    }
                    if(result2 == 1){
                        System.out.println("Success");
                        panel.add(new JLabel("Points Updated Sucessfully"));
                    }
                    else{
                        System.out.println("Failed");
                        System.out.println("Failed");
                    }
                    userScreen();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        panel.add(new JLabel("Books Available for Rent"));
        panel.add(booksavailablechooser);
        panel.add(RentExecuter);

        Frame.setSize(400, 500);
        //Frame.pack();
        Frame.setVisible(true);

    }

    public void loginClick(){
        ActionListener btlst1 = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                loginScreen();
                
            }
            
        };
        
        login.addActionListener(btlst1);

    }

    public void registerClick(){
        ActionListener btlst2 = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                registerScreen();
                
            }
            
        };
        
        register.addActionListener(btlst2);

    }

    
}
