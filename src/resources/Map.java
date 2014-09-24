package resources;
/**
 * 
 * @author Jorge
 * @description Class that represent the matrix where the map will be defined, similar to a upper view plant, with some methods to proper manipulation
 */

import java.util.*;


public class Map {      

	private char[][] m;
	private ArrayList<Lot> emptyLots = new ArrayList<Lot>();
	private int dim;


	public Map(int dim)
	{
		this.dim = dim;
		m = new char[dim][dim];

	}

	public void fillMapRandomly()
	{
		Random rand = new Random();

		//Fill with empty slots
		for(int u = 0; u < dim; u++)
		{
			int n = rand.nextInt(12) +1;
			int l = rand.nextInt(12) +1;
			m[n][l] = 'E';
		}

		//Fill with water source
		int a = 11;
		int b = 1;
		m[a+1][b] = 'W';
		m[a+1][b+1] = 'W';
		m[a+1][b-1] = 'W';
		m[a-1][b] = 'W';
		m[a-1][b+1] = 'W';
		m[a-1][b-1] = 'W';
		m[a][b+1] = 'W';
		m[a+1][b+1] = 'W';
		m[a-1][b+1] = 'W';
		m[a][b-1] = 'W';
		m[a+1][b-1] = 'W';
		m[a-1][b-1] = 'W';
		m[a][b] = 'W';

	}

	//Draws the map as is

	public void drawMap(){

		for(int r = 0; r < dim; r++)
		{	 
			System.out.printf("|");

			for(int c = 0; c < dim; c++)
			{
				System.out.print(m[c][r]);
				System.out.printf("|");
			}

			System.out.printf("\n");

		}

	}

	//Empty lot verification

	public boolean isEmptyLot(int x, int y)
	{
		if (m[x][y] == 'E')
		{
			return true;
		}

		return false;
	}

	//Built buildings verification

	public boolean isSchool(int x, int y)
	{
		if (m[x][y] == 'S')
		{
			return true;
		}

		return false;
	}

	public boolean isRecreationalPark(int x, int y)
	{
		if (m[x][y] == 'P')
		{
			return true;
		}

		return false;
	}

	public boolean isHousingComplex(int x, int y)
	{
		if (m[x][y] == 'H')
		{
			return true;
		}

		return false;
	}

	public boolean isApartment(int x, int y)
	{
		if (m[x][y] == 'A')
		{
			return true;
		}

		return false;
	}

	public boolean isCemetry(int x, int y)
	{
		if (m[x][y] == 'C')
		{
			return true;
		}

		return false;
	}

	public boolean isDumpyard(int x, int y)
	{
		if (m[x][y] == 'D')
		{
			return true;
		}

		return false;
	}

	public boolean isChurch(int x, int y)
	{
		if (m[x][y] == 'C')
		{
			return true;
		}

		return false;
	}

	public boolean isShoppingMall(int x, int y)
	{
		if (m[x][y] == 'M')
		{
			return true;
		}

		return false;
	}
	public boolean isRestaurant(int x, int y)
	{
		if (m[x][y] == 'R')
		{
			return true;
		}

		return false;
	}

	public boolean isLibrary(int x, int y)
	{
		if (m[x][y] == 'L')
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
					if(m[i][u] == 'E')
					{
						m[i][u] = 'S';
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
}
