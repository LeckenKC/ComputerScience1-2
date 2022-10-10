import java.util.Scanner;

public class NumeralStaticMethods {

    public static String showDivisors (int input)
    {
        String rawOutput = "";
        String finalOutput = "";
        int lastPos = 0;
        for (int i = 1; i < input; i++) // Finds the divisors using modulo
        {
            if ((input % i) == 0)
            {
                rawOutput = rawOutput + i + " ";
            }
        }
        for (int i = 0; i < rawOutput.length(); i++) // I have no clue if you were actually serious about the commas and the period being the output, but just in case :)
        {
            if ((rawOutput.charAt(i) == ' ') && (i < rawOutput.length() - 1)) // Adds a comma for all but the last number
            {
                finalOutput += rawOutput.substring(lastPos, i) + ",";
                lastPos = i;
            }
            if ((rawOutput.charAt(i) == ' ') && (i == rawOutput.length() - 1)) // Adds the period for the last number
            {
                finalOutput += rawOutput.substring(lastPos, i) + ".";
                lastPos = i;
            }
        }
        return finalOutput;
    }

    public static String isPrime (int input)
    {
        String output = "";
        int absInput = Math.abs(input);
        for (int i = 2; i < 10; i++)
        {
            if ((absInput % i) == 0 && absInput != i) // If the input is divisible by anything other than one and the input the code prints "'input' is not prime"
            {
                output = input + " is not prime";
                break;
            }
            else
            {
                output = input + " is a prime number";
            }
        }
        return output;
    }

    public static String factorial (int input)
    {
        String output = "";
        int factorialResult = 1;
        output += input + "! = ";
        if (input == 0)
        {
            output += "1.";
        }
        else if (input == 1)
        {
            output += "1.";
        }
        else
        {
            output += input;
            factorialResult *= input;
            for (int i = input - 1; i > 0; i--)
            {
                output += " x " + i;
                factorialResult *= i;
            }
            output += " = " + factorialResult + ".";
        }
        return output;
    }

    public static void contains (int inputNum, int parseNum)
    {
        int amountOfZeros = 0;
        String inputNumString = "" + inputNum;
        String parseNumString = "" + parseNum;
        for (int i = inputNumString.length() - 1; i >= 0; i--)
        {
            if (inputNumString.charAt(i) == parseNumString.charAt(0)) // I use a very quirky way of converting an int to a char, at what point do I just ask for a character value >.<
            {
                System.out.print("1");
                for (int x = amountOfZeros; x > 0; x--)
                {
                    System.out.print("0");
                }
                System.out.println("");
            }
            amountOfZeros++; // I use this to calculate the amount of zeros that would belong at each occurrence of the parse number
        }
    }

    public static void main (String[] args)
    {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Type an integer input for 'showDivisors': ");
        int inputShowDivisors = scnr.nextInt();
        System.out.println(showDivisors(inputShowDivisors));

        System.out.println("Type an integer input for 'isPrime': ");
        int inputIsPrime = scnr.nextInt();
        System.out.println(isPrime(inputIsPrime));

        System.out.println("Type an integer input for 'factorial': ");
        int inputFactorial = scnr.nextInt();
        System.out.println(factorial(inputFactorial));

        System.out.println("Type an integer to parse through in 'contains': ");
        int inputContainsNum = scnr.nextInt();
        System.out.println("Type an integer to search for in 'contains': ");
        int inputContainsParse = scnr.nextInt();
        contains(inputContainsNum, inputContainsParse);
    }
}
