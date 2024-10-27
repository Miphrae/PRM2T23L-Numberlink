package Game;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Point;

public class NumberlinkGame extends JFrame implements ActionListener {
    private final JButton[][] buttons;
    private final boolean[][] clicked;
    private Color currentColor;
    private final int[][] numbers; //baremap

    private ArrayList<ArrayList<Integer>> winMap = new ArrayList<>(); //map

    //ArrayList<ArrayList<Integer>> wejscie, ArrayList<ArrayList<Integer>> wejscieWin
    public NumberlinkGame() {
        //this.winMap = wejscieWin;
        setTitle("Numberlink Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        JPanel panel = new JPanel(new GridLayout(7, 7));
        buttons = new JButton[7][7];
        clicked = new boolean[7][7];
        currentColor = Color.GREEN;
        numbers = new int[7][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
                numbers[i][j] = -1;
            }
        }

        add(panel);
        pack();
        setVisible(true);

        Dimension windowSize = new Dimension(800, 800);
        setPreferredSize(windowSize);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);

        createColorButtons();
        //setNumbers(wejscie);
    }

    private void createColorButtons() {
        JPanel colorPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        JButton greenButton = new JButton("Green");
        greenButton.addActionListener(e -> currentColor = Color.GREEN);
        colorPanel.add(greenButton);

        JButton blueButton = new JButton("Blue");
        blueButton.addActionListener(e -> currentColor = Color.BLUE);
        colorPanel.add(blueButton);

        JButton redButton = new JButton("Red");
        redButton.addActionListener(e -> currentColor = Color.RED);
        colorPanel.add(redButton);

        JButton blackButton = new JButton("Pink");
        blackButton.addActionListener(e -> currentColor = Color.PINK);
        colorPanel.add(blackButton);

        JButton yellowButton = new JButton("Yellow");
        yellowButton.addActionListener(e -> currentColor = Color.YELLOW);
        colorPanel.add(yellowButton);

        JButton orangeButton = new JButton("Orange");
        orangeButton.addActionListener(e -> currentColor = Color.ORANGE);
        colorPanel.add(orangeButton);

        JButton cyanButton = new JButton("Cyan");
        cyanButton.addActionListener(e -> currentColor = Color.CYAN);
        colorPanel.add(cyanButton);

        JButton magentaButton = new JButton("Magenta");
        magentaButton.addActionListener(e -> currentColor = Color.MAGENTA);
        colorPanel.add(magentaButton);

        JButton grayButton = new JButton("Gray");
        grayButton.addActionListener(e -> currentColor = Color.GRAY);
        colorPanel.add(grayButton);

        JButton lightGrayButton = new JButton("Light Gray");
        lightGrayButton.addActionListener(e -> currentColor = Color.LIGHT_GRAY);
        colorPanel.add(lightGrayButton);

        JButton darkGrayButton = new JButton("Dark Gray");
        darkGrayButton.addActionListener(e -> currentColor = Color.DARK_GRAY);
        colorPanel.add(darkGrayButton);

        JButton generateButton = new JButton("New Game");
        generateButton.addActionListener(e -> {
            ArrayMapGenerator nowaMapa = new ArrayMapGenerator();
            nowaMapa.generateMap();
            nowaMapa.listForGUI();
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    numbers[i][j] = nowaMapa.getBareMap().get(i).get(j);
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(true);
                    clicked[i][j] = false;
                }
            }

            ArrayList<ArrayList<Integer>> arrayNumbers = new ArrayList<>();
            for (int i = 0; i < this.numbers.length; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int j = 0; j < this.numbers[0].length; j++) {
                    temp.add(this.numbers[i][j]);
                }
                arrayNumbers.add(temp);
            }
            setNumbers(arrayNumbers);

            this.winMap = nowaMapa.getMap();

            System.out.println(nowaMapa);

        });
        buttonPanel.add(generateButton);

        JButton safeYouGameButton = new JButton("Save Game");
        safeYouGameButton.addActionListener(e -> {
            try {
                writeMap();
                writeBareMap();
            }
            catch (Exception exc) {
                System.out.println("znowu upsik, nie ma pliku");
            }

        });
        buttonPanel.add(safeYouGameButton);

        JButton upLoadButton = new JButton("Load Game");
        upLoadButton.addActionListener(e -> {
            try {
//                ArrayMapGenerator nowaMapa = new ArrayMapGenerator();
//                nowaMapa.readMap();
//                nowaMapa.readBareMap();
//                this.winMap = nowaMapa.getMap();
//                System.out.println(winMap);
//
//                for (int i = 0; i < 7; i++) {
//                    for (int j = 0; j < 7; j++) {
//                        numbers[i][j] = nowaMapa.getBareMap().get(i).get(j);
//                    }
//                }
//
//                ArrayList<ArrayList<Integer>> arrayNumbers = new ArrayList<>();
//                for (int i = 0; i < this.numbers.length; i++) {
//                    ArrayList<Integer> temp = new ArrayList<>();
//                    for (int j = 0; j < this.numbers[0].length; j++) {
//                        temp.add(this.numbers[i][j]);
//                    }
//                    arrayNumbers.add(temp);
//                }
//                setNumbers(arrayNumbers);
                readMap();
                readBareMap();
                ArrayList<ArrayList<Integer>> arrayNumbers = new ArrayList<>();
                for (int i = 0; i < this.numbers.length; i++) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    for (int j = 0; j < this.numbers[0].length; j++) {
                        temp.add(this.numbers[i][j]);
                    }
                    arrayNumbers.add(temp);
                }
                setNumbers(arrayNumbers);
            }
            catch (Exception exc) {
                System.out.println("znowu znowu upsik, nie ma pliku");
            }
        });
        buttonPanel.add(upLoadButton);

        JButton screenshotButton = new JButton("Capture Screenshot");
        screenshotButton.addActionListener(e -> captureScreenshot("screenshot.png"));
        buttonPanel.add(screenshotButton);






        JButton checkVictoryPanel = new JButton("Check if Correct");
        checkVictoryPanel.addActionListener(e -> {
            if (checkWin()) {
                Victory.activate();
            }
        });
        buttonPanel.add(checkVictoryPanel);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    numbers[i][j] = winMap.get(i).get(j);
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(true);
                    clicked[i][j] = false;
                }
            }

            ArrayList<ArrayList<Integer>> arrayNumbers = new ArrayList<>();
            for (int i = 0; i < this.numbers.length; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int j = 0; j < this.numbers[0].length; j++) {
                    temp.add(this.numbers[i][j]);
                }
                arrayNumbers.add(temp);
            }
            setNumbers(arrayNumbers);
            System.out.println(arrayNumbers);
        });
        buttonPanel.add(solveButton);

        add(colorPanel, BorderLayout.NORTH);

        add(buttonPanel, BorderLayout.SOUTH);

    }

    public void writeBareMap() throws IOException {
        ArrayList<ArrayList<Integer>> arrayNumbers = new ArrayList<>();
        for (int i = 0; i < this.numbers.length; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < this.numbers[0].length; j++) {
                temp.add(this.numbers[i][j]);
            }
            arrayNumbers.add(temp);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Game/savedbaremap.txt"))) {
            for(ArrayList<Integer> line : arrayNumbers) {
                writer.write(String.valueOf(line));
                writer.newLine();
            }
        }
    }
    public void writeMap() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Game/savedmap.txt"))) {
            for (ArrayList<Integer> line : this.winMap) {
                writer.write(String.valueOf(line));
                writer.newLine();
            }
        }
    }



    public void readBareMap() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Game/savedbaremap.txt"))) {
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
            //System.out.println(fullLista);

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    numbers[i][j] = fullLista.get(i).get(j);
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

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    this.winMap.get(i).set(j, fullLista.get(i).get(j));
                }
            }
        }
    }

    private boolean checkWin() {
        ArrayList<ArrayList<Integer>> arrayNumbers = new ArrayList<>();
        for (int i = 0; i < this.numbers.length; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < this.numbers[0].length; j++) {
                temp.add(this.numbers[i][j]);
            }
            arrayNumbers.add(temp);
        }

        if (arrayNumbers.equals(this.winMap)) {
            return true;
        }
        else {
            return false;
        }

    }
    private void setNumbers(ArrayList<ArrayList<Integer>> wejscie) {
//        numbers[0][4] = 3;
//        numbers[0][5] = 2;
//        numbers[0][6] = 1;
//        numbers[1][4] = 1;
//        numbers[3][2] = 2;
//        numbers[5][1] = 3;
//        numbers[5][2] = 5;
//        numbers[5][5] = 4;
//        numbers[6][0] = 4;
//        numbers[6][6] = 5;
//        numbers[5][4] = -10;
        for (int i = 0; i < wejscie.size(); i++) {
            for (int j = 0; j < wejscie.get(0).size(); j++) {
                numbers[i][j] = wejscie.get(i).get(j);
            }
        }

//        ArrayList<ArrayList<Integer>> test = new ArrayList<>();
//        for (int i = 0; i < wejscie.size()-1; i++) {
//            ArrayList<Integer> temp = new ArrayList<>();
//            for (int j = 0; j < wejscie.get(0).size()-1; j++) {
//                temp.add(this.numbers[i][j]);
//            }
//            test.add(temp);
//        }
//        System.out.println(test);



        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (numbers[i][j] < 0 ) {
                    buttons[i][j].setText(String.valueOf(numbers[i][j]));
                }
                if(numbers[i][j] == -3){
                    buttons[i][j].setBackground(Color.PINK);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -1){
                    buttons[i][j].setBackground(Color.GREEN);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -2){
                    buttons[i][j].setBackground(Color.RED);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -4){
                    buttons[i][j].setBackground(Color.BLUE);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -5){
                    buttons[i][j].setBackground(Color.YELLOW);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -6){
                    buttons[i][j].setBackground(Color.ORANGE);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -7){
                    buttons[i][j].setBackground(Color.CYAN);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -8){
                    buttons[i][j].setBackground(Color.MAGENTA);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -9) {
                    buttons[i][j].setBackground(Color.GRAY);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -10) {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                    buttons[i][j].setEnabled(false);
                }
                if (numbers[i][j] == -11) {
                    buttons[i][j].setBackground(Color.DARK_GRAY);
                    buttons[i][j].setEnabled(false);
                }
                if(numbers[i][j] == 0){
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.BLACK);
                    buttons[i][j].setEnabled(false);
                }
                if(numbers[i][j] == 100) {
                    buttons[i][j].setBackground(null);
                }
                if(numbers[i][j] == 3){
                    buttons[i][j].setBackground(Color.PINK);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 1){
                    buttons[i][j].setBackground(Color.GREEN);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 2){
                    buttons[i][j].setBackground(Color.RED);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 4){
                    buttons[i][j].setBackground(Color.BLUE);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 5){
                    buttons[i][j].setBackground(Color.YELLOW);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 6){
                    buttons[i][j].setBackground(Color.ORANGE);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 7){
                    buttons[i][j].setBackground(Color.CYAN);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 8){
                    buttons[i][j].setBackground(Color.MAGENTA);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 9) {
                    buttons[i][j].setBackground(Color.GRAY);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 10) {
                    buttons[i][j].setBackground(Color.LIGHT_GRAY);
                    buttons[i][j].setEnabled(true);
                }
                if (numbers[i][j] == 11) {
                    buttons[i][j].setBackground(Color.DARK_GRAY);
                    buttons[i][j].setEnabled(true);
                }

            }
        }
    }

    private void captureScreenshot(String fileName) {
        try {
            Robot robot = new Robot();

            BufferedImage screenshot = robot.createScreenCapture(getBounds());

            ImageIO.write(screenshot, "png", new File(fileName));

            System.out.println("Screenshot saved as " + fileName);
        } catch (AWTException | IOException ex) {
            ex.printStackTrace();
        }
    }

//    public void setMap(ArrayList<ArrayList<Integer>> map) {
//        this.currentMap = map;
//    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (buttons[i][j] == source) {
                    if (clicked[i][j]) {
                        source.setBackground(null);
                        numbers[i][j] = 100;
                        clicked[i][j] = false;
                    } else {
                        source.setBackground(currentColor);
                        clicked[i][j] = true;

                        if (currentColor.equals(Color.GREEN)) {
                            numbers[i][j] = 1;
//                            ArrayList<ArrayList<Integer>> test = new ArrayList<>();
//                            for (int pi = 0; pi < 7; pi++) {
//                                ArrayList<Integer> temp = new ArrayList<>();
//                                for (int pj = 0; pj < 7; pj++) {
//                                    temp.add(this.numbers[pi][pj]);
//                                }
//                                test.add(temp);
//                            }
//                            System.out.println(test);

                        }
                        else if (currentColor.equals(Color.RED)) {
                            numbers[i][j] = 2;
                        }
                        else if (currentColor.equals(Color.PINK)) {
                            numbers[i][j] = 3;
                        }
                        else if (currentColor.equals(Color.BLUE)) {
                            numbers[i][j] = 4;
                        }
                        else if (currentColor.equals(Color.YELLOW)) {
                            numbers[i][j] = 5;
                        }
                        else if (currentColor.equals(Color.ORANGE)) {
                            numbers[i][j] = 6;
                        }
                        else if (currentColor.equals(Color.CYAN)) {
                            numbers[i][j] = 7;
                        }
                        else if (currentColor.equals(Color.MAGENTA)) {
                            numbers[i][j] = 8;
                        }
                        else if (currentColor.equals(Color.GRAY)) {
                            numbers[i][j] = 9;
                        }
                        else if (currentColor.equals(Color.LIGHT_GRAY)) {
                            numbers[i][j] = 10;
                        }
                        else if (currentColor.equals(Color.DARK_GRAY)) {
                            numbers[i][j] = 11;
                        }
                    }
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        //NumberlinkGame game = new NumberlinkGame();
//        ArrayMapGenerator nowaMapa = new ArrayMapGenerator();
//
//
//
//        nowaMapa.generateMap();
//        nowaMapa.listForGUI();
//        //System.out.println(nowaMapa.getBareMap());
//        System.out.println(nowaMapa);

        SwingUtilities.invokeLater(() ->{
                    NumberlinkGame game = new NumberlinkGame();
                }
        );
    }


}
