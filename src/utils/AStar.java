package utils;

import logic.Map;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import logic.Landuse;
import logic.Lot;

public class AStar {

	ArrayList<String> solutions = new ArrayList<String>();
	ArrayList<Landuse> landuses = new ArrayList<Landuse>();
	ArrayList<Lot> unassignedLots = new ArrayList<Lot>();
	ArrayList<Landuse> assignedLanduses = new ArrayList<Landuse>();
	ArrayList<Map> mapStates = new ArrayList<Map>();
	private ArrayList<Map> visitedMaps = new ArrayList<Map>();
	public Map solution;
	public int count;

	double totalPrice, fn;

	public AStar(Map initialMap)
	{
		this.solutions = solutions;
		this.mapStates.add(initialMap);
		this.unassignedLots = mapStates.get(0).getEmptyLots();
	}

	public ArrayList<Map> getMapStates() {
		return mapStates;
	}

	public void setMapStates(ArrayList<Map> mapStates) {
		this.mapStates = mapStates;
	}

	public List<String> getSolutions() {
		return solutions;
	}

	public void setSolutions(ArrayList<String> solutions) {
		this.solutions = solutions;
	}

	public List<Landuse> getLanduses() {
		return landuses;
	}

	public void setLanduses(ArrayList<Landuse> landuses) {
		this.landuses = landuses;
	}

	public List<Lot> getUnassignedLots() {
		return unassignedLots;
	}

	public void setUnassignedLots(ArrayList<Lot> unassignedLots) {
		this.unassignedLots = unassignedLots;
	}

	public double updateHeuristicValue(List<Landuse> landuses, ArrayList<Lot> unassignedLots) throws InterruptedException {

		double hn = 0;
		double al = 0;
	
		ArrayList<Lot> uLots = new ArrayList<Lot>();
		uLots = unassignedLots;
		
		lotComparator comp = new lotComparator();
		Collections.sort(uLots, comp);
		
		for(int a = 0; a < landuses.size(); a++) { 
			hn += uLots.get(a).getPrice();
		}
		
		for(int y = 0; y < landuses.size(); y++)
		al += landuses.get(y).getRestrictions().size();

		double al1 = Math.pow(al, 2);
		System.out.println("HN: " + hn);
		return hn;
	}

	public void applyAlgorithm() throws InterruptedException
	{
		ArrayList<Landuse> landuseList = new ArrayList<Landuse>();
		ArrayList<Lot> openLotList = new ArrayList<Lot>(); //already crescent ordered
		ArrayList<Lot> closedLotList = new ArrayList<Lot>();

		landuseList = landuses;		
		openLotList = unassignedLots;

		double accumulatedCost = 0;
		int count = 0;

		lotComparator lotCompare = new lotComparator();

		Collections.sort(openLotList, lotCompare);


		fn = calculateFn(accumulatedCost, this.updateHeuristicValue(landuseList, openLotList));

		closedLotList.add(openLotList.get(0));


		Lot currentLot;

		landuseComparator landuseCompare =  new landuseComparator();

		Collections.sort(landuseList, landuseCompare);


		while(!landuseList.isEmpty())
		{	
			currentLot = closedLotList.get(count);

			if(landuseList.size() > 0)
			{				
				for(int c = 0; c < landuseList.size(); c++){

					Landuse landu = landuseList.get(c);

					//					if(currentLot.applyLanduseRestrictions(landu))
					{
						solutions.add(landu.getType() + " was allocated in the lot with X = " + currentLot.getX() + " and Y = " + currentLot.getY());
						landu.setX(currentLot.getX());
						landu.setY(currentLot.getY());
						assignedLanduses.add(landu);

						totalPrice += currentLot.getPrice();
						landuseList.remove(landu);
						openLotList.remove(currentLot);						
					}
				}
			}

			count++;

			for(int q = 0; q < openLotList.size(); q++)
			{
				closedLotList.add(openLotList.get(q)); 


				fn = calculateFn(totalPrice, this.updateHeuristicValue(landuseList, openLotList));

				for(int v = 0; v < closedLotList.size(); v++)
				{
					if((openLotList.get(q).equals(closedLotList.get(v))) && (fn >= openLotList.get(q).getPrice()))
					{
						continue;
					}

					if(!(openLotList.contains(openLotList.get(q))) || (fn < openLotList.get(q).getPrice()))
					{
						currentLot = openLotList.get(q);

						accumulatedCost = fn;
						fn = calculateFn(accumulatedCost, this.updateHeuristicValue(landuseList, openLotList));

						if(!(openLotList.contains(openLotList.get(q))))
						{
							closedLotList.add(openLotList.get(q));

						}
					}
				}
			}
		}

		System.out.println("Allocation complete. Allocation (Terrain-Building):");
		System.out.println("Solution: " + solutions);
		System.out.println("Total price: " + totalPrice);
	}

	public void apply() throws CloneNotSupportedException, InterruptedException 
	{
		System.out.println("começou");
		System.out.println(mapStates.size());

		System.out.println("States: " + count);
		//		for(int i = 0; i < mapStates.size(); i++)
		//		{
		//			mapStates.get(i).drawMap();
		//			System.out.println();
		//		}

		if(mapStates.size() == 0)
		{
			System.out.println("Não há solução");
			solution = null;
			return;
		}
		System.out.println("income opt: ");
		System.out.println(mapStates.get(0).getAssignedLanduses().size());
		mapStates.get(0).drawMap();
		Thread.sleep(1000);
		Map map = Map.newMap(mapStates.get(0));
		mapStates.remove(map);
		System.out.println(map.getUnassignedLanduses().size());

		if(map.getUnassignedLanduses().isEmpty()){
			solution = map;

			for(int g = 0; g < solution.getAssignedLots().size(); g++)
			{
				solutions.add(solution.getAssignedLots().get(g).land.getType() + " was allocated in the lot with X = " + solution.getAssignedLots().get(g).land.getX() + " and Y = " + solution.getAssignedLots().get(g).land.getY());
			}
			System.out.println("Allocation complete. Allocation (Terrain-Building):");
			System.out.println("Solution: " + solutions );
			System.out.println("Total price: " + map.getFN());
			return;
		}
		Map map1;

		for(int i = 0; i < map.getEmptyLots().size(); i++)
		{
			System.out.println("lots for");
			System.out.println("Lot: " + i);
			map.getEmptyLots().get(i).print();
			ArrayList<Landuse> uLands = map.getUnassignedLanduses();
			landuseComparator comp = new landuseComparator();
			Collections.sort(uLands, comp);
			
			for(int u = 0; u < map.getUnassignedLanduses().size(); u++)
			{	
				System.out.println("landuses for");
				System.out.println("Landuse: " +uLands.get(u).getType());
				if(!visitedMaps.contains(map) )
				{
					System.out.println("map that came from apply recurse: ");
					map.drawMap();
					if(map.applyLanduseRestrictions(uLands.get(u), map.getEmptyLots().get(i)))
					{
						map1 = Map.newMap(map);
						System.out.println("Clone: ");
						System.out.println(map1.getEmptyLots().size());
						map1.drawMap();

						Lot lot2 = map1.getEmptyLots().get(i);
						Landuse land2 = map1.getUnassignedLanduses().get(u);


						map1.insertLanduseToLot(land2, lot2);
//						if(i == 0)
//						{}
//						else if(i == 1)
//						{}
//						else
//							i--;
//
//						if(u == 0)
//						{}
//						else if(u == 1)
//						{}
//						else u--;
						map1.setAccumCost(map1.getAccumCost()+ lot2.getPrice());


						fn = map1.getAccumCost() + this.updateHeuristicValue(map1.getUnassignedLanduses(), map1.getEmptyLots());
						map1.setFN(fn);


						System.out.println(map1.getUnassignedLanduses().size());
						System.out.println(map1.getAccumCost());
						count++;
						System.out.println("Entrada em states:");
						map1.drawMap();
						for(int t = 0; t < map1.getAssignedLanduses().size(); t++)
						{
							System.out.println(map1.getAssignedLanduses().get(t).getType());
						}
						Thread.sleep(100);
						mapStates.add(map1);
						//map1.removeLanduseFromLot(land2, lot2);



					}
					else
					{
						System.out.println(map.getUnassignedLanduses().get(u).getType());
						System.out.println("Restrições activas: ");
						map.getUnassignedLanduses().get(u).getRestrictions().get(0).print();
						System.out.println("Para Lote: ");
						map.getEmptyLots().get(i).print();
					}
				}

			}
		}
		//		for(int z = 0; z < mapStates.size(); z++)
		//		{
		//			System.out.println("map: " + z);
		//			mapStates.get(z).drawMap();
		//		}

		visitedMaps.add(map);

		mapComparator comp = new mapComparator();

		Collections.sort(mapStates, comp);
		System.out.println("Optimal state: ");
		mapStates.get(0).drawMap();
		System.out.println("apply" );
		apply();

	}






	public double calculateFn (double g, double hn)
	{
		return hn+g;

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

	public ArrayList<Landuse> getAssignments()
	{
		return assignedLanduses;

	}

	public class mapComparator implements Comparator<Map> {

		@Override
		public int compare(Map o1, Map o2) {
			// TODO Auto-generated method stub
			//			int valid = -2;
			//
			//			for(int i = 0; i< o1.getUnassignedLanduses().size(); i++)
			//			{
			//				for(int u = 0; u < o2.getUnassignedLanduses().size(); u++)
			//				{
			//					if(o1.getFN() < o2.getFN() && o1.getUnassignedLanduses().get(i).getRestrictions().size() < o2.getUnassignedLanduses().get(u).getRestrictions().size())
			//					{
			//						valid = 1;
			//					}
			//					else if(o1.getFN() == o2.getFN() && o1.getUnassignedLanduses().get(i).getRestrictions().size() == o2.getUnassignedLanduses().get(u).getRestrictions().size())
			//					{
			//						valid = 0;
			//					}
			//					else
			//						valid = -1;
			//				}
			//
			//			}
			if(o1.getFN() < o2.getFN())
			{
				return 1;
			}
			else if(o1.getFN() == o2.getFN())
				return 0;
			else
				return -1;
			//return valid;
		}
	}

}





