import javafx.scene.control.Button;

public class GameButton extends Button {

    private int row;
    private int col;
    private String color;

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    
    public void setRow(int row) {
    	this.row = row;
    }
    
    public void setCol(int col) {
    	this.col = col;
    }
    
    public void setColor(String color) {
    	this.color = color;
    }
    
    public String getColor() {
    	return color;
    }
    
}
