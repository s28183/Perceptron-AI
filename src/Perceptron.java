import java.util.List;

public class Perceptron {

    private double[] weights;
    private double learningRate;
    private int epochs;
    private List<double[]> testInputs;
    private List<Integer> testLabels;

    public Perceptron(int numberOfInputs, double learningRate, int epochs, List<double[]> testInputs, List<Integer> testLabels) {
        this.weights = new double[numberOfInputs + 1];
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.testInputs = testInputs;
        this.testLabels = testLabels;
    }

    public void train(List<double[]> trainData,List<Integer> labels){
        for (int x = 0; x < epochs; x++){
            for (int i = 0; i < trainData.size(); i++){
                double[] inputs = trainData.get(i);
                int label = labels.get(i);
                double output = predict(inputs);
                double difference = label - output;
                for (int j = 0; j < weights.length - 1; j++) { // jezeli 0 to nie przesuwamy wektora
                    weights[j] = weights[j] + learningRate * difference * inputs[j];
                }
                weights[weights.length - 1] = weights[weights.length - 1] + learningRate * difference;
            }

            double accuracy = test(testInputs, testLabels);
            System.out.println("Epoka: " + (x + 1)+ " Dokładność: " + accuracy);
        }
    }



    public double predict(double[] inputs){
        double suma = 0.0;
        for (int i = 0; i < inputs.length; i++) {
            suma = suma + weights[i] * inputs[i];
        }
        suma = suma + weights[weights.length - 1];
        if (suma >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public double test(List<double[]> data, List<Integer> label) {
        int git = 0;
        for (int i = 0; i <data.size(); i++){
            double[] inputs = data.get(i);
            int expected = label.get(i);
            int prediction = (int)predict(inputs);
            if (prediction==expected) {
                git++;
            }
        }
        return (double)git/data.size();
    }
}
