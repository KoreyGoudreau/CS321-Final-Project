package FinalProject.State;

import FinalProject.Observer.Stoplight;

public class GreenState implements StoplightState {
	
	private int counter = 0;
	
	/**
	 * Represents the green phase of a Stoplight.
	 * Each time Stoplight performs action(), the counter is incremented, until it reaches stoplight.getDuration().
	 * Then the Stoplight sets its state to YellowState and notifies all queues in the intersection.
	 *
	 * @param stoplight the Stoplight instance assigned this state
	 */
	@Override
	public void action(Stoplight stoplight) {
		this.counter++;
		
		if(this.counter >= stoplight.getDuration()) {
			stoplight.setState(new YellowState());
			stoplight.notifyObservers();
		}
	}
	
	@Override
	public String getLightColor() {
		return "green";
	}
}