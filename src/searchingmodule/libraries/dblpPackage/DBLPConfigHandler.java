/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.dblpPackage;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author MManoli
 */
public class DBLPConfigHandler extends DefaultHandler {

    boolean bauthor = false;
    boolean btitle = false;
    boolean byear = false;
    boolean burl = false;
    boolean bpages = false;
    boolean bkey = false;
    boolean barticle = false;
    
    private Connection conn;
    private String authorStr = null;
    private String articleStr = null;
    private String pagesStr = null;
    private String yearStr = null;
    private String urlStr = null;
    private List<String> authorList = new ArrayList<>();
    private DBQueries query = new DBQueries();
    
    public DBLPConfigHandler(Connection con) {
        this.conn = con;
    }

    @Override
    public void startElement(String namespaceURI, String localName,
            String rawName, Attributes atts) throws SAXException {
        String k;
        if (rawName.equalsIgnoreCase("article")) {
            barticle = true;
        }
        if (rawName.equalsIgnoreCase("author")) {
            bauthor = true;
        }
        if (rawName.equalsIgnoreCase("title")) {
            btitle = true;
        }
        if (rawName.equalsIgnoreCase("pages")) {
            bpages = true;
        }
        if (rawName.equalsIgnoreCase("year")) {
            byear = true;
        }
        if (rawName.equalsIgnoreCase("url")) {
            burl = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (bauthor) {
            authorStr = new String(ch, start, length);
            if(barticle){
                authorList.add(authorStr);
            }
            bauthor = false;
        }
        if (btitle) {
            articleStr = new String(ch, start, length);
            btitle = false;
        }
        if (bpages) {
            pagesStr = new String(ch, start, length);
            bpages = false;
        }
        if (byear) {
            yearStr = new String(ch, start, length);
            byear = false;
        }
        if (burl) {
            urlStr = new String(ch, start, length);
            burl = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("article")){
            try {
                String sql = query.getInsertArticleQuery();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, articleStr);
                pstmt.setString(2, pagesStr);
                pstmt.setString(3, yearStr);
                pstmt.setString(4, urlStr);
                System.out.println(pstmt.toString());
                
                String sqlLastID = query.getLastInsertIDQuery();
                PreparedStatement lastIDstmt = conn.prepareStatement(sqlLastID);
                
                ResultSet rs = lastIDstmt.executeQuery();
                
                // i will be back
                
              //  pstmt.executeUpdate();
//                String sqlAuthor = query.getInsertAuthorQuery();
//                PreparedStatement authstmt = conn.prepareStatement(sqlAuthor);
//                for (int i = 0; i < authorList.size(); i++) {
//                    String name = authorList.get(i);
//                     authstmt.setString(1, name);
//                }
                authorList.clear();
                
            } catch (SQLException ex) {
                System.out.println("Problema SQL");
            }
        }
    }
    
    private void Message(String mode, SAXParseException exception) {
        System.out.println(mode + " Line: " + exception.getLineNumber()
                + " URI: " + exception.getSystemId() + "\n" + " Message: "
                + exception.getMessage());
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {

        Message("**Parsing Warning**\n", exception);
        throw new SAXException("Warning encountered");
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {

        Message("**Parsing Error**\n", exception);
        throw new SAXException("Error encountered");
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {

        Message("**Parsing Fatal Error**\n", exception);
        throw new SAXException("Fatal Error encountered");
    }
}
