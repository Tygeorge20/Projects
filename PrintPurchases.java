import java.sql.*;

public class PrintPurchases {

  public static void main( String args[] ) {

   Connection c = null;
   Statement stmt = null;
   try {
        System.out.println("PURCHASES DATABASE");
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:purchaselog.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM PURCHASES;" );
        
        while ( rs.next() ) {
            int purchase_id = rs.getInt("purchase_id");
            int id = rs.getInt("id");
            // String title = rs.getString("title");
            float price = rs.getFloat("price");
            
            System.out.println( "PURCHASE_ID = " + purchase_id );
            System.out.println( "ID = " + id );
            System.out.println( "PRICE = " + price );
            System.out.println();
      }
      rs.close();
      stmt.close();
      c.close();
   } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
   }
   System.out.println("Operation done successfully");
  }
}