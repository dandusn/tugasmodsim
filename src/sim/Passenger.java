/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.util.Random;

public class Passenger {
    int destination;
    double time;
    int timeload;
    
    Passenger(double t){
        time = t;
        Random r = new Random();
        timeload = r.nextInt(11)+15;
    }
}
