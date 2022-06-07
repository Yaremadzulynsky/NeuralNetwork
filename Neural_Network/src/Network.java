import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Network {

	//attributes
	ArrayList<ArrayList<Neuron>> neuronMatrix = new ArrayList<ArrayList<Neuron>>();
	double learningRate = 0.01;

	//processing formalities
	PApplet sketch;

	//constructor
	public Network(PApplet sketch_, int rowOne, int rowTwo, int rowThree)	{

		sketch = sketch_;

		//Create Positioning Information
		int across = sketch.width/3;
		int rowOneDistribution = sketch.height/rowOne;
		int rowTwoDistribution = sketch.height/rowTwo;
		int rowThreeDistribution = sketch.height/rowThree;



		neuronMatrix.add(new ArrayList<Neuron>());
		neuronMatrix.add(new ArrayList<Neuron>());
		neuronMatrix.add(new ArrayList<Neuron>());



		for(int n = 0; rowOne > n; n++)	{

			neuronMatrix.get(0).add(new Neuron(sketch, new PVector(1*across-across/2, n*rowOneDistribution+rowOneDistribution/2), 1));

		}
		for(int n = 0; rowTwo > n; n++)	{

			neuronMatrix.get(1).add(new Neuron(sketch, new PVector(2*across-across/2, n*rowTwoDistribution+rowTwoDistribution/2), 1));

		}
		for(int n = 0; rowThree > n; n++)	{

			neuronMatrix.get(2).add(new Neuron(sketch, new PVector(3*across-across/2, n*rowThreeDistribution+rowThreeDistribution/2), 1));

		}

		for(int n = 1; n < neuronMatrix.size(); n++)	{

			for(Neuron e: neuronMatrix.get(n))	{


				for(int x = 0; x < neuronMatrix.get(n-1).size(); x++)	{

					e.preConnections.add(new Connector(sketch, 1, neuronMatrix.get(n-1).get(x), e));


				}

				for(Neuron e3: neuronMatrix.get(n-1))	{

					for(Connector e4: e.preConnections)	{

						if(e4.origin == e3)	{

							e3.nextConnections.add(e4);

						}

					}
				}
			}



		}



	}

	public Network(PApplet sketch_, int rowOne, int rowTwo, int rowThree, int rowFour, int rowFive)	{

		sketch = sketch_;

		//Create Positioning Information
		int across = sketch.width/5;
		int rowOneDistribution = sketch.height/rowOne;
		int rowTwoDistribution = sketch.height/rowTwo;
		int rowThreeDistribution = sketch.height/rowThree;
		int rowFourDistribution = sketch.height/rowFour;
		int rowFiveDistribution = sketch.height/rowFive;



		neuronMatrix.add(new ArrayList<Neuron>());
		neuronMatrix.add(new ArrayList<Neuron>());
		neuronMatrix.add(new ArrayList<Neuron>());
		neuronMatrix.add(new ArrayList<Neuron>());
		neuronMatrix.add(new ArrayList<Neuron>());



		for(int n = 0; rowOne > n; n++)	{

			neuronMatrix.get(0).add(new Neuron(sketch, new PVector(1*across-across/2, n*rowOneDistribution+rowOneDistribution/2), 1));

		}
		for(int n = 0; rowTwo > n; n++)	{

			neuronMatrix.get(1).add(new Neuron(sketch, new PVector(2*across-across/2, n*rowTwoDistribution+rowTwoDistribution/2), 1));

		}
		for(int n = 0; rowThree > n; n++)	{

			neuronMatrix.get(2).add(new Neuron(sketch, new PVector(3*across-across/2, n*rowThreeDistribution+rowThreeDistribution/2), 1));

		}
		for(int n = 0; rowFour > n; n++)	{

			neuronMatrix.get(3).add(new Neuron(sketch, new PVector(4*across-across/2, n*rowFourDistribution+rowFourDistribution/2), 1));

		}
		for(int n = 0; rowFive > n; n++)	{

			neuronMatrix.get(4).add(new Neuron(sketch, new PVector(5*across-across/2, n*rowFiveDistribution+rowFiveDistribution/2), 1));

		}

		for(int n = 1; n < neuronMatrix.size(); n++)	{

			for(Neuron e: neuronMatrix.get(n))	{


				for(int x = 0; x < neuronMatrix.get(n-1).size(); x++)	{

					e.preConnections.add(new Connector(sketch, 1, neuronMatrix.get(n-1).get(x), e));


				}

				for(Neuron e3: neuronMatrix.get(n-1))	{

					for(Connector e4: e.preConnections)	{

						if(e4.origin == e3)	{

							e3.nextConnections.add(e4);

						}

					}
				}
			}



		}



	}

	void forwardPropagate(Neuron origin)	{


		for(Connector e: origin.nextConnections)	{

			double sum = 0;

			for(Connector e2: e.next.preConnections)	{

				sum = sum + (e2.activationWeight*e2.origin.activationState);

			}

			e.next.activationState = sum/(double)(e.next.preConnections.size());

			forwardPropagate(e.next);


		}


	}

	void backPropagate(Neuron origin, double scale, char c)	{


		if(c == 't')	{
			for(Connector e: origin.preConnections) {

				e.setRGB(0, 256, 0);
				e.trueWeight = scale;
				backPropagate(e.origin, (scale/(double)origin.preConnections.size())*e.activationWeight, 't');


			}
		}
		if(c == 'f')	{
			for(Connector e: origin.preConnections) {

				e.setRGB(0, 256, 0);
				e.falseWeight = scale;
				backPropagate(e.origin, (scale/(double)origin.preConnections.size())*e.activationWeight, 't');

			}
		}

	}

	void newLearn(Neuron trueNeuron, double trueError, Neuron falseNeuron, double falseError, boolean firstGo)	{

		if(firstGo)	{
			trueError = trueError - trueNeuron.activationState;
			falseError = falseError - falseNeuron.activationState;
		}

		for(Connector e: trueNeuron.preConnections)	{

			e.activationWeight = e.activationWeight + e.trueWeight*trueError*learningRate;

			newLearn(e.origin, trueError, falseNeuron, falseError, false);

		}

//		for(Connector e: falseNeuron.preConnections)	{
//
//
//			e.activationWeight = e.activationWeight + e.falseWeight*falseError*learningRate;
//
//			newLearn(trueNeuron, trueError, e.origin, falseError, false);
//
//		}


	}

	void learn(boolean propperOutcome, Neuron trueNeuron, Neuron falseNeuron)	{

		double falseError;
		double trueError;

		if(propperOutcome)	{

			trueError = 1 - trueNeuron.activationState;
			falseError = 0 - falseNeuron.activationState;

			System.out.println("correct: " + 100*(trueError/1));
			System.out.println("incorrect: " + 100*(falseError/1));

		}
		else	{


			trueError = 0 - trueNeuron.activationState;
			falseError = 1 - falseNeuron.activationState;

			System.out.println("correct: " + 100*(trueError/1));
			System.out.println("incorrect: " + 100*(falseError/1));

		}

		for(ArrayList<Neuron> e: neuronMatrix)	{

			for(Neuron e2: e)	{

				for(Connector e3: e2.nextConnections)	{

					//					System.out.println(((double)(learningRate*e3.trueWeight*trueError + learningRate*e3.falseWeight*falseError)));
					//					if(((double)(learningRate*e3.trueWeight*trueError + learningRate*e3.falseWeight*falseError)/2) < (double)-0.00001 && ((double)(learningRate*e3.trueWeight*trueError + learningRate*e3.falseWeight*falseError)/2) > (double)0.00001)	{

					//					if(e3.activationWeight > 0.001 && e3.activationWeight < 1)	{
					e3.activationWeight = (e3.activationWeight*(1+(learningRate*(/**e3.trueWeight*/trueError + /**e3.falseWeight*/falseError))));
					//					}
					//					else if(e3.activationWeight < 0.001 && ((learningRate*e3.trueWeight*trueError + learningRate*e3.falseWeight*falseError)) > 0) {
					//
					//						e3.activationWeight = (e3.activationWeight*(1+((learningRate*e3.trueWeight*trueError + learningRate*e3.falseWeight*falseError))));
					//
					//					}
					//					else if(e3.activationWeight > 1 && ((learningRate*e3.trueWeight*trueError + learningRate*e3.falseWeight*falseError)) < 0) {
					//
					//						e3.activationWeight = (e3.activationWeight*(1+((learningRate*e3.trueWeight*trueError + learningRate*e3.falseWeight*falseError))));
					//
					//					}
					//					System.out.println(e3.origin.position.x + " " + e3.origin.position.y + " " + e3.activationWeight);
				}

			}

		}

		//		for(Connector e: origin.preConnections) {
		//
		//			
		//
		//		}

	}


	void visualize()	{

		//		sketch.fill(255);
		//		sketch.stroke(255);

		for(ArrayList<Neuron> e1: neuronMatrix)	{

			for(Neuron e2: e1)	{

				Connector.visualize(e2.nextConnections);

			}
		}
		//		sketch.fill(255);
		//		sketch.stroke(255);

		for(ArrayList<Neuron> e1: neuronMatrix)	{

			for(Neuron e2: e1)	{

				e2.visualize(50);	


			}


		}

	}

}
//