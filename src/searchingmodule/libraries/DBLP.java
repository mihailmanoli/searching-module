/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

import searchingmodule.libraries.dblpPackage.DBConnection;
import searchingmodule.libraries.dblpPackage.Parser;

/**
 *
 * @author MManoli
 */
public class DBLP extends Library implements Runnable {

    String author;
    String article;
    String year;

    public DBLP(String author, String article, String year) {
        System.out.println("DBLP Search");
        this.author = author;
        this.article = article;
        this.year = year;
    }

    @Override
    public void run() {
        Parser p = new Parser("http://dblp.uni-trier.de/xml/dblp.xml", this.author, this.article, this.year); 
    }
}
