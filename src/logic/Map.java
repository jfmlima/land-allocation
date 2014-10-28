package logic;

/**
 * 
 * @author Jorge
 * @description Class that represent the matrix where the map will be defined, similar to a upper view plant, with some methods to proper manipulation
 */

import java.lang.reflect.Array;
import java.util.*;


public class Map {      

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

	public Map(int dim)
	{
		this.dim = dim;
		m = new String[dim][dim];
		this.FN = 0;
		this.accumCost = 0;

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

		firstLetter = String.valueOf(land.getType().charAt(0));

		m[lot.getX()][lot.getY()] = firstLetter;
		
		land.setX(lot.getX());
		land.setY(lot.getY());
		
		lot.land = land;
		
		assignedLots.add(lot);
		emptyLots.remove(lot);

		assignedLanduses.add(land);
		unassignedLanduses.remove(land);
		
	}



}
