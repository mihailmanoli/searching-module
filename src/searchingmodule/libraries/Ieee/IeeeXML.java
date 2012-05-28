/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.Ieee;

import java.net.*;
import java.io.*;

/**
 *
 * @author Phoenix
 */
public class IeeeXML {

    public IeeeXML(String outFileName, String title, String author) {

        try {
            /*
             * Get a connection to the URL and start up a buffered reader.
             */
            long startTime = System.currentTimeMillis();

            String sLink = "http://ieeexplore.ieee.org/gateway/ipsSearch.jsp?";
            String maxNumber = "200";


            String tit = title.trim();
            String aut = author.trim();

            title = formatInput(title);
            author = formatInput(author);

            String querry = new String();
            if (tit.length() > 0 && aut.length() > 0) {
                querry = "ti=" + title + "&au=" + author;
            } else if (tit.length() > 0 && aut.length() == 0) {
                querry = "ti=" + title;
            } else if (tit.length() == 0 && aut.length() > 0) {
                querry = "au=" + author;
            }
            sLink = sLink + querry + "&hc=" + maxNumber;


            System.out.println(sLink);


            URL url = new URL(sLink);
            url.openConnection();
            InputStream reader = url.openStream();

            /*
             * Setup a buffered file writer to write out what we read from the
             * website.
             */
            FileOutputStream writer = new FileOutputStream("IEEESearchOutput.xml");
            byte[] buffer = new byte[153600];
            int totalBytesRead = 0;
            int bytesRead = 0;

            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[153600];
                totalBytesRead += bytesRead;
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ParseIeee("IEEESearchOutput.xml", outFileName);
    }

    private String formatInput(String input) {
        if (input.contains(" ")) {
            input = input.replace(" ", "%20");
        }
        return input;
    }
}
