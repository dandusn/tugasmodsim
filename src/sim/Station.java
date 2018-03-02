/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.javasim.RestartException;
import org.javasim.SimulationException;

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
    
    Station(int i, double mean) throws SimulationException, RestartException{
        id = i;
        Ar = new Arrivals(mean);
    }
    
    double updateQueue(){
        int i = 0;
        double s;
        double res=0;
        
		/*System.out.println(Ar.passengers.size());
        if(Ar.passengers.size()>0) {
			if(id!=2){
				Ar.passengers.get(0).destination=2;
			} else{
				s = r.nextInt(1000)+1;
				if(s<=583){
					Ar.passengers.get(0).destination=0;
				} else  Ar.passengers.get(0).destination=1;
			}
			passengers.add(Ar.passengers.get(0));
			System.out.println("added to station");

		}*/
        
        while(i<Ar.passengers.size() && Ar.passengers.size()>0){
            Passenger q = Ar.passengers.get(i);
            if(q.time>300){
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
				//System.out.println("added to station");
                res += q.time;
                i++;
            }
        }
        int x=0;
        for(int j =0;x<=i && Ar.passengers.size()>0;){
            Ar.passengers.remove(j);
			x++;
        }
        
        if(maxqueuesize<passengers.size()) maxqueuesize = passengers.size();
        
        sumqueuesize+=passengers.size();
        
        if(maxbusinstation<res) maxbusinstation = res;
        
        if(minbusinstation>res) minbusinstation = res;
        
        sumbusinstation+=res;

        return res;
    }
}