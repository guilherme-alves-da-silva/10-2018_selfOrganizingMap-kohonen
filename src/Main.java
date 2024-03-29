
public class Main {

    public static void main(String[] args) {
        Network n = new Network(4, 4, 3);

        Double[][] data = {
            {0.2471, 0.1778, 0.2905},
            {0.8240, 0.2223, 0.7041},
            {0.4960, 0.7231, 0.5866},
            {0.2923, 0.2041, 0.2234},
            {0.8118, 0.2668, 0.7484},
            {0.4837, 0.8200, 0.4792},
            {0.3248, 0.2629, 0.2375},
            {0.7209, 0.2116, 0.7821},
            {0.5259, 0.6522, 0.5957},
            {0.2075, 0.1669, 0.1745},
            {0.7830, 0.3171, 0.7888},
            {0.5393, 0.7510, 0.5682}
        };

        data = Util.normalize(data);

        n.train(data);

        n.getOutput(data[0]);
        n.getOutput(data[1]);
        n.getOutput(data[2]);
    }
}
