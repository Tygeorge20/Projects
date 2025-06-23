import java.sql.*;

public class Functions {

    public static String restock(int id, int quantity) { 
        String sql = "UPDATE BOOKS SET STOCK = STOCK + " + quantity + " WHERE ID = " + id;
        System.out.println("Restocked item number: " + id + " by " + quantity + " copies.");
        return sql;
    }

    public static String update(int id, float price) { 
        String sql = "UPDATE BOOKS SET PRICE = " + price + " WHERE ID = " + id;
        System.out.println("Item number: " + id + " now costs " + price + ".");
        return sql;
    }

    public static void log() {
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
        System.out.println("Purchase Log Printed Successfully");
    }

    public static void main( String args[] ) {
        Connection c = null;
        Statement stmt = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ktkbooks.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            // Using CLI to trigger restock function
            if (args[0].equals("restock")) {
                int id = Integer.parseInt(args[1]);
                int quantity = Integer.parseInt(args[2]);
                stmt.executeUpdate(restock(id, quantity));
            }

            // Using CLI to trigger update function
            if (args[0].equals("update")) {
                int id = Integer.parseInt(args[1]);
                float price = Float.parseFloat(args[2]);
                stmt.executeUpdate(update(id, price));
            }

            if (args[0].equals("log")) {
                log();
            }
            
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records updated successfully");
    }
    }