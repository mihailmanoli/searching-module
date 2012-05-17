/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries.dblpPackage;

/**
 *
 * @author MManoli
 */
public class DBQueries {

    public String getInsertArticleQuery() {
        return "INSERT INTO articles (title, pages, year, url) VALUES (?, ?, ?, ?)";
    }

    public String getInsertAuthorQuery() {
        return "INSERT INTO authors (name, articleID) VALUES (?, ?)";
    }

    public String getInsertLastUpdateQuery() {
        return "INSERT INTO updates (last_update) VALUES (?)";
    }

    public String getSelectLastUpdateQuery() {
        return "SELECT last_update FROM updates";
    }

    public String getArticleByTitleQuery() {
        return "SELECT articleID, title, pages, year, url FROM articles WHERE title=?";
    }

    public String getArticleByAuthorQuery() {
        return "SELECT articleID, title, name, pages, year, url FROM articles, authors WHERE authors.articleID = articles.articleID"
                + " AND name=?";
    }

    public String getAuthorsByArticleQuery() {
        return "SELECT authorID, name FROM authors WHERE articleID=?";
    }

    public String getLastInsertIDQuery() {
        return "SELECT LAST_INSERT_ID()";
    }
}
