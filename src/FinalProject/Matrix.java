package FinalProject;

import FinalProject.Skeleton.SimulationInput;
import FinalProject.Skeleton.Unit;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that is responsible for running the simulation.
 * <p>
 * You will need to modify the run method to initialize, and run all of your units.
 *
 */
public class Matrix {
	public static void run(SimulationInput input) {
		
		// Setup the city
		City city = City.getInstance(input);
		
		// Retrieve all Units from City
		List<Unit> units = new ArrayList<>();
		units.addAll(city.getStoplights());
		units.addAll(city.getCars());
		
		// Convert all units into threads
		ArrayList<Thread> threads = new ArrayList<>();
		for (Unit u : units) {
			Thread t = new Thread(u);
			threads.add(t);
		}
		
		// Run all threads
		Timer.startTimer();
		for (Thread t : threads) {
			t.start();
		}
		
		// Prevent this Thread from terminating before all the threads are finished execution
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace(System.out);
			}
		}
	}
}