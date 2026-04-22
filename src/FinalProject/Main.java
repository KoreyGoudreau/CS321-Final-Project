/**
 *  Name: Korey Goudreau
 *  Date: April 24 2026
 *  Description: A City Traffic Simulator.
 */

package FinalProject;

import FinalProject.Skeleton.SimulationInput;
import FinalProject.Skeleton.StatisticsContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class is responsible for the testing. It has a helper method
 * that makes it easier to run many tests.
 **/
public class Main {
	/**
	 * Runs a test with the given input and returns the statistics
	 * produced from the test run. Simplifies the testing process.
	 *
	 * @param input The input to run the test with.
	 * @return The statistics of the test run.
	 **/
	public static StatisticsContainer runTest(SimulationInput input) {
		// Initialize the stats singleton here so the input can
		// be ignored in future calls
		StatisticsContainer stats = StatisticsContainer.getInstance(input);
		Matrix.run(input);
		
		return stats;
	}
	
	/**
	 * See method above for details.
	 **/
	public static StatisticsContainer runTest(ArrayList<ArrayList<String>> input) {
		return runTest(new SimulationInput(input));
	}
	
	public static void main(String[] args) {
		/*
		You can either prepare your input as an array, or add it directly to your
		FinalProject.Skeleton.SimulationInput object (see below).

			ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
			input.add(
				// The time to run in seconds
				new ArrayList<String>(Arrays.asList("Time", "600"))
			);
			input.add(
				// The number of actions units must perform per second
				new ArrayList<String>(Arrays.asList("ActionsPerSecond", "60"))
			);
		*/
		
		SimulationInput si = new SimulationInput();
		
		// Setup simulation
		si.addInput("Time", List.of("60")); // Run time in number of minutes (the FinalProject.Skeleton code Unit.java uses seconds!!!)
		si.addInput("ActionsPerSecond", List.of("1"));
		
		// Setup the Grid
		si.addInput("GridIntersectionsPerRow", List.of("4"));
		si.addInput("GridRoadLength", List.of("3"));
		
		// Setup Stoplights
		si.addInput("StopLightDuration", List.of("4")); // Number of minutes before stoplight switches state
		
		// Setup Cars
		si.addInput("OwnerNames", List.of("Alex", "Bob", "Caleb", "Derek", "Eric"));
		si.addInput("CarSpeeds", List.of("1", "2", "1", "3", "1")); // Minutes it takes for a car to move 1 position
		si.addInput("RouteStrategies", List.of("verticalfirst", "horizontalfirst", "zigzag", "verticalfirst", "zigzag"));
		
		// Setup Buildings
		si.addInput("BuildingNames", List.of("Home1", "Destination1", "Home2"));
		si.addInput("BuildingTypes", List.of("home", "destination", "home"));
		si.addInput("OpeningTimes", List.of("30"));
		
		// Setup each building's location on the grid, then map Cars to the Building (using OwnerNames)
		si.addInput("Home1", List.of("0", "2", "Alex", "Bob", "Caleb"));
		si.addInput("Destination1", List.of("8", "6", "Alex", "Bob", "Caleb", "Derek", "Eric"));
		si.addInput("Home2", List.of("10", "12", "Derek", "Eric"));
		
		// Run the simulation
		StatisticsContainer stats = runTest(si);
		
		// Post the finalized statistics
		stats.printStatisticsContainer();

		/*
			Add many more tests below using different input. Try to probe for edge cases and organize
			your tests properly.
		*/
		
		// You can change the input, and then reset the statistics singleton with:
		// input = new FinalProject.Skeleton.SimulationInput();
		// // ... Add input
		// si.resetInstance(input);
	}
}