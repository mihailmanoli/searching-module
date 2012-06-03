/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.citeSeerx;

import searchingmodule.libraries.citeSeerx.GetRecord;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/* jsoup: Java HTML Parser */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.xml.sax.SAXException;

/**
 *
 * @author Phoenix
 */
public class CiteSeerx {

    ArrayList identifiers = new ArrayList();

    public CiteSeerx(String outFileName, String title, String author) {

        File input = new File("tmp/input.html");
        String tit = title.trim();
        String aut = author.trim();

        title = formatInput(title);
        author = formatInput(author);
        
        
        if (tit.length() > 0 && aut.length() > 0) {
            String sLink = "http://citeseerx.ist.psu.edu/search?q=title%3A" + title + "+AND+author%3A" + author +"&sort=cite&t=doc";
            System.out.println(sLink);
            getIdentifiers(sLink, 0);
        } else if (tit.length() > 0 && aut.length() == 0) {
            String sLink = "http://citeseerx.ist.psu.edu/search?q=title%3A" + title + "&sort=cite&t=doc";
            System.out.println(sLink);
            getIdentifiers(sLink, 0);
        } else if (tit.length() == 0 && aut.length() > 0) {
            String sLink = "http://citeseerx.ist.psu.edu/search?q=author%3A" + author +"&sort=cite&t=doc";
            System.out.println(sLink);
            getIdentifiers(sLink, 0);
        }
        
        /*
         * -------------Creez XML-----------------------------------------------
         */
        try {
            System.out.println(new Date());

            OutputStream out = System.out;
            String metadataPrefix = "oai_dc";
            String baseURL = "http://citeseerx.ist.psu.edu/oai2";

            out = new FileOutputStream("brutCS.xml");
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes("UTF-8"));
            out.write("<citeseer>\n".getBytes("UTF-8"));
            //out.write(new Identify(baseURL).toString().getBytes("UTF-8"));
            //out.write("\n".getBytes("UTF-8"));
            run1(baseURL, metadataPrefix, identifiers, out, 0);
            out.write("\n".getBytes("UTF-8"));
            out.write("</citeseer>\n".getBytes("UTF-8"));

            if (out != System.out) {
                out.close();
            }
            System.out.println(new Date());
        } catch (IllegalArgumentException e) {
            System.err.println("RawWrite <-from date> <-until date> <-metadataPrefix prefix> <-setSpec setName> <-resumptionToken token> <-out fileName> baseURL");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        ParseXML px = new ParseXML("brutCS.xml",outFileName);


    }
    
    private String formatInput(String input) {
        if (input.contains(" ")) {
            input = input.replace(' ', '+');
        }

        if (input.contains(",")) {
            input = input.replace(",", "%2C+");
        }
        
        if (input.contains("+")) {
        input = "%28" + input + "%29";
        }
        return input;
        
    }

    public boolean getIdentifiers(String searchLink, int stop) {
        if (stop == 10) {           
            return false;
        }
        boolean c=true;
        Document doc = new Document(searchLink);
        try {
            doc = Jsoup.connect(searchLink).get();
            
            
        } catch (IOException ex) {
            System.out.println("Indisponibil, repet accesarea");
            c=false;
            getIdentifiers(searchLink, stop+1);
            // Logger.getLogger(ORG.oclc.oai.harvester2.app.RawWrite.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(c)
        {
        Element content = doc.getElementById("result_list");
            Elements links = content.getElementsByTag("a");
            for (Element link : links) {
                String linkClas = link.attr("class");
                if (linkClas.equals("remove doc_details")) {
                    String linkHref = link.attr("href");
                    int st = linkHref.lastIndexOf("=");

                    identifiers.add("oai:CiteSeerX.psu:" + linkHref.substring(st + 1));
                }
            }
            for (int i = 0; i < identifiers.size(); i++) {
                System.out.println(identifiers.get(i));
            }
        }
            
       
        
        return true;
    }

    public static void run1(String baseURL, String metadataPrefix, ArrayList identifiers, OutputStream out, int stop)
            throws IOException, ParserConfigurationException, SAXException, TransformerException,
            NoSuchFieldException {
        if (stop == 1) {
            return;
        }

        for (int i = 0; i < identifiers.size(); i++) {
            // System.out.println(identifiers.get(i));
            try {
                out.write(new GetRecord(baseURL, identifiers.get(i).toString(), metadataPrefix).toString().getBytes("UTF-8"));
                out.write("\n".getBytes("UTF-8"));
            } catch (IOException e) {
                //Logger.getLogger(ORG.oclc.oai.harvester2.app.RawWrite.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("Indisponibil");
                run1(baseURL, metadataPrefix, identifiers, out, stop+1);
            }
        }

    }
}
