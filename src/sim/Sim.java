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
        
        int i = 2;
        while(time<80*60*60){
            for (int j = 0; j<s[i].Ar.passengers.size(); j++){
                if(b.passengers.size()<20){
                    b.addpassenger(s[i].Ar.passengers.get(j));
                    time += s[i].Ar.passengers.get(j).timeload;
                }
            }
            
        }
    }
}