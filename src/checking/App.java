package checking;
import java.io.DataInputStream;
import java.io.FileInputStream;
 
public class App{
 
	public static void main (String args[]) {
 
	try{
 
	         DataInputStream dis = 
		    new DataInputStream (
		    	 new FileInputStream ("E:\\codes\\k_means\\dbindex_kmeans.txt"));
 
		 byte[] datainBytes = new byte[dis.available()];
		 dis.readFully(datainBytes);
		 dis.close();
 
		 String content = new String(datainBytes, 0, datainBytes.length);
 
		 System.out.println(content);
 
	}catch(Exception ex){
		ex.printStackTrace();
	}
 
  }
}