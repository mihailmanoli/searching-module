/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

/**
 *
 * @author MManoli
 */
public class WorldCat extends Library implements Runnable{

    public WorldCat() {
        System.out.println("WorldCat Search");
    }

    @Override
    public void run() {
        System.out.println("WorldCat Implementation");
    }
    
}
