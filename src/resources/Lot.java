package resources;
/**
 * 
 * @author Jorge
 *
 * @description Class that implements each lot
 */


public class Lot {

	private int x;
	private int y;
	private double price;
	private char symbol;
	boolean active;
	boolean used;

	public Lot(int x, int y, double price, char symbol)
	{
		this.x = x;
		this.y = y;
		this.price = price;
		this.symbol = symbol;
		this.active = false;
		this.used = false;
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

	public double getPrice()
	{
		return price;
	}

	public boolean getActive()
	{
		return active;
	}

	public void setActive(boolean act)
	{
		this.active = act;
	}

	public boolean getUsed()
	{
		return used;
	}
	
	public void setUsed(boolean used)
	{
		this.used = used;
	}





}
