
public class Square 
{
	boolean isMine;
	int adjMines;
	boolean flag;
	String symbol;
	String Hidesymbol;
	boolean seen;
	
	
	public Square()
	{
		this.isMine = false;
		this.adjMines = 0;
		this.flag = false;
		this.symbol = " ? ";
		this.Hidesymbol = " ? ";
		this.seen = false;
	}
	
	public void setMine(boolean mine)
	{
		this.isMine = mine;
		this.Hidesymbol = " * ";
	}
	public void setAdj(int numMines)
	{
		this.adjMines = numMines;
		this.Hidesymbol = " " + numMines + " ";
	}
	public void setFlag(boolean flag)
	{
		this.flag = flag;
		if(this.flag == true)
		{
			this.symbol = " F ";
		}
		else
		{
			this.symbol = " ? ";
		}
	}
	public void Uncover(String symbol)
	{
		this.symbol = symbol;
	}
	
	
	
}
