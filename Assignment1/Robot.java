import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.NumberOfDocuments;

import java.util.List;


public class Robot implements Runnable{

    private int id;
    public int aircraftid;
    

    public static String state = "Ready";

    public static String checkState;
    public final int NumberOfRobots = 10;
    public static int numberOfParts = 20;
    public static HashMap<Integer, String> RobotParts;

    public String partneeded;

    public Robot(int id, int aircraftid) {
        this.id = id;
        this.aircraftid = aircraftid;


        RobotParts = new HashMap<Integer, String>();

        RobotParts.put(1, "A+");
        RobotParts.put(2, "B+");
        RobotParts.put(3, "C+");
        RobotParts.put(4, "D+");
        RobotParts.put(5, "E+");
        RobotParts.put(6, "F+");
        RobotParts.put(7, "G+");
        RobotParts.put(8, "H+");
        RobotParts.put(9, "I+");
        RobotParts.put(10, "J+");

    }

    public static synchronized void currentState(int id, int aircraftid) {

        if (state == "Ready") {
            checkState = "Ready";
        } else if (state == "Assigned") {
            checkState = "Assigned";
        }

        assignRobot(checkState, id, aircraftid);

    }

    public static void assignRobot(String checkState, int id, int aircraftid) {
        
        if (checkState != "Ready") {
            System.out.println("Robot already assigned");
            assignRobot(checkState, id, aircraftid);
        } 
        else {
        
            state = "Assigned";
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Robot " + id + " assigned to aircraft " + aircraftid);
            working(id, aircraftid);
            
        }
    }

    public static synchronized void working(int id, int aircraftid) {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Robot " + id + " starts working on aircraft " + aircraftid);
        
        numberOfParts -= 1;
        System.out.println("Number of parts remaining: " + numberOfParts);
        ExecutorService Storage = Executors.newSingleThreadExecutor();
        Storage.execute(new Storage(numberOfParts));
        Storage.shutdown();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        state = "Ready";
        System.out.println("Robot " + id + " finished working on aircraft " + aircraftid);
    }

   
    
   
    @Override
    public void run(){

        currentState(id, aircraftid);

    }
}