package Laba2;

import java.util.concurrent.ThreadLocalRandom;

public class Battlefield {

    private final char[][] field;
    private final int size = 10;
    public int getSize(){
        return size;
    }
    public Battlefield() {
        field = new char[size][size];
        initializeField();
    }
    private void initializeField() {
        boolean flag = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int rand = ThreadLocalRandom.current().nextInt(0, 25);
                if (i == 0) {
                    field[i][j] = Character.forDigit(j,10);
                } else if (j == 0) {
                    field[i][j] = Character.forDigit(i,10);
                } else if (rand == 0 && flag) {
                    field[i][j] = '+';
                    flag = false;
                } else if (rand == 1) {
                   field[i][j] = 'o';
                } else {
                    field[i][j] = '*';
                }
            }
        }
    }
    public void draw() {
        char[] botsChar = {'S','B','O','A','W','C','L','K','H'};
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == '*') {
                    System.out.print((char)27 + "[37m" + field[i][j] + (char)27 + "[39m ");
                } else if (field[i][j] == 'o') {
                    System.out.print((char)27 + "[31m" + field[i][j] + (char)27 + "[39m ");
                } else if (field[i][j] == 'S' || field[i][j] == 'B' || field[i][j] == 'O' || field[i][j] == 'A' || field[i][j] == 'W' || field[i][j] == 'C' || field[i][j] == 'L' || field[i][j] == 'K' || field[i][j] == 'H') {
                    System.out.print((char)27 + "[32m" + field[i][j] + (char)27 + "[39m ");
                } else if (field[i][j] == '+') {
                    System.out.print((char)27 + "[35m" + field[i][j] + (char)27 + "[39m ");
                } else {
                    System.out.print(field[i][j] + " ");
                }

            }
            System.out.println();
        }
    }
    public void addHero(int x, int y, char symbol) {
        if (x > 0 && x < size && y > 0 && y < size) {
            field[x][y] = symbol;
        }
    }
    public void removeHero(int x, int y) {
        if (x > 0 && x < size && y > 0 && y < size) {
            field[x][y] = '*';
        }
    }

    public boolean isField(int x, int y) {
        return field[x][y] == '*';
    }

    public boolean isFieldHole(int x, int y) {
        return field[x][y] == 'o';
    }

    public boolean isFieldMoney(int x, int y) {
        return field[x][y] == '+';
    }
}
