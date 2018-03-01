/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.util.ArrayList;
import java.util.List;

public class Bus {
    List<Passenger> passengers = new ArrayList<>();
    int position = 2;
    final int capacity = 20;
    int speed = 30;
    
    void move(){
        if (position<3){
            position++;
            
        } else{
            position=1;
        }
    }
    
    void addpassenger(Passenger p){
        if(passengers.size()<capacity){
            passengers.add(p);
        }
    }
    
    void removepassenger(){
        int i = 0;
        while(i < passengers.size()){
            if(passengers.get(i).destination == position) {
                passengers.remove(i);
            }else i++;
        }
    }
    
    double timetounload(){
        double res = 0;
        for(int i = 0; i<passengers.size(); i++){
            if(passengers.get(i).destination == position) res+= passengers.get(i).timeunload;
        }
        return res;
    }
}
