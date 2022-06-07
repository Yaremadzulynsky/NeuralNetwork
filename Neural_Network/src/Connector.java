import java.util.ArrayList;

import processing.core.PApplet;

public class Connector {



	//Attributes
	Neuron origin;
	Neuron next;
	double relativeWeight;
	double trueWeight;
	double falseWeight;
	double activationWeight;
	int RGB[] = {256, 0, 100};
	int strokeWeight = 3;

	//Processing Formalities
	PApplet sketch;


	//Constructor
	public Connector(PApplet sketch_, double activationWeight_, Neuron origin_, Neuron next_)	{

		origin = origin_;
		next = next_;
		activationWeight = activationWeight_;
		sketch = sketch_;

	}

	void randActivationWeight()	{

		activationWeight = (double)Math.random();

	}

	public static void randActivationWeight(ArrayList<ArrayList<Neuron>> neuronMatrix)	{

		for(ArrayList<Neuron> e: neuronMatrix)	{

			for(Neuron e2: e)	{
				
				for(Connector e3: e2.nextConnections)	{
					
					e3.randActivationWeight();
					
				}
				

			}

		}

	}
	
	
	void setRGB(int a, int b, int c)	{

		RGB[0] = a;
		RGB[1] = b;
		RGB[2] = c;

	}

	void setRGB(int a, int b, int c, int w)	{

		RGB[0] = a;
		RGB[1] = b;
		RGB[2] = c;
		strokeWeight = w;

	}
	
	int alpha()	{
		
		return (int)((double)activationWeight*(double)256);
		
	}
	
	public static void visualize(ArrayList<Connector> connections)	{

		for(Connector e: connections) {

			e.visualize();

		}

	}

	Connector visualize()	{
		
//		System.out.println("activationWeight: " + activationWeight);
//		System.out.println("alpha(): " + alpha());
		
		sketch.stroke(RGB[0], RGB[1], RGB[2], alpha());
		sketch.strokeWeight(strokeWeight);
		sketch.line(origin.position.x, origin.position.y, next.position.x, next.position.y);

		return this;

	}

}
