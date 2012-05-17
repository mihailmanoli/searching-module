/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.dblpPackage;

import com.mysql.jdbc.Connection;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author MManoli
 */
public class DBConnection {
    
    private Connection conn = null;
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    
    public DBConnection() {
        try {
            DBConfigHandler handler = new DBConfigHandler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();  
            saxParser.parse("db.xml", handler);
            
            this.host = handler.getHost();
            this.port = handler.getPort();
            this.database = handler.getDatabase();
            this.username = handler.getUsername();
            this.password = handler.getPassword();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
            
        String url = "jdbc:mysql://"+this.host+":"+this.port+"/";
        String driver = "com.mysql.jdbc.Driver";
        
        if(password.equals("\n")) password = "";
        
        try {
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection(url + this.database, this.username, this.password);
            System.out.println("Connected to the database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnectionString(){
        return this.conn;
    }
    
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Problema la inchiderea conexiunii la baza de date");
        }
    }    
    
}
