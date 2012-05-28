/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.Ieee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Phoenix
 */
public class ParseIeee {
    
    public ParseIeee (String xmlFile, String outFileName){
    try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(xmlFile));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            System.out.println ("Root element of the doc is " + 
                 doc.getDocumentElement().getNodeName());


            NodeList listOfBooks = doc.getElementsByTagName("document");
            int totalPersons = listOfBooks.getLength();
            System.out.println("Total no of pubications/articles : " + totalPersons);
            
            OutputStream out = System.out;
            out = new FileOutputStream(outFileName);
            String line = new String();
            
            line="<site name=\"ieeexplore\">\n";
            out.write(line.getBytes("UTF-8"));

            for(int s=0; s<listOfBooks.getLength() ; s++){       
                out.write("<book>\n".getBytes("UTF-8"));
                Node firstNode = listOfBooks.item(s);
                if(firstNode.getNodeType() == Node.ELEMENT_NODE){


                    Element firstElement = (Element)firstNode;

                    //-------
                    NodeList firstNameList = firstElement.getElementsByTagName("title");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    line="<title>"+((Node)textFNList.item(0)).getNodeValue().trim()+"</title>\n";
                    out.write(line.getBytes("UTF-8"));
                    
                    NodeList author = firstElement.getElementsByTagName("authors");
                    Element lastElement = (Element)author.item(0);

                    NodeList textLNList = lastElement.getChildNodes();
                    String au=((Node)textLNList.item(0)).getNodeValue().trim();
                    String[] authors=au.split(";");
                    out.write("<authors>\n".getBytes("UTF-8"));
                    for (int j = 0; j < authors.length; j++) {
                        line = "<author>" + authors[j] + "</author>\n";
                        out.write(line.getBytes("UTF-8"));
                    }
                    out.write("</authors>\n".getBytes("UTF-8"));

                    try {
                    NodeList affiliationsList = firstElement.getElementsByTagName("affiliations");
                    Element affiliationsElement = (Element)affiliationsList.item(0);

                    NodeList textAgeList = affiliationsElement.getChildNodes();
                    line = "<affiliations>" + ((Node)textAgeList.item(0)).getNodeValue().trim() + "</affiliations>\n";
                        out.write(line.getBytes("UTF-8"));  
                    } catch (NullPointerException e) {
                    } 
                    
                    try {
                        NodeList isbnList = firstElement.getElementsByTagName("isbn");
                        Element isbnElement = (Element) isbnList.item(0);

                        NodeList textIsbnList = isbnElement.getChildNodes();
                        line = "<isbn>" + ((Node) textIsbnList.item(0)).getNodeValue().trim() + "</isbn>\n";
                        out.write(line.getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }
                    
                    try {
                        NodeList doiList = firstElement.getElementsByTagName("doi");
                        Element doiElement = (Element) doiList.item(0);

                        NodeList textdoiList = doiElement.getChildNodes();
                        line = "<doi>" + ((Node) textdoiList.item(0)).getNodeValue().trim() + "</doi>\n";
                        out.write(line.getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }

                }//end of if clause

                out.write("</book>\n".getBytes("UTF-8"));
            }
            out.write("</site>".getBytes("UTF-8"));
            if (out != System.out) {
                out.close();
            }

        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);

    }//end of main
    
}
