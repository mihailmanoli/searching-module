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
        IEEE ieeeSearch = new IEEE();
        CiteSeer citeSeerSearch = new CiteSeer();
        GoogleScholar googleScholarSearch = new GoogleScholar();
        WorldCat worldCatSearch = new WorldCat();
        DBLP dblpSearch = new DBLP("Dan Cristea", "DB", "1995");

        /*
         * 4 Threads
         */

        new Thread(ieeeSearch).start();
        new Thread(citeSeerSearch).start();
        new Thread(googleScholarSearch).start();
        new Thread(worldCatSearch).start();
        new Thread(dblpSearch).start();
    }
}
