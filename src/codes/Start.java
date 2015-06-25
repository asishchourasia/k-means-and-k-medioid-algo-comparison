package codes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Start extends JFrame implements ActionListener {

	public JMenuBar menu_bar;
	public JMenu menu_contact,menu_abtus;
	//public JComboBox combo_method;
	public JLabel lbl_choice,lbl_cluster;
	public JButton btn_kmeans,btn_kmedoid,btn_kboth,btn_exit;
	public JTextField txtcluster;
	public JPanel panel1;
	
	
	
	public Start()
	{
		
		lbl_choice=new JLabel("ENTER YOUR CHOICE :");
		lbl_cluster=new JLabel("Enter how many cluster :");
		String method[]=new String[10];

		//combo_method=new JComboBox(method);
		btn_kmeans=new JButton("Perform Kmeans");
		btn_kmedoid=new JButton("Perform KMedoids");
		btn_kboth=new JButton("Perform Both and compare");
		btn_exit=new JButton("Exit from program");

        txtcluster=new JTextField(10);
		
		
		panel1=new JPanel();
		panel1.add(lbl_cluster);
		panel1.add(txtcluster);
		setLayout(new GridLayout(6,4));
		
		setSize(450,200);
		setLocation(0,0);
	//	setLocation();
		setTitle("CLUSTERING");
		setVisible(true);
		setResizable(false);
		
		add(lbl_choice);
		add(panel1);
		add(btn_kmeans);
		add(btn_kmedoid);
		add(btn_kboth);
		add(btn_exit);
		
		btn_kmeans.addActionListener(this);
		btn_kmedoid.addActionListener(this);
		btn_kboth.addActionListener(this);
		btn_exit.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource()==btn_kmeans)
		{
			
				try {
					if(checkValidation()==true)
					{
						String cluster=txtcluster.getText();
						KMeans.main(cluster);
						//System.out.print("coming in K means result");
					    Work.main(null);
					    
					}
					else
					{
						new Start();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} 
			
		  //System.out.print("coming out of K means result");
			
			
		
		if(event.getSource()==btn_kmedoid)
		{
			try {
				if(checkValidation()==true)
				{
			String cluster=txtcluster.getText();
			KMedioid.main(cluster);
			Work1.main(null);
				}
				else
				{
					new Start();
				}
			}
				catch(IOException e)
				{}
		}
		if(event.getSource()==btn_kboth)
		{
			try {
				if(checkValidation()==true)
				{
			
			String cluster=txtcluster.getText();
			KMedioid.main(cluster);
	//		Work.main(null);
			KMeans.main(cluster);
			CompareIndex.main();
		//	Work1.main(null);
				}
				else
				{
					new Start();
				}
			}
			catch(IOException e)
			{}
		}
		if(event.getSource()==btn_exit)
		{
			System.exit(0);
			
		}
		     }
	public boolean checkValidation() throws IOException
	{

		String clus=txtcluster.getText();
		if(clus.equals(""))
		{
			JOptionPane.showMessageDialog(this,"Enter cluster");
			
			return false;
				
		}
		
		//System.out.print("value of txt is "+txt);
		
		ArrayList<Double> al = new ArrayList<Double>();
		
		FileInputStream fileInputStream = new FileInputStream(new File(D:\\8th Sem\\NewExcelFile.xls));
	    XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet worksheet = workbook.getSheet("FirstSheet");
		  
		Iterator<Row> rowIterator = worksheet.iterator();
       int row1=0,col1,col=0;
		int txt=Integer.parseInt(txtcluster.getText());
		//FOR GETTING ROWS AND COLUMNS FROM A FILE
		while(rowIterator.hasNext()) 
		{
			row1=row1+1;
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext())
			{
				Cell cell = cellIterator.next();
				double a1Val = cell.getNumericCellValue();
				al.add(a1Val);
				col=col+1;
			}
			col1=col;
			col=0;
		}
		al.clear();
		if(txt>row1)
			{
			JOptionPane.showMessageDialog(this,"Enter cluster less than "+row1);
			
		return false;
			}
		else
			return true;
	}
}
