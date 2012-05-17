/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

/**
 *
 * @author MManoli
 */
public class IEEE extends Library implements Runnable{

    public IEEE() {
        System.out.println("IEEE Search");
    }

    @Override
    public void run() {
        System.out.println("IEEE Implementation");
    }
    
}
