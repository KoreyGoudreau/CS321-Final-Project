package FinalProject;

/**
 * There are 3 types of Cells, "road", "intersection" or "none".
 */
public class Cell {
	
	private final int row;
	private final int col;
	private final String type;
	private final Grid grid;
	
	private Intersection intersection = null;
	
	public Cell(int row, int col, String type, Grid grid) {
		this.row = row;
		this.col = col;
		this.type = type;
		this.grid = grid;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public Intersection getIntersection() {
		return this.intersection;
	}
	
	/**
	 * Assign to this Cell an Intersection if this Cell is of type "intersection".
	 * Returns true if successful, false if this Cell is not of type "intersection".
	 *
	 * @param intersection the assigned Intersection
	 * @return boolean value representing the success of this function
	 */
	public boolean setIntersection(Intersection intersection) {
		if (this.isTypeIntersection()) {
			this.intersection = intersection;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if this Cell is of type "road" and is not on a Cell adjacent to an Intersection.
	 * Buildings cannot be placed on a Cell next to an Intersection.
	 *
	 * @return boolean
	 */
	public boolean canSetBuilding() {
		return this.isTypeRoad() && this.noAdjacentIntersections();
	}
	
	public boolean isTypeRoad() {
		return "road".equals(this.type);
	}
	
	public boolean isTypeIntersection() {
		return "intersection".equals(this.type);
	}
	
	/**
	 * Cars can drive on Cell of type "road" or "intersection".
	 *
	 * @return boolean
	 */
	public boolean canDriveOn() {
		return this.isTypeRoad() || this.isTypeIntersection();
	}
	
	public Cell getNorth() {
		return grid.getCell(this.row + 1, this.col);
	}
	
	public Cell getSouth() {
		return grid.getCell(this.row - 1, this.col);
	}
	
	public Cell getEast() {
		return grid.getCell(this.row, this.col + 1);
	}
	
	public Cell getWest() {
		return grid.getCell(this.row, this.col - 1);
	}
	
	public boolean noAdjacentIntersections() {
		return (
			(this.getNorth() == null || !this.getNorth().isTypeIntersection()) &&
				(this.getEast() == null || !this.getEast().isTypeIntersection()) &&
				(this.getSouth() == null || !this.getSouth().isTypeIntersection()) &&
				(this.getWest() == null || !this.getWest().isTypeIntersection())
		);
	}
	
	/**
	 * 2 Cells on the same grid are equal if they have the same row and column values.
	 *
	 * @param obj the reference object with which to compare.
	 * @return if both Cells are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cell otherCell) {
			return this.row == otherCell.getRow() && this.col == otherCell.getCol();
		}
		return false;
	}
}