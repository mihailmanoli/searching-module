/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

import searchingmodule.libraries.citeSeerx.CiteSeerx;

/**
 *
 * @author MManoli
 */
public class CiteSeer extends Library implements Runnable{
    private String author;
    private String article;
    private String uri;
    
    public CiteSeer(String author, String article, String uri) {
        System.out.println("CiteSeer Search");
        this.author = author;
        this.article = article;
        this.uri = uri;
    }

    @Override
    public void run() {
        System.out.println("CiteSeer Implementation");
        CiteSeerx c = new CiteSeerx(uri, article, author);
    }
    
}
