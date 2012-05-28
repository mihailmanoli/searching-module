/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

import searchingmodule.libraries.dblpPackage.DBLPSearch;

/**
 *
 * @author MManoli
 */
public class DBLP extends Library implements Runnable {

    private String author;
    private String article;
    private String uri;
    
    public DBLP(String author, String article, String uri) {
        System.out.println("DBLP Search");
        this.author = author;
        this.article = article;
        this.uri = uri;
    }

    @Override
    public void run() {
        DBLPSearch s = new DBLPSearch(author, article, uri);
    }
}
