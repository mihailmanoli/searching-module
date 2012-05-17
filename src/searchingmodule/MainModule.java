/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule;

import searchingmodule.factoryCreators.SearchingModule;
import searchingmodule.libraries.Library;

/**
 *
 * @author MManoli
 */
public class MainModule {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainModule libs = new MainModule();
        libs.startSearch();
    }
    
    public void startSearch(){
        SearchingModule creator = new SearchingModule();
    }
}
