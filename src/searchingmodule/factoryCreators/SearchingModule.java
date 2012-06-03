/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.factoryCreators;

import searchingmodule.libraries.*;

/**
 *
 * @author MManoli
 */
public class SearchingModule {

    public SearchingModule() {
        siteFactory();
    }

    public void siteFactory() {
        String ieeeOutput = "outputIeee.xml";
        String citeSeerOutput = "outputCiteSeer.xml";
        String worldCatOutput = "outputWorldCat.xml";
        String dblpOutput = "outputDBLP.xml";
        String author = "Mihai Alex;Diana Trandabat";
        String article = "";
        
        IEEE ieeeSearch = new IEEE(author, article, ieeeOutput);
        CiteSeer citeSeerSearch = new CiteSeer(author, article, citeSeerOutput);
        WorldCat worldCatSearch = new WorldCat(author, article, worldCatOutput);
        
        DBLP dblpSearch = new DBLP(author, article, dblpOutput);

        /*
         * 4 Threads
         */

        new Thread(ieeeSearch).start();
        new Thread(citeSeerSearch).start();
        new Thread(worldCatSearch).start();
 //       new Thread(dblpSearch).start();
    }
}
