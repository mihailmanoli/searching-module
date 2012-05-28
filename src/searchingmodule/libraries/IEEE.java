/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

import searchingmodule.libraries.Ieee.IeeeXML;

/**
 *
 * @author MManoli
 */
public class IEEE extends Library implements Runnable{
    private String author;
    private String article;
    private String uri;
    
    public IEEE(String author, String article, String uri) {
        System.out.println("IEEE Search");
        this.author = author;
        this.article = article;
        this.uri = uri;
    }

    @Override
    public void run() {
        System.out.println("IEEE Implementation");
        IeeeXML i = new IeeeXML(uri, article, author);
    }
    
}
