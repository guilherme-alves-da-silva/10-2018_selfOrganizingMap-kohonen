
public class Util {

    private Util() {
    }

    private static Double modulo(Double[] sample) {
        Double sum = 0.0;

        for (int i = 0; i < sample.length; i++) {
            sum += Math.pow(sample[i], 2);
        }

        return Math.sqrt(sum);
    }

    public static Double[][] normalize(Double[][] input) {
        Double[][] result = new Double[input.length][input[0].length];
        Double currentModulo;

        for (int i = 0; i < input.length; i++) {
            currentModulo = modulo(input[i]);

            for (int j = 0; j < input[0].length; j++) {
                result[i][j] = input[i][j] / currentModulo;
            }
        }

        return result;
    }
}
