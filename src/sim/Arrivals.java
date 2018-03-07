/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.ExponentialStream;

public class Arrivals extends SimulationProcess
{
    List<Passenger> passengers = new ArrayList<>();
    double maxqueuedelay = 0;
	double sumqueuedelay = 0;
	int maxqueuesize = 0;
	double sumqueuesize = 0;
	int sumperson = 0;
	
	
    public Arrivals(double mean)
    {
        InterArrivalTime = new ExponentialStream(mean);
    }

    public void run ()
    {
        while (!terminated())
        {
            try
            {
                hold(InterArrivalTime.getNumber());
            }
            catch (SimulationException e)
            {
            }
            catch (RestartException e)
            {
            }
            catch (IOException e)
            {
            }

            try {
				//System.out.println(InterArrivalTime.getNumber());
				double x = InterArrivalTime.getNumber();
                passengers.add(new Passenger(x, SimulationProcess.currentTime()));
				//System.out.println("Passenger added");
				sumperson++;
				sumqueuesize+=passengers.size();
				if(maxqueuesize<passengers.size()) maxqueuesize=passengers.size();
				
				sumqueuedelay += x;
				if(maxqueuedelay<x) maxqueuedelay = x;
				
				
            } catch (IOException ex) {
                Logger.getLogger(Arrivals.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArithmeticException ex) {
                Logger.getLogger(Arrivals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ExponentialStream InterArrivalTime;
}
