/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import static java.lang.Thread.sleep;
import org.javasim.RestartException;
import org.javasim.Scheduler;
import org.javasim.Simulation;
import org.javasim.SimulationException;

/**
 *
 * @author ACER
 */
public class Sim {
    
    public static void main(String[] args) throws SimulationException, RestartException, InterruptedException {
        double time = 0;
        double movetime;
		double maxloop = 0;
		double minloop = 80*3600;
		double sumloop = 0;
		double timeloop = 0;
		double sumallpersontime = 0 ;
		double maxallpersontime = 0 ;
		double minallpersontime = 80*3600 ;
		double allperson = 0;
		boolean start = false;
		int loop=0;
        
		Simulation.start();
		
        Bus b = new Bus();
        Station[] s = new Station[3]; 
        s[0] = new Station(1, 3600/14);
        s[1] = new Station(2, 3600/10);
        s[2] = new Station(3, 3600/24);
        s[0].Ar.start();
		s[1].Ar.start();
		s[2].Ar.start();
		
		while(s[0].Ar.passengers.size()==0 || s[1].Ar.passengers.size()==0 || s[2].Ar.passengers.size()==0 ) {
			System.out.println("waiting for passenger...");
			sleep(1000);
		}
		
        while(time<80*3600){
			//System.out.println("masuk " + loop);
			if(b.position==2) {
				sumloop += timeloop;
				if(maxloop<timeloop) maxloop = timeloop;
				if(minloop>timeloop && start) minloop = timeloop;
				timeloop=0;
			}
			int i = b.position;
			double updating=s[i].updateQueue();
			//System.out.println(updating);
			time+=updating;
			timeloop+=updating;
			//System.out.println("waktu queue: " + time);
			int k=0;
			time += b.timetounload();
			
			//System.out.println("waktu unload: " + time);
			//System.out.println("passenger di " + i + ": "+ s[i].passengers.size());
			
			if(start) {
				timeloop+=b.timetounload();
			}
			System.out.println("Stasiun ke: " + (i+1) + ", jumlah passenger: " + s[i].passengers.size());
			b.removepassenger();
			
            for (int j = 0; j<s[i].passengers.size(); j++){
				//System.out.println("Jml passenger: " + b.passengers.size());
                if(b.passengers.size()<20){
                    b.addpassenger(s[i].passengers.get(j));
					k=j;
					timeloop += s[i].passengers.get(j).timeload;
                    time += s[i].passengers.get(j).timeload;
					//System.out.println("waktu load: " + time);
                }
            }
			if(s[i].passengers.size()==0) {
				time+=300;
				timeloop+=300;
			}
            System.out.println("banyak penumpang: "+k);
			int x=0;
            for(int j=0;x<=k && s[i].passengers.size()>0;) {
				double z = time-s[i].passengers.get(j).arrivaltime;
				if(maxallpersontime<z) maxallpersontime=z;
				if(minallpersontime>z) minallpersontime=z;
				sumallpersontime += z;
                s[i].passengers.remove(j);
				x++;
            }
            
            if(b.position==2 || b.position==1){
                movetime = 4.5*3600/b.speed;
            } else movetime = 3600/b.speed;
    
			//System.out.println("movetime: " + movetime);
			if(b.position==2) {
				start=true;
			}
            b.move();
			timeloop+=movetime;
            time+=movetime;
			System.out.println("waktu gerak: " + time);
			loop++;
        }
		
		s[0].Ar.terminate();
		s[1].Ar.terminate();
		s[2].Ar.terminate();
		
		Simulation.stop();
		
		System.out.println("Banyak loop: " + loop);
		double avgqueuesize0 = 3*s[0].sumqueuesize/loop,
				avgqueuesize1 = 3*s[1].sumqueuesize/loop,
				avgqueuesize2 = 3*s[2].sumqueuesize/loop,
				avgqueuedelay0 = s[0].Ar.sumqueuedelay/s[0].Ar.sumperson,
				avgqueuedelay1 = s[1].Ar.sumqueuedelay/s[1].Ar.sumperson,
				avgqueuedelay2 = s[2].Ar.sumqueuedelay/s[2].Ar.sumperson,
				avgpassenger = b.sumpassenger/loop,
				avgbusinstation0 = 3*s[0].sumbusinstation/loop,
				avgbusinstation1 = 3*s[1].sumbusinstation/loop,
				avgbusinstation2 = 3*s[2].sumbusinstation/loop,
				avgloop = sumloop/(80*3600),
				avgallpersontime = sumallpersontime/(s[0].Ar.sumperson+s[1].Ar.sumperson+s[2].Ar.sumperson);
		
		avgqueuesize0=s[0].Ar.sumqueuesize/time;
		avgqueuesize1=s[1].Ar.sumqueuesize/time;
		avgqueuesize2=s[2].Ar.sumqueuesize/time;
		
		System.out.println("Average number in queue 1: " + avgqueuesize0);
		System.out.println("Average number in queue 2: " + avgqueuesize1);
		System.out.println("Average number in queue 3: " + avgqueuesize2);
		System.out.println("Maximum number in queue 1: " + s[0].Ar.maxqueuesize);
		System.out.println("Maximum number in queue 2: " + s[1].Ar.maxqueuesize);
		System.out.println("Maximum number in queue 3: " + s[2].Ar.maxqueuesize);
		System.out.println("Average delay in queue 1: " + avgqueuedelay0);
		System.out.println("Average delay in queue 2: " + avgqueuedelay1);
		System.out.println("Average delay in queue 3: " + avgqueuedelay2);
		System.out.println("Maximum delay in queue 1: " + s[0].Ar.maxqueuedelay);
		System.out.println("Maximum delay in queue 2: " + s[1].Ar.maxqueuedelay);
		System.out.println("Maximum delay in queue 3: " + s[2].Ar.maxqueuedelay);
		System.out.println("Average number on the bus: " + avgpassenger);
		System.out.println("Maximum number on the bus: " + b.maxpassenger);
		System.out.println("Average bus stopped in station 1: " + avgbusinstation0);
		System.out.println("Average bus stopped in station 2: " + avgbusinstation1);
		System.out.println("Average bus stopped in station 3: " + avgbusinstation2);
		System.out.println("Maximum bus stopped in station 1: " + s[0].maxbusinstation);
		System.out.println("Maximum bus stopped in station 2: " + s[1].maxbusinstation);
		System.out.println("Maximum bus stopped in station 3: " + s[2].maxbusinstation);
		System.out.println("Minimum bus stopped in station 1: " + s[0].minbusinstation);
		System.out.println("Minimum bus stopped in station 2: " + s[1].minbusinstation);
		System.out.println("Minimum bus stopped in station 3: " + s[2].minbusinstation);
		System.out.println("1 Loop per "+ (double)80*3600/loop +" seconds");
		System.out.println("Max Time per loop: "+ maxloop);
		System.out.println("Min Time per loop: "+ minloop);
		System.out.println("Avg person in system: "+ avgallpersontime/60);
		System.out.println("Max person in system: "+ maxallpersontime/60);
		System.out.println("Min person in system: "+ minallpersontime/60);
    }
}