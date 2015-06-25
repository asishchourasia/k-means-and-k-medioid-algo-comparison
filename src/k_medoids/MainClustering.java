package k_medoids;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class MainClustering {

	/**
	 * K-Medoids (PAM)
	 * 
	 * [1] arbitrary choose K objects as initial medoids 
	 * [2] assign remaining objects to nearest medoids 
	 * [3] randomly select a non-medoid object, O(random) 
	 * [4] compute total cost of swapping 
	 * [5] swap O(i) with O(random) if quality is improved 
	 * [6] repeat [3] - [5] until no more swaps
	 * 
	 * @author scott julian
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// for testing
		 args = new String[2];
		 args[0] = "3";
		 args[1] = "input2.txt";
		
		if (args.length == 0) {
			System.out.println("USAGE: MainClustering [k] [input filename]");
			System.out.println("No parameters specified, exiting...");
			System.exit(0);
		}

		// Files
		FileWriter fstream = new FileWriter("output.txt");
		BufferedWriter fout = new BufferedWriter(fstream);
		Scanner scan = new Scanner(new File(args[1]));

		// Array of all data points in file
		final List<DataPoint> ALL_DP = new ArrayList<DataPoint>();
		int x, y;
		while (scan.hasNext()) {
			x = scan.nextInt();
			y = scan.nextInt();
			ALL_DP.add(new DataPoint(x, y));
		}
		System.out.println("size of arraylist are "+ALL_DP.size());

		// K -> Number of Clusters
		final int K_MEDOIDS = Integer.parseInt(args[0]);

		// The array of Clusters
		Cluster mainClusters[] = new Cluster[K_MEDOIDS];
		Cluster tempClusters[] = new Cluster[K_MEDOIDS];
		Cluster testClusters[] = new Cluster[K_MEDOIDS];
		Cluster swapCluster = new Cluster();

		// Random Object
		Random rand = new Random();
		int randNum = rand.nextInt(ALL_DP.size());
		System.out.println("initial random numvber "+randNum);
		// Previous selected medoid indexes
		List<Integer> prevMedoids = new ArrayList<Integer>();
		//System.out.println(" contents of previous selected medoids"+prevMedoids);
		// Cost
		double mainClustersTotalCost = 0.0;
		double totalCost = 0.0;

		/*
		 * BEGIN ALGORITHM K-MEDOIDS (PAM)
		 */

		// [1] arbitrary choose K objects as initial medoids
		for (int i = 0; i < K_MEDOIDS; i++) {
			while (prevMedoids.contains(randNum))
			{
				
				randNum = rand.nextInt(ALL_DP.size());
				}
				System.out.println("new random number is "+randNum);
			tempClusters[i] = new Cluster(ALL_DP.get(randNum));
			tempClusters[i].clusterLabel = i + 1;
			System.out.println(" the contents of tempcluster at pos "+i+" is"+tempClusters[i].clusterLabel);
			prevMedoids.add(randNum);
			System.out.println(" contents of previous selected medoids and random number is"+prevMedoids+" "+randNum);
		}
	/*	for(int j=0;j<K_MEDOIDS;j++)
		{
			System.out.println(" the cpntents of tempcluster at position"+j+"  is "+tempClusters[j]);
		}*/
		// [2] assign remaining objects to nearest medoids
		for (DataPoint dp : ALL_DP) {
			// set nearest medoid to the first medoid
			int prevMedoid = 0;
			tempClusters[prevMedoid].add(dp);
			dp.setNMedoid(tempClusters[prevMedoid].medoid);

			for (int a = 1; a < K_MEDOIDS; a++) {
				// swap medoids if new medoid is closer
				if (dp.getDistance(tempClusters[a].medoid) < dp.getDistance(dp
						.getNMedoid())) {
					tempClusters[prevMedoid].remove(dp);
					dp.setNMedoid(tempClusters[a].medoid);
					tempClusters[a].add(dp);
					prevMedoid = a;
				}
			}
		}
		// set total distances for each medoid
		for (Cluster c : tempClusters) {
			c.setTotalMedoidDist();
			totalCost += c.totalMedoidDist;
		}

		// main = temp
		for (int p = 0; p < K_MEDOIDS; p++)
			mainClusters[p] = new Cluster(tempClusters[p]);
		mainClustersTotalCost = totalCost;

		/* BIG LOOP */
		do {

			// [3] randomly select a non-medoid object, O (random)
			while (prevMedoids.contains(randNum))
				randNum = rand.nextInt(ALL_DP.size());
			swapCluster = new Cluster(ALL_DP.get(randNum));
			prevMedoids.add(randNum);

			// [4] compute total cost of swapping
			// Use minkowski distance
			// cost is total of total distance from each medoid
			double swapCosts[] = new double[K_MEDOIDS];

			int swapIndex = 0;
			for (int theCount = 0; theCount < K_MEDOIDS; theCount++) {
				// calculate swap cost for each medoid

				// put medoids into a testing clusters
				for (int v = 0; v < K_MEDOIDS; v++) {
					if (swapIndex == v)
						testClusters[v] = new Cluster(swapCluster);
					else
						testClusters[v] = new Cluster(tempClusters[v]);

					testClusters[v].clusterLabel = v + 1;
				}

				// ASSIGN DP TO TEST CLUSTERS
				for (DataPoint dp : ALL_DP) {
					// set nearest medoid to the first medoid
					int prevMedoid = 0;
					testClusters[prevMedoid].add(dp);
					dp.setNMedoid(testClusters[prevMedoid].medoid);

					for (int a = 1; a < K_MEDOIDS; a++) {
						// swap medoids if new medoid is closer
						if (dp.getDistance(testClusters[a].medoid) < dp
								.getDistance(dp.getNMedoid())) {
							testClusters[prevMedoid].remove(dp);
							dp.setNMedoid(testClusters[a].medoid);
							testClusters[a].add(dp);
							prevMedoid = a;
						}
					}
				}
				for (Cluster c : testClusters) {
					c.setTotalMedoidDist();
					swapCosts[theCount] += c.totalMedoidDist;
				}
				swapIndex++;
			}

			// [5] swap O(i) with O(random) if quality is improved
			int cheapIndex = 0;
			for (int u = 0; u < K_MEDOIDS; u++)
				if (swapCosts[u] < swapCosts[cheapIndex])
					cheapIndex = u;
			if (swapCosts[cheapIndex] < totalCost) {
				mainClusters[cheapIndex] = new Cluster(swapCluster);
				mainClustersTotalCost = swapCosts[cheapIndex];
				totalCost = swapCosts[cheapIndex];
			}

		} while (prevMedoids.size() < ALL_DP.size());

		int label = 1;
		for (Cluster c : mainClusters) {
			c.clusterLabel = label;
			label++;
		}
		for (DataPoint dp : ALL_DP) {
			// set nearest medoid to the first medoid
			int prevMedoid = 0;
			mainClusters[prevMedoid].add(dp);
			dp.setNMedoid(mainClusters[prevMedoid].medoid);

			for (int a = 1; a < K_MEDOIDS; a++) {
				// swap medoids if new medoid is closer
				if (dp.getDistance(mainClusters[a].medoid) < dp.getDistance(dp
						.getNMedoid())) {
					mainClusters[prevMedoid].remove(dp);
					dp.setNMedoid(mainClusters[a].medoid);
					mainClusters[a].add(dp);
					prevMedoid = a;
				}
			}
		}
		for(Cluster c : mainClusters) fout.write(c.toString());	
		fout.close(); fstream.close();
	}

}
