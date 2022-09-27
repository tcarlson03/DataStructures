/*

  Author:Taylor Carlson
  Email:tcarlson2021@my.fit.edu
  Course:CSE 2010
  Section: Section 3
  Description of this file:To find multi-word palindromes within a given list

 */
 
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
// Used for the collections.sort()
import java.util.*;

public class HW2
{
   // Combines the words together to see if they make a palindrome
   public static boolean pair (ArrayList<String> words, ArrayList<String> current, int palLength) {
      // Copies the arrayList of words so changes can be made
      ArrayList<String> arr = new ArrayList<String>(words);
      // Runs through each word and adds word to make a palindrome
      for (String word : words) {
         arr.remove(word);
         current.add(word);
         // Sees if the amount of words is equal to the input of the user
         if (palLength == 1) {
            String line = "";
            // Adds words in the arrayList to a string
            for (int i = 0; i < current.size(); i++) {
               line = line + current.get(i) + " ";
            }
            // Calls the Palindrome method to check if the string is a palindrome
            if (Palindrome(line)) {
               System.out.println(line);
            }
         }
         // Calls this method again if palLength is not correct
         else {
            pair(arr, current , palLength -1);
         }
         arr.add(word);
         // Sorts the arrayList arr so the output prints alphabetically
         Collections.sort(arr);
         current.remove(word);
      }
      return false;
   }
   
   public static boolean Palindrome (String word) {
   // Checks to see if the string is a palindrome
   int leftSide = 0;
   int rightSide = word.length() - 1;
   boolean answer = false;
   while (true) {
      // Ignores spaces in the string
      while (!Character.isLetter(word.charAt(leftSide))) {
         leftSide++;
      }
      while(!Character.isLetter(word.charAt(rightSide))) {
         rightSide--;
      }
      if (leftSide > rightSide) {
         return true;
      }
      // Assigns the character at the front and the end of the string to char
      char x = Character.toLowerCase(word.charAt(leftSide));
      char y = Character.toLowerCase(word.charAt(rightSide));
      // Determines if the characters are equal to each other
      if (x != y) {
         return answer;
      }
      // Moves on to the next character
      leftSide++;
      rightSide--;
   }
   }
   
   public static void main(String[] args) throws FileNotFoundException {
   // Scans the file and number input
   Scanner scan = new Scanner(new File(args[0]));
   // Creates arrayLists to hold strings
   ArrayList<String> words = new ArrayList<String>();
   ArrayList<String> current = new ArrayList<String>();
   // User inputs a number for the length of the palindrome
   int palLength = Integer.parseInt(args[1]);
   // Puts the file in the arrayList words
   while (scan.hasNext()) {
      words.add(scan.next());
   }
   // Calls on the pair method to print all the possible palindromes
   pair(words, current, palLength);
   }
}
