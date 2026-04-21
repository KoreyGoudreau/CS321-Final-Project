package FinalProject.State;

import FinalProject.Observer.Stoplight;

public class YellowState implements StoplightState {
	
	private int counter = 0;
	
	/**
	 * Represents the yellow phase of a Stoplight.
	 * Each time Stoplight performs action(), the counter is incremented, until it reaches stoplight.getDuration().
	 * Then the Stoplight rotates the direction it is facing.
	 * It then sets its state to GreenState and notifies all queues in the intersection.
	 *
	 * @param stoplight the Stoplight instance assigned this state
	 */
	@Override
	public void action(Stoplight stoplight) {
		this.counter++;
		
		if (this.counter >= stoplight.getDuration()) {
			stoplight.rotateDirection();
			stoplight.setState(new GreenState());
			stoplight.notifyObservers();
		}
	}
	
	@Override
	public String getLightColor() {
		return "yellow";
	}
}