import java.sql.*;

public class SelectInformation {

  public static void main( String args[] ) {

   Connection c = null;
   Statement stmt = null;
   try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:ktkbooks.db");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM BOOKS;" );
      
      while ( rs.next() ) {
         int id = rs.getInt("id");
         String title = rs.getString("title");
         String topic  = rs.getString("topic");
         int stock = rs.getInt("stock");
         float price = rs.getFloat("price");
         
         System.out.println( "ID = " + id );
         System.out.println( "TITLE = " + title );
         System.out.println( "TOPIC = " + topic );
         System.out.println( "STOCK = " + stock );
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