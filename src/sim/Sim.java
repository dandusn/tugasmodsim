/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

/**
 *
 * @author ACER
 */
public class Sim {
    
    public static void main(String[] args) {
        double time = 0;
        
        Bus b = new Bus();
        Station[] s = new Station[3]; 
        s[0] = new Station(1, 3600/14);
        s[1] = new Station(2, 3600/10);
        s[2] = new Station(3, 3600/24);
        s[0].Ar.start();
		s[1].Ar.start();
		s[2].Ar.start();
		
        while(time<80*60*60){
			int i = b.position;
			int k=0;
			time += b.timetoload();
			b.removepassenger();
            for (int j = 0; j<s[i].passengers.size(); j++){
				//System.out.println("Jml passenger: " + b.passengers.size());
				s[i].updateQueue();
                if(b.passengers.size()<20){
                    b.addpassenger(s[i].passengers.get(j));
					k=j;
                    time += s[i].passengers.get(j).timeload;
                }
            }
			for(int j=0;j<=k;j++) {
				s[i].passengers.remove(j);
			}
            b.move();
        }
    }
}