/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Station {
    Arrivals Ar;
    List<Passenger> passengers = new ArrayList<>();
    int id;
    Random r = new Random();
    int maxqueuesize = 0;
    double sumqueuesize = 0;
    double maxbusinstation = 0;
    double sumbusinstation = 0;
    double minbusinstation = 80*3600;
    
    Station(int i, double mean){
        id = i;
        Ar = new Arrivals(mean);
    }
    
    double updateQueue(){
        int i = 0;
        int s;
        double res=0;
        
        if(id!=2){
            Ar.passengers.get(0).destination=2;
        } else{
            s = r.nextInt(1000)+1;
            if(s<=583){
                Ar.passengers.get(0).destination=0;
            } else  Ar.passengers.get(0).destination=1;
        }
        passengers.add(Ar.passengers.get(0));
        
        while(i-1<Ar.passengers.size()){
            Passenger p = Ar.passengers.get(i);
            Passenger q = Ar.passengers.get(i+1);
            if(q.time-p.time>300){
                break;
            }else{
                if(id!=2){
                    q.destination=2;
                } else{
                    s = r.nextInt(1000)+1;
                    if(s<=583){
                        q.destination = 0;
                    } else q.destination=1;
                }
                passengers.add(q);
                res += q.time-p.time;
                i++;
            }
        }
        
        for(int j =0;j<=i;j++){
            Ar.passengers.remove(j);
        }
        
        if(maxqueuesize<passengers.size()) maxqueuesize = passengers.size();
        
        sumqueuesize+=passengers.size();
        
        if(maxbusinstation<res) maxbusinstation = res;
        
        if(minbusinstation>res) minbusinstation = res;
        
        sumbusinstation+=res;

        return res;
    }
}