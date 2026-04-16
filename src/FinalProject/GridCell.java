package FinalProject;

/**
 * The possible gridCell types are:
 * 	"intersection"
 * 	"road"
 * 	"null"
 */
public class GridCell {
	private final int row;
	private final int col;
	private String type;
	
	public GridCell(int row, int col, String type) {
		this.row = row;
		this.col = col;
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
}