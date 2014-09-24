package main;
import resources.*;
import java.util.*;

import resources.Map;


public class ILA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map mapUsed = new Map(13);

		//Menu

		//TODO: make user interface to received lot location and price input;
		showMenu(mapUsed);		

		System.out.printf("Chegou aqui!");
	}

	public static void clearScreen()
	{
		System.out.print("\u001b[2J");
		System.out.flush();
	}

	public static int showMenu(Map map1) // Display menu graphics
	{
		int value;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("===============================================================");
		System.out.println("|                   Intelligent Lot Allocation                |");
		System.out.println("===============================================================");
		System.out.println("| Options:                                                    |");
		System.out.println("|        1. Fill map with empty slots.                        |");
		System.out.println("|        2. Alocate buildings to empty slot.                  |");
		System.out.println("|        3. Exit                                              |");
		System.out.println("===============================================================");
		value = sc.nextInt();

		switch (value) {
		case 1:	
			Map map2;
			map2 = fillMapWithEmptyLot(map1);
			
			Lot lot1 = new Lot(1,5, 5.4, 'E');
			map2.insertEmptyLot(lot1);			
			map2.drawMap();
			
			showMenu(map2);
			break;
		case 2:
			map1.insertBuildingIntoEmptyLot();
			map1.drawMap();
			showMenu(map1);
			break;
		case 3:			
			return 0;

		default:
			System.out.println("Invalid selection");
			break; 		  
		}

		return 0;
	}

	public static Map fillMapWithEmptyLot(Map map1)
	{
		String c;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please insert the location of the empty slot: (example: 1,6 from 0-9)");
		c = sc.next();
		String[] coords = c.split(",");


		int x = Integer.parseInt(coords[0]);
		int y = Integer.parseInt(coords[1]);
		
		System.out.println("Please insert the price of the empty slot: (in millions)");
		c = sc.next();
		
		double price = Double.parseDouble(c);
		
		Lot emptyLot = new Lot(x, y, price, 'E');
		
		map1.insertEmptyLot(emptyLot);

		System.out.println("Empty slot being placed in x: " + x + " and y: " + y);
				
		return map1;

	}

}
