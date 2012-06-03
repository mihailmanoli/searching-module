/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.citeSeerx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Phoenix
 */
public class ParseXML {

    public ParseXML(String xmlFile, String outFileName) {
        try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(xmlFile));

            // normalize text representation
            NodeList listOfBooks = null;
            try {
                listOfBooks = doc.getElementsByTagName("record");
                int totalPersons = listOfBooks.getLength();
                System.out.println("Total no of pubications/articles : " + totalPersons);
            } catch (NullPointerException e) {
            }

            OutputStream out = System.out;
            out = new FileOutputStream(outFileName);
            String line = new String();

            line = "<site name=\"citeseerx\">\n";
            out.write(line.getBytes("UTF-8"));

            for (int s = 0; s < listOfBooks.getLength(); s++) {
                out.write("<book>\n".getBytes("UTF-8"));

                Node title = listOfBooks.item(s);
                if (title.getNodeType() == Node.ELEMENT_NODE) {

                    Element firstElement = (Element) title;

                    //-------
                    try {
                        NodeList firstNameList = firstElement.getElementsByTagName("dc:title");
                        Element firstNameElement = (Element) firstNameList.item(0);
                        NodeList textFNList = firstNameElement.getChildNodes();
                        line = "<title>" + replaceSpecialCharacters(((Node) textFNList.item(0)).getNodeValue().trim()) + "</title>\n";
                        out.write(line.getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }

                    try {
                        out.write("<authors>\n".getBytes("UTF-8"));
                        NodeList author = firstElement.getElementsByTagName("dc:creator");
                        for (int i = 0; i < author.getLength(); i++) {
                            Element lastElement = (Element) author.item(i);
                            NodeList textLNList = lastElement.getChildNodes();
                            line = "<author>" + replaceSpecialCharacters(((Node) textLNList.item(0)).getNodeValue().trim()) + "</author>\n";
                            out.write(line.getBytes("UTF-8"));
                        }
                        out.write("</authors>\n".getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }

                    try {
                        NodeList urlList = firstElement.getElementsByTagName("dc:identifier");
                        Element urlElement = (Element) urlList.item(0);
                        NodeList url = urlElement.getChildNodes();
                        line = "<url>" + formatLink(replaceSpecialCharacters(((Node) url.item(0)).getNodeValue().trim())) + "</url>\n";
                        out.write(line.getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }
                    
                    try {
                        NodeList languageList = firstElement.getElementsByTagName("dc:language");
                        Element languageElement = (Element) languageList.item(0);
                        NodeList language = languageElement.getChildNodes();
                        line = "<language>" + formatLink(replaceSpecialCharacters(((Node) language.item(0)).getNodeValue().trim())) + "</language>\n";
                        out.write(line.getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }
                    
                    try {
                        NodeList doiList = firstElement.getElementsByTagName("identifier");
                        Element doiElement = (Element) doiList.item(0);
                        NodeList doiFNList = doiElement.getChildNodes();
                        line = "<doi>" + formatLink(replaceSpecialCharacters(((Node) doiFNList.item(0)).getNodeValue().trim())) + "</doi>\n";
                        out.write(line.getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }

                    try {
                        out.write("<subjects>\n".getBytes("UTF-8"));
                        NodeList subject = firstElement.getElementsByTagName("dc:subject");
                        for (int i = 0; i < subject.getLength(); i++) {
                            Element subjectElement = (Element) subject.item(i);
                            NodeList subjectLNList = subjectElement.getChildNodes();
                            line = "<subject>" + replaceSpecialCharacters(((Node) subjectLNList.item(0)).getNodeValue().trim()) + "</subject>\n";
                            out.write(line.getBytes("UTF-8"));
                        }
                        out.write("</subjects>\n".getBytes("UTF-8"));
                    } catch (NullPointerException e) {
                    }

                }//end of if clause

                out.write("</book>\n".getBytes("UTF-8"));
            }//end of for loop with s var
            out.write("</site>".getBytes("UTF-8"));
            if (out != System.out) {
                out.close();
            }

        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line "
                    + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }//end of ParseXML
    
    private String formatLink(String str) {      
        str = str.replace("citeseerx/", "");
        return str;
    }

    private String replaceSpecialCharacters(String str) {
        if (str.contains("&")) {
            str = str.replace("&", "&amp;");
        }
        if (str.contains("<")) {
            str = str.replace("<", "&lt;");
        }
        if (str.contains(">")) {
            str = str.replace(">", "&gt;");
        }
        if (str.contains("\"")) {
            str = str.replace("\"", "&quot;");
        }
        if (str.contains("'")) {
            str = str.replace("'", "&apos;");
        }
        return str;
    }
}
