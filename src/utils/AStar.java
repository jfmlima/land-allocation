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

	public ArrayList<Map> getVisitedMaps() {
		return visitedMaps;
	}

	public void setVisitedMaps(ArrayList<Map> visitedMaps) {
		this.visitedMaps = visitedMaps;
	}

	public Map solution;
	public int count;

	double totalPrice, fn;

	public AStar(Map initialMap)
	{
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
		return hn;
	}

	public void apply() throws CloneNotSupportedException, InterruptedException 
	{
		if(mapStates.size() == 0)
		{
			System.out.println("Não há solução");
			solution = null;
			return;
		}

		Map map = Map.newMap(mapStates.get(0));
		mapStates.remove(map);
//		System.out.println("éEste");
//		map.drawMap();
//		System.out.println();

		if(map.getUnassignedLanduses().isEmpty()){
			solution = map;

			for(int g = 0; g < solution.getAssignedLanduses().size(); g++)
			{
				solutions.add(solution.getAssignedLanduses().get(g).getType() + " was allocated in the lot with X = " + solution.getAssignedLanduses().get(g).getX() + " and Y = " + solution.getAssignedLanduses().get(g).getY());
			}
			System.out.println("Allocation complete:");
			System.out.println("Solution: " + solutions );
			System.out.println("Total price: " + map.getFN());
			return;
		}
		Map map1;

		for(int i = 0; i < map.getEmptyLots().size(); i++)
		{


			ArrayList<Landuse> uLands = map.getUnassignedLanduses();
			landuseComparator comp = new landuseComparator();
			Collections.sort(uLands, comp);
			

			for(int u = 0; u < map.getUnassignedLanduses().size(); u++)
			{	

				if(!visitedMaps.contains(map) )
				{
					
					if(map.applyLanduseRestrictions(uLands.get(u), map.getEmptyLots().get(i)))
					{
						map1 = Map.newMap(map);


						Lot lot2 = map1.getEmptyLots().get(i);
						Landuse land2 = map1.getUnassignedLanduses().get(u);


						map1.insertLanduseToLot(land2, lot2);

						map1.setAccumCost(map1.getAccumCost()+ lot2.getPrice());


						fn = map1.getAccumCost() + this.updateHeuristicValue(map1.getUnassignedLanduses(), map1.getEmptyLots());
						map1.setFN(fn);



						count++;


						mapStates.add(map1);
//						map1.drawMap();
//						System.out.println(map1.getFN());
						//map1.removeLanduseFromLot(land2, lot2);

					}
					else
					{
					

					}
				}
			}
		}

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
			if(o1.getFN() <  o2.getFN())
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





