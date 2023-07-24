package sql.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ToyService {

    Scanner scan = new Scanner(System.in);
    Random random = new Random();
    Set<Toy> toys = new HashSet<>();

    public Toy getById(int randomValue) {
        return toys.stream().filter(toy -> toy.getId() == randomValue).findFirst().orElse(null);
    }

    public void saveToyToFile(Toy toy) {
        String text = toy.toString();
        try (FileWriter writer = new FileWriter("prize.txt", true)) {
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void newToy() {
        try {
            String name = inputName();
            if (toys.stream().anyMatch(t -> t.getName().equals(name))) {
                System.out.println("Игрушка с данным именем уже есть в магазине");
                return;
            }
            Integer probability = inputProbability();
            if (probability == null) {
                return;
            }
            Integer count = inputCount();
            if (count == null) {
                return;
            }
            Toy toy = new Toy(random.nextInt(Integer.MAX_VALUE), name, count, probability);
            toys.add(toy);
            System.out.printf("Игрушка %s в магазине!", toy);
            System.out.println();
        } catch (InputMismatchException e) {
            System.out.println("Данное поле должно быть целым числом");
            scan.next();
        }
    }

    public void changeProbability() {
        String name = inputName();
        if (toys.stream().noneMatch(toy -> toy.getName().equals(name))) {
            System.out.println("Данной игрушки нет в магазине");
            return;
        }
        try {
            Integer probability = inputProbability();
            if (probability == null) {
                return;
            }
            toys.stream().filter(toy -> toy.getName().equals(name))
                    .peek(toy -> toy.setProbability(probability))
                    .forEach(System.out::println);

        } catch (InputMismatchException e) {
            System.out.println("Данное поле должно быть целым числом");
            scan.next();
        }
    }

    private String inputName() {
        System.out.println("Введите имя игрушки");
        return scan.next();
    }

    private Integer inputProbability() {
        System.out.println("Введите вес игрушки в %");
        int probability = scan.nextInt();
        if (probability > 100) {
            System.out.println("Вероятность не может быть больше 100 %");
            return null;
        }
        return probability;
    }

    private Integer inputCount() {
        System.out.println("Введите количество игрушки");
        int count = scan.nextInt();
        if (count < 0) {
            System.out.println("Количество игрушек должно быть больше нуля");
            return null;
        }
        return count;
    }

    public void getPrize() {
        List<Integer> listToysIdByWeight = new ArrayList<>();
        for (Toy toy : toys) {
            if (toy.getCount() == 0) {
                continue;
            }
            for (int countWeight = 0; countWeight < toy.getProbability(); countWeight++) {
                listToysIdByWeight.add(toy.getId());
            }
        }
        if (listToysIdByWeight.size() == 0) {
            System.out.println("В магазине нет игрушек");
            return;
        }
        int randomValue = random.nextInt(listToysIdByWeight.size());
        Toy prize = getById(listToysIdByWeight.get(randomValue));
        prize.setCount(prize.getCount() - 1);
        System.out.println(prize);
        saveToyToFile(prize);

    }
}
