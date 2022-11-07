package Minesweeper;

import java.util.Scanner;

public class TextWindow {
    int firstClickX, firstClickY, currentClickX, currentClickY, mapSize, bombRatio;

    public TextWindow() {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Type a value for map size between 5 and 25, or leave empty: ");
        String inputMapSize = scnr.nextLine();
        System.out.println("Type a value for bomb ratio between 1 and 25, or leave empty: ");
        String inputBombRatio = scnr.nextLine();

        // Checks if the user would like to use a non-default or default value for mapSize
        try {
            if(Integer.parseInt(inputMapSize.strip()) > 25 || Integer.parseInt(inputMapSize.strip()) < 5) {
                this.mapSize = 10;
            }
            else {
                this.mapSize = Integer.parseInt(inputMapSize.strip());;
            }
        }
        catch (Exception e) {
            this.mapSize = 10;
        }

        // Checks if the user would like to use a non-default or default value for bombRatio
        try {
            if(Integer.parseInt(inputBombRatio.strip()) > 25 || Integer.parseInt(inputBombRatio.strip()) < 1) {
                this.bombRatio = 15;
            }
            else {
                if (this.mapSize < 10) {
                    this.bombRatio = 15;
                }
                else {
                    this.bombRatio = Integer.parseInt(inputBombRatio.strip());
                }
            }
        }
        catch (Exception e) {
            this.bombRatio = 15;
        }

        // Generates the initial map with no known squares
        System.out.println();
        System.out.println(initialMap());
        System.out.println("\u001B[0m" + "Which tile would you like to interact with, ex. 'A4' or 'b0': ");

        boolean initialInputCheck = false;
        while (!initialInputCheck) {
            String initialInput = scnr.nextLine();

            // Converts from char to int
            this.firstClickX = initialInput.toUpperCase().charAt(0) - 65;

            // Checks if the input initialInput is too short
            if (initialInput.length() < 2) {
                System.out.println("\u001B[31m" + "< Invalid Input >");
                System.out.println("\u001B[0m" + "Which tile would you like to interact with, ex. 'A4' or 'b0': ");
            }
            else {
                // Checks the length of the initialInput so that it can calculate the correct coordinate
                if (initialInput.length() < 3) {
                    this.firstClickY = initialInput.charAt(1) - 48;
                }
                else {
                    this.firstClickY = ((initialInput.charAt(1) - 48) * 10) + (initialInput.charAt(2) - 48);
                }

                // Checks to make sure the point it is trying to interact with is within the bounds of the play space
                if ((this.firstClickX < this.mapSize && this.firstClickX >= 0) && (this.firstClickY < this.mapSize && this.firstClickY >= 0)) {
                    initialInputCheck = true;
                }
                else {
                    System.out.println("\u001B[31m" + "< Invalid Input >");
                    System.out.println("\u001B[0m" + "Which tile would you like to interact with, ex. 'A4' or 'b0': ");
                }
            }
        }
        System.out.println();
        MapGen map = new MapGen(this.firstClickX, this.firstClickY, this.mapSize, this.bombRatio);
        map.mapInteraction(this.firstClickX, this.firstClickY);
        this.currentClickX = this.firstClickX;
        this.currentClickY = this.firstClickY;
        System.out.println(map.getCurrentMap());
        //System.out.println(map);

        // Allows you to select a coordinate for the current map as long as you did not select a bomb on your last turn
        while ((map.getMineMapVal(currentClickX, currentClickY) != -1) && map.getTilesLeft() != 0) {
            System.out.println("\u001B[0m" + "Which tile would you like to interact with, ex. 'A4' or 'b0': ");
            boolean inputCheck = false;

            // Runs while the input is invalid allowing the user to try again if they put in an invalid input
            while (!inputCheck) {
                String input = scnr.nextLine();
                // Changes the input for char to int so that it can be used with methods
                this.currentClickX = input.toUpperCase().charAt(0) - 65;

                // Checks if the input is too short
                if (input.length() < 2) {
                    System.out.println("\u001B[31m" + "< Invalid Input >");
                    System.out.println("\u001B[0m" + "Which tile would you like to interact with, ex. 'A4' or 'b0': ");
                }
                else {
                    // Checks the inputs length so that if the second coordinate is two digits it can account for it
                    if (input.length() < 3) {
                        this.currentClickY = input.charAt(1) - 48;
                    }
                    else {
                        this.currentClickY = ((input.charAt(1)-48) * 10) + (input.charAt(2) - 48);
                    }

                    // Checks if the user has interacted with the tile before or if it has already been revealed
                    if ((this.currentClickX < this.mapSize && this.currentClickX >= 0) && (this.currentClickY < this.mapSize && this.currentClickY >= 0)) {
                        if (!map.getMapInteraction(this.currentClickX, this.currentClickY)) {
                            inputCheck = true;
                        }
                        else {
                            System.out.println("\u001B[31m" + "< This tile has already been interacted with >");
                            System.out.println("\u001B[0m" + "Which tile would you like to interact with, ex. 'A4' or 'b0': ");
                        }
                    }
                    else {
                        System.out.println("\u001B[31m" + "< Invalid Input >");
                        System.out.println("\u001B[0m" + "Which tile would you like to interact with, ex. 'A4' or 'b0': ");
                    }
                }
            }
            // Applies the new input to the current map then displays the current map
            map.mapInteraction(this.currentClickX, this.currentClickY);
            System.out.println(map.getCurrentMap());
        }

        // Will display the corresponding message after the while loop has ended
        if (map.getTilesLeft() == 0) {
            System.out.println("\u001B[33m" + "< Wow you actually won! To be honest I fully expected you to explode! >");
            System.out.println();
            System.out.println("< Look at all those bombs, how did you avoid all that! >");
            System.out.println(map);
        }
        else if (map.getMineMapVal(this.currentClickX, this.currentClickY) == -1) {
            System.out.println("\u001B[33m" + "< You hit the jackpot! A bomb right at your feet! >");
            System.out.println();
            System.out.println("< Look at all those bombs, makes sense that you died! >");
            System.out.println(map);
        }
    }
    public String initialMap() {
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
                output.append("\u001B[32m").append("#").append("  ");
            }
            output.append("\n");
        }
        return output.toString();
    }
}
