package logic;
import utils.Restriction;

import java.util.ArrayList;

/**
 * 
 * @author Jorge
 *
 * @description Class that implements each lot
 */


public class Lot implements Cloneable {

	private int x;
	private int y;
	private double price;
	private double leaning;
	private double width;
	private double height;
	private String symbol;
	private boolean active;
	private boolean used;
	//private ArrayList<Restriction> restrictions = new ArrayList<Restriction>();
	public Landuse land;



	public Lot(int x, int y, double price, double leaning, double width, double height, String symbol)
	{
		this.x = x;
		this.y = y;
		this.price = price;
		this.symbol = symbol;
		this.active = false;
		this.used = false;
		this.width = width;
		this.leaning = leaning;
		this.height = height;
		//this.restrictions = restrictions;
	}

	public double getLeaning() {
		return leaning;
	}

	public void setLeaning(double leaning) {
		this.leaning = leaning;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getSymbol()
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
	
	public void print()
	{
		System.out.println();
		System.out.println("Lot:");
		System.out.println("Position: (" + this.x + "," + this.y + ")");
		System.out.println("Price: " + this.price);
		System.out.println("Leaning: " + this.leaning);
		System.out.println("Width: " + this.width);
		System.out.println("Height: " + this.height);
		System.out.println();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException 
	{
		return super.clone();
	}

}
