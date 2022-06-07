import java.util.ArrayList;

public class Accessor{

	//attributes
	ArrayList<ArrayList<Neuron>> neuronMatrix = new ArrayList<ArrayList<Neuron>>();

	public Accessor(ArrayList<ArrayList<Neuron>> neuronMatrix_)	{

		neuronMatrix = neuronMatrix_;
		
		int termination = neuronMatrix.size();
		
		for(int x = 0; x < termination; x++) {
			
			neuronMatrix.add(new ArrayList<Neuron>());
			
		}

	}

	Neuron getNeuron(int row, int column)	{

		return neuronMatrix.get(row).get(column);

	}

	
	//Convenient Access
	double getActivationState(int row, int column)	{

		return getNeuron(row, column).activationState;

	}

	double getTrueModValue(int row, int column)	{

		return getNeuron(row, column).trueModValue;

	}

	double getFalseModValue(int row, int column)	{

		return getNeuron(row, column).falseModValue;

	}

}
