/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

/**
 *
 * @author MManoli
 */
public class GoogleScholar extends Library implements Runnable{

    public GoogleScholar() {
        System.out.println("GoogleScholar Search");
    }

    @Override
    public void run() {
         System.out.println("GoogleScholar Implementation");
    }
    
}
