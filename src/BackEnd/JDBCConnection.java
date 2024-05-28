package BackEnd;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Retrieve from Practical prac06 and modified to suit the program
 */


public class JDBCConnection {

    //private Connection connection;
    private static Connection instance = null;

    private JDBCConnection(){
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");
            // get a connection
            Class.forName("org.mariadb.jdbc.Driver");
            instance = DriverManager.getConnection(url + "/" + schema, username,
                    password);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch(Exception e){

        }

    }

    /**
     * Database connection
     * @return Connection class
     */
    public static Connection getInstance() {
        if (instance == null) {
            new JDBCConnection();
        }
        return instance;
    }
}
