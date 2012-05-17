/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.dblpPackage;

import java.util.HashMap;

/**
 *
 * @author MManoli
 */
public class Publication {
    
    public String authorName;
    public String article;
    public String year;
    public String url;
    
    public String getArticle() {
        return article;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getUrl() {
        return url;
    }

    public String getYear() {
        return year;
    }
    
    
    public Publication(String authorName, String article, String year, String url) {
        this.authorName = authorName;
        this.article = article;
        this.year = year;
        this.url = url;
    }
        
}
