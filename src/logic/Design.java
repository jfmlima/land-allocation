package logic;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Design extends JFrame implements ActionListener {
	
	JPanel mainPanel = new JPanel();
	JLabel label = new JLabel("Welcome to the Intelligent Iot Allocation System");
	JButton button1, button2, button3, button4;
	
	public Design(){
		super("Main Menu");
		
		setSize(400,300);
		setResizable(true);
		
		//mainPanel.add(button);
		mainPanel.add(label);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBorder(new EmptyBorder(new Insets(10, 80, 40, 60)));

        button1 = new JButton("Choose empty lots");       
        mainPanel.add(button1);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        button1.addActionListener(this);
        
        mainPanel.add(new JButton("Landuses selection"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(new JButton("Landuses placement"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(new JButton("Exit"));
        
		add(mainPanel);
		
		pack();

        setTitle("ILAS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {        
        
		// TODO Auto-generated method stub
		Map map2 = new Map(13);
		//fillMapWithEmptyLot(map2);
		map2.drawMap();
		
	//	Lot lot1 = new Lot(1,5, 5.4, "E");
		//map2.insertEmptyLot(lot1);			
		//map2.drawMap();
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
		
		//Lot emptyLot = new Lot(x, y, price, "E");
		
		//map1.insertEmptyLot(emptyLot);

		System.out.println("Empty slot being placed in x: " + x + " and y: " + y);
				
		return map1;

	}

}
