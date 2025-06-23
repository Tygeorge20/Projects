import java.sql.*;

public class SQLiteJDBC {

   public static void main( String args[] ) {
      Connection c = null;
      Statement stmt = null;
      
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:ktkbooks.db");
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "CREATE TABLE BOOKS " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " TITLE           CHAR(100)    NOT NULL, " + 
                        " TOPIC            CHAR(50)     NOT NULL, " + 
                        " STOCK            INT     NOT NULL, " + 
                        " PRICE            FLOAT     NOT NULL, "; 
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
   }
}