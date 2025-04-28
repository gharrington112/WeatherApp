package WeatherPackage;

import java.sql.*;
import javax.swing.*;

//Database Class
public class Database {

    public Database()
    {

    }
    // Declaration of variables

    private String url = System.getProperty("user.dir");
    private String filePath = "jdbc:ucanaccess://" + url.replace("\\", "/")
            + "/WeatherDB.accdb";

    //createTable() drops the current table and creates a new one
    public void createTable() {

        try
        {
            // load database driver class
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");


            // connect to database
            Connection con = DriverManager.getConnection(filePath);
            Statement stmt = con.createStatement();

            //this code may need to be commented out because an exception will be thrown
            //if this table doesn't exist in the database
            stmt.execute("DROP TABLE Weather");

            stmt.execute("CREATE TABLE Weather" +
                    "(DayName varchar(255)," +
                    " Forecast varchar(255)," +
                    " HighTemp number," +
                    " LowTemp number)");

            stmt.close();
            con.close();
        }
        // detect problems interacting with the database
        catch ( SQLException sqlException ) {
            JOptionPane.showMessageDialog( null,
                    sqlException.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE );

            System.exit( 1 );
        }//end catch block

        // detect problems loading database driver
        catch ( ClassNotFoundException classNotFound ) {
            JOptionPane.showMessageDialog( null,
                    classNotFound.getMessage(), "Driver Not Found",
                    JOptionPane.ERROR_MESSAGE );

            System.exit( 1 );
        }//end catch block

    }//end createTable()


    //this method accepts the weather data as input and stores it to the database
    public void storeRecord(String DayName, String Forecast, int HighTemp, int LowTemp){

        try {

            // load database driver class
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // connect to database
            Connection con = DriverManager.getConnection(filePath);

            Statement stmt = con.createStatement();
            //this Insert statement puts student info in the database
            stmt.executeUpdate("INSERT INTO Weather VALUES ('"+DayName+"','"+Forecast+"','"+HighTemp+"','"+LowTemp+"')");

            stmt.close();
            con.close();
        }//end try
        catch(Exception e)
        {
            e.printStackTrace();
        }//end catch

    }//end storeRecord()
}// end Database class