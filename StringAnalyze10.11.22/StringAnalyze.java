/**
 * Author: Kenzie Leckenby
 * Date: Oct 4, 2022
 * Description
 *
 *
 *
 *
 *
 */

package CoolSwagPrograms;

import java.util.Scanner;

public class StringAnalyze {
	public static String commonLetters(String input1, String input2) // Input: two different strings, Output: characters that are common between the two
	{
		String outputStr = "";
		input1 = input1.toLowerCase();
		input2 = input2.toLowerCase();
		for (int i = 0; i < input1.length(); i++)
		{
			for (int o = 0; o < input2.length(); o++)
			{
				if (input1.charAt(i) == input2.charAt(o) && outputStr.indexOf(input2.charAt(o)) == -1)
				{
					outputStr += input1.charAt(i);
				}
			}
		}
		return outputStr;
	}
	
	public static String swapHere(String input, int displacement) // Input: one string and one integer, Output: the inputed string modified by the inputed integer
	{
		String outputStr = "";
		for (int i = 0; i < input.length()-(displacement-1); i++)
		{
			outputStr += input.charAt(i);
		}
		for (int o = input.length()-1; o >= displacement-1; o--)
		{
			outputStr = input.charAt(o) + outputStr;
		}
		return outputStr;
	}
	
	public static Integer letterSum(String input)
	{
		int sum = 0;
		input = input.toLowerCase();
		for (int i = 0; i < input.length(); i++)
		{
			if (input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
			{
				sum += input.charAt(i)-96;
			}
		}
		return sum;
	}
	public static boolean isValidPassword(String input)
	{
		int amountOfNum = 0;
		int amountOfCapital = 0;
		int amountOfLower = 0;
		
		for (int i = 0; i < input.length(); i++)
		{
			if (input.charAt(i) >= '0' && input.charAt(i) <= '9')
			{
				amountOfNum++;
			}
			else if (input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
			{
				amountOfLower++;
			}
			else if (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z')
			{
				amountOfCapital++;
			}
			else
			{
				return false;
			}
		}
		if (amountOfNum >= 2 && amountOfCapital >= 2 && amountOfLower >= 2 && input.length() >= 8)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static void main(String[] args)
	{
		System.out.println(commonLetters("Apple", "Apple"));
		System.out.println(swapHere("Computer", 5));
		System.out.println(letterSum("dog!!"));
		System.out.println(isValidPassword("work1NGpassw0rd"));
	}
}
