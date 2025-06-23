import java.util.*; 
import java.net.URL;     
import org.apache.xmlrpc.*;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class JavaClient {
    public static void main (String [] args) {
	if(args.length == 0) {
	    System.out.println("Usage: java Client <server>");
	    System.exit(1);
	}

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        XmlRpcClient client=null;
        try {
            config.setServerURL(new URL("http://" + args[0] + ":" + "8889"));
            client = new XmlRpcClient();
            client.setConfig(config);
        } catch (Exception e) {
	    System.err.println("Client error: "+ e);
	}

        Vector<Object> params = new Vector<Object>();
        Vector<Object> string_params = new Vector<Object>();
        params.addElement(Integer.valueOf(53477));
        string_params.addElement(String.valueOf("CSCI"));

        try {
            // String result = (String)client.execute("sample.Buy", params.toArray()); // UNCOMMENT TO TEST BUY FUNCTIONALITY
            // String result = (String)client.execute("sample.Lookup", params.toArray()); // UNCOMMENT TO TEST LOOKUP FUNCTIONALITY
            String result = (String)client.execute("sample.Search", string_params.toArray());

            System.out.println(result);

        } catch (Exception e) {
	    System.err.println("Client error: " + e);
	}
    }
}