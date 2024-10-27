package Game;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayMapGenerator {
    private ArrayList<ArrayList<Integer>> map = new ArrayList<>();

    private ArrayList<ArrayList<Integer>> bareMap = new ArrayList<>();

    private int N = 7;

    private Point begin;
    private Point next;

    private int number = 1;

    public ArrayMapGenerator() {
        for (int i = 0; i < this.N; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < this.N; j++) {
                temp.add(0);
            }
            map.add(temp);
        }
    }

    public static boolean inArea(Point position, ArrayList<ArrayList<Integer>> map) {
        int x = (int) position.getX();
        int y = (int) position.getY();

        if (y < 0 || (y > map.size()-1) || x < 0 || (x > map.get(0).size()-1)) {
            return false;
        }
        else {
            return true;
        }
    }


    public static boolean isEmpty(Point position, ArrayList<ArrayList<Integer>> map){
        if (!inArea(position, map)) {
            return false;
        }
        else {
            if (map.get((int)position.getY()).get((int)position.getX()) == 0 ) {
                return true;
            }
            else {
                return false;
            }
        }
    }


    public static boolean areNeighboursEmpty(Point position, ArrayList<ArrayList<Integer>> map, int currentNumber) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point neighbour = new Point((int)position.getX()+j, (int)position.getY()+i);
                if (inArea(neighbour, map)) {
                    if ((i == -1 && j == -1) || (i == -1 && j == 1) || (i == 0 && j == 0) || (i == 1 && j == -1) || (i == 1 && j == 1)) {
                        continue;
                    }
                    if (isEmpty(neighbour, map)) {
                        if (!isSameNumber(position, neighbour, map, currentNumber)){
                            counter += 1;
                        }
                    }
                }
            }
        }
        if (counter == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public static ArrayList<Point> whichNeighboursEmpty(Point position, ArrayList<ArrayList<Integer>> map, int currentNumber) {
        ArrayList<Point> wynik = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point neighbour = new Point((int)position.getX()+j, (int)position.getY()+i);
                if (inArea(neighbour, map)) {
                    if ((i == -1 && j == -1) || (i == -1 && j == 1) || (i == 0 && j == 0) || (i == 1 && j == -1) || (i == 1 && j == 1)) {
                        continue;
                    }
                    // map.get((int)neighbour.getX()).get((int)neighbour.getY())
                    if (isEmpty(neighbour, map)) {
                        if (!isSameNumber(position, neighbour, map, currentNumber)) {
                            wynik.add(neighbour);
                        }
                    }
                }

            }
        }
        return wynik;
    }



    public static boolean isSameNumber(Point position, Point neighbour, ArrayList<ArrayList<Integer>> map, int currentNumber) {

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point neighbourOfNeighbour = new Point((int) neighbour.getX() + j, (int) neighbour.getY() + i);
                if (inArea(neighbourOfNeighbour, map)) {
                    if ((i == -1 && j == -1) || (i == -1 && j == 1) || (i == 0 && j == 0) || (i == 1 && j == -1) || (i == 1 && j == 1) || ((((int) neighbourOfNeighbour.getY()) == (int) position.getY()) && (int) neighbourOfNeighbour.getX() == (int) position.getX())) {
                        continue;
                    }

                    if (map.get((int) neighbourOfNeighbour.getY()).get((int) neighbourOfNeighbour.getX()) == currentNumber) {
                        return true;
                    }

                }

            }
        }
        return false;
    }


    public static boolean areNeighboursOfNeighbourEmpty(Point position, Point neighbour, ArrayList<ArrayList<Integer>> map) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point neighbourOfNeighbour = new Point((int) neighbour.getX() + j, (int) neighbour.getY() + i);
                if (inArea(neighbourOfNeighbour, map)) {
                    if ((i == -1 && j == -1) || (i == -1 && j == 1) || (i == 0 && j == 0) || (i == 1 && j == -1) || (i == 1 && j == 1) || ((((int) neighbourOfNeighbour.getY()) == (int) position.getY()) && (int) neighbourOfNeighbour.getX() == (int) position.getX())) {
                        continue;
                    }
                    else {
                        if (isEmpty(neighbourOfNeighbour, map)) {
                            counter += 1;
                        }
                    }
                }
            }
        }

        if (counter == 0){
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean threeInARow(Point position, ArrayList<ArrayList<Integer>> map, int currentNumber) {
        int counter = 0;
        if (areNeighboursEmpty(position, map, currentNumber)) {
            ArrayList<Point> neighbours = whichNeighboursEmpty(position, map, currentNumber);
            for (Point neighbour : neighbours) {
                if (areNeighboursOfNeighbourEmpty(position, neighbour, map)) {
                    counter += 1;
                }
            }
        }
        else {
            return false;
        }

        if (counter == 0) {
            return false;
        }
        else {
            return true;
        }
    }


    public static boolean isAvailable(ArrayList<ArrayList<Integer>> map, int currentNumber, int N ) {
        //sprawdza czy sa miejsca

        int counter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Point temp_point = new Point(j,i);
                if (isEmpty(  (temp_point), map)) {
                    if (threeInARow(temp_point, map, currentNumber)) {
                        counter += 1;
                    }


                }
            }

        }
        if (counter == 0) {
            System.out.println("Nie znaleziono miejsca");
            return false;
        }
        else {
            return true;
        }

    }


    public void generateMap() {

        boolean flag = true;

        while (flag) {

            if (!isAvailable(this.map, this.number, this.N)) {
                flag = false;
            } else {

                boolean searchingForFirstEmpty = true;
                while (searchingForFirstEmpty) {

                    this.begin = new Point();

                    int x = ThreadLocalRandom.current().nextInt(0, 9);
                    int y = ThreadLocalRandom.current().nextInt(0, 9);

                    this.begin.setLocation(x, y);

                    if (!isEmpty(this.begin, this.map) || !threeInARow(this.begin, this.map, this.number)) {
                        continue;
                    } else {
                        this.map.get((int) this.begin.getY()).set((int) this.begin.getX(), this.number * (-1));
                        ArrayList<Point> neighbours = whichNeighboursEmpty(this.begin, this.map, this.number);
                        boolean trying = true;
                        while (trying) {
                            int drawNumber = ThreadLocalRandom.current().nextInt(0, neighbours.size());
                            if (areNeighboursEmpty(neighbours.get(drawNumber), this.map, this.number)) {
                                this.next = neighbours.get(drawNumber);
                                trying = false;
                            }
                        }
                        this.map.get((int) this.next.getY()).set((int) this.next.getX(), this.number);
                        searchingForFirstEmpty = false;
                    }

                    int maxLineCounter = ThreadLocalRandom.current().nextInt(0, this.N+3);
                    System.out.println(maxLineCounter);
                    boolean searching = true;
                    while (searching && maxLineCounter < (this.N + 3)) {
                        this.begin = this.next;
                        boolean sprawdzam = areNeighboursEmpty(this.begin, this.map, this.number);
                        if (areNeighboursEmpty(this.begin, this.map, this.number)) {
                            ArrayList<Point> neighbours = whichNeighboursEmpty(this.begin, this.map, this.number);
                            int drawNumber = ThreadLocalRandom.current().nextInt(0, neighbours.size());
                            this.next = neighbours.get(drawNumber);
                            this.map.get((int) this.next.getY()).set((int) this.next.getX(), this.number);
                            maxLineCounter += 1;
                        } else {
                            this.map.get((int) this.next.getY()).set((int) this.next.getX(), this.number * (-1));
                            searching = false;
                            this.number += 1;
                        }
                    }

                    if (searching) {
                        this.map.get((int) this.next.getY()).set((int) this.next.getX(), this.number * (-1));
                        number += 1;
                    }
                }
            }
        }
    }


    public void setMap(ArrayList<ArrayList<Integer>> map) {
        this.map = map;
    }

    public ArrayList<ArrayList<Integer>> getMap(){
        return this.map;
    }

    public void setPosition(int indexI, int indexJ, int value) {
        this.bareMap.get(indexI).set(indexJ, value);
    }

    @Override
    public String toString() {
        StringBuilder wynik = new StringBuilder();
        for (ArrayList<Integer >el : this.map) {
            wynik.append(el);
            wynik.append("\n");
        }
        return wynik.toString();
    }


    public void listForGUI() {
        this.bareMap = new ArrayList<>();
        for(int i = 0; i < this.map.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for(int j = 0; j < this.map.size(); j++) {
                temp.add(this.map.get(i).get(j));
            }
            this.bareMap.add(temp);
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(bareMap.get(i).get(j) > 0) {
                    bareMap.get(i).set(j, 100);
                }
            }
        }
    }
    public ArrayList<ArrayList<Integer>> getBareMap() {
        return bareMap;
    }
    public void writeBareMap() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Game/savedbaremap.txt"))) {
            for(ArrayList<Integer> line : this.bareMap) {
                writer.write(String.valueOf(line));
                writer.newLine();
            }
        }
    }
    public void writeMap() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Game/savedmap.txt"))) {
            for (ArrayList<Integer> line : this.map) {
                writer.write(String.valueOf(line));
                writer.newLine();
            }
        }
    }


    public void readBareMap() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Game/savedbaremap.txt"))) {
            String line;
            StringBuilder zKlamerkami = new StringBuilder();
            while((line = reader.readLine()) != null) {
                //this.bareMap.add(zKlamerkami.toString());
                zKlamerkami.append(line);
                zKlamerkami.append("\n");


            }
            String bezKlamerek = zKlamerkami.toString();
            bezKlamerek = bezKlamerek.replace("[", "");
            bezKlamerek = bezKlamerek.replace("]", "");
            bezKlamerek = bezKlamerek.replace(",", "");
            String[] lines = bezKlamerek.split("\n");
            ArrayList<String[]> lista = new ArrayList<>();
            //int i =0;
            for (String l : lines) {
                String[] temp = l.split(" ");
                lista.add(temp);
            }
            ArrayList<ArrayList<Integer>> fullLista = new ArrayList<>();
            for (String[] el : lista) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (String item : el) {
                    temp.add(Integer.valueOf(item));
                }
                fullLista.add(temp);

            }

            for (int i = 0; i < this.N; i++) {
                for (int j = 0; j < this.N; j++) {
                    this.bareMap.get(i).set(j, fullLista.get(i).get(j));
                }
            }

        }

    }

    public void readMap() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Game/savedmap.txt"))) {
            String line;
            StringBuilder zKlamerkami = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                //this.bareMap.add(zKlamerkami.toString());
                zKlamerkami.append(line);
                zKlamerkami.append("\n");


            }
            String bezKlamerek = zKlamerkami.toString();
            bezKlamerek = bezKlamerek.replace("[", "");
            bezKlamerek = bezKlamerek.replace("]", "");
            bezKlamerek = bezKlamerek.replace(",", "");
            String[] lines = bezKlamerek.split("\n");
            ArrayList<String[]> lista = new ArrayList<>();
            //int i =0;
            for (String l : lines) {
                String[] temp = l.split(" ");
                lista.add(temp);
            }
            ArrayList<ArrayList<Integer>> fullLista = new ArrayList<>();
            for (String[] el : lista) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (String item : el) {
                    temp.add(Integer.valueOf(item));
                }
                fullLista.add(temp);

            }

            for (int i = 0; i < this.N; i++) {
                for (int j = 0; j < this.N; j++) {
                    this.map.get(i).set(j, fullLista.get(i).get(j));
                }
            }
        }
    }



    public static void main(String[] args) {
        ArrayMapGenerator nowaMapa = new ArrayMapGenerator();

        nowaMapa.generateMap();
        System.out.println("-------------------------------------------------------");
        System.out.println(nowaMapa);
        nowaMapa.listForGUI();
        System.out.println(nowaMapa.getBareMap());
        try {
            nowaMapa.writeBareMap();
            nowaMapa.writeMap();
            System.out.println("---------------------------------");
            nowaMapa.readBareMap();
            nowaMapa.readMap();
        }catch(Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

    }

}
