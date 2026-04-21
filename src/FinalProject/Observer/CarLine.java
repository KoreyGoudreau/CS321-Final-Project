package FinalProject.Observer;

import java.util.ArrayList;

public class CarLine implements StoplightObserver {
	
	private final ArrayList<Car> carLine;
	private final Intersection intersection;
	private final Stoplight stoplight;
	private final String direction;
	
	public CarLine(Intersection intersection, String direction) {
		this.carLine = new ArrayList<>();
		this.intersection = intersection;
		this.stoplight = intersection.getStoplight();
		this.direction = direction;
		
		// Setup this queue as an observer for the Stoplight
		this.stoplight.addObserver(this);
	}
	
	/**
	 * Only 1 Car Thread can add itself to the CarLine at a time.
	 * This prevents data corruption if 2 Cars reach an intersection at the same time.
	 * 
	 * @param car the Car being added
	 */
	public synchronized void addCar(Car car) {
		
		// Add the Car to the back of the line
		this.carLine.add(car);
		
		moveFirstCar();
	}
	
	/**
	 * Moves the first Car in the Carline. Only 1 car can move at a time.
	 */
	private synchronized void moveFirstCar() {
		
		// Move cars when the light is green and facing the same direction as this Carline
		if(this.stoplight.isGreenForDirection(this.direction) && !this.carLine.isEmpty()) {
			
			// Moves the first car in the line
			Car car = this.carLine.getFirst();
			
			// Try to acquire the intersection lock
			if (this.intersection.getPassingCarSemaphore().tryAcquire()) {
				
				// Remove the car that just moved from the line
				this.carLine.removeFirst();
				
				car.crossIntersection(this);
			}
		}
	}
	
	/**
	 * Called by a Car after it moves into the Intersection.
	 */
	public void notifyCarMoved() {
		
		// Release the intersection lock
		this.intersection.getPassingCarSemaphore().release();
		
		moveFirstCar();
	}
	
	@Override
	public void update(String direction, String lightColor) {
		
		moveFirstCar();
	}
}
