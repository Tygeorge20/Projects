import org.apache.xmlrpc.webserver.WebServer; 
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.XmlRpcException;
import java.sql.*;

public class FrontEndServer { 
    //Uncomment to experiment with the effect of using a
    // stateless server (a new instance of the server is
    // created for each rpc!)
    //int x = 0;

    int purchase_number = 0;

    /**
     * Allows the user to specify a topic (or category) and returns all entries 
     * belonging to that category (a title and an item number are displayed for each match).
     */
    public String Search(String topic) {
	    Statement stmt = null;
        Connection c = null;
        String match = "";

        if (!topic.equals("CSCI") && !topic.equals("College")) {
            String return_message = "This category is not in our bookstore.";
            return return_message;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ktkbooks.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "SELECT * FROM BOOKS WHERE TOPIC =" + "'" + topic + "'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int stock = rs.getInt("stock");
                float price = rs.getFloat("price");
                
                // Displaying ID and TITLE only
                System.out.println( "ID = " + id );
                System.out.println( "TITLE = " + title );
                match = match + "TITLE: " + title + " = " + "ID: " + id + "\n";
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

        return match;
    }

    /**
     * Allows an item number to be specified and returns details such as title, 
     * cost, subject, and whether or not the item is in stock.
     */
    public String Lookup(int id) {
        Statement stmt = null;
        Connection c = null;
        String return_message = "";
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ktkbooks.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "SELECT * FROM BOOKS WHERE ID =" + id;
            ResultSet rs = stmt.executeQuery(sql);
            
            while ( rs.next() ) {  
                String title = rs.getString("title");
                int stock = rs.getInt("stock");
                float price = rs.getFloat("price");
                String topic = rs.getString("topic");
                rs.close();

                if(stock == 0){
                    return_message =  return_message + "The book titled '" + title + "' costs " + price + " . The subject of this book is " + topic + " and it is out of stock.";
                }
                else{
                    return_message =  return_message + "The book titled '" + title + "' costs " + price + " . The subject of this book is " + topic + " and it is in stock.";
                }

            }
            stmt.close();
            c.close();
        }
        catch(Exception exception){
                System.err.println("Whups! " + exception);
        }

        return return_message;
    }



    /**
     * Specifies an item number for purchase (assume the desired quantity is always 1).
     */
    public String Buy(int id) {
        Statement stmt = null;
        Connection c = null;

        Statement stmt2 = null;
        Connection c2 = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ktkbooks.db");
            c2 = DriverManager.getConnection("jdbc:sqlite:purchaselog.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE BOOKS SET STOCK = STOCK - 1 WHERE id =" + id + " AND STOCK > 0";
            // int rowCount = stmt.executeUpdate(sql);
            // System.out.println(((Object) rowCount).getClass().getName());
            // System.out.println(rowCount > 0);

            if (stmt.executeUpdate(sql) > 0) {
                stmt2 = c2.createStatement();
                System.out.println("UPDATING OUR DATABASE"); 

                ResultSet rs = stmt.executeQuery("SELECT PRICE FROM BOOKS WHERE id =" + id);
                float price = rs.getFloat("price");
                System.out.println(id);
                System.out.println(price);

                System.out.println("ADDING TO DATABASE");
                String purchase_sql = "INSERT INTO PURCHASES(PURCHASE_ID, ID, PRICE) VALUES((SELECT MAX(PURCHASE_ID) + 1 FROM PURCHASES)," + id + "," + price + ")";
                stmt2.executeUpdate(purchase_sql);
                stmt2.close();
                c2.close();
            } else {
                String return_message = "Unable to purchase book number: " + id + " due to insufficient stock";
                return return_message;
            }
            stmt.close();
            c.close();
        }
        catch(Exception exception){
            System.err.println("Whups! " + exception);
        }
        String return_message = "Book number: " + id + " was successfully purchased.";
        return return_message;
    }

    public static void main (String [] args) {
        try {
            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            XmlRpcServer xmlRpcServer;
	        WebServer server = new WebServer(8889);
            xmlRpcServer = server.getXmlRpcServer();
            phm.addHandler("sample", FrontEndServer.class);
            xmlRpcServer.setHandlerMapping(phm);
	        System.out.println("Server starting on port 8889");
            server.start();
        } catch (Exception exception) {
            System.err.println("Server: " + exception);
        }
    }
}   