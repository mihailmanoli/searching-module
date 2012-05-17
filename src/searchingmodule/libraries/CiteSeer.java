/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingmodule.libraries;

/**
 *
 * @author MManoli
 */
public class CiteSeer extends Library implements Runnable{

    public CiteSeer() {
        System.out.println("CiteSeer Search");
    }

    @Override
    public void run() {
        System.out.println("CiteSeer Implementation");
    }
    
}
