package k_medoids;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	public int clusterLabel = 0;
	public DataPoint medoid = null;
	public List<DataPoint> dataPoints = new ArrayList<DataPoint>();
	public double totalMedoidDist;
	
	public Cluster(){
	}
	
	public Cluster(Cluster inC){
		this.dataPoints = new ArrayList<DataPoint>();
		this.medoid = new DataPoint(inC.medoid);
		this.clusterLabel = inC.clusterLabel;
	}
	
	public Cluster(DataPoint dataPoint) {
		this.medoid = new DataPoint(dataPoint);
	}

	public void add(DataPoint dp){
		this.dataPoints.add(dp);
	}
	
	public void remove(DataPoint dp){
		this.dataPoints.remove(dp);
	}

	public void setMedoid(DataPoint medoid) {
		this.medoid = new DataPoint(medoid);
	}
	
	public void setTotalMedoidDist(){
		this.totalMedoidDist = 0.0;
		for(DataPoint d : this.dataPoints)
			this.totalMedoidDist += d.getDistance(this.medoid);
	}
	
	public String toString(){
		String out = "";
		for(DataPoint p : dataPoints){
			out += p.x + "\t" + p.y + "\t" + clusterLabel + "\n";
		}
		return out;
	}
	
}
