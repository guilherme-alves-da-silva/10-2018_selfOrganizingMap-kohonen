
import java.util.ArrayList;
import java.util.Random;

public class Neuron {

    private Random rand;
    public Double[] weight;
    public ArrayList<Coordinate> neighbors;

    public Neuron(int amountOfFeatures) {
        rand = new Random();
        neighbors = new ArrayList<Coordinate>();
        weight = new Double[amountOfFeatures];

        //set random weights
        for (int i = 0; i < weight.length; i++) {
            weight[i] = rand.nextDouble();
        }
    }
}
