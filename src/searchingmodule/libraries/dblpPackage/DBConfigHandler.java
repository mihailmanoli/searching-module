/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.dblpPackage;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author MManoli
 */
public class DBConfigHandler extends DefaultHandler {

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }
    boolean bhost = false;
    boolean bport = false;
    boolean bdb = false;
    boolean buser = false;
    boolean bpass = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("host")) {
            bhost = true;
        }
        if (qName.equalsIgnoreCase("port")) {
            bport = true;
        }
        if (qName.equalsIgnoreCase("dbname")) {
            bdb = true;
        }
        if (qName.equalsIgnoreCase("username")) {
            buser = true;
        }
        if (qName.equalsIgnoreCase("pass")) {
            bpass = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (bhost) {
            host = new String(ch, start, length);
            bhost = false;
        }
        if (bport) {
            port = new String(ch, start, length);
            bport = false;
        }
        if (bdb) {
            database = new String(ch, start, length);
            bdb = false;
        }
        if (buser) {
            username = new String(ch, start, length);
            buser = false;
        }
        if (bpass) {
            password = new String(ch, start, length);
            bpass = false;
        }

    }
}
