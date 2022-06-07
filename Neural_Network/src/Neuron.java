import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Neuron {

	//Attributes
	double activationState;
	double trueModValue;
	double falseModValue;
	PVector position;
	int RGB[] = {255, 255,255};

	//Processing Formalities
	PApplet sketch;

	//Object Attributes
	ArrayList<Connector> nextConnections = new ArrayList<Connector>();
	ArrayList<Connector> preConnections = new ArrayList<Connector>();

	//Constructor
	public Neuron(PApplet sketch_, PVector position_, double activationState_)	{

		activationState = activationState_; 
		sketch = sketch_;
		position = position_;

	}

	int alpha()	{

		return (int)(activationState*(double)256);

	}

	void setRGB(int a, int b, int c)	{

		RGB[0] = a;
		RGB[1] = b;
		RGB[2] = c;

	}

	void randActivationState()	{

		activationState = (double)Math.random();

	}

	public static void randActivationState(ArrayList<ArrayList<Neuron>> neuronMatrix)	{

		for(ArrayList<Neuron> e: neuronMatrix)	{

			for(Neuron e2: e)	{


				e2.randActivationState();
//				System.out.println(e2.activationState);

			}

		}

	}

	void visualize(float radius_)	{

		sketch.stroke(RGB[0], RGB[1], RGB[2]);
		sketch.fill(255, alpha());
		sketch.ellipse(position.x, position.y, radius_, radius_);

	}

}
