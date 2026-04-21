package FinalProject.Observer;

import FinalProject.State.GreenState;
import FinalProject.State.StoplightState;
import Skeleton.Unit;
import Skeleton.SimulationInput;

import java.util.ArrayList;
import java.util.Random;

public class Stoplight extends Unit implements StoplightSubject {
	private static final String[] CARDINAL_DIRECTIONS = {"north", "east", "south", "west"};
	private int currentDirectionIndex;
	private StoplightState state;
	private final ArrayList<StoplightObserver> observers;
	private final int duration;
	
	public Stoplight(SimulationInput input) {
		super("Stoplight", input);
		
		this.duration = input.getIntegerInput("StopLightDuration");
		this.observers = new ArrayList<>();
		
		// Random initial direction
		Random random = new Random();
		this.currentDirectionIndex = random.nextInt(4);
		
		// Initial state is always green
		this.state = new GreenState();
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public String getCurrentDirection() {
		return CARDINAL_DIRECTIONS[this.currentDirectionIndex];
	}
	
	public void setState(StoplightState newState) {
		this.state = newState;
	}
	
	/**
	 * Returns true if a Stoplight is facing the same direction and is green.
	 *
	 * @param direction the direction a CarLine is facing as a String
	 * @return boolean
	 */
	public boolean isGreenForDirection(String direction) {
		return this.getCurrentDirection().equals(direction) && this.state.getLightColor().equals("green");
	}
	
	/**
	 * Ensures that by incrementing currentDirectionIndex, a value from CARDINAL_DIRECTIONS can always be extracted.
	 * The String values for CARDINAL_DIRECTIONS move in a circular sequence ("north", "east", "south", "west", "north" ...).
	 */
	public void rotateDirection() {
		this.currentDirectionIndex = (this.currentDirectionIndex + 1) % 4;
	}
	
	/**
	 * Updates all the CarLines for this Stoplight's associated intersection of a change in state.
	 */
	@Override
	public void notifyObservers() {
		for (StoplightObserver o : observers) {
			o.update(this.getCurrentDirection(), state.getLightColor());
		}
	}
	
	@Override
	public void addObserver(StoplightObserver o) {
		this.observers.add(o);
	}
	
	@Override
	public void performAction() {
		state.action(this);
	}
	
	/**
	 * No Statistics submitted.
	 */
	@Override
	public void submitStatistics() {
	}
}