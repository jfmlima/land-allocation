package logic;

/**
 * 
 * @author Jorge
 * @description Class that represent the matrix where the map will be defined, similar to a upper view plant, with some methods to proper manipulation
 */

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

import utils.Restriction;
import utils.AStar.lotComparator;


public class Map implements Cloneable, Serializable {      

	private String[][] m;
	private ArrayList<Lot> emptyLots = new ArrayList<Lot>();
	private ArrayList<Lot> assignedLots = new ArrayList<Lot>();

	private ArrayList<Landuse> unassignedLanduses = new ArrayList<Landuse>();
	private ArrayList<Landuse> assignedLanduses = new ArrayList<Landuse>();

	private int dim;
	private double FN;
	private double accumCost;


	public double getFN() {
		return FN;
	}

	public void setFN(double fN) {
		FN = fN;
	}

	public Map(int dim, String[][] m, ArrayList<Lot> empty, ArrayList<Lot> usedLots, ArrayList<Landuse> unassignedLand, ArrayList<Landuse> assignedLand, double FN, double accumCost) throws InterruptedException
	{
		this.dim = new Integer(dim);
		this.m = new String[this.dim][this.dim];
		this.FN = new Double(FN);
		this.accumCost = new Double(accumCost);
		this.assignedLanduses = new ArrayList<Landuse>(assignedLand);
		this.unassignedLanduses = new ArrayList<Landuse>(unassignedLand);
		this.emptyLots = new ArrayList<Lot>(empty);
		this.assignedLots = new ArrayList<Lot>(usedLots);
		for(int i = 0; i < this.emptyLots.size(); i++)
		{
			insertEmptyLot(emptyLots.get(i));
		}
		for(int u = 0; u < this.assignedLanduses.size(); u++)
		{
			String firstLetter = "";
			String se = "";

			firstLetter = String.valueOf(assignedLanduses.get(u).getType().charAt(0));
			se = String.valueOf(assignedLanduses.get(u).getType().charAt(1));


			if(this.assignedLanduses.contains(this.assignedLanduses.get(u)))
			{
				this.assignedLanduses.get(u).setX(assignedLanduses.get(u).getX());
				this.assignedLanduses.get(u).setY(assignedLanduses.get(u).getY());
				this.m[assignedLanduses.get(u).getX()][assignedLanduses.get(u).getY()] = firstLetter+se ;
			}
			

		}
	}
	
	public Map(int dim)
	{
		this.dim = dim;
		m = new String[dim][dim];
		this.FN = 0;
		this.accumCost = 0;
	}

	public static Map newMap(Map aMap) throws InterruptedException
	{
		return new Map(aMap.getDim(), aMap.getM(), aMap.getEmptyLots(), aMap.getAssignedLots(), aMap.getUnassignedLanduses(), aMap.getAssignedLanduses(), aMap.getFN(), aMap.getAccumCost());
	}


	//Draws the map as is

	public double getAccumCost() {
		return accumCost;
	}

	public void setAccumCost(double accumCost) {
		this.accumCost = accumCost;
	}

	public void drawMap(){

		for(int r = 0; r < dim; r++)
		{	 
			System.out.print("|");
			for (int c = 0; c < dim; c++)
			{	
				if(m[c][r]  == null)
				{
					System.out.print(" ");
					System.out.print("|");
				}
				else
				{
					System.out.print(m[c][r]);
					System.out.print("|");
				}
			}
			System.out.println();
		}
	}

	public String[][] getM() {
		return m;
	}

	public void setM(String[][] m) {
		this.m = m;
	}

	public ArrayList<Lot> getEmptyLots() {
		lotComparator comp = new lotComparator();
		Collections.sort(emptyLots, comp);
		return emptyLots;
	}

	public void setEmptyLots(ArrayList<Lot> emptyLots) {
		this.emptyLots = emptyLots;

		for(int i = 0; i < this.emptyLots.size(); i++)
		{
			insertEmptyLot(emptyLots.get(i));
		}
	}



	public ArrayList<Lot> getAssignedLots() {

		return assignedLots;
	}

	public void setAssignedLots(ArrayList<Lot> assignedLots) {
		this.assignedLots = assignedLots;
	}

	public ArrayList<Landuse> getUnassignedLanduses() {
		landuseComparator comp = new landuseComparator();
		Collections.sort(unassignedLanduses, comp);
		return unassignedLanduses;
	}

	public void setUnassignedLanduses(ArrayList<Landuse> unassignedLanduses) {
		this.unassignedLanduses = unassignedLanduses;
	}

	public ArrayList<Landuse> getAssignedLanduses() {
		return assignedLanduses;
	}

	public void setAssignedLanduses(ArrayList<Landuse> assignedLanduses) {
		this.assignedLanduses = assignedLanduses;
	}

	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	//Empty lot verification

	public boolean isEmptyLot(int x, int y)
	{
		if (m[x][y] == "E")
		{
			return true;
		}

		return false;
	}

	public void insertEmptyLot(Lot eLot)
	{

		m[eLot.getX()][eLot.getY()] = eLot.getSymbol();

		eLot.setActive(true);
	}

	public Lot getLotByCoordinates(int x, int y)
	{
		for(Lot var : emptyLots)
		{
			if(var.getX() == x & var.getY() == y)
				return var;
		}
		System.out.println("Error, Lot not found");		
		return null;
	}

	public void insertLanduseToLot(Landuse land, Lot lot)
	{
		String firstLetter = "";
		String se = "";

		firstLetter = String.valueOf(land.getType().charAt(0));
		se = String.valueOf(land.getType().charAt(1));

		m[lot.getX()][lot.getY()] = firstLetter+se;

		land.setX(lot.getX());
		land.setY(lot.getY());

		lot.land = land;

		assignedLots.add(lot);
		emptyLots.remove(lot);

		assignedLanduses.add(land);
		unassignedLanduses.remove(land);

	}

	public void removeLanduseFromLot(Landuse land, Lot lot)
	{


		m[lot.getX()][lot.getY()] = " ";

		land.setX(-1);
		land.setY(-1);

		lot.land = null;

		assignedLots.remove(lot);
		emptyLots.add(lot);

		assignedLanduses.remove(land);
		unassignedLanduses.add(land);

	}

	public class lotComparator implements Comparator<Lot> {

		@Override
		public int compare(Lot o1, Lot o2) {
			// TODO Auto-generated method stub
			if(o1.getPrice() > o2.getPrice())
			{
				return 1;
			}
			else if(o1.getPrice() == o2.getPrice())
				return 0;
			else
				return -1;

		}
	}
	public class landuseComparator implements Comparator<Landuse> {

		@Override
		public int compare(Landuse o1, Landuse o2) {
			// TODO Auto-generated method stub

			if(o1.getRestrictions().size() < o2.getRestrictions().size())
			{
				return 1;
			}
			else if(o1.getRestrictions().size() == o2.getRestrictions().size())
				return 0;
			else
				return -1;

		}
	}

	public boolean isInRange(Lot lot, Landuse land, int count)
	{
		for(int i = 1; i <= land.getRestrictions().get(count).getValue(); i++)
		{
			for(int j = 0; j < assignedLots.size(); j++)
			{
				if(assignedLots.get(j).getX() < i)
				{

				}
			}
		}
		return true;

	}

	public boolean applyLanduseRestrictions(Landuse land, Lot lot)
	{
		ArrayList<Restriction> restrictions = land.getRestrictions();

		for(int i =0; i< restrictions.size(); i++)
		{
			//Verify restriction requirement and checks if valid - MUST HAVE

			if(restrictions.get(i).getReq().equals("MUST HAVE"))
			{
				if(restrictions.get(i).getArithmetics().equals("MORE THAN"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						for(int z = 1; z <= restrictions.get(i).getValue(); z++)
						{
							for(int j = 0; j < assignedLots.size(); j++)
							{
								if(assignedLots.get(j).getX() > z)
								{

								}
							}
						}
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{

						if(lot.getLeaning() > restrictions.get(i).getValue())
						{
							return true;
						}

						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() > restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() > restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals( "price"))
					{
						if(lot.getPrice() > restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("MORE OR THE SAME AS"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() >= restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() >= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() >= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() >= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("EXACTLY"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() == restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() == restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() == restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() == restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("LESS THAN"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() < restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() < restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() < restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() < restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("LESS OR THE SAME AS"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() <= restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() <= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() <= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() <= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}
			}

			//Verify restriction requirement and checks if valid - MUST NOT HAVE


			if(restrictions.get(i).getReq().equals("MUST NOT HAVE"))
			{
				if(restrictions.get(i).getArithmetics().equals("MORE THAN"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{

					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(!(lot.getLeaning() > restrictions.get(i).getValue()))
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(!(lot.getWidth() > restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(!(lot.getHeight() > restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(!(lot.getPrice() > restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("MORE OR THE SAME AS"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(!(lot.getLeaning() >= restrictions.get(i).getValue()))
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(!(lot.getWidth() >= restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(!(lot.getHeight() >= restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(!(lot.getPrice() >= restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("EXACTLY"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(!(lot.getLeaning() == restrictions.get(i).getValue()))
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(!(lot.getWidth() == restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(!(lot.getHeight() == restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(!(lot.getPrice() == restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("LESS THAN"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(!(lot.getLeaning() < restrictions.get(i).getValue()))
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(!(lot.getWidth() < restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(!(lot.getHeight() < restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(!(lot.getPrice() < restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("LESS OR THE SAME AS"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(!(lot.getLeaning() <= restrictions.get(i).getValue()))
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(!(lot.getWidth() <= restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(!(lot.getHeight() <= restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(!(lot.getPrice() <= restrictions.get(i).getValue()))
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}
			}

			//Verify restriction requirement and checks if valid - CAN HAVE


			if(restrictions.get(i).getReq().equals("CAN HAVE"))
			{
				if(restrictions.get(i).getArithmetics().equals("MORE THAN"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{

					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() > restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() > restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() > restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() > restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("MORE OR THE SAME AS"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() >= restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() >= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() >= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() >= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("EXACTLY"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() == restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() == restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() == restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() == restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("LESS THAN"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() < restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() < restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() < restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() < restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}

				else if(restrictions.get(i).getArithmetics().equals("LESS OR THE SAME AS"))
				{
					if(restrictions.get(i).getType().equals("distance"))
					{
						//						if(lot.getLeaning() >= restrictions.get(i).getValue())
						//							return true;											CHECK FOR POSITION VALIDATION
						//						else return false;
					}
					else if(restrictions.get(i).getType().equals("leaning"))
					{
						if(lot.getLeaning() <= restrictions.get(i).getValue())
							return true;
						else return false;

					}
					else if(restrictions.get(i).getType().equals("width"))
					{
						if(lot.getWidth() <= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("height"))
					{
						if(lot.getHeight() <= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else if(restrictions.get(i).getType().equals("price"))
					{
						if(lot.getPrice() <= restrictions.get(i).getValue())
							return true;
						else return false;
					}
					else
					{
						System.out.println("Error: Not Valid!");
					}
				}
			}

		}
		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map clone() throws CloneNotSupportedException 
	{
		Map result = (Map) super.clone();

		result.assignedLanduses = (ArrayList<Landuse>) this.assignedLanduses.clone();
		result.assignedLots = (ArrayList<Lot>) this.assignedLots.clone();
		result.emptyLots = (ArrayList<Lot>) this.emptyLots.clone();
		result.unassignedLanduses = (ArrayList<Landuse>) this.unassignedLanduses.clone();
		return result;
	}



}
