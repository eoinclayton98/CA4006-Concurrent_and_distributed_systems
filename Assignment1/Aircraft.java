import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Aircraft extends Thread {
	public static String id;
	public static List<String> parts = new ArrayList();
	public static Boolean stateAircraft = false;


	private final static int numberOfProductionLines = 1;

	public Aircraft(String id, List<String> parts){
		this.id = id;
		this.parts = parts;
	}

	public String toString(){
		String listString = "|";

		for (String s : this.parts){
			listString += s + "|";
		}
		
		return "Aircraft("+ this.id + " " + "[" + listString + "]"  + ")";
	}

	public Boolean checkCompletion(){
		return this.stateAircraft;
	}

	public void wipe(){
		this.parts.clear();
	}

	public void completed(String arg){
		this.parts.remove(arg);
		
	}

	public static void main(String[] args) {

		System.out.println("====Start===="); 


		final ExecutorService ProductionLines = Executors.newFixedThreadPool(numberOfProductionLines);

        ProductionLines.execute(new ProductionLine(1));
        ProductionLines.execute(new ProductionLine(2));

        ProductionLines.shutdown();
        
		

		List<String> tasks = new ArrayList();
		tasks.add("A+");
		tasks.add("B+");
		tasks.add("C+");
		tasks.add("D+");
		tasks.add("E+");
		tasks.add("F+");
		tasks.add("G+");
		tasks.add("H+");
		tasks.add("I+");
		tasks.add("J+");
	
		//MyQueue que = new MyQueue();
		
		for(int i = 0; i < 10; i++){
			int time = getRandomNumberInRange(5,10);
			try{
				String aircraftID = String.format("Aircraft_%s",Integer.toString(i+1));
				int id = i+1;

				//System.out.println(aircraftID);

				Random r = new Random();
				int subsetSize = getRandomNumberInRange(1,10);
				
				for (int j = 0; j < subsetSize; j++){
					int indexToSwap = i + r.nextInt(10 - i);
					String temp = tasks.get(j);
					tasks.set(j, tasks.get(indexToSwap));
					tasks.set(indexToSwap, temp);
				}
				//System.out.println(tasks.subList(0, subsetSize));
				List<String> partsRequired = new ArrayList();
				for (String st : tasks.subList(0, subsetSize))
				{
					
					partsRequired.add(st);
					
					
				}
				TimeUnit.SECONDS.sleep(time);
				ProductionLine.aircraftArrived(id, partsRequired);
				//Aircraft new1 = new Aircraft(aircraftID,partsRequired);
				//System.out.println(new1);
				
		
				
				
				//que.put(new1);
				//String idPlane = String.format("Aircraft_%s",Integer.toString(i));
				//Aircraft newAircraft = new Aircraft(idPlane,TaskGenerator(tasks));
				
				//System.out.println(newAircraft);
			}
			catch(Exception e){
			}
			
		}
	}
	
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}

