/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.dblpPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author MManoli
 */
public class DBLPConfigHandler extends DefaultHandler {

    private String uri;

    public DBLPConfigHandler(String uri) {
        this.uri = uri;
    }
    private String articleStr = null;
    private boolean barticle = false;
    private DocumentBuilderFactory docBuilderFactory;
    private DocumentBuilder docBuilder;
    private OutputStream out;
    private String line;
    
    public String getArticleStr() {
        return articleStr;
    }

    @Override
    public void startDocument() throws SAXException {
        try {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();

            out = System.out;
            out = new FileOutputStream(this.uri);
            line = new String();
            line = "<site name=\"dblp\">\n";
            out.write(line.getBytes("UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("title")) {
            barticle = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (barticle) {
            articleStr = new String(ch, start, length);
            barticle = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("title")) {
            try {
                String articleParser = "<?xml version=\"1.0\"?><wrapper>";
                articleParser = articleParser.concat(articleStr);
                articleParser = articleParser.concat("</wrapper>");
                //  DBLPXML dblxml = new DBLPXML(articleParser, this.uri);
                articleParser.replace("&", " and ");
                System.out.println(articleParser);
                Document doc = docBuilder.parse(new InputSource(new StringReader(articleParser)));

                // normalize text representation
                doc.getDocumentElement().normalize();
                System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
                NodeList listOfBooks = doc.getElementsByTagName("wrapper");
                int totalPersons = listOfBooks.getLength();
                System.out.println("Total no of pubications/articles : " + totalPersons);
                for (int s = 0; s < listOfBooks.getLength(); s++) {
                    out.write("<book>\n".getBytes("UTF-8"));
                    Node firstNode = listOfBooks.item(s);
                    if (firstNode.getNodeType() == Node.ELEMENT_NODE) {


                        Element firstElement = (Element) firstNode;

                        //-------
                        NodeList firstNameList = firstElement.getElementsByTagName("dblp:title");
                        Element firstNameElement = (Element) firstNameList.item(0);

                        NodeList textFNList = firstNameElement.getChildNodes();
                        NamedNodeMap attributes = firstNameList.item(0).getAttributes();
                        String[] names = new String[attributes.getLength()];
                        for (int i=0; i<names.length; i++) {
                            names[i] = attributes.item(i).getTextContent();
                        }
                        
                        line = "<title>" + ((Node) textFNList.item(0)).getNodeValue().trim() + "</title>\n";
                        out.write(line.getBytes("UTF-8"));

                        NodeList author = firstElement.getElementsByTagName("dblp:authors");
                        Element lastElement = (Element) author.item(0);

                        NodeList textLNList = lastElement.getChildNodes();
                        out.write("<authors>\n".getBytes("UTF-8"));
                        for (int j = 0; j < textLNList.getLength(); j++) {
                            line = "<author>" + ((Node) textLNList.item(j)).getTextContent() + "</author>\n";
                            out.write(line.getBytes("UTF-8"));
                        }
                        out.write("</authors>\n".getBytes("UTF-8"));

                        try {
                            NodeList venueList = firstElement.getElementsByTagName("dblp:venue");
                            Element venueElement = (Element) venueList.item(0);

                            NodeList textVenueList = venueElement.getChildNodes();
                            line = "<venue>" + ((Node) textVenueList.item(0)).getNodeValue().trim() + "</venue>\n";
                            out.write(line.getBytes("UTF-8"));
                        } catch (NullPointerException e) {
                        }

                        try {
                            NodeList yearList = firstElement.getElementsByTagName("dblp:year");
                            Element yearElement = (Element) yearList.item(0);

                            NodeList textYearList = yearElement.getChildNodes();
                            line = "<year>" + ((Node) textYearList.item(0)).getNodeValue().trim() + "</year>\n";
                            out.write(line.getBytes("UTF-8"));
                        } catch (NullPointerException e) {
                        }
                        
                        line = "<url>" + names[0].trim() + "</url>\n";
                        out.write(line.getBytes("UTF-8"));

                    }//end of if clause

                    out.write("</book>\n".getBytes("UTF-8"));
                }
            } catch (Exception ex) {
                System.out.println("Big problem :D");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            out.write("</site>".getBytes("UTF-8"));
            if (out != System.out) {
                out.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
