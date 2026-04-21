package FinalProject.Strategy;

import FinalProject.Cell;

public interface RouteStrategy {
	/**
	 * Returns the next Cell a Car that is driving on the road should move to next.
	 * If a Car cannot move anywhere then by default return the Car's current cell.
	 * This ensures the Car does not move.
	 * Cars cannot move on Cells of type "none".
	 *
	 * @param current     the Cell representing a Car's current position of the grid
	 * @param destination the Cell representing a Car's travel Destination
	 * @return the next Cell the Car should move to
	 */
	Cell nextCell(Cell current, Cell destination);
}