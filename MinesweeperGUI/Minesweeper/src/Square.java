import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Square 
{
	private static final int TILE_SIZE = 40;
	boolean isMine;
	int adjMines;
	boolean flag;
	String symbol;
	String Hidesymbol;
	boolean seen;
	Button space;
	

	
	
	public Square() 
	{
		this.isMine = false;
		this.adjMines = 0;
		this.flag = false;
		this.symbol = "?";
		this.Hidesymbol = "?";
		this.seen = false;
		this.space = new Button();
	}
	
	public void setMine(boolean mine)
	{
		this.isMine = mine;
		this.Hidesymbol = "*";
	}
	public void setAdj(int numMines)
	{
		this.adjMines = numMines;
		this.Hidesymbol = "" + numMines + "";
	}
	public void setFlag(boolean flag)
	{
		this.flag = flag;
		if(this.flag == true)
		{
			this.symbol = "F";
		}
		else
		{
			this.symbol = "?";
		}
	}
	public void Uncover(String symbol)
	{
		this.symbol = symbol;
	}
	
	
	
}
