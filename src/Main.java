import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<double[]> trainingInputs = new ArrayList<>();
    private static List<Integer> trainingLabels = new ArrayList<>();
    private static List<double[]>testInputs = new ArrayList<>();
    private static List<Integer> testLabels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wprodwadz sciezke dane treningowe: ");
        String trainPath = scanner.nextLine();
        System.out.println("Wprowadz sciezke dane testowe: ");
        String Path = scanner.nextLine();
        System.out.println("Wprowadz learning rate: ");
        double learningRate = Double.parseDouble(scanner.nextLine());
        System.out.println("Wprowadz ile razy ma zadzialac petla: ");
        int epochs = Integer.parseInt(scanner.nextLine());

        loadData(trainPath, trainingInputs, trainingLabels);
        loadData(Path, testInputs, testLabels);

        Perceptron perceptron = new Perceptron(trainingInputs.get(0).length, learningRate, epochs,testInputs, testLabels);
        perceptron.train(trainingInputs, trainingLabels);


        fromConsole(scanner, perceptron);
        scanner.close();
    }
//dla liczb
//    private static void loadData(String path, List<double[]> inputs, List<Integer> labels) throws IOException {
//        List<String> lines = Files.readAllLines(Paths.get(path));
//        for (String line : lines) {
//            if (line.trim().isEmpty()) continue;
//            String[] parts = line.split(",");
//            double[] features = new double[parts.length - 1];
//            for (int i = 0; i < parts.length - 1; i++) {
//                features[i] = Double.parseDouble(parts[i]);
//            }
//            try {
//                labels.add(Integer.parseInt(parts[parts.length - 1]));
//            } catch (Exception e) {
//            }
//
//            inputs.add(features);
//        }
//    }
private static void loadData(String sciezka, List<double[]> inputs, List<Integer> labels) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get(sciezka));
    for (String line : lines){
        String[] parts = line.split(",");
        double[] features = new double[parts.length - 1];
        for (int i = 0; i < parts.length -1; i++){
            features[i] = Double.parseDouble(parts[i]);
        }
        String labelPart = parts[parts.length - 1].trim();
        int label = -1;
        switch (labelPart) {
            case "Iris-virginica":
                label = 1;
                break;
            case "Iris-versicolor":
                label = 0;
                break;
            default:
                try {
                    label = Integer.parseInt(labelPart);
                } catch (Exception e) {

                }
                break;
        }

        if (label >= 0) {
            inputs.add(features);
            labels.add(label);
        }
    }
}


    private static void fromConsole(Scanner scanner,Perceptron perceptron){
        while(true){
            System.out.println("Wprowadz obserwacje oddzielona przecinkami: ");
            String input = scanner.nextLine();
            try {
                String[] parts = input.split(",");
                double[] nowe = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    nowe[i] = Double.parseDouble(parts[i]);
                }
                double prediction = perceptron.predict(nowe);
                System.out.println("Predykcja: " + (int)prediction);
            } catch (NumberFormatException e) {
            } catch (Exception e) {
            }
        }
    }
}





