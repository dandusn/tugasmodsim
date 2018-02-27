/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

public class Station {
    Arrivals Ar;
    int id;
    
    Station(int i, double mean){
        id = i;
        Ar = new Arrivals(mean);
    }
}
