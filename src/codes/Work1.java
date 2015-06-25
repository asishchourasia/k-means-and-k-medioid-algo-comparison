package codes;

import java.awt.BorderLayout;
import java.awt.Color;  
import java.awt.Font;

import javax.swing.*;  

import java.io.*;
  
public class Work1 extends JFrame {
	public Work1()
	{
		super("Result of the clustering of Kmedoid");
		JPanel p = new JPanel();
		JTextPane tp=new JTextPane();
		TextFomFile(tp);
		Font font = new Font("",Font.BOLD,20);
		tp.setFont(font);
		tp.setBackground(Color.BLACK);
		tp.setForeground(Color.WHITE);
		JScrollPane jp=new JScrollPane(tp);
		p.setLayout(new BorderLayout());
		p.add(jp,BorderLayout.CENTER); 
		setContentPane(p);
		
		
		setLocation(600, 000);
		setSize(600,700);
		
		setVisible(true);
	}
	public static void TextFomFile(JTextPane tp)
	{
		try
		{
			
			String path="E:\\codes\\k_means\\kmedoid_output.txt";
			File file=new File(path);
			FileReader fr=new FileReader(file);
			while(fr.read()!=-1)
			{
				tp.read(fr,null);
			}
			fr.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) {  
        new Work1();  
    }  
}  