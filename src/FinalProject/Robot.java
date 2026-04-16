package FinalProject;

import Skeleton.SimulationInput;
import Skeleton.StatisticsContainer;
import Skeleton.Unit;
import Skeleton.WorkerStatistic;

import java.util.ArrayList;
import java.util.List;

/**
 * Example class that implements the Skeleton.Unit abstract class.
 **/
class Robot extends Unit {
	public Robot(String name, SimulationInput input) {
		super(name, input);
		this.getStats().addStatistic(
			"RobotActionsPerformed",
			new WorkerStatistic("RobotActionsPerformed")
		);
	}
	
	public Robot(SimulationInput input) {
		super("DefaultRobotName", input);
	}
	
	/**
	 * The example robot yells something from the input, as well
	 * as it's given name.
	 * */
	public void performAction() {
		System.out.println(
			// Note how the value you receive is an arraylist object (so
			// you can set multiple values for the input field if needed).
			this.getSimInput().getInput("RobotsMustYell").get(0)
		);
		System.out.println(String.format("THIS IS %s", this.getName()));
	}
	
	/** Submit the number of actions performed (always 1).
	 *
	 * Use submitStatisics for things you want to submit AFTER the action is complete
	 * (this could be for things involving combination metrics, or conditional metrics).
	 * **/
	public void submitStatistics() {
		this.getStats().getStatistic("RobotActionsPerformed").addValue(1);
	}
}