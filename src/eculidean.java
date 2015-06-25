import java.util.Scanner;


public class eculidean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("enter x1 and x2");
		Scanner in =new Scanner(System.in);
		double x1=in.nextDouble();
		double x2=in.nextDouble();
		System.out.println("enter y1 and y2");
		double y1=in.nextDouble();
		double y2=in.nextDouble();
		double res=Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
		System.out.println("The distance is" +res);

	}

}
