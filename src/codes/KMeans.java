package codes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KMeans extends JFrame
{
	int itr=0;
	

	
	/**
	 * @param args
	 */
	public static void main(String args) 
	{
		
		int i,j=0,col=0,row1=0,col1=0,cluster,k=0;
		double res=0,dist=0; 
		
		//area1 = new JTextArea(1000,1000);
 
		try {

			ArrayList<Double> al = new ArrayList<Double>();
			ArrayList<Double> al2 = new ArrayList<Double>();
			ArrayList<Double> al3 = new ArrayList<Double>();
			ArrayList<Double> al4=new ArrayList<Double>();
			FileInputStream fileInputStream = new FileInputStream(new File("E:\\NewExcelFile.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet worksheet = workbook.getSheet("FirstSheet");
			Scanner in =new Scanner(System.in);   
			Iterator<Row> rowIterator = worksheet.iterator();

			//FOR GETTING ROWS AND COLUMNS FROM A FILE
			while(rowIterator.hasNext()) 
			{
				row1++;
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext())
				{
					Cell cell = cellIterator.next();
					double a1Val = cell.getNumericCellValue();
					al.add(a1Val);
					col++;
				}
				col1=col;
				col=0;
			}
			al.clear();

			//for getting the ELEMENTS IN MATRIX B
			double b[][]=new double[row1][col1];
			Iterator<Row> rowIterator1 = worksheet.iterator();
			i=0;
			j=0;
			while(rowIterator1.hasNext()) 
			{
				Row row = rowIterator1.next();
				Iterator<Cell> cellIterator1 = row.cellIterator();
				j=0;
				while(cellIterator1.hasNext())
				{
					Cell cell = cellIterator1.next();
					double a1Val = cell.getNumericCellValue();
					b[i][j]=a1Val;
					j++;
				}
				i++;
			}

			//PRINTING ELEMENTS OF 2-D ARRAY B
			System.out.println("Printing The Content Of The File...\n");
			for(i=0;i<row1;i++)
			{
				for(j=0;j<col1;j++)
				{
					System.out.print(b[i][j]+"  ");
				}
				System.out.print("\n");
			}


			//taking input for number of clusters
			//System.out.println("Enter the number of clusters  -  ");
		//	cluster=in.nextInt(); 
            cluster=Integer.parseInt(args);
            
			double centroid[][]=new double[cluster][col1];
			//Initializing the centroids..using diff approach.............

			double ori[]=new double[col1];
			for(i=0;i<col1;i++)
			{
				ori[i]=0;
			}
			double val[][]=new double[row1][2];
			double ans,ans1=0;
			for(i=0;i<row1;i++)
			{
				for(j=0;j<col1;j++)
				{
					ans1+=Math.pow((b[i][j]-ori[j]), 2);
				}
				ans=Math.sqrt(ans1);
				val[i][0]=Math.round(ans*100.0)/100.0;
				val[i][1]=i;
				ans1=0;
			}
			//before sorting
/*			System.out.println("Printing distance from origin matrix...before sorting..\n");
			for(i=0;i<row1;i++)
			{
				for(j=0;j<2;j++)
				{
					System.out.print("\t"+val[i][j]);
				}
				System.out.print("\n");
			}
*/

			//sorting
			for(i=0;i<row1-1;i++)
			{
				for(j=0;j<row1-1-i;j++)
				{
					if (val[j][0] > val[j+1][0]) /* For decreasing order use < */
					{
						double swap= val[j][0];
						double swap1=val[j][1];
						val[j][0]= val[j+1][0];
						val[j][1]=val[j+1][1];
						val[j+1][0] = swap;
						val[j+1][1]=swap1;
					}
				}
			}
/*			System.out.println("Printing distance from origin matrix...sorted manner..\n");
			for(i=0;i<row1;i++)
			{
				for(j=0;j<2;j++)
				{
					System.out.print("\t"+val[i][j]);
				}
				System.out.print("\n");
			}
*/			
			for(i=0;i<row1;i++)
			{
				al2.add(val[i][1]);
			}

			
			int temp1=cluster;
			for(j=0;j<cluster;j++)
			{
				double k1= Math.ceil((double)al2.size()/temp1--);
		
				for (i = 0; i < k1; i++)
				{
					al3.add(al2.get(0));
					al2.remove(0);
				}
				double var1=al3.get(0);
				double var2=al3.get(al3.size()-1);
				al3.clear();	
				
				for(i=0;i<col1;i++)
				{
		
					double avg=(b[(int) var1][i]+b[(int) var2][i])/2;
					centroid[k][i]=avg;
					
				}
                 k++;
				
			}

		
			//Displaying the initial centroids
		/*System.out.println("Displaying Centroids......\n");
		for(i=0;i<cluster;i++)
		{
			for(j=0;j<col1;j++)
			{
				System.out.print(centroid[i][j]+" ");

			}
			System.out.println();
		}
*/
		//TRANSPOSE MATRIX B
		double transpose[][]=new double[col1][row1];
		for ( i = 0 ; i < row1 ; i++ )
		{
			for ( j = 0 ; j < col1 ; j++ )               
				transpose[j][i] = b[i][j];
		}
		int mat_pos[]=new int[row1];
		int temp[]=new int[row1];

 		KMeans ra = new KMeans();
		ra.findMatPosition(centroid, transpose, row1, col1, cluster,mat_pos);

		for(i=0;i<row1;i++)
			temp[i]=0;
		while(ra.compareArray(temp, mat_pos, row1)!=0)
		{
			for(i=0;i<row1;i++)
			{
				temp[i]=mat_pos[i];
			}

			//while(temp=maxpos)
				// Dangeres part

			int n=0;
			double sum=0,avg=0;
			ArrayList<Integer> al1=new ArrayList<Integer>();
			while(n<cluster)
			{
				for(i=0;i<row1;i++)
				{
					if(n==mat_pos[i])
					{
						al1.add(i);
					}
				}
				for(i=0;i<col1;i++)
				{
					for(j=0;j<al1.size();j++)
					{
						sum=sum+b[al1.get(j)][i];
					}
					avg=sum/al1.size();
					avg=Math.round(avg*100.0)/100.0;
					centroid[n][i]=avg;
					sum=avg=0;

				}
				al1.clear();
				n++;

			}
        	ra.findMatPosition(centroid, transpose, row1, col1, cluster,mat_pos);

		} 
/*		System.out.println("\nFinal centroids with  \n");
		for(i=0;i<cluster;i++)
		{
			for(j=0;j<col1;j++)
			{
				System.out.print(centroid[i][j]+" ");

			}
			System.out.println();
		}
*/

		// Printing minimum position array..

		/*for(i=0;i<row1;i++)
		{
			System.out.print(" "+mat_pos[i]);
		}
		*/
		double result[][]=new double[row1][col1+1];
		for(i=0;i<row1;i++)
		{
			for(j=0;j<col1;j++)
			{
				result[i][j]=b[i][j];
			}
			result[i][col1]=mat_pos[i]+1;
		}
	
		
		//PRINTING THE FINAL RESULT
		
		System.out.println("\n\nTHE FINAL RESULT WHICH IS TO BE SHOWN IS");
		File file = new File("kmeans_output.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        File file1 = new File("dbi_kmeans_output.txt");
        BufferedWriter output1 = new BufferedWriter(new FileWriter(file1));
        
        output.append("THE FINAL RESULT WHICH IS TO BE SHOWN IS\n\n");
		for(i=0;i<row1;i++)
		{
			for(j=0;j<=col1;j++)
			{
				System.out.print((result[i][j])+"\t");
				output.append((result[i][j])+"\t");
				output1.append((result[i][j])+"\t");
				
//				area1.append(Integer.toString((int) (result[i][j]+1)));
			}
			output.append("\n");
			output1.append("\n");
			
			System.out.println();
			//					mat_pos = (int[])(mat_pos);
			//						return mat_pos;
		}
		int mat_pos1[]=new int[row1];
		for(i=0;i<row1;i++)
		{
			mat_pos1[i]=mat_pos[i];
		}
		 for(i=0;i<cluster;i++)
		 {
			 int count=0;
			 for(j=0;j<row1;j++)
			 {
				 if(mat_pos1[j]==i)
				 {
					 count++;
					 for(k=0;k<col1;k++)
					 {
						 al4.add(b[j][k]);
					 }

				 }
			 }
			// System.out.print("Arraylist al4 is  "+al4);
			// System.out.println("AL4 SIZE : "+al4.size()); 
			 
			 output.append("the value of cluster "+(i+1)+" is \n");
			 output.append("{");
			 System.out.println("the value of cluster "+(i+1)+" is ");
			 System.out.print("{");
			 System.out.print("(");
			 for(k=0;k<al4.size();k++)
			 {
				 output.append("(");
			     
				 for(j=0;j<count;j++)
				 {
					 System.out.print("(");
					 output.append("(");
                   for(int m=0;m<col1;m++)
                   {
					 output.append(al4.get(0)+",");
					 System.out.print(al4.get(0)+",");
					 al4.remove(0);
                   }
				 
				 output.append(")");
				 System.out.print(")");
				 }
			 }
			 output.append("}\n");
			 System.out.println("}");
		 }
output.close();
output1.close();



//calculation of dbi index


double x1=0.0,x2=0.0,x3=0.0,x4=0.0,x5=0.0;
double di = 0,dj=0,dij=0,dbindex,maxdij=0;
ArrayList<Double> xx1=new ArrayList<Double>();
ArrayList<Double> DIJ=new ArrayList<Double>();
ArrayList<Double> DIJ1=new ArrayList<Double>();
for( i=0;i<cluster;i++)
   {
	   float i1=i;
	  // System.out.println(" I :"+i1);
	   for( j=0;j<cluster;j++)
	   {
		   if(i!=j)
		   {
		   // FOR CALCULATING DI
		   for(int p=0;p<row1;p++)
		   {
			  
			   if(result[p][col1] == i1)
			   {
				  // System.out.println("P :"+p);
				   for(int q=0;q<col1;q++)
				   {
					 // System.out.println(""+centroid[i][q]);
		              x1+=Math.sqrt(Math.pow(result[p][q]-centroid[i][q], 2));
				
				   }
				  //  System.out.println("Value "+(x1/col1));
				    double x11=(x1/col1);
				    xx1.add(x11);
				    x1=0.0;
			   }
		   }
			 //  System.out.println("Size "+xx1.size());
			   for(int o=0;o<xx1.size();o++)
			   {
				   x2+=xx1.get(o);
			   }
			    
			     di=(x2/xx1.size());
			  //  System.out.println("Value Of dI where I = "+i+"J = "+j+" Is "+di);
			
			   x2=0;
			   xx1.clear();
			 
		   //FOR CALCULATING DJ
			   
			//   System.out.println("XXXXXX1"+x1+"YYYYYYY1"+x2);
			   
			   for(int p=0;p<row1;p++)
			   {
				  
				   if(result[p][col1] == i1)
				   {
					  // System.out.println("P :"+p);
					   for(int q=0;q<col1;q++)
					   {
						 // System.out.println(""+centroid[i][q]);
			              x1+=Math.sqrt(Math.pow(result[p][q]-centroid[j][q], 2));
					
					   }
					  //  System.out.println("Value "+(x1/col1));
					    double x11=(x1/col1);
					    xx1.add(x11);
					    x1=0.0;
				   }
			   }
				 //  System.out.println("Size "+xx1.size());
				   for(int o=0;o<xx1.size();o++)
				   {
					   x2+=xx1.get(o);
				   }
				    
				     dj=(x2/xx1.size());
				//    System.out.println("Value Of dJ where I = "+i+"J = "+j+" Is "+dj);
				
				   x2=0;
				   xx1.clear();
				   
				   
				// FOR CALCULATING DIJ
				   for(int r=0;r<col1;r++)
				   {
				    x3+=Math.sqrt(Math.pow(centroid[i][r]-centroid[j][r], 2));
				   }
				   dij=(x3/col1);
				   x3=0;
			//	   System.out.println("Value Of dIJ where I = "+i+"J = "+j+" Is "+dij);
				   
				//FOR CALCULATING RIJ
				   
				   x4=((di+dj)/dij);
		//		   System.out.println("Value Of DIJ where I = "+i+"J = "+j+" Is "+x4);
				    DIJ.add(x4);
				   x4=0;
				   
			  
		   
		   }
		   
	   }
	   
	   for(int s=0;s<DIJ.size();s++)
	   {
		   if(DIJ.get(s)>maxdij)
		   {
			   maxdij=DIJ.get(s);
		   }
	   }
	   DIJ1.add(maxdij);
	//   System.out.println("DIJ ARRAYLIST :"+DIJ);
	   DIJ.clear();
	   maxdij=0;
   }
//System.out.println("DIJ1 ARRAYLIST :"+DIJ1);
for(i=0;i<DIJ1.size();i++)
{
	x5+=DIJ1.get(i);
}

dbindex=(x5/cluster);


System.out.println("THE VALUE OF DB-INDEX IS : "+dbindex);
File file2 = new File("dbindex_kmeans.txt");
BufferedWriter output2 = new BufferedWriter(new FileWriter(file2));
output2.write(Double.toString(dbindex));
output2.close();



	 
//Kmeans_result result1 = new Kmeans_result();
		//result1.show();
				
	}
	catch (FileNotFoundException e) {
		e.printStackTrace();
	} 
	catch (IOException e) {
		e.printStackTrace();
	}
	}

	
//END OF PUBLIC STATIC VOID
public int compareArray(int []temp,int []mat_pos,int row1)
{

	int i,flag=0;

	for(i=0;i<row1;i++)
	{
		if(temp[i]!=mat_pos[i])
		{
			flag=1;

		}
	}
	return flag;

}


public int[] findMatPosition(double [][]centroid,double [][]transpose,int row1,int col1,int cluster,int []mat_pos)
{
	
	
	//distance matrix generation

	ArrayList<Double> al = new ArrayList<Double>();

	double dist_mat[][]=new double[row1][cluster];
	int i,j,k;
	double dist=0,res=0;
	for(i=0;i<cluster;i++)
	{
		for(j=0;j<row1;j++)
		{
			for(k=0;k<col1;k++)
			{
				dist=dist+Math.pow(centroid[i][k]-transpose[k][j],2);

			}
			res=Math.round(Math.sqrt(dist)*100.0)/100.0;
			al.add(res);
			dist=0;
		}
	}

	k=0;
	for(j=0;j<cluster;j++)
	{
		for(i=0;i<row1;i++)
		{
			dist_mat[i][j]=al.get(k++);
		}
	}
/*	System.out.println("\n\nDisplaying Distance Matrix...\n");
	for(i=0;i<row1;i++)
	{
		for(j=0;j<cluster;j++)
		{
			System.out.print(+dist_mat[i][j]+"\t\t");
		}
		System.out.println();
	}
	*/
	
	//Finding the minimum frm distance matrix..

	for(i=0;i<row1;i++)
	{
		double min=dist_mat[i][0];
		for(j=0;j<cluster;j++)
		{
			if(dist_mat[i][j]<min)
			{
				min=dist_mat[i][j];
				mat_pos[i]=j;

			}
		}

	}
	
/*	System.out.println("\nDisplaying Centroids in interation  "+itr+++" is  = \n");
	for(i=0;i<cluster;i++)
	{
		for(j=0;j<col1;j++)
		{
			System.out.print(centroid[i][j]+" ");

		}
		System.out.println();
	}
*/

	
	// Printing minimum position array..
/*	for(i=0;i<row1;i++)
	{
		System.out.print(" "+mat_pos[i]);
	}*/
	mat_pos = (int[])(mat_pos);
	return mat_pos;

}
}			






