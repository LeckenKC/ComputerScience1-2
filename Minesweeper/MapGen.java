/**
 * Author: Kenzie Leckenby
 * Date: Oct 4, 2022
 * Description
 */
package Minesweeper;

import java.util.Random;
import java.util.Scanner;

public class MapGen {
    int[][] mineMap;
    boolean[][] interactedMap;
    boolean[][] tempFoundZeros;
    boolean[][] testedZeros;
    int totalBombs;
    int mapSize;
    int firstClickX;
    int firstClickY;
    int newZeros;

    // Calculates all the numbers on the board
    private void bombProx() {
        for (int x = 0; x < this.mineMap.length; x++) {
            for (int y = 0; y < this.mineMap[x].length; y++) {
                if(this.mineMap[x][y] == -1) { // Checks if the tile is a bomb
                    int xFirst, xLast, yFirst, yLast; // Bounds for the for miniArray that checks for bomb proximity
                    xFirst = x - 1 < 0 ? 0 : -1; // Checks if the first x bound is out of bounds
                    xLast = x + 1 >= this.mineMap.length ? 1 : 2; // Checks if the last x bound is out of bounds
                    yFirst = y - 1 < 0 ? 0 : -1; // Checks if the first y bound is out of bounds
                    yLast = y + 1 >= this.mineMap[x].length ? 1 : 2; // Checks if the last y bound is out of bounds
                    for(int miniArrayX = xFirst; miniArrayX < xLast; miniArrayX++) {
                        for(int miniArrayY = yFirst; miniArrayY < yLast; miniArrayY++) {
                            if ((miniArrayY != 0 || miniArrayX != 0) && (this.mineMap[x + miniArrayX][y + miniArrayY] != -1)) { // Checks if the tile is a bomb
                                this.mineMap[x + miniArrayX][y + miniArrayY] += 1; // Adds that there is a nearby bomb
                            }
                        }
                    }
                }
            }
        }
    }

    // Checks when placing bombs that the location that its trying to place the bomb in is valid
    private boolean validBombLoc(int curX, int curY) {
        if (this.mineMap[curX][curY] == 0) {
            int xFirst, xLast, yFirst, yLast;

            xFirst = (this.firstClickX - 1 < 0) ? this.firstClickX : this.firstClickX - 1; // Checks if the first x bound is out of bounds
            xLast = (this.firstClickX + 1 >= this.mineMap.length) ? this.firstClickX + 1: this.firstClickX + 2; // Checks if the last x bound is out of bounds
            yFirst = (this.firstClickY - 1 < 0) ? this.firstClickY : this.firstClickY - 1; // Checks if the first y bound is out of bounds
            yLast = (this.firstClickY + 1 >= this.mineMap[this.firstClickX].length) ? this.firstClickY + 1 : this.firstClickY + 2; // Checks if the last y bound is out of bounds

            for(int miniArrayX = xFirst; miniArrayX < xLast; miniArrayX++) {
                for (int miniArrayY = yFirst; miniArrayY < yLast; miniArrayY++) {
                    // Checks if the tile is too close to the first click
                    if (curX == miniArrayX && curY == miniArrayY) {
                        //System.out.println("Tried: " + curY + ", " + curX);
                        //System.out.println("Too close to click");
                        return false; // invalid spot
                    }
                }
            }
        }
        else {
            //System.out.println("Tried: " + curY + ", " + curX);
            //System.out.println("Already a bomb");
            return false; // Already a bomb
        }
        return true;
    }

    // Finds all adjacent zeros from a starting point
    private void adjacentZeros(int startX, int startY) {
        int xFirst, xLast, yFirst, yLast;

        xFirst = (startX - 1 < 0) ? startX : startX - 1; // Checks if the first x bound is out of bounds
        xLast = (startX + 1 >= this.mapSize) ? startX + 1: startX + 2; // Checks if the last x bound is out of bounds
        yFirst = (startY - 1 < 0) ? startY : startY - 1; // Checks if the first y bound is out of bounds
        yLast = (startY + 1 >= this.mapSize) ? startY + 1 : startY + 2; // Checks if the last y bound is out of bounds

        for (int miniArrayX = xFirst; miniArrayX < xLast; miniArrayX++) {
            for (int miniArrayY = yFirst; miniArrayY < yLast; miniArrayY++) {
                this.interactedMap[miniArrayX][miniArrayY] = true;
                if (this.mineMap[miniArrayX][miniArrayY] == 0) {
                    this.tempFoundZeros[miniArrayX][miniArrayY] = true;
                }
            }
        }
        // Checks how many new zeros it found when running the code to inform the while loop
        this.newZeros = 0;
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                if (this.tempFoundZeros[i][j] && !this.testedZeros[i][j]) {
                    newZeros++;
                }
            }
        }
        // Checks if it only found the zero it was initially checking
        if(newZeros == 1) {
            newZeros--;
        }
        // Declares that it has tested this zero fully
        this.testedZeros[startX][startY] = true;
    }

    // Default Constructor: 10x10 grid where 15% of the spaces are bombs
    public MapGen(int xInput, int yInput) {
        Random rand = new Random();
        this.totalBombs = 15;
        this.mapSize = 10;
        this.mineMap = new int[10][10];
        this.interactedMap = new boolean[10][10];
        this.tempFoundZeros = new boolean[10][10];
        this.testedZeros = new boolean[10][10];
        this.firstClickX = yInput; // Fuck dem 2d arrays
        this.firstClickY = xInput;

        for(int i = 0; i < totalBombs; i++) { // Will assign 10 random coordinates with -1 to represent bombs
            int tempX = rand.nextInt(10);
            int tempY = rand.nextInt(10);
            if (validBombLoc(tempX, tempY)) { // Checks to see if it is a repeat coordinate and if true finds a different coordinate
                this.mineMap[tempX][tempY] = -1;
            }
            else {
                i--;
            }
        }
        bombProx();
    }

    // MapGen with non-default mapSize and default bomb ratio which is 15%
    public MapGen(int xInput, int yInput, int mapSize) {
        Random rand = new Random();
        this.mapSize = mapSize;
        this.totalBombs = (int) (Math.pow(mapSize, 2) * .15);
        this.firstClickX = yInput;
        this.firstClickY = xInput;
        this.mineMap = new int[mapSize][mapSize];
        this.interactedMap = new boolean[mapSize][mapSize];
        this.tempFoundZeros = new boolean[mapSize][mapSize];
        this.testedZeros = new boolean[mapSize][mapSize];

        for(int i = 0; i < this.totalBombs; i++) { // Will assign 10 random coordinates with -1 to represent bombs
            int tempX = rand.nextInt(mapSize);
            int tempY = rand.nextInt(mapSize);
            if (validBombLoc(tempX, tempY)) { // Checks to see if it is a repeat coordinate and if true finds a different coordinate
                this.mineMap[tempX][tempY] = -1;
            }
            else {
                i--;
            }
        }
        bombProx();
    }

    // MapGen with non-default mapSize and bombRatio
    public MapGen(int xInput, int yInput, int mapSize, int percentOfBombs) {
        Random rand = new Random();
        float bombRatio = percentOfBombs / 100f;
        this.mapSize = mapSize;
        this.totalBombs = (int) (Math.pow(this.mapSize, 2) * bombRatio);
        this.firstClickX = yInput;
        this.firstClickY = xInput;
        this.mineMap = new int[mapSize][mapSize];
        this.interactedMap = new boolean[mapSize][mapSize];
        this.tempFoundZeros = new boolean[mapSize][mapSize];
        this.testedZeros = new boolean[mapSize][mapSize];

        for(int i = 0; i < this.totalBombs; i++) { // Will assign 10 random coordinates with -1 to represent bombs
            int tempX = rand.nextInt(mapSize);
            int tempY = rand.nextInt(mapSize);
            if (validBombLoc(tempX, tempY)) { // Checks to see if it is a repeat coordinate and if true finds a different coordinate
                this.mineMap[tempX][tempY] = -1;
            }
            else {
                i--;
            }
        }
        bombProx();
    }

    // Used for determining the which image to change the clicked buttons to
    public int getMineMapVal(int x, int y) {
        return this.mineMap[y][x];
    }

    // Saves which tile the user has interacted with
    public void mapInteraction(int xInput, int yInput) {
        this.interactedMap[yInput][xInput] = true;
        // Checks for all adjacent zeros
        if (this.mineMap[yInput][xInput] == 0) {
            adjacentZeros(yInput, xInput);
            while (this.newZeros > 0) {
                for (int i = 0; i < this.mapSize; i++) {
                    for (int j = 0; j < this.mapSize; j++) {
                        if (tempFoundZeros[i][j] && !testedZeros[i][j]) {
                            adjacentZeros(i, j);
                            this.newZeros--;
                        }
                    }
                }
                // System.out.println("< " + this.newZeros + " >");
            }
        }
    }

    public boolean getMapInteraction(int xInput, int yInput) {
        return this.interactedMap[yInput][xInput];
    }

    // Tiles left
    public int getTilesLeft() {
        int totalTiles = (int) (Math.pow(this.mapSize, 2));
        int tilesTouched = 0;
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                if (this.interactedMap[i][j]) {
                    tilesTouched++;
                }
            }
        }
        return (totalTiles - this.totalBombs) - tilesTouched;
    }

    // Shows the current state of the map
    public String getCurrentMap() {
        StringBuilder output = new StringBuilder();
        output.append("    ");
        for (int i = 0; i < this.mapSize; i++) {
            char ascii = (char) (i + 65);
            output.append("\u001B[31m").append(ascii).append("  ");
        }
        output.append("\n");
        for (int i = 0; i < this.mapSize; i++) {
            if (i < 10) {
                output.append(" ").append("\u001B[31m").append(i).append("  ");
            }
            else {
                output.append("\u001B[31m").append(i).append("  ");
            }
            for (int j = 0; j < this.mapSize; j++) {
                if (this.interactedMap[i][j]) {
                    if (mineMap[i][j] == -1) {
                        output.append("\u001B[31m").append("X").append("  "); // Colors bombs black for backend testing
                    } else if (this.mineMap[i][j] == 1) {
                        output.append("\u001B[36m").append(this.mineMap[i][j]).append("  "); // Sets color to cyan
                    } else if (this.mineMap[i][j] == 2) {
                        output.append("\u001B[34m").append(this.mineMap[i][j]).append("  "); // Set color to green
                    } else if (this.mineMap[i][j] == 3) {
                        output.append("\u001B[33m").append(this.mineMap[i][j]).append("  "); // Sets color to yellow
                    } else if (this.mineMap[i][j] == 4) {
                        output.append("\u001B[35m").append(this.mineMap[i][j]).append("  "); // Sets color to purple
                    } else if (this.mineMap[i][j] >= 5) {
                        output.append("\u001B[31m").append(this.mineMap[i][j]).append("  "); // Sets color to red
                    } else {
                        output.append("\u001B[37m").append(this.mineMap[i][j]).append("  "); // Set color to white
                    }
                }
                else {
                    output.append("\u001B[32m").append("#").append("  ");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }

    // Purely for backend stuffs
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("    ");
        for (int i = 0; i < this.mineMap.length; i++) {
            char ascii = (char) (i + 65);
            output.append("\u001B[31m").append(ascii).append("  ");
        }
        output.append("\n");
        for (int i = 0; i < this.mineMap.length; i++) {
            if (i < 10) {
                output.append(" ").append("\u001B[31m").append(i).append("  ");
            }
            else {
                output.append("\u001B[31m").append(i).append("  ");
            }
            for (int j = 0; j < this.mineMap[i].length; j++) {
                if (mineMap[i][j] == -1) {
                    output.append("\u001B[31m").append("X").append("  "); // Colors bombs black for backend testing
                } else if (this.mineMap[i][j] == 1) {
                    output.append("\u001B[36m").append(this.mineMap[i][j]).append("  "); // Sets color to cyan
                } else if (this.mineMap[i][j] == 2) {
                    output.append("\u001B[32m").append(this.mineMap[i][j]).append("  "); // Set color to green
                } else if (this.mineMap[i][j] == 3) {
                    output.append("\u001B[33m").append(this.mineMap[i][j]).append("  "); // Sets color to yellow
                } else if (this.mineMap[i][j] == 4) {
                    output.append("\u001B[35m").append(this.mineMap[i][j]).append("  "); // Sets color to purple
                } else if (this.mineMap[i][j] >= 5) {
                    output.append("\u001B[31m").append(this.mineMap[i][j]).append("  "); // Sets color to red
                } else {
                    output.append("\u001B[37m").append(this.mineMap[i][j]).append("  "); // Set color to white
                }
            }

            output.append("\n");
        }
        return output.toString();
    }

    public void debugMap() {
        Scanner scnr = new Scanner(System.in);
        String debugMenu = """
                \u001B[33m< Debug Menu >
                \u001B[33m1: Revealed Map
                \u001B[33m2: Tiles Left
                \u001B[33m3: Amount of Bombs
                """;
        System.out.print(debugMenu);

        boolean inputPassed = false;

        while (!inputPassed) {
            int input = scnr.nextInt();
            try {
                if (input > 3 || input < 1) {
                    System.out.println("\u001B[31m + < Invalid Input >");
                }
                else {
                    switch (input) {
                        case 1 -> System.out.println(this);
                        case 2 -> System.out.println("\u001B[33m" + "< " + getTilesLeft() + " Tiles Left >");
                        case 3 -> System.out.println("\u001B[33m" + "< " + this.totalBombs + " Total Bombs >");
                    }
                    inputPassed = true;
                }
            } catch (Exception e) {
                System.out.println("\u001B[31m + < Invalid Input >");
            }
        }
    }
}
