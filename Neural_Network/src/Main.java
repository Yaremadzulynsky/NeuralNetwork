import processing.core.PApplet;

public class Main extends PApplet{

	//attributes
	Network network;
	Accessor accessor;

	boolean learning = true;
	boolean go = true;

	public void settings()	{

		size(800, 800);

	}

	public void setup()	{

		network = new Network(this, 2, 10, 12, 10, 2);
		accessor = new Accessor(network.neuronMatrix);
		Connector.randActivationWeight(accessor.neuronMatrix);

	}

	public void draw()	{

		if(go)	{
			//		try {
			//			Thread.sleep(100);
			//		} catch (InterruptedException e) {
			//			// TODO Auto-generated catch block
			//			e.printStackTrace();
			//		}
			background(256);

			accessor.getNeuron(0, 0).activationState = (double)((double)mouseX/(double)width);
			accessor.getNeuron(0, 1).activationState = (double)((double)mouseY/(double)height);

			network.forwardPropagate(accessor.getNeuron(0, 0));

			if(learning)	{
//				network.backPropagate(accessor.getNeuron(4, 0), 1, 't');
//				network.backPropagate(accessor.getNeuron(4, 1), 1, 'f');
				
				if(mouseX > width/2 && mouseY > height/2)	{
					
					network.newLearn(accessor.getNeuron(4, 0), 1, accessor.getNeuron(4, 0), 0, true);
					
				}
				else	{
					
					network.newLearn(accessor.getNeuron(4, 0), 0, accessor.getNeuron(4, 1), 1, true);
					
				}
				
				
				
				network.learn(outcome(mouseX, mouseY), accessor.getNeuron(4, 0), accessor.getNeuron(4, 1));
			}

			network.visualize();

		}


	}


	public void keyPressed()	{
		
		background(256);

		if(key == 'r')	{

			for(int x = 0; x < width; x = x + 8)	{

				for(int y = 0; y < height; y = y + 8)	{

					accessor.getNeuron(0, 0).activationState = (double)((double)x/(double)width);
					accessor.getNeuron(0, 1).activationState = (double)((double)y/(double)height);
					network.forwardPropagate(accessor.getNeuron(0, 0));
					
					if(accessor.getActivationState(4,  0) > accessor.getActivationState(4,  1))	{
//						System.out.println("true");
						fill(0, 256, 0);
						stroke(0, 256, 0);
						
					}
					else	{
//						System.out.println("false");
						fill(256, 0, 0);
						stroke(256, 0, 0);
						
					}
				
					
					ellipse(x+4, y+4, 5, 5);

				}

			}

		}

		if(key == ' ')	{

			if(learning)	{
				learning = false;
			}
			else	{
				learning = true;
			}


		}
		if(key == 't')	{

			if(go)	{

				go = false;

			}
			else	{
				go = true;
			}

		}
		if(key == 'l')	{

			for(int n = 0; n < 100000; n++)	{
				int randX = (int)(Math.random()*width);
				int randY = (int)(Math.random()*height);

				//				System.out.println(randX + ", " + randY);

				network.forwardPropagate(accessor.getNeuron(0, 0));

				accessor.getNeuron(0, 0).activationState = (double)((double)randX/(double)width);
				accessor.getNeuron(0, 1).activationState = (double)((double)randY/(double)height);

				network.backPropagate(accessor.getNeuron(4, 0), 1, 't');
				network.backPropagate(accessor.getNeuron(4, 1), 1, 'f');
				network.learn(outcome(randX, randY), accessor.getNeuron(4, 0), accessor.getNeuron(4, 1));

			}
			background(256);
			network.visualize();
			//			try {
			//				Thread.sleep(100);
			//			} catch (InterruptedException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}

		}
		if(key == 'g')	{

			if(accessor.getNeuron(4, 0).activationState > accessor.getNeuron(4, 1).activationState)	{

				System.out.println("Guess: true");

			}
			else	{
				System.out.println("Guess: false");
			}

		}

	}

	
	
	boolean outcome(int x, int y)	{

		if(x > width/2 && y > height/2)	{

			return true;

		}
		else	{

			return false;

		}

	}
}
