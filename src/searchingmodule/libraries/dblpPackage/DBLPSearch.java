package searchingmodule.libraries.dblpPackage;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class DBLPSearch {

    private String authorName;
    private String article;
    private String uri;
    
    
    public DBLPSearch(String author, String article, String uri) {
        this.authorName = author;
        this.article = article;
        this.uri = uri;
        searchDBLPData();
        parseOutput();
    }
    
    public void searchDBLPData() {
        try { 
            String QAuthor = this.authorName.toLowerCase().replace(" ", "_");
            String QArticle = this.article.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
            QArticle= QArticle.replace(" ", "%20");
             URL url = null;
            if(this.authorName.equals("")){
                url = new URL("http://www.dblp.org/search/api/?q="+QArticle+"&h=1000&c=4&f=0&format=xml");
            }else if(this.article.equals("")){
                url = new URL("http://www.dblp.org/search/api/?q=ce:author:"+QAuthor+":*&h=1000&c=4&f=0&format=xml");
            } else
                url = new URL("http://www.dblp.org/search/api/?q=ce:author:"+QAuthor+":*%20"+QArticle+"&h=1000&c=4&f=0&format=xml");
            url.openConnection();
            System.out.println(url);
            InputStream reader = url.openStream();

            FileOutputStream writer = new FileOutputStream("DBLPSearchOutput.xml");
            byte[] buffer = new byte[153600];
            int bytesRead = 0;

            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[153600];
            }
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void parseOutput(){
        try {
            DBLPConfigHandler handler = new DBLPConfigHandler(this.uri);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();  
            saxParser.parse("DBLPSearchOutput.xml", handler);
        } 
        catch (SAXException ex) {
            System.out.println("Problem :D");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Nu sa gasit fisierul");
        }  
        catch (Exception ex) {
            System.out.println("Big problem :D");
            ex.printStackTrace();
        }
    }
}
