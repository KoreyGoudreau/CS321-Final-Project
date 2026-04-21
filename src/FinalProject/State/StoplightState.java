package FinalProject.State;

import FinalProject.Observer.Stoplight;

public interface StoplightState {
	/**
	 * StoplightState represents a state assigned to a Stoplight instance.
	 * Stoplight is a subclass of and inherits performAction() from Unit.
	 * When an instance of Stoplight calls performAction(), it calls this method.
	 * This allows implementations of StoplightState to define different behaviors for a Stoplight.
	 *
	 * @param stoplight the Stoplight instance assigned this state
	 */
	void action(Stoplight stoplight);
	
	/**
	 * Returns the color of this StoplightState.
	 *
	 * @return the light color as a string
	 */
	String getLightColor();
}