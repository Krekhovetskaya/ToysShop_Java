package sql.demo;

import java.util.InputMismatchException;

public class ToyController {

    ToyService toyService = new ToyService();

    public void choice() {
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
                n = toyService.scan.nextInt();
            } catch (InputMismatchException e) {
                toyService.scan.next();
                continue;
            }
            switch (n) {
                case 1 -> addNewToy();
                case 2 -> change();
                case 3 -> getPrize();
                case 4 -> toyService.toys.forEach(System.out::println);
                case 5 -> exit = true;
            }
        }
    }

    private void getPrize() {
        toyService.getPrize();
    }

    private void addNewToy() {
        toyService.newToy();
    }


    public void change() {
        toyService.changeProbability();
    }

}
