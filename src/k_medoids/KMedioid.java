package k_medoids;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KMedioid 
{

	public static void main(String[] args) 
	{

		int i,j=0,col=0,row1=0,col1=0,cluster;

		try {
            int itr=0;
			ArrayList<Double> al = new ArrayList<Double>();
			ArrayList<Integer> index = new ArrayList<Integer>();
			ArrayList<Double> al4=new ArrayList<Double>();
			
			FileInputStream fileInputStream = new FileInputStream(new File("E:\\NewExcelFile.xls"));
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
		//	double result[][]=new double[row1][col+1];
			//PRINTING ELEMENTS OF 2-D ARRAY DATA
			System.out.println("Printing The Content Of The File...\n");
			for(i=0;i<row1;i++)
			{
				for(j=0;j<col1;j++)
				{
					System.out.print(b[i][j]+"  ");
				}
				System.out.print("\n");
			}

			System.out.println("Enter The Number Of Clusters  -  ");
			cluster=in.nextInt();


			// Randomly Select
			int z=0;

			Random rand = new Random();
			int indexArr[]= new int[cluster];
			while(z!=cluster)
			{
				int randIndex=rand.nextInt(row1);
				if(!index.contains(randIndex))
				{
					index.add(randIndex);
					indexArr[z]=randIndex;
					z++;
				}

			}

			//First Cenroid Initialization
			double centroid[][]=new double[cluster][col1];
			for(i=0;i<cluster;i++)
			{
				for(j=0;j<col1;j++)
				{
					centroid[i][j]=b[indexArr[i]][j];
				}
			}
		 System.out.println("\n\nPrinting Initial Centroids at iteration "+itr);
			for(i=0;i<cluster;i++)
			{
				for(j=0;j<col1;j++)
				{
					System.out.print(" "+centroid[i][j]);
				}
				System.out.println();
			}

			//distance calculation
			double dist_mat[][]=new double[row1][cluster];
			int k;
			double dist=0,res=0;
			for(i=0;i<cluster;i++)
			{
				for(j=0;j<row1;j++)
				{
					for(k=0;k<col1;k++)
					{
						dist=dist+Math.pow(centroid[i][k]-b[j][k],2);

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
			System.out.println("Displaying Initial Distance Matrix for iteration "+itr+"  is");
			for(i=0;i<row1;i++)
			{
				for(j=0;j<cluster;j++)
				{
					System.out.print(+dist_mat[i][j]+"\t\t");
				}
				System.out.println();
			}

			//Finding the minimum frm distance matrix..
			double dist_cost=0;
			int mat_pos[]=new int[row1];
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

				//System.out.println("The minimum value in row"+i+" is "+min);
				dist_cost+=min;
			}
			System.out.print("\n\nThe Initial Total cost calculated for iteration "+itr+" is \n"+dist_cost);
			// Printing minimum position array..
			for(i=0;i<row1;i++)
			{
				System.out.print(" "+mat_pos[i]);
			}
			// Saving the mat_pos and totalcost in temp arry mat_pos1 totalcost1
			int mat_pos1[]=new int[row1];
			for(i=0;i<row1;i++)
			{
				mat_pos1[i]=mat_pos[i];
			}
			double total_cost=dist_cost;






			int	randIndex=rand.nextInt(row1);
			//BIG LOOP
     		itr=0;
			int s=0;
			while(s<(row1-cluster))
			{
				
				//System.out.println("S = :"+s);
				randIndex=rand.nextInt(row1);
				if(!index.contains(randIndex))
				{
					index.add(randIndex);
					indexArr[cluster-1]=randIndex;


					for(i=0;i<cluster;i++)
					{
						for(j=0;j<col1;j++)
						{
							centroid[i][j]=b[indexArr[i]][j];
						}
					}
					itr++;
					System.out.println("\n\n---------------------------------------------------------------------------");
					System.out.println("\nPrinting Centroids In  Iteration... "+itr);
					for(i=0;i<cluster;i++)
					{
						for(j=0;j<col1;j++)
						{
							System.out.print(" "+centroid[i][j]);
						}
						System.out.println();
					}

					al.clear();
					dist=0;res=0;
					for(i=0;i<cluster;i++)
					{
						for(j=0;j<row1;j++)
						{
							for(k=0;k<col1;k++)
							{
								dist=dist+Math.pow(centroid[i][k]-b[j][k],2);

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
					System.out.println("\n\nDisplaying Distance Matrix In Iteration..."+itr+"\n");
					for(i=0;i<row1;i++)
					{
						for(j=0;j<cluster;j++)
						{
							System.out.print(+dist_mat[i][j]+"\t\t");
						}
						System.out.println();
					}

					//Finding the minimum frm distance matrix..
					dist_cost=0;

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

						//System.out.println("The minimum value in row"+i+" is "+min);
						dist_cost+=min;
					}
					System.out.print("\n\nThe Total Cost In Iteration "+itr+" Is "+dist_cost+"\n");
					
					// Printing minimum position array..
					System.out.println("\n\nPosition Of Objects In Cluster In Iteration "+itr+"\n");
					for(i=0;i<row1;i++)
					{
						System.out.print(" "+mat_pos[i]);
					}
					if(dist_cost<total_cost)
					{
						total_cost=dist_cost;
						for(i=0;i<row1;i++)
						{
							mat_pos1[i]=mat_pos[i];
						}
					}
					s++;

				}
				
			}
			System.out.println("\n\n---------------------------------------------------------------------------");
			System.out.println("\n..........FINAL RESULT.............");
			
			System.out.println("Mat Position....");
			for(i=0;i<row1;i++)
			{
				System.out.print(" "+mat_pos1[i]);
			}
				System.out.println("\n\nValue Of Minimum Cost Is "+total_cost);

				double result[][]=new double[row1][col1+1];
				for(i=0;i<row1;i++)
				{
					for(j=0;j<col1;j++)
					{
						result[i][j]=b[i][j];
					}
					result[i][col1]=mat_pos1[i];
				}
			
				
				//PRINTING THE FINAL RESULT
				System.out.println("\n\nTHE FINAL RESULT WHICH IS TO BE SHOWN IS");

				 for(i=0;i<cluster;i++)
				 {
					 for(j=0;j<row1;j++)
					 {
						 if(mat_pos1[j]==i)
						 {
							 for(k=0;k<col1;k++)
							 {
								 al4.add(b[j][k]);
							 }

						 }
					 }
					// System.out.println("AL4 SIZE : "+al4.size()); 

					 System.out.println("the value of cluster "+(i+1)+" is ");
					 System.out.print("{");
					
					 for(k=0;k<al4.size();k++)
					 {
						 System.out.print("(");
						 for(j=0;j<col1;j++)
						 {
                  
							 System.out.print(al4.get(0)+",");
							 al4.remove(0);
						 }
						 System.out.print(")");
						
					 }
					 System.out.println("}");
				 }

			 

				
				
				
			/*	System.out.println("\n\nTHE FINAL RESULT WHICH IS TO BE SHOWN IS");

				for(i=0;i<row1;i++)
				{
					for(j=0;j<col1;j++)
					{
						System.out.print((result[i][j])+"\t");
					}
					System.out.print((int)(result[i][j]));
					System.out.println();
					
				}
*/			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}






}			






