/**
 * 
 * @author Jorge
 *
 * @description Class that implements each lot
 */


public class Lot {
	
	private int x;
	private int y;
	private char symbol;
	
	Lot(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public char getSymbol()
	{
		return symbol;
		
	}
	
	public boolean hasBuilding()
	{
		if(symbol == 'E')
		{
			return false;
		}
		
		return true;	
		
	}

}
