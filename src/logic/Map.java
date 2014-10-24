package logic;

/**
 * 
 * @author Jorge
 * @description Class that represent the matrix where the map will be defined, similar to a upper view plant, with some methods to proper manipulation
 */

import java.util.*;


public class Map {      

	private String[][] m;
	private ArrayList<Lot> emptyLots = new ArrayList<Lot>();
	private int dim;


	public Map(int dim)
	{
		this.dim = dim;
		m = new String[dim][dim];

	}

	public void fillMapRandomly()
	{
		Random rand = new Random();

		//Fill with empty slots
		for(int u = 0; u < dim; u++)
		{
			int n = rand.nextInt(12) +1;
			int l = rand.nextInt(12) +1;
			m[n][l] = "E";
		}

		//Fill with water source
		int a = 11;
		int b = 1;
		m[a+1][b] = "W";
		m[a+1][b+1] = "W";
		m[a+1][b-1] = "W";
		m[a-1][b] = "W";
		m[a-1][b+1] = "W";
		m[a-1][b-1] = "W";
		m[a][b+1] = "W";
		m[a+1][b+1] = "W";
		m[a-1][b+1] = "W";
		m[a][b-1] = "W";
		m[a+1][b-1] = "W";
		m[a-1][b-1] = "W";
		m[a][b] = "W";

	}

	//Draws the map as is

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

	//Empty lot verification

	public boolean isEmptyLot(int x, int y)
	{
		if (m[x][y] == "E")
		{
			return true;
		}

		return false;
	}

	public void insertBuildingIntoEmptyLot()
	{
		schoolInsert:
			for(int i = 0; i < dim; i++)
			{
				for(int u = 0; u < dim; u++)
				{
					if(m[i][u] == "E")
					{
						m[i][u] = "S";
						break schoolInsert;
					} 

				}			
			}
	}

	public void insertEmptyLot(Lot eLot)
	{

		m[eLot.getX()][eLot.getY()] = eLot.getSymbol();

		eLot.setActive(true);

		emptyLots.add(eLot);
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

	public void insertLanduse(Landuse land)
	{
		String firstLetter = "";

		firstLetter = String.valueOf(land.getType().charAt(0));
		
			m[land.getX()][land.getY()] = firstLetter;
		
	}
}
