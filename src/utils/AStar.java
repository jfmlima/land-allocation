package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import logic.Landuse;
import logic.Lot;

public class AStar {

	List<String> solutions = new ArrayList<String>();
	List<Landuse> landuses = new ArrayList<Landuse>();
	List<Lot> unassignedLots = new ArrayList<Lot>();
	ArrayList<Landuse> assignedLanduses = new ArrayList<Landuse>();

	double totalPrice, fn;

	public AStar(ArrayList<String> solutions, ArrayList<Landuse> landuses, ArrayList<Lot> unassignedLots)
	{
		this.solutions = solutions;
		this.landuses = landuses;
		this.unassignedLots = unassignedLots;
	}

	public List<String> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<String> solutions) {
		this.solutions = solutions;
	}

	public List<Landuse> getLanduses() {
		return landuses;
	}

	public void setLanduses(List<Landuse> landuses) {
		this.landuses = landuses;
	}

	public List<Lot> getUnassignedLots() {
		return unassignedLots;
	}

	public void setUnassignedLots(List<Lot> unassignedLots) {
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

		//Order price list by crescent order       //TODO WRITE COMPARATOR 

		/*for(int w = 0; w < this.getUnassignedLots().size(); w++)
		{
			orderedPrices.add(this.getUnassignedLots().get(w).getPrice());
		}

		Collections.sort(orderedPrices);

		//Rearranging Lot List ordered by price

		for(int j = 0; j < orderedPrices.size(); j++)
		{
			for(int u = 0; u < this.getUnassignedLots().size(); u++)
			{
				if(orderedPrices.get(j) == this.getUnassignedLots().get(u).getPrice())
				{
					openLotList.add(this.getUnassignedLots().get(u));
				}
			}
		}*/

		lotComparator lotCompare = new lotComparator();

		Collections.sort(openLotList, lotCompare);


		fn = calculateFn(accumulatedCost, this.updateHeuristicValue(landuseList, openLotList));

		closedLotList.add(openLotList.get(0));
		openLotList.remove(openLotList.get(0));


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
						//closedLotList.remove(currentLot);

						break;

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
					if(!((openLotList.get(q).equals(closedLotList.get(v)))) && (fn >= openLotList.get(q).getPrice()))
					{
						continue;
					}

					if(!(openLotList.contains(openLotList.get(q))) || (fn < openLotList.get(q).getPrice()))
					{
						currentLot = openLotList.get(q);


						fn = calculateFn(accumulatedCost, this.updateHeuristicValue(landuseList, openLotList));

						if(!(openLotList.contains(openLotList.get(q))))
						{
							closedLotList.add(openLotList.get(q));
						}
					}
					
					
				}
			}
		}
		
		for(int z = 0; z <assignedLanduses.size(); z++)
		{
			System.out.println("isto");
			System.out.println(assignedLanduses.get(z).getX());
			System.out.println(assignedLanduses.get(z).getY());
			System.out.println("depois");
		}


		System.out.println("Allocation complete. Allocation (Terrain-Building):");
		System.out.println("Solution: " + solutions);
		System.out.println("Total price: " + totalPrice);
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


}





