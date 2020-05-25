package geekbrains.lesson_4;

import javax.swing.*;
import java.util.Random;
import java.util.Scanner;
/*TASKS 2. Переделать проверку победы, чтобы она не была реализована просто набором условий, например, с использованием циклов.
3. * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4. Очень желательно не делать это просто набором условий для каждой из возможных ситуаций;
4. *** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.
 */
public class Main {
    public static int SIZE = 5;// Поле 5х5
    public static int DOTS_TO_WIN = 4;//4ре знака подряд для победы
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char[][] map;
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();
    public static void main(String[] args) {
        System.out.println("Homework, Lesson 4");
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Поздравляю вы победили!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победила программа:*(");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
        System.out.println("Гейм овер");
    }



    //Логика проверки победы переписанна
    static boolean checkWin(char symb) {
        for (int col=0; col<SIZE - DOTS_TO_WIN + 1; col++) {
            for (int row=0; row<SIZE - DOTS_TO_WIN + 1; row++) {
                if (checkDiagonal(symb, col, row) || checkLines(symb, col, row)) return true;
            }
        }
        return false;
    }

    // Проверяем диагонали
    static boolean checkDiagonal(char symb, int offsetX, int offsetY) {
        boolean rightDiagonal, leftDiagonal;
        rightDiagonal = true;// установили логическое значение 1
        leftDiagonal = true;// установили логическое значение 1
        for (int i=0; i<DOTS_TO_WIN; i++) {// цикл от 0 до условия количества символов подряд необходимых для победы
            rightDiagonal &= (map[i+offsetX][i+offsetY] == symb);
            leftDiagonal &= (map[DOTS_TO_WIN-i-1+offsetX][i+offsetY] == symb);
        }

        if (rightDiagonal || leftDiagonal) return true;
// вернули (true), если во всех клетках левой или правой диагонали нам встретились символы symb
        return false;
    }
    //Проверяем линии и колонки
    static boolean checkLines(char symb, int offsetX, int offsetY) {
        boolean lines, rows;
        for (int col=offsetX; col<DOTS_TO_WIN+offsetX; col++) {
            lines = true;
            rows = true;
            for (int row=offsetY; row<DOTS_TO_WIN+offsetY; row++) {
                lines &= (map[col][row] == symb);
                rows &= (map[row][col] == symb);
            }
// условие после каждой проверки колонки и столбца
            if (lines || rows) return true;
        }

        return false;
    }

    static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }
    //Переписанный ИИ для блокирующего хода, если игрок в шаге от победы он займёт выйгрышную клетку
    static void aiTurn() {

        //Проверим игрока а нет ли у него будующего выигрышного хода
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(i, j)) {
                    map[i][j] = DOT_X;
                    if (checkWin(DOT_X)) {
                        map[i][j] = DOT_O;
                        return;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        // если ничего нет пускай ходит в любые свободные координаты
        int x, y;
        do {
            x = rand.nextInt(SIZE - 1);
            y = rand.nextInt(SIZE - 1);

        } while (!isCellValid(x, y));

        System.out.println("AI сделал свой ход" + (x + 1) + " " + (y + 1));

        map[y][x] = DOT_O;
    }

    static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введит координаты своего хода в формате X(номер ячейки по горизонтали), нажмите enter и введите Y(номер ячейки по вертикали)");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));

        map[x][y] = DOT_X;
    }

    static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (map[y][x] == DOT_EMPTY) return true;
        return false;
    }

    static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}