package k_medoids;

import java.lang.Math;
import java.util.List;

public class DataPoint{
	public int x;
	public int y;
	public int clusterLabel = 0;
	public DataPoint nearestMedoid = null;
	
	public DataPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public DataPoint(DataPoint dp) {
		this.x = dp.x;
		this.y = dp.y;
	}
	
	/**This method calculates the euclidean distance between two data points
	 * @param dpIn
	 * @return Euclidean distance
	 */
	public double getDistance(DataPoint dpIn){
		//using Euclidean distance
		return Math.sqrt( ((this.x - dpIn.x)*(this.x - dpIn.x)) + ((this.y - dpIn.y)*(this.y - dpIn.y)) );
	}
	
	/**sets nearest medoid
	 * @param dp
	 */
	public void setNMedoid(DataPoint dp){
		this.nearestMedoid = new DataPoint(dp);
		this.clusterLabel = dp.clusterLabel;
	}
	
	/**
	 * returns nearest set medoid
	 * @return
	 */
	public DataPoint getNMedoid(){
		return this.nearestMedoid;
	}
	
	public Boolean equals(DataPoint inDP){
		if(inDP.x == this.x && inDP.y == this.y)
			return true;	
		return false;
	}
	
	public String toString(){
		 return new String("x = " + this.x + ", y = " + this.y + ", label = " + this.clusterLabel);
	}
}
