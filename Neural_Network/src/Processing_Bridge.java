import processing.core.PApplet;
public class Processing_Bridge {


	public static void main(String[] args) {

		String[] processingArgs = { "Main" };
		Main mySketch = new Main();
		PApplet.runSketch(processingArgs, mySketch);



	}


}
