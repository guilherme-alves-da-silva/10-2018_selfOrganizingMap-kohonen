
import java.util.ArrayList;

public class Network {

    public static final Double LEARNING_RATE = 0.01;
    public static final Double E = 0.1;
    private Neuron network[][];
    private static int epochs;

    /*
     * distance of each neuron, this grid is reused for each sample to populate it with distances
     * and find the closest, then the closest is put in the ArrayList.
     *
     * the distance of a neuron is an attribute of a neuron, however because the distance of a
     * neuron is recalculated a few times, the functionality comes from the network and not from the
     * neuron, therefore the distances could be stored in the network to be reused and maintained,
     * but i'm not sure, maybe they should be in the neuron.
     */
    private Double distance[][];
    private ArrayList<Coordinate> distanceOfTheClosestNeuron; //of each sample, in the current epoch

    public Network(int width, int height, int amountOfFeatures) {
        network = new Neuron[width][height];
        distance = new Double[width][height];
        distanceOfTheClosestNeuron = new ArrayList<Coordinate>();

        //instantiation of neurons
        for (int i = 0; i < network.length; i++) {
            for (int j = 0; j < network[0].length; j++) {
                network[i][j] = new Neuron(amountOfFeatures);
            }
        }

        //populate the list of neighbors of each neuron
        for (int line = 0; line < network.length; line++) {
            for (int column = 0; column < network[0].length; column++) {
                if (checkNorthBorder(line)) {
                    network[line][column].neighbors.add(new Coordinate(line - 1, column));
                }
                if (checkEastBorder(column)) {
                    network[line][column].neighbors.add(new Coordinate(line, column + 1));
                }
                if (checkSouthBorder(line)) {
                    network[line][column].neighbors.add(new Coordinate(line + 1, column));
                }
                if (checkWestBorder(column)) {
                    network[line][column].neighbors.add(new Coordinate(line, column - 1));
                }
            }
        }

        epochs = 0;
    }

    private boolean checkNorthBorder(int line) {
        return line > 0;
    }

    private boolean checkEastBorder(int column) {
        return column < network[0].length - 1;
    }

    private boolean checkSouthBorder(int line) {
        return line < network.length - 1;
    }

    private boolean checkWestBorder(int column) {
        return column > 0;
    }

    /**
     * going through each neuron in the network and getting the distance of it to the currentSample,
     * then after populating the grid of distances, finding the smallest distance value and
     * returning the coordinate of the neuron to which the distance belongs.
     *
     * @return "x, y"
     */
    private Coordinate getShortestDistance(Double[] currentSample) {

        //for each neuron in the network
        for (int i = 0; i < network.length; i++) {
            for (int j = 0; j < network[0].length; j++) {
                Double sum = 0.0;

                for (int feature = 0; feature < currentSample.length - 1; feature++) {
                    sum += Math.pow(currentSample[feature] - network[i][j].weight[feature], 2);
                }

                /*
                 * getting the distance of each neuron to the sample, putting it in the grid that is
                 * being reused.
                 */
                distance[i][j] = Math.sqrt(sum);
            }
        }

        Coordinate shortest = new Coordinate(distance[0][0]);

        //going through the distances to find the shortest
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < distance[0].length; j++) {
                if (distance[i][j] < shortest.distance) {
                    shortest.distance = distance[i][j];
                    shortest.x = i;
                    shortest.y = j;
                }
            }
        }

        return shortest;
    }

    /**
     * just getting the biggest value really, from the list of distances that is reused for each
     * epoch.
     *
     * @return "distance"
     */
    private Double getLongestDistance() {
        Double longestDistance = distanceOfTheClosestNeuron.get(0).distance;

        for (int i = 0; i < distanceOfTheClosestNeuron.size(); i++) {
            if (distanceOfTheClosestNeuron.get(i).distance > longestDistance) {
                longestDistance = distanceOfTheClosestNeuron.get(i).distance;
            }
        }

        System.out.println("current longest distance: " + longestDistance);
        return longestDistance;
    }

    public void train(Double[][] input) {
        do {
            distanceOfTheClosestNeuron.clear();

            //for each sample
            for (int sample = 0; sample < input.length; sample++) {

                //find closest neuron
                Coordinate closestNeuron = getShortestDistance(input[sample]);

                //update the closest neuron
                for (int feature = 0; feature < input[0].length - 1; feature++) {
                    network[closestNeuron.x][closestNeuron.y].weight[feature] += LEARNING_RATE * (input[sample][feature] - network[closestNeuron.x][closestNeuron.y].weight[feature]);
                }

                //update neighbors of the closest neuron
                for (int i = 0; i < network[closestNeuron.x][closestNeuron.y].neighbors.size(); i++) {
                    Coordinate neighbor = network[closestNeuron.x][closestNeuron.y].neighbors.get(i);

                    for (int feature = 0; feature < input[0].length - 1; feature++) {
                        network[neighbor.x][neighbor.y].weight[feature] += (LEARNING_RATE / 2.0) * (input[sample][feature] - network[neighbor.x][neighbor.y].weight[feature]);
                    }
                }

                //put in the list of the closest distances, the closest neuron to the current sample
                distanceOfTheClosestNeuron.add(closestNeuron);
            }
            epochs++;

            //just printing, not needed for the algorithm to work
            System.out.println("\ndistances in the epoch: " + epochs);
            for (int i = 0; i < distanceOfTheClosestNeuron.size(); i++) {
                System.out.println("input[" + i + "] _ closest neuron: " + distanceOfTheClosestNeuron.get(i));
            }

            /*
             * in this condition (inside the while) i can just check if any distance in the list
             * is > E, there's no need to find the longest distance to do it, if i find one that
             * is > E that's already enough for the loop to continue.
             */
        } while (getLongestDistance() > E);
    }

    /**
     * only run this after training.
     *
     * @param inputSample
     * @return weights
     */
    public Double[] getOutput(Double[] inputSample) {
        Coordinate neuron = getShortestDistance(inputSample);

        //just printing, not needed for the algorithm to work
        System.out.println("\noutput: " + neuron);
        for (int i = 0; i < network[neuron.x][neuron.y].weight.length; i++) {
            System.out.print(network[neuron.x][neuron.y].weight[i] + " ");
        }
        System.out.println();

        return network[neuron.x][neuron.y].weight;
    }
}
