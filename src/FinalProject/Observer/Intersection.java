package FinalProject.Observer;

import Skeleton.SimulationInput;

import java.util.concurrent.Semaphore;

public class Intersection {
	
	private final Stoplight stoplight;
	private final CarLine northCarLine;
	private final CarLine eastCarLine;
	private final CarLine southCarLine;
	private final CarLine westCarLine;
	private final Semaphore passingCarSemaphore;
	
	public Intersection(SimulationInput input) {
		this.stoplight = new Stoplight(input);
		this.northCarLine = new CarLine(this, "north");
		this.eastCarLine = new CarLine(this, "east");
		this.southCarLine = new CarLine(this, "south");
		this.westCarLine = new CarLine(this, "west");
		this.passingCarSemaphore = new Semaphore(1);
	}
	
	public Stoplight getStoplight() {
		return this.stoplight;
	}
	
	/**
	 * Returns this intersection's Semaphore.
	 * This Semaphore is used to only allow 1 Car to cross at a time.
	 *
	 * @return a Semaphore
	 */
	public Semaphore getPassingCarSemaphore() {
		return passingCarSemaphore;
	}
	
	/**
	 * A Car moving upwards on the grid is facing towards the "north" direction.
	 * A Car moving towards the right is facing the "east" direction.
	 * Similar reasoning for "south" and "west".
	 * When a Car reaches an intersection it is facing 1 of these 4 cardinal directions.
	 * The direction a Car is what determines which queue they enter.
	 * For example, a Car facing "north" enters the "north" queue.
	 *
	 * @param direction the arriving Car's facing direction
	 * @return the queue that Car can join at this intersection
	 */
	public CarLine getCarLine(String direction) {
		return switch (direction) {
			case "north" -> this.northCarLine;
			case "east" -> this.eastCarLine;
			case "south" -> this.southCarLine;
			case "west" -> this.westCarLine;
			default -> null;
		};
	}
}