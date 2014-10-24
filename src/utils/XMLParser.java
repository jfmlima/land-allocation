package utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.ParseConversionEvent;
import javax.xml.bind.helpers.ParseConversionEventImpl;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import logic.Landuse;
import logic.Lot;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser
{
	private ArrayList<Lot> Lots = new ArrayList<Lot>();

	private ArrayList<Landuse> Landuses = new ArrayList<Landuse>();

	private ArrayList<Restriction> rests = new ArrayList<Restriction>();

	public ArrayList<Lot> getLots() {
		return Lots;
	}

	public void setLots(ArrayList<Lot> lots) {
		Lots = lots;
	}

	public ArrayList<Landuse> getLanduses() {
		return Landuses;
	}

	public void setLanduses(ArrayList<Landuse> landuses) {
		Landuses = landuses;
	}

	public void getLotList(String fileName)
	{

		ArrayList<Lot> LotList = new ArrayList<Lot>();

		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file = new File(fileName);

			if(file.exists())
			{
				Document doc = db.parse(file);
				Element docElem = doc.getDocumentElement();

								
				NodeList lotList = docElem.getElementsByTagName("lot");

				// Print total student elements in document
				System.out
				.println("Total Lots: " + lotList.getLength());

				if (lotList != null && lotList.getLength() > 0) {
					for (int i = 0; i < lotList.getLength(); i++) {

						Node node = lotList.item(i);

						if (node.getNodeType() == Node.ELEMENT_NODE) {

							Element e = (Element) node;
							NodeList nodeList = e.getElementsByTagName("x");
							String xs = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							int x = Integer.parseInt(xs);

							nodeList = e.getElementsByTagName("y");
							String ys = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							int y = Integer.parseInt(ys);

							nodeList = e.getElementsByTagName("price");
							String prices = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							double price = Double.parseDouble(prices);

							nodeList = e.getElementsByTagName("leaning");
							String leanings = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							double leaning = Double.parseDouble(leanings);

							nodeList = e.getElementsByTagName("width");
							String widths = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							double width = Double.parseDouble(widths);

							nodeList = e.getElementsByTagName("height");
							String heights = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							double height = Double.parseDouble(heights);

							Lot lot = new Lot(x, y, price, leaning, width, height, "E");
							lot.print();
							LotList.add(lot);
						}
					}
				}
				else 
				{
					System.out.println("ERRO");
					System.exit(1);
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}

		this.Lots = LotList;
	}

	public void getLanduseList(String fileName)
	{
		ArrayList<Landuse> LandusesList = new ArrayList<Landuse>();


		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file = new File(fileName);

			if(file.exists())
			{
				Document doc = db.parse(file);
				Element docElem = doc.getDocumentElement();

				NodeList landuseList = docElem.getElementsByTagName("landuse");

				// Print total student elements in document
				System.out
				.println("Total Landuses: " + landuseList.getLength());

				if (landuseList != null && landuseList.getLength() > 0) {
					for (int i = 0; i < landuseList.getLength(); i++) {

						Element landuseNodes = (Element) landuseList.item(i);

						if (landuseNodes.getNodeType() == Node.ELEMENT_NODE) {

							Element e = (Element) landuseNodes;
							NodeList nodeList = e.getElementsByTagName("name");
							String name = nodeList.item(0).getChildNodes().item(0).getNodeValue();


							NodeList restrictions = e.getElementsByTagName("restriction");

							if (restrictions != null && restrictions.getLength() > 0) 
							{
								for (int n = 0; n < restrictions.getLength(); n++) 
								{
									Element resElem = (Element) restrictions.item(n);
									if (resElem.getNodeType() == Node.ELEMENT_NODE) {

										Element b = (Element) resElem;
										NodeList nodes = b.getElementsByTagName("req");
										String req = nodes.item(0).getChildNodes().item(0).getNodeValue();

										nodes = b.getElementsByTagName("ari");
										String ari = nodes.item(0).getChildNodes().item(0).getNodeValue();

										nodes = b.getElementsByTagName("type");
										String type = nodes.item(0).getChildNodes().item(0).getNodeValue();


										nodes = b.getElementsByTagName("val");
										String vals = nodes.item(0).getChildNodes().item(0).getNodeValue();
										double val = Double.parseDouble(vals);

										Restriction res = new Restriction(req, type, ari, val);
										
										rests.add(res);							

									}
								}
							}

							Landuse land = new Landuse(name);
							land.setRestrictions(rests);

							land.print();
							LandusesList.add(land);
							
							rests = new ArrayList<Restriction>();
						}
					}
				}
			}
			else 
			{
				System.out.println("ERRO");
				System.exit(1);
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}

		this.Landuses = LandusesList;
		
	}
}
