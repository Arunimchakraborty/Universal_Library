import java.sql.*;
import java.util.Scanner;  
public class backend implements functions{
    int points = 2;
    Scanner sc = new Scanner(System.in);
    public void login(){
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
            System.out.println("Enter ID ");
            int ID = sc.nextInt();
            System.out.println("Enter Password : ");
            String password = sc.next();
            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM univlib.user_details WHERE ID = " + ID + " AND password = " + password);
            if(rs.next() == true){
                System.out.println("ID FOUND\nLOGIN SUCCESFULL");
            }
            else{
                System.out.println("NO ID FOUND PLEASE RENTER ID");
                login();
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void register() throws Exception{
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
        System.out.println("Enter First Name : ");
        String FName = sc.next();
        System.out.println("Enter Last Name : ");
        String LName = sc.next();
        System.out.println("Enter Area Living : ");
        String Area = sc.next();
        System.out.println("Enter Password : ");
        String password = sc.next();
        PreparedStatement stmt=con.prepareStatement("INSERT INTO user_details (FirstName, LastName, AreaLiving, password) VALUES (?,?,?,?)");  
        //ResultSet rs = stmt.executeQuery("INSERT INTO user_details (FirstName, LastName, AreaLiving, password) VALUES ("+FName+", "+LName+", "+Area+", "+password+")");
        stmt.setString(1, FName);
        stmt.setString(2, LName);
        stmt.setString(3, Area);
        stmt.setString(4, password);
        stmt.executeUpdate();
    }

    @Override
    public void showRented() {
        try {
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/univlib","root","1234");
            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT * FROM trade WHERE BorrowedFrom = 2");
            while(rs.next() == true){
                System.out.println("Book ID : " + rs.getInt(3) + " Book rented out to : " + rs.getInt(2));
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
    }

    @Override
    public void showBorrowed() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void borrow() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void rent() {
        // TODO Auto-generated method stub
        
    }
}
