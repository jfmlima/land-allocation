package utils;

import logic.Map;

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

	double totalPrice, fn;

	public AStar(ArrayList<String> solutions, Map initialMap)
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

	public double updateHeuristicValue(List<Landuse> landuses, List<Lot> unassignedLots) {

		double hn = 0;
		ArrayList<Double> orderedPrices = new ArrayList<Double>();

		for(int i = 0; i < unassignedLots.size(); i++) {
			orderedPrices.add(unassignedLots.get(i).getPrice());
		}

		Collections.sort(orderedPrices);

		for(int a = 0; a < landuses.size(); a++) { 
			hn += orderedPrices.get(a);
		}


		return hn;
	}

	public void applyAlgorithm()
	{
		List<Landuse> landuseList = new ArrayList<Landuse>();
		List<Lot> openLotList = new ArrayList<Lot>(); //already crescent ordered
		List<Lot> closedLotList = new ArrayList<Lot>();

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

					if(currentLot.applyLanduseRestrictions(landu))
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

	public void apply()
	{
		System.out.println("começou");
		System.out.println(mapStates.size());
		System.out.println("States: ");
		for(int i = 0; i < mapStates.size(); i++)
		{
			mapStates.get(i).drawMap();
			System.out.println();
		}

		if(mapStates.size() == 0)
		{
			System.out.println("Não há solução");
			solution = null;
			return;
		}

		Map map = mapStates.get(0);
		mapStates.remove(map);

		if(map.getUnassignedLanduses().isEmpty()){
			solution = map;
			System.out.println("Allocation complete. Allocation (Terrain-Building):");
			System.out.println("Solution: " + solutions );
			System.out.println("Total price: " + map.getFN());
			return;
		}

		for(int i = 0; i < map.getEmptyLots().size(); i++)
		{
			Map map2 = map;
			
			for(int u = 0; u < map.getUnassignedLanduses().size(); u++)
			{	
				if(!visitedMaps.contains(map))
				{
					System.out.println("entrou");
					if(map.getEmptyLots().get(i).applyLanduseRestrictions(map.getUnassignedLanduses().get(u)))
					{


						Map map1 = map2;
						Lot lot2 = map1.getEmptyLots().get(i);
						Landuse land2 = map1.getUnassignedLanduses().get(u);
						solutions.add(land2.getType() + " was allocated in the lot with X = " + lot2.getX() + " and Y = " + lot2.getY());
						map1.insertLanduseToLot(land2, lot2);
						map1.setAccumCost(map1.getAccumCost()+ lot2.getPrice());


						if(i == 0)
						{}
						else
							i--;

						if(u==0)
						{}
						else
							u--;

						fn = map1.getAccumCost() + this.updateHeuristicValue(map1.getUnassignedLanduses(), map1.getEmptyLots());
						map1.setFN(fn);



						mapStates.add(map1);


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
			int valid = -2;

			for(int i = 0; i< o1.getUnassignedLanduses().size(); i++)
			{
				for(int u = 0; u < o2.getUnassignedLanduses().size(); u++)
				{
					if(o1.getFN() < o2.getFN() && o1.getUnassignedLanduses().get(i).getRestrictions().size() > o2.getUnassignedLanduses().get(u).getRestrictions().size())
					{
						valid = 1;
					}
					else if(o1.getFN() < o2.getFN() && o1.getUnassignedLanduses().get(i).getRestrictions().size() == o2.getUnassignedLanduses().get(u).getRestrictions().size())
					{
						valid = 0;
					}
					else
						valid = -1;
				}

			}
			//			if(o1.getFN() < o2.getFN() && o1.getUnassignedLanduses().)
			//			{
			//				return 1;
			//			}
			//			else if(o1.getFN() == o2.getFN())
			//				return 0;
			//			else
			//				return -1;
			return valid;
		}
	}

}





