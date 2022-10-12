/**
 * Author: Kenzie Leckenby
 * Date: Oct 4, 2022
 * Description
 * Multiple methods that analyze a string input
 */

package Assignments;

import java.util.Scanner;

public class StringAnalyze {
    public static String commonLetters(String input1, String input2) { // Input: two different strings, Output: characters that are common between the two
        String outputStr = "";
        input1 = input1.toLowerCase();
        input2 = input2.toLowerCase();
        for (int i = 0; i < input1.length(); i++) {
            for (int o = 0; o < input2.length(); o++) {
                if (input1.charAt(i) == input2.charAt(o) && outputStr.indexOf(input2.charAt(o)) == -1) {
                    outputStr += input1.charAt(i);
                }
            }
        }
        if (outputStr.length() == 0) {
            outputStr = "<none>";
        }
        return outputStr;
    }

    public static String swapHere(String input, int displacement) { // Input: one string and one integer, Output: the input string modified by the inputed integer
        if (displacement <= 0 || displacement > input.length()){
            return input;
        }
        return (input.substring(displacement - 1, input.length()) + input.substring(0, displacement - 1));
    }

    public static Integer letterSum(String input) { // Input: one string, Output: the sum of the letters where a is 1 and z is 24
        int sum = 0;
        input = input.toLowerCase();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= 'a' && input.charAt(i) <= 'z') {
                sum += input.charAt(i)-96;
            }
        }
        return sum;
    }
    public static boolean isValidPassword(String input) { // Input: one string, Output: boolean value based off of if input is valid
        int amountOfNum = 0;
        int amountOfCapital = 0;
        int amountOfLower = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                amountOfNum++;
            }
            else if (input.charAt(i) >= 'a' && input.charAt(i) <= 'z') {
                amountOfLower++;
            }
            else if (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z') {
                amountOfCapital++;
            }
            else {
                return false;
            }
        }
        return amountOfNum >= 2 && amountOfCapital >= 2 && amountOfLower >= 2 && input.length() >= 8;
    }
    public static void main(String[] args)
    {
        System.out.println(commonLetters("", "Apple"));
        System.out.println(swapHere("123456789", 5));
        System.out.println(letterSum("Dog !!?@"));
        System.out.println(isValidPassword("pword1"));

        /*Scanner scnr = new Scanner(System.in);

        System.out.println("What is your first input for 'commonLetters': ");
        String commonLettersInput1 = scnr.nextLine();
        System.out.println("What is your second input for 'commonLetters': ");
        String commonLettersInput2 = scnr.nextLine();
        System.out.println(commonLetters(commonLettersInput1, commonLettersInput2));

        System.out.println("What is the word you would like to modify for 'swapHere': ");
        String swapHereWord = scnr.nextLine();
        System.out.println("By how many letters would you like to modify the previous input for 'swapHere': ");
        int swapHereModifier = scnr.nextInt();
        System.out.println(swapHere(swapHereWord, swapHereModifier));

        System.out.println("What is your input for 'letterSum': ");
        String letterSumInput = scnr.nextLine();
        System.out.println(letterSum(letterSumInput));

        System.out.println("What is your input for 'isValidPassword': ");
        String isValidPasswordInput = scnr.nextLine();
        System.out.println(isValidPassword(isValidPasswordInput));
        */
    }
}
