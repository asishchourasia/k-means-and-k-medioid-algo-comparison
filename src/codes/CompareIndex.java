package codes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class CompareIndex extends JFrame{

	public CompareIndex()
	{
		super("Result of the clustering of Kmeans");
		JPanel p = new JPanel();
		JTextPane tp=new JTextPane();
		ResultFromFile(tp);
		Font font = new Font("",Font.BOLD,20);
		tp.setFont(font);
		tp.setBackground(Color.BLACK);
		tp.setForeground(Color.WHITE);
		JScrollPane jp=new JScrollPane(tp);
		p.setLayout(new BorderLayout());
		p.add(jp,BorderLayout.CENTER); 
		setContentPane(p);
		setLocationRelativeTo(null);
		setSize(500,200);
		setVisible(true);
	}
	public static void ResultFromFile(JTextPane tp)
	{
		try
		{
			//System.out.print("Enetring");
			String path = null,path1 = null;
		
			
			Scanner scanner1 = new Scanner(new File("dbindex_kmeans.txt"));
			while(scanner1.hasNextLine())
				 path=scanner1.nextLine();
				 Scanner scanner2 = new Scanner(new File("dbindex_kmedoid.txt"));
					while(scanner2.hasNextLine())
						 path1=scanner2.nextLine();
						 
						if(path.compareTo(path1)>0)
						{
							System.out.print("Kmedoid will give better clustering result ");
							tp.setText("Kmedoid will give better clustering result as \ndbi index of kmedoid is "+path1+"\ndbi index of kmeans is "+path);
							
						}
						else if(path.compareTo(path1)<0)
						{
							System.out.print("KMeans will give better clustering result ");
							tp.setText("KMeans will give better clustering result as \ndbi index of kmeans is "+path+" \ndbi index of kmedoid is "+path1);
						}
						else{
							System.out.print("Both are equally proficient as \ndbi index of kmeans is "+path+" \ndbi index of kmedoid is "+path1);
							tp.setText("Both are equally proficient");
						}
						}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main() {
		// TODO Auto-generated method stub
		new CompareIndex();

	}

}
