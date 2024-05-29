import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Labirynt {
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    char[][] labirynt;
    int wiersze, kolumny;
    Point startowy, docelowy;
    Point aktualnaPozycja;
    Random losowy = new Random();
    Scanner wczytaj = new Scanner(System.in);
    List<Character> wszystkieKroki = new ArrayList<>();
    List<Character> doceloweKroki = new ArrayList<>();

    public static void main(String[] args) {
        Labirynt labirynt = new Labirynt();
        labirynt.generujLabirynt();
        labirynt.generujSciezke();
        labirynt.interaktywnePrzechodzenie();
    }

    void generujLabirynt() {
        System.out.println("Podaj dwie liczby dla zbudowania labiryntu:");
        wiersze = wczytaj.nextInt();
        kolumny = wczytaj.nextInt();
        wczytaj.nextLine(); 

        labirynt = new char[wiersze][kolumny];
        startowy = new Point(0, losowy.nextInt(kolumny));
        docelowy = new Point(wiersze - 1, losowy.nextInt(kolumny));

        //generowanie losowego labiryntu
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                labirynt[i][j] = losowy.nextDouble() < 0.3 ? '#' : '.';
            }
        }

        labirynt[startowy.x][startowy.y] = '$';
        labirynt[docelowy.x][docelowy.y] = '@';
        aktualnaPozycja = new Point(startowy.x, startowy.y);
    }

    void generujSciezke() {
        //tworzenie ścieżki
        int x = startowy.x;
        int y = startowy.y;

        while (x != docelowy.x || y != docelowy.y) {
            if (x < docelowy.x) {
                x++;
            } else if (x > docelowy.x) {
                x--;
            } else if (y < docelowy.y) {
                y++;
            } else if (y > docelowy.y) {
                y--;
            }
            if (labirynt[x][y] != '@') {
                labirynt[x][y] = '.';
            }
        }
//wyświetlam labirynt
        System.out.println("Masz taki labirynt:");
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                System.out.print(labirynt[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Podaj jeden krok (G - gora, D - dol, L - lewo, P - prawo):");
    }

    void interaktywnePrzechodzenie() {
        while (true) {
            char krok = wczytaj.nextLine().charAt(0);
            wszystkieKroki.add(krok);

            if (wykonajKrok(krok)) {
                if (labirynt[aktualnaPozycja.x][aktualnaPozycja.y] == '@') {
                    System.out.println("Gratulacje, jestes w punkcie docelowym!");
                    doceloweKroki.add(krok);
                    break;
                } else {
                    System.out.println("Prawidlowy krok. Contynuj!");
                    doceloweKroki.add(krok);
                }
            } else {
                System.out.println("Nieprawidlowy krok. Sprobuj ponownie.");
            }
        }

        //wszystkie kroki
        System.out.print("Wszystkie kroki: ");
        for (char krok1 : wszystkieKroki) {
            System.out.print(krok1);
        }
        System.out.println();

        //prawidłowe kroki
        System.out.print("Prawidlowe kroki do punktu docelowego: ");
        for (char krok1 : doceloweKroki) {
            System.out.print(krok1);
        }
        System.out.println();
    }

    boolean wykonajKrok(char krok) {
        int newX = aktualnaPozycja.x;
        int newY = aktualnaPozycja.y;

        switch (krok) {
            
            case 'G':
             newX--;
              break;
            case 'D': 
             newX++; 
              break;
            case 'L': 
             newY--;
              break;
            case 'P': 
             newY++;
              break;

            default: 
            return false;
        }

        //sprawdzenie czy nie wychodzimy poza labirynt lub nie wchodzimy na ścianę
        if (newX < 0 || newX >= wiersze || newY < 0 || newY >= kolumny || labirynt[newX][newY] == '#') {
            System.out.println("Uderzyles w sciane lub wyszedles poza labirynt.");
            return false;
        }

        aktualnaPozycja.x = newX;
        aktualnaPozycja.y = newY;
        return true;
    }
}
