package logic;
import utils.Restriction;

import java.util.ArrayList;

public class Landuse {

	private int x;
	private int y;
	private String type;
	private boolean isActive;
	private ArrayList<Restriction> restrictions = new ArrayList <Restriction>();


	public Landuse(String type) {
		super();
		this.type = type;
	}

	public Landuse(String type, ArrayList<Restriction> restrictions) {
		super();
		this.type = type;
		this.restrictions =restrictions;
	}

	public ArrayList<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(ArrayList<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void print()
	{
		System.out.println();
		System.out.println("Landuse: " + this.type);
		System.out.println();
		System.out.println("Restrictions:");
		for(int i = 0; i < this.restrictions.size(); i++)
		{
			System.out.println("Requirement: " + this.restrictions.get(i).getReq());
			System.out.println("Type: " + this.restrictions.get(i).getType());
			System.out.println("Arithmetics: " + this.restrictions.get(i).getArithmetics());
			System.out.println("Value: " + this.restrictions.get(i).getValue());
			System.out.println();
			
		}

	}

}

