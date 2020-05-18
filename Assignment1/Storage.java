import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.NumberOfInterveningJobs;

public class Storage implements Runnable{

    int numberOfParts;

    public Storage(int numberOfParts){
        this.numberOfParts = numberOfParts;
    }

    public synchronized void orderParts(int numberOfParts){
        if(Robot.numberOfParts == 5){
            //System.out.println("Hello");
            try {
                System.out.println("Ordering new parts");
                TimeUnit.SECONDS.sleep(5);
                Robot.numberOfParts = 20;
                System.out.println("Order arrived. Number of parts remaining: " + Robot.numberOfParts);
            } catch (Exception e) {
                e.printStackTrace();
                
            }
        }
    }
    public void run(){
        orderParts(numberOfParts);
    }
}