import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;

import java.util.List;

class ProductionLine implements Runnable {
    
    private final int id;
    private static int Max_capacity = 2;
    private final static int NumberOfRobots = 10;

    public static String partNeeded;
    public static int robotNeeded;

    public static Lock lock = new ReentrantLock();

    public ProductionLine(final int id) {
        this.id = id;
    }

    public static void aircraftArrived(int id, final List<String> Details) { 
        System.out.println("Aircraft " + id + " arrives and requests parts " + Details);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Checking for space in the production line");



        if (Max_capacity == 0) {
            
            System.out.println(("No space in productionLine for aircraft " + id));
            // add to queue
        } else {
            
            //ProductionLineList.add(id.toString());// add to production line
            Max_capacity = Max_capacity - 1;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Aircraft " + id + " enters the production line");

            CheckForRobot(id, Details);
        }
    }

    public static void CheckForRobot(int id, final List<String> Details) {
        int count = 0;

        for (int i = 0; i <= Details.size(); i++) {

            partNeeded = Details.get(i);
            System.out.print("Aircraft " + id + " needs part " + partNeeded + "\n");

            final ExecutorService Robots = Executors.newFixedThreadPool(NumberOfRobots);
            
            for (final Integer key : Robot.RobotParts.keySet()) {
                
                if (Robot.RobotParts.get(key).equals(partNeeded)) {
                    robotNeeded = key;
                    System.out.print("Robot " + robotNeeded + " needed \n");
                                
                    Robots.execute(new Robot(robotNeeded, id));
                    
                    Robots.shutdown();

                    count++;

                    if ((count == Details.size())){
                        //System.out.println("Aircraft " + id + " Complete"); 
                        Max_capacity =  Max_capacity + 1;
                         
                    }
                       
                    
                    
                }  
                
            }  
            
        }
    }


    
   


    @Override
    public synchronized void run() {
        //System.out.println("Production Line " + id + " ready");

        Robot robots = new Robot(robotNeeded, id);
        //createaircrafts();
        //final ExecutorService Robots = Executors.newFixedThreadPool(NumberOfRobots);
        //Robots.execute(new Robot(robotNeeded));
        
        
        //robots.run();
    }
}