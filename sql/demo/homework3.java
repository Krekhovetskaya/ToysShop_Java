package sql.demo;

import java.util.*;

public class homework3 {

    static Scanner scan = new Scanner(System.in);
    static Set<Toy> toys = new HashSet<>();

    public static void main(String[] args) {
        choice();
    }

    public static void choice() {
        System.out.println("Список действий:");
        System.out.println("1. Добавить новую игрушку");
        System.out.println("2. Изменить вес существующей игрушки");
        System.out.println("3. Провести розыгрыш");
        System.out.println("4. Показать все игрушки");
        System.out.println("5. Закончить");
        boolean exit = false;
        while (!exit) {
            System.out.println("Выберете действие");
            int n = 0;
            try {
                n = scan.nextInt();
            } catch (InputMismatchException e) {
                scan.next();
                continue;
            }
            switch (n) {
                case 1 -> {
                    Toy toy = newToy();
                    if (toy == null) {
                        break;
                    }
                    if (toys.stream().anyMatch(t -> t.getName().equals(toy.getName()))) {
                        System.out.println("Игрушка с данным именем уже есть в магазине");
                        break;
                    }
                    toys.add(toy);
                    System.out.printf("Игрушка %s в магазине!", toy);
                    System.out.println();
                }
                case 2 -> change();
                case 3 -> change();
                case 4 -> toys.forEach(System.out::println);
                case 5 -> exit = true;
            }
        }
    }

    public static Toy newToy() {
        Random ranndom = new Random();
        try {
            System.out.println("Введите имя игрушки");
            String name = scan.next();
            System.out.println("Введите вес игрушки в %");
            int probability = scan.nextInt();
            if (probability >= 100) {
                System.out.println("Вероятность не может быть больше 100 %");
                return null;
            }
            System.out.println("Введите количество игрушки");
            int count = scan.nextInt();
            return new Toy(ranndom.nextInt(Integer.MAX_VALUE), name, count, probability);
        } catch (InputMismatchException e) {
            System.out.println("Данное поле должно быть целым числом");
            scan.next();
            return null;
        }
    }

    public static void change() {
        Toy newToy = newToy();
        toys.stream().filter(toy -> toy.equals(newToy))
                .peek(toy -> toy.setCount(newToy.getCount()))
                .peek(toy -> toy.setProbability(newToy.getProbability()))
                .forEach(System.out::println);
    }

}