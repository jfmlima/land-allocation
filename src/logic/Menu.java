package logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import utils.AStar;
import utils.Restriction;
import utils.XMLParser;

public class Menu {

	private static Map map;
	private static ArrayList<Lot> lotList = new ArrayList<Lot>();
	private static ArrayList<Landuse> Landuses = new ArrayList<Landuse>();

	public static void main(String[] args){

		map = new Map(13);
		while(true)
		{
			System.out.println("Welcome");
			System.out.println("What would you like to do ?");
			System.out.println("0 - Run Algorithm saved Information.");
			System.out.println("1 - Show map actual state.");
			System.out.println("2 - Select Lot and Landuses");
			System.out.println("3 - Run Algorithm on Map");

			Scanner opt = new Scanner(System.in);

			int val = opt.nextInt();

			switch(val)
			{

			case 0:
			{	
				XMLParser parser = new XMLParser();
			    parser.getLanduseList("src/xml/Landuses.xml");
			    Landuses = parser.getLanduses();
			    
			    XMLParser pp = new XMLParser();
			    pp.getLotList("src/xml/LotList.xml");			    
			    lotList = pp.getLots();
			    			    
			    ArrayList<String> solutions = new ArrayList<String>();
			    AStar as = new AStar(solutions, Landuses, lotList);			    
			    
			    as.applyAlgorithm();
			    
			    ArrayList<Landuse> toDraw = as.getAssignments();
			    for(int v = 0; v < toDraw.size(); v++)
			    {
			    	map.insertLanduse(toDraw.get(v));
			    }
			    System.out.println();
			    map.drawMap();
			    
			    break;
			    
				
			}

			case 1:
			{
				map.drawMap();
				break;
			}

			case 2:
			{	
				System.out.println("Lot Selection:");
				System.out.println("How many empty lots do you want:");
				int numberOfLots = opt.nextInt();

				for(int i = 0; i < numberOfLots; i++)
				{
					System.out.println("Please insert the lot location:");
					System.out.println("X: ");
					int x = opt.nextInt();
					System.out.println("Y: ");
					int y = opt.nextInt();

					System.out.println("Lot Caracteristics: ");
					System.out.println("Please insert the lot price: ");
					double price = opt.nextDouble();

					System.out.println("Please insert the lot leaning: ");
					double leaning = opt.nextDouble();

					System.out.println("Please insert the lot width: ");
					double width = opt.nextDouble();

					System.out.println("Please insert the lot height: ");
					double height = opt.nextDouble();

					Lot lot = new Lot(x, y, price, leaning, width, height, "E");

					lotList.add(lot);

					System.out.println("Lot #" + (i + 1) + " inserted in position (" + lotList.get(i).getX() +"," + lotList.get(i).getY() + "), with the cost of " + lotList.get(i).getPrice() + ", leaning of " + lotList.get(i).getLeaning() + ", width of " + lotList.get(i).getWidth() + " and height of " + lotList.get(i).getHeight() + ".");
					map.insertEmptyLot(lot);
					map.drawMap();
				}

				System.out.println("Landuse Selection:");
				System.out.println("How many Landuses do you want:");
				int numberOfLanduses = opt.nextInt();
				for(int u = 0; u < numberOfLanduses; u++)
				{
					System.out.println("Please insert the Landuse name: ");
					opt.nextLine();
					String type = opt.nextLine();

					System.out.println("Landuse Restrictions: ");
					ArrayList<Restriction> restrictions = new ArrayList<Restriction>();

					System.out.println("How many restrictions do you want applied to this Landuse?");
					int v = opt.nextInt();
					int a = 0;
					for(int x = 0; x < v; x++)				{

						System.out.println("What is the restriction type?");					
						System.out.println("1 - Distance ");
						System.out.println("2 - Leaning ");
						System.out.println("3 - Width ");
						System.out.println("4 - Height ");
						System.out.println("4 - Lot Price ");
						int ty = opt.nextInt();
						String typ = null;

						switch(ty)
						{
						case 1:
						{
							typ = "distance";
							break;
						}

						case 2:
						{
							typ = "leaning";
							break;
						}

						case 3:
						{
							typ = "width";
							break;
						}			

						case 4:
						{
							typ = "height";
							break;
						}

						case 5:
						{
							typ ="price";
							break;
						}					
						}

						System.out.println("What is the requirement level?");
						System.out.println("1 - Must Have ");
						System.out.println("2 - Can Have ");
						System.out.println("3 - Must Not Have ");
						int req = opt.nextInt();
						String requi = null;
						switch(req)
						{
						case 1:
						{
							requi = "MUST HAVE";
							break;
						}

						case 2:
						{
							requi = "CAN HAVE";
							break;
						}

						case 3:
						{
							requi = "MUST NOT HAVE";
							break;
						}					
						}

						System.out.println("What is the arithmetics of the restriction?");					
						System.out.println("1 - More Than ");
						System.out.println("2 - More or the same As ");
						System.out.println("3 - Less Than ");
						System.out.println("4 - Less or the same As ");
						System.out.println("4 - Exactly ");
						int ar = opt.nextInt();
						String ari = null;

						switch(ar)
						{
						case 1:
						{
							ari = "MORE THAN";
							break;
						}

						case 2:
						{
							ari = "MORE OR THE SAME AS";
							break;
						}

						case 3:
						{
							ari = "LESS THAN";
							break;
						}

						case 4:
						{
							ari = "LESS OR THE SAME AS";
							break;
						}

						case 5:
						{
							ari = "EXACTLY";
							break;
						}
						}

						System.out.println("What is the value of the restriction?");	
						double value = opt.nextDouble();

						if(typ.equals("distance"))
						{
							System.out.println("You choose the type: " + typ + " - please provide the the other landuse name: ");
							opt.nextLine();
							String str = opt.nextLine();
							Landuse land = new Landuse(str);

							Restriction restr = new Restriction(requi, typ, ari, value, land);

							restrictions.add(restr);
						}
						else
						{
							Restriction rst = new Restriction(requi, typ, ari, value);
							restrictions.add(rst);
						}

						Landuse landuse = new Landuse(type, restrictions);

						Landuses.add(landuse);



						System.out.println("You have inserted the following restriction to the landuse " + Landuses.get(u).getType()+ " : ");

						if(Landuses.get(u).getRestrictions().get(a).getType().equals("distance"))
						{
							System.out.println("This Landuse " + Landuses.get(u).getRestrictions().get(a).getReq() + " " + Landuses.get(u).getRestrictions().get(a).getType() + " " + Landuses.get(u).getRestrictions().get(a).getArithmetics() + " " + Landuses.get(u).getRestrictions().get(a).getValue() + " from the landuse " + Landuses.get(u).getRestrictions().get(a).getTo().getType()); 
						}
						else
							System.out.println("This Landuse " + Landuses.get(u).getRestrictions().get(a).getReq() + " " + Landuses.get(u).getRestrictions().get(a).getType() + " " + Landuses.get(u).getRestrictions().get(a).getArithmetics() + " " + Landuses.get(u).getRestrictions().get(a).getValue());
						a++;
						System.out.println();
						System.out.println();
					}

					System.out.println("You have selected the following restrictions for the landuse " + Landuses.get(u).getType());
					for(int z = 0; z < Landuses.get(u).getRestrictions().size(); z++)
					{
						if(Landuses.get(u).getRestrictions().get(z).getType().equals("distance"))
						{
							System.out.println("This Landuse " + Landuses.get(u).getRestrictions().get(z).getReq() + " " + Landuses.get(u).getRestrictions().get(z).getType() + " " + Landuses.get(u).getRestrictions().get(z).getArithmetics() + " " + Landuses.get(u).getRestrictions().get(z).getValue() + " from the landuse " + Landuses.get(u).getRestrictions().get(z).getTo().getType()); 
						}
						else 
							System.out.println("This Landuse " + Landuses.get(u).getRestrictions().get(z).getReq() + " " + Landuses.get(u).getRestrictions().get(z).getType() + " " + Landuses.get(u).getRestrictions().get(z).getArithmetics() + " " + Landuses.get(u).getRestrictions().get(z).getValue());
					}


				}

			}

			case 3:
			{
				ArrayList<String> solutions = new ArrayList<String>();

				AStar alg = new AStar(solutions, Landuses, lotList);
				alg.applyAlgorithm();
			}


			}
		}
	}


}




