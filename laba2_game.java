package Laba2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class laba2_game {

    public static Unit unitDefinition(char H) {
        if (H == 'S') {
            return new Swordsman();
        }
        if (H == 'L') {
            return new LanceBearer();
        }
        if (H == 'A') {
            return new Axeman();
        }
        if (H == 'B') {
            return new ArcherBigBow();
        }
        if (H == 'O') {
            return new ArcherOrdinary();
        }
        if (H == 'W') {
            return new Crossbowman();
        }
        if (H == 'K') {
            return new Knight();
        }
        if (H == 'C') {
            return new Cuirassier();
        }
        if (H == 'H') {
            return new HorseArcher();
        }
        return new Swordsman();
    }

    public static char unitDefinitionReverse(Unit unit) {
        if (unit instanceof Swordsman) {
            return 'S';
        }
        if (unit instanceof LanceBearer) {
            return 'L';
        }
        if (unit instanceof Axeman) {
            return 'A';
        }
        if (unit instanceof ArcherBigBow) {
            return 'B';
        }
        if (unit instanceof ArcherOrdinary) {
            return 'O';
        }
        if (unit instanceof Crossbowman) {
            return 'W';
        }
        if (unit instanceof Knight) {
            return 'K';
        }
        if (unit instanceof Cuirassier) {
            return 'C';
        }
        if (unit instanceof HorseArcher) {
            return 'H';
        }
        return 'X';
    }

    public static void tableOfHero() {
        final Object[][] table = new String[10][];
        table[0] = new String[] { "Название", "Символ", "Здоровье", "Атака", "Дальность", "Защита", "Перемещение", "Стоимость" };
        table[1] = new String[] { "Мечник", "S", "50", "5", "1", "8", "3", "10" };
        table[2] = new String[] { "Копьеносец", "L", "35", "3", "1", "4", "6", "15" };
        table[3] = new String[] { "Топорщик", "A", "45", "9", "1", "3", "4", "20" };
        table[4] = new String[] { "Лучник(дл. лук)", "B", "30", "6", "5", "8", "2", "15" };
        table[5] = new String[] { "Лучник", "O", "25", "3", "3", "4", "4", "19" };
        table[6] = new String[] { "Арбалетчик", "W", "40", "7", "6", "3", "2", "23" };
        table[7] = new String[] { "Рыцарь", "K", "30", "5", "1", "3", "6", "20" };
        table[8] = new String[] { "Кирасир", "C", "50", "2", "1", "7", "5", "23" };
        table[9] = new String[] { "Конный лучник", "H", "25", "3", "3", "2", "5", "25" };
        for (final Object[] row : table) {
            System.out.format("%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", row);
        }
    }

    public static boolean sumHealth(ArrayList<Unit> heroUnit) {
        int sum = 0;
        for (Unit unit : heroUnit) {
            sum += unit.getHealth();
        }
        return sum > 0;
    }

    public static int indexOf (ArrayList<Unit> units, char symbol) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getSymbol() == symbol) {
                return i;
            }
        }
        return -1;
    }

    public static void printAllUnit (ArrayList<Unit> playersUnit, ArrayList<Unit> botsUnit) {
        System.out.println("Ваши герои:");
        for (Unit unit : playersUnit) {
            unit.printUnit();
        }
        System.out.println("Герои бота:");
        for (Unit unit : botsUnit) {
            unit.printUnit();
        }
    }

    public static void botThinks(ArrayList<Unit> playersUnit, ArrayList<Unit> botsUnit, Battlefield battlefield) {
        boolean flag = true;
        for (Unit unitB : botsUnit) {
            for (Unit unitP : playersUnit) {
                if (Math.sqrt((unitB.getX() - unitP.getX()) * (unitB.getX() - unitP.getX()) + (unitB.getY() - unitP.getY()) * (unitB.getY() - unitP.getY())) <= unitB.getAttackRange()) {
                    unitB.attack(unitP, playersUnit, battlefield);
                    System.out.println("Бот атаковал!");
                    printAllUnit(playersUnit, botsUnit);
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            int i = ThreadLocalRandom.current().nextInt(0,  botsUnit.size());
            botsUnit.get(i).moveBot(botsUnit.get(i).getSymbol(), battlefield);
            battlefield.draw();
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        Battlefield battlefield = new Battlefield();
        System.out.println((char)27 + "[40m                                          ------Bauman's Gate------");
        System.out.println("На город Bauman готовится нападение!!! Готовы ли вы встать на защиту? Сколько героев вам потребуется?");
        int N = in.nextInt();
        int rand = ThreadLocalRandom.current().nextInt(15,  20);
        System.out.println("В казне " + N*rand + " золота. Выберете " + N + " героев по их символу: ");
        tableOfHero();
        int sum = 0;
        ArrayList<Unit> playersUnit = new ArrayList<>();
        ArrayList<Unit> botsUnit = new ArrayList<>();
        char[] botsChar = {'S','B','O','A','W','C','L','K','H'};
        for (int i = 0; i < N; i++) {
            char hero = in.next().charAt(0);
            sum += unitDefinition(hero).cost;
            if (sum > N*rand) {
                System.out.println("Недостаточно золота для покупки " + hero + "-героя!");
                break;
            } else {
                playersUnit.add(unitDefinition(hero));
                battlefield.addHero(9, i + 1, playersUnit.get(i).getSymbol());
                playersUnit.get(i).setXY(9, i + 1);
                botsUnit.add(unitDefinition(botsChar[i]));
                battlefield.addHero(1, 9-i, botsUnit.get(i).getSymbol());
                botsUnit.get(i).setXY(1, 9-i);
            }
        }
        System.out.println("Перед вами поле: * - свободная клетка, o - пропасть, + - добавление нового героя");
        battlefield.draw();

        while (sumHealth(playersUnit) || sumHealth(botsUnit)) {
            if (!sumHealth(playersUnit)) {
                System.out.println("Вы проиграли!");
                break;
            } else if (!sumHealth(botsUnit)) {
                System.out.println("Вы выиграли!");
                break;
            } else {
                System.out.println("Ваш ход, выберите героя:");
                char hero = in.next().charAt(0);
                System.out.println("0 - атака, 1 - перемещение");
                if (in.nextInt() == 0) {
                    System.out.println("Кого будете атаковать?");
                    char attackedHero = in.next().charAt(0);
                    playersUnit.get(indexOf(playersUnit, hero)).attack(botsUnit.get(indexOf(botsUnit,attackedHero)), botsUnit, battlefield);
                    printAllUnit(playersUnit, botsUnit);
                } else {
                    playersUnit.get(indexOf(playersUnit, hero)).movePlayer(unitDefinitionReverse(playersUnit.get(indexOf(playersUnit, hero))), playersUnit, battlefield);
                    battlefield.draw();
                }
                if (sumHealth(botsUnit) && sumHealth(playersUnit)) {
                    System.out.println("Ход бота: ");
                    botThinks(playersUnit, botsUnit, battlefield);
                }
            }
        }
    }
}
