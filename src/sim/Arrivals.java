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
import org.javasim.Simulation;
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
	double prevtime = 0, deltaT=0;
	
	
    public Arrivals(double mean) throws SimulationException, RestartException
    {
		super();
        InterArrivalTime = new ExponentialStream(mean);
		super.activate();
    }

	@Override
    public void run ()
    {
        while (!terminated())
        {
            try
            {
                hold(InterArrivalTime.getNumber());
            }
            catch (SimulationException | RestartException | IOException e)
            {
            }

            try {
				//System.out.println(InterArrivalTime.getNumber());
                passengers.add(new Passenger(InterArrivalTime.getNumber(), super.time()));
				//System.out.println("Passenger added");
				sumperson++;
				deltaT=super.time()-prevtime;
				sumqueuesize+=passengers.size();
				prevtime=super.time();
				System.out.println(super.time());
				if(maxqueuesize<passengers.size()) maxqueuesize=passengers.size();
				
				sumqueuedelay += InterArrivalTime.getNumber();
				if(maxqueuedelay<InterArrivalTime.getNumber()) maxqueuedelay = InterArrivalTime.getNumber();
				
				
            } catch (IOException | ArithmeticException ex) {
                Logger.getLogger(Arrivals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private final ExponentialStream InterArrivalTime;
}
