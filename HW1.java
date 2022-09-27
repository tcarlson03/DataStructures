/*

  Author:                     Taylor Carlson
  Email:                      tcarlson2021@my.fit.edu
  Course:                     CSE 2010
  Section:                    Section 2
  Description of this file:   Allows people to chat with customer service represenatatives

 */
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;

public class HW1 {
   static class Node {
      // Creates an integer and a given next node
      int storage;
      Node next;
      
      //Stores int in a variable and creates an empty variable
      public Node(int storage) {
         this.storage = storage;
         this.next = null;
      }
   }
   
   // Creates two empty Nodes
   static Node head = null;
   static Node tail = null;
   
   // Searches for the largest number within a Node
   static int largest (Node head) {
      int maxNumber = Integer.MIN_VALUE;
      while (head != null) {
         if (maxNumber < head.storage) {
            maxNumber = head.storage;
         }
         else {
            head = head.next;
         }
      }
      return maxNumber;
   }
   
   // Adds number to a Node
   public void add (int data) {
      Node newN = new Node(data);
      if (head == null) {
         head = newN;
         tail = newN;
      }
      else {
         tail.next = newN;
         tail = newN;
      }
   }
   
   // Removes a certain number from the Node
   
   public static void main (String[] args) throws FileNotFoundException {

	/* description of variables */
   SinglyLinkedList <String> reps = new SinglyLinkedList <String>();
   reps.addFirst("Alice");
   reps.addLast("Bob");
   reps.addLast("Carol");
   reps.addLast("David");
   reps.addLast("Emily"); 
   // Creates list of represenatatives that is then filled with the names of the customer service workers
   
   SinglyLinkedList <String> customersOnHold = new SinglyLinkedList <String>();              // To hold the customers that decided to wait for a represenatative
   SinglyLinkedList <String> repsAssigned = new SinglyLinkedList <String>();                 // To hold the represenatatives that were assigned to a person
   SinglyLinkedList <String> lines = new SinglyLinkedList <String>();                        // Whole file is put into a linked list to compile
   
   // Reads the file input to run the program
   Scanner scan = new Scanner(new File(args[0]));
   
   // Reads the linked list that has the whole file input
   while (scan.hasNext()) {
      lines.addLast(scan.next());
   }
   // Extra arrays needed 
   ArrayList<Integer> startHold = new ArrayList<Integer>();          // To hold the start time of each customer on hold
   ArrayList<Integer> endHold = new ArrayList<Integer>();            // To hold the end time of each customer on hold
   ArrayList<Integer> holdLength = new ArrayList<Integer>();      
   ArrayList<String> hold = new ArrayList<String>();
   
   // Runs through the program until there is no more input in the linked list
   while (lines.size() > 0) {
      String command = lines.first();
      lines.removeFirst();
      // If a person commands a ChatRequest, the time, name, and decision to wait or come later is taken in
      if (command.equalsIgnoreCase("ChatRequest")) {
         System.out.print("ChatRequest ");
         int requestTime = Integer.parseInt(lines.first());
         System.out.print(requestTime + " ");
         lines.removeFirst();
         String customer = lines.first();
         System.out.print(customer + " ");
         lines.removeFirst();
         String waitOrlater = lines.first();
         // If a person decided to wait and there are available represenatatives, then the customer is assigned one
         if (waitOrlater.equals("wait")) {
            if (reps.size() > 0) {
               String rep = reps.first();
               repsAssigned.addLast(rep);
               reps.removeFirst();
               int assignmentTime = requestTime;
               System.out.println(waitOrlater);
               System.out.println("RepAssignment " + customer + " " + rep + " " + assignmentTime);          // Gives the output of the repAssignment line
               lines.removeFirst();
            }
            // If a person decided to wait and there are no available reps, then the customer is put on hold
            else {
               customersOnHold.addLast(customer);
               startHold.add(requestTime);
               hold.add(customer);
               System.out.println(waitOrlater);                                                             
               lines.removeFirst();
               System.out.println("PutOnHold " + customer + " " + requestTime);                             // Gives the output of the PutOnHold line
            }
         }
         // If the person decided to try again later, but there is a rep available, then the customer is assigned
         else {
            if (reps.size() > 0) {
               String rep = reps.first();
               reps.removeFirst();
               int assignmentTime = requestTime;
               System.out.println(waitOrlater);      
               System.out.println("RepAssignment " + customer + " " + rep + " " + assignmentTime);          // Gives the output of the repAssignment line
               lines.removeFirst();
            }
            // If the customer chose to call later, but there is no represenatative available, the customer is to try later
            else {
               System.out.println(waitOrlater);                                          
               lines.removeFirst();
               System.out.println("TryLater " + customer + " " + requestTime);                              // Gives the output of the TryLater line
            }
         }
      }
      // The available representatives are retrieved from the linked list and printed out 
      else if (command.equalsIgnoreCase("PrintAvailableRepList")) {
         System.out.print("AvailableRepList ");
         int requestTime = Integer.parseInt(lines.first());
         System.out.print(requestTime + " ");
         lines.removeFirst();
         System.out.println(reps.toString());
      }
      // If a customer quits on hold, then the requestTime is erased and the customer is erased from being on hold
      else if (command.equals("QuitOnHold")) {
         ArrayList<Integer> quit = new ArrayList<Integer>();
         int requestTime = Integer.parseInt(lines.first());
         quit.add(requestTime);
         System.out.print("QuitOnHold " + requestTime + " ");                                               // Gives output of the QuitOnHold line
         lines.removeFirst();
         String customer = lines.first();
         System.out.println(customer);
         lines.removeFirst();
         // Removes startTime of customer
         if (hold.size() <= 2) {
            int index = hold.indexOf(customer);
            startHold.remove(index);
         }
         else {
            int index = hold.indexOf(customer);
            startHold.remove(index + quit.size());
         }    
         // Removes customer
         hold.remove(customer);
      }
      // If a chat ends, then the first person in line on hold is picked up or the rep becomes available
      else if (command.equalsIgnoreCase("ChatEnded")) {
         String customer = lines.first();
         lines.removeFirst();
         String repAssigned = lines.first();
         lines.removeFirst();
         int callEnded = Integer.parseInt(lines.first());
         lines.removeFirst();
         System.out.println("ChatEnded " + customer + " " + repAssigned + " " + callEnded);
         // If customers are on hold, the represenatative is assigned
         if (hold.size() > 0) {
            String cust = hold.get(0);
            System.out.println("RepAssignment " + cust + " " + repAssigned + " " + callEnded);
            hold.remove(0);                      
            endHold.add(callEnded);
         }
         // If there are no customers on hold, the represenatative becomes available again
         else {
            reps.addLast(repAssigned);
         }  
      }
      // To find the max wait time is to take the time a customer is first put on hold to when they are assigned a represenatative
      else if(command.equalsIgnoreCase("PrintMaxWaitTime")) {      
         int time = Integer.parseInt(lines.first());
         lines.removeFirst();
         int greatest = 0;
         int x = endHold.size();
         // Runs through all of the lengths of time
         for (int i = 0; i < x; i++) {
            int start = startHold.get(0);
            int stop = endHold.get(0);
            holdLength.add(stop - start);
            startHold.remove(0);
            endHold.remove(0);
         }
         // Finds the greatest length of time
         for (int i = 0; i < holdLength.size(); i++) {     
            if (holdLength.get(0) < holdLength.get(i)) {
               greatest = holdLength.get(i);
               int less = holdLength.get(0);
               holdLength.add(i, less);
               holdLength.add(0, greatest);
            }
            // Prints if the first number is the greatest
            else {
               greatest = holdLength.get(0);
            }
         }
         System.out.println("PrintMaxWaitTime " + time + " " + greatest);                                        // Outputs the max wait time line
      }
   }
   }
}
