package Laba2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static Laba2.laba2_game.unitDefinition;

public class Unit {
    public void printUnit () {
        System.out.println(symbol + ": здоровье " + health + ", защита " + defence + ".");
    }

    public void decreaseHealth (int damage, ArrayList<Unit> arrayUnit, Battlefield battlefield) {
        if (damage <= defence) {
            this.defence = defence - damage;
        }
        else {
            this.health = health + defence - damage;
            this.defence = 0;
        }
        if (this.health <= 0) {
            battlefield.removeHero(this.x,this.y);
            arrayUnit.remove(this);
        }
    }

    public void attack(Unit unit, ArrayList<Unit> arrayUnit, Battlefield battlefield) {
        if (Math.sqrt( (unit.x-this.x)*(unit.x-this.x) + (unit.y-this.y)*(unit.y-this.y)) > this.attackRange) {
            System.out.println("Невозможно атаковать, похоже герой слишком далеко");
        } else {
            unit.decreaseHealth(this.attackValue, arrayUnit, battlefield);
        }
    }

    public void movePlayer(char H, ArrayList<Unit> arrayUnit, Battlefield battlefield) {
        Scanner in = new Scanner (System.in);
        System.out.println("Введите координаты:");
        int x = in.nextInt();
        int y = in.nextInt();
        int size = battlefield.getSize();
        if (x >= size || y >= size) {
            System.out.println("Некорректные координаты! Попробуйте еще раз");
            movePlayer(H, arrayUnit, battlefield);
        } else if (Math.sqrt( (x-this.x)*(x-this.x) + (y-this.y)*(y-this.y)) > this.moving) {
            System.out.println("У героя нет возможности пойти так далеко, попробуйте еще раз");
            movePlayer(H, arrayUnit, battlefield);
        } else if (battlefield.isFieldHole(x, y)) {
            System.out.println("Ваш герой " + H + " упал в пропасть!!!");
            decreaseHealth(100, arrayUnit, battlefield);
        } else if (battlefield.isFieldMoney(x, y)) {
            battlefield.addHero(x, y, H);
            battlefield.removeHero(this.x, this.y);
            this.setXY(x, y);
            System.out.println("Вам выпала возможность выбрать нового героя!");
            char hero = in.next().charAt(0);
            arrayUnit.add(unitDefinition(hero));
            battlefield.addHero(9, 9, arrayUnit.getLast().getSymbol());
            arrayUnit.getLast().setXY(9, 9);
        } else if (!battlefield.isField(x, y)) {
            System.out.println("Клетка поля не свободна, попробуйте еще раз");
            movePlayer(H, arrayUnit, battlefield);
        } else {
            battlefield.addHero(x, y, H);
            battlefield.removeHero(this.x, this.y);
            this.setXY(x, y);
        }
    }

    public void moveBot (char H, Battlefield battlefield) {
        int randX = ThreadLocalRandom.current().nextInt(0,  this.moving);
        int randY = ThreadLocalRandom.current().nextInt(0, this.moving);
        if(battlefield.isField(this.x + randX, this.y - randY)) {
            battlefield.addHero(this.x + randX, this.y - randY, H);
            battlefield.removeHero(this.x, this.y);
            this.setXY(this.x + randX, this.y - randY);
        } else {
            moveBot(H,battlefield);
        }
    }

    protected char symbol;
    public char getSymbol(){
        return symbol;
    }
    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    private int x, y;
    public int getX(){
        return x;
    }
    public int getY() {
        return y;
    }
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected int health;
    public int getHealth() {
        return health;
    }

    protected int attackValue;
    protected int attackRange;
    public int getAttackRange() {
        return attackRange;
    }

    protected int defence;
    protected int moving;
    protected int cost;

}
