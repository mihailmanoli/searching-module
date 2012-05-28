/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

import searchingmodule.libraries.worldCat.WorldCatSearch;

/**
 *
 * @author MManoli
 */
public class WorldCat extends Library implements Runnable{
    private String author;
    private String article;
    private String uri;
    
    public WorldCat(String author, String article, String uri) {
        System.out.println("WorldCat Search");
        this.author = author;
        this.article = article;
        this.uri = uri;
    }

    @Override
    public void run() {
        System.out.println("WorldCat Implementation");
        WorldCatSearch WC = new WorldCatSearch(author, article, uri);
    }
    
}
