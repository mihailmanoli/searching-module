package searchingmodule.libraries.dblpPackage;

import com.sun.media.sound.AuFileReader;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class Parser {

    private final int maxAuthorsPerPaper = 200;
    private HashMap publications = new HashMap(600000);
    
    private String authorName;
    private String article;
    private String year;

    public Parser(String uri, String author, String article, String year) {
        this.authorName = author;
        this.article = article;
        this.year = year;
        DBConnection conexiune =  new DBConnection();
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            DBLPConfigHandler handler = new DBLPConfigHandler(conexiune.getConnectionString());
            parser.getXMLReader().setFeature(
                    "http://xml.org/sax/features/validation", true);
            URL u = new URL(uri);
            parser.parse(u.openStream(), handler);
        } catch (IOException e) {
            System.out.println("Error reading URI: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("SAXError in parsing: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println("Error in XML parser configuration: "
                    + e.getMessage());
        }
    }
}
