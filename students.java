/* Name: Ellis Sentoso
*  Instructor : Dave Harden
*  Class: CS1 Java
*  Assignment: Lab 9
*  Description: Sorting a list of students' information by default, first name, and total points
*  Email: ellissentoso@gmail.com
*  Date: 08/03/2019
*/

import javax.naming.ldap.SortKey;
import javax.swing.*;

// import com.sun.tools.classfile.StackMapTable_attribute.stack_map_frame;

public class Main {

	public static void main(String[] args) {
		
		int sort = Student.SORT_BY_LAST;
      Student[] myClass = { new Student("smith","fred", 95,sort), 
               new Student("bauer","jack",123,sort),
               new Student("jacobs","carrie", 195,sort), 
               new Student("renquist","abe",148,sort),
               new Student("3ackson","trevor", 108,sort), 
               new Student("perry","fred",225,sort),
               new Student("loceff","fred", 44,sort), 
               new Student("stollings","pamela",452,sort),
               new Student("charters","rodney", 295,sort), 
               new Student("cassar","john",321,sort),
            };

      System.out.println("Before: " + StudentArrayUtilities.toString(myClass));
      System.out.println("Sorting by default ---------------");
      StudentArrayUtilities.arraySort(myClass);
      System.out.println("After: " + StudentArrayUtilities.toString(myClass));

      System.out.println("Sorting by first name ---------------");
      myClass[0].setSortKey(Student.SORT_BY_FIRST);
      StudentArrayUtilities.arraySort(myClass);
      System.out.println("After: " + StudentArrayUtilities.toString(myClass));

      System.out.println("Sorting by total points ---------------");
      myClass[0].setSortKey(Student.SORT_BY_POINTS);
      StudentArrayUtilities.arraySort(myClass);
      System.out.println("After: " + StudentArrayUtilities.toString(myClass));

      myClass[0].setSortKey(Student.SORT_BY_FIRST);

      System.out.println("Median of evenClass = " + StudentArrayUtilities.getMedianDestructive(myClass, myClass.length));

      if (myClass[0].getSortKey() == Student.SORT_BY_FIRST)
         System.out.println("Successfully preserved sort key.");
      else
         System.out.println("Failed to preserve sort key.");

      
      System.out.println("Median of oddClass = " + StudentArrayUtilities.getMedianDestructive(myClass, myClass.length - 1));
      
      
      System.out.println("Median of smallClass = " + StudentArrayUtilities.getMedianDestructive(myClass, 3));

      
      System.out.println("Median of noClass = " + StudentArrayUtilities.getMedianDestructive(myClass, 0));
	}

}

class Student
{
	// Declaring variables
   private String lastName;
   private String firstName;
   private int totalPoints;
   private static int sortKey;

   public static final String DEFAULT_NAME = "zz-error";
   public static final int DEFAULT_POINTS = 0;
   public static final int MAX_POINTS = 1000;
   public static final int SORT_BY_FIRST = 88;
   public static final int SORT_BY_LAST = 98;
   public static final int SORT_BY_POINTS = 108;

   // constructor requires parameters - no default supplied
   public Student( String last, String first, int points, int key)
   {
	   
	  
      if ( !setLastName(last) )
         lastName = DEFAULT_NAME;
      if ( !setFirstName(first) )
         firstName = DEFAULT_NAME;
      if ( !setPoints(points) )
         totalPoints = DEFAULT_POINTS;  
      if (!setSortKey(key)) {
    	  sortKey = DEFAULT_POINTS;
      }
   }

   public String getLastName() { return lastName; }
   public String getFirstName() { return firstName; } 
   public int getTotalPoints() { return totalPoints; }

   public boolean setLastName(String last)
   {
      if ( !validString(last) )
         return false;
      lastName = last;
      return true;
   }

   public boolean setFirstName(String first)
   {
      if ( !validString(first) )
         return false;
      firstName = first;
      return true;
   }

   public boolean setPoints(int pts)
   {
      if ( !validPoints(pts) )
         return false;
      totalPoints = pts;
      return true;
   }

   // could be an instance method and, if so, would take one parameter
   public static int compareTwoStudents( Student firstStud, Student secondStud )
   {
      int result = 0;

      if (firstStud.getSortKey() == SORT_BY_LAST)
         // this particular version based on last name only (case insensitive)
         result = firstStud.lastName.compareToIgnoreCase(secondStud.lastName);
      else if (firstStud.getSortKey() == SORT_BY_FIRST)
         // this particular version based on first name only (case insensitive)
         result = firstStud.firstName.compareToIgnoreCase(secondStud.firstName);
      else if (firstStud.getSortKey() == SORT_BY_POINTS)
         // this particular version based on points only
         result = firstStud.getTotalPoints() - secondStud.getTotalPoints();
      return result;
   }

   public String toString()
   {
      String resultString;

      resultString = " "+ lastName 
         + ", " + firstName
         + " points: " + totalPoints
         + "\n";
      return resultString;
   }

   private static boolean validString( String testStr )
   {
      if (
            testStr != null && testStr.length() > 0 
            && Character.isLetter(testStr.charAt(0))
         )
         return true;
      return false;
   }

   private static boolean validPoints( int testPoints )
   {
      if (testPoints >= 0 && testPoints <= MAX_POINTS)
         return true;
      return false;
   }
   
   public boolean setSortKey(int key)
   {

	   switch (key) {
		   case SORT_BY_FIRST:
		   case SORT_BY_LAST:
		   case SORT_BY_POINTS:
			   this.sortKey = key;
			   return true;
		   default: 
			   return false;


	   }
   } 
   public int getSortKey()
   {
      return sortKey;
   }
}

class StudentArrayUtilities
{

   public static String toString(Student[] data) {
      String output = "";

      // build the output string from the individual Students:
      for (int k = 0; k < data.length; k++)
         output += " " + data[k].toString();

      return output;
   }

   // returns true if a modification was made to the array
   private static boolean floatLargestToTop(Student[] data, int top)
   {
      boolean changed = false;
      Student temp;

      // compare with client call to see where the loop stops
      for (int k = 0; k < top; k++)
         if ( Student.compareTwoStudents(data[k], data[k + 1]) > 0 )
         {
            temp = data[k];
            data[k] = data[k + 1];
            data[k + 1] = temp;
            changed = true;
         }
      return changed;
   }

   // public callable arraySort() - assumes Student class has a compareTo()
   public static void arraySort(Student[] array)
   {
      for (int k = 0; k < array.length; k++)
         // compare with method def to see where inner loop stops
         if ( !floatLargestToTop(array, array.length - 1 - k) )
            return;
   }

   public static double getMedianDestructive(Student[] array, int top){
      double median = 0;
      // return 0 if length = 0
      if (top == 0)
         return median;

      // save key
      int sort_key = array[0].getSortKey();

      // change sortKey and sort it
      array[0].setSortKey(Student.SORT_BY_POINTS);

      arraySort(array);

      //Even-numbered arrays >= 2 elements:  find the two middle elements and return their average of their total points.
      //Odd-numbered arrays >= 3 elements:  return the total points of the exact middle element.
      if (top % 2 == 1)
         median = array[top / 2].getTotalPoints();
      else
         median = ( array[top / 2 - 1].getTotalPoints() + (double)array[top / 2].getTotalPoints() ) / 2;
      array[0].setSortKey(sort_key);
      return median;
   }
   
   



}


/*------------------------------------- Sample Run -------------------------------------------- 
Before:   smith, fred points: 95
  bauer, jack points: 123
  jacobs, carrie points: 195
  renquist, abe points: 148
  zz-error, trevor points: 108
  perry, fred points: 225
  loceff, fred points: 44
  stollings, pamela points: 452
  charters, rodney points: 295
  cassar, john points: 321

Sorting by default ---------------
After:   bauer, jack points: 123
  cassar, john points: 321
  charters, rodney points: 295
  jacobs, carrie points: 195
  loceff, fred points: 44
  perry, fred points: 225
  renquist, abe points: 148
  smith, fred points: 95
  stollings, pamela points: 452
  zz-error, trevor points: 108

Sorting by first name ---------------
After:   renquist, abe points: 148
  jacobs, carrie points: 195
  loceff, fred points: 44
  perry, fred points: 225
  smith, fred points: 95
  bauer, jack points: 123
  cassar, john points: 321
  stollings, pamela points: 452
  charters, rodney points: 295
  zz-error, trevor points: 108

Sorting by total points ---------------
After:   loceff, fred points: 44
  smith, fred points: 95
  zz-error, trevor points: 108
  bauer, jack points: 123
  renquist, abe points: 148
  jacobs, carrie points: 195
  perry, fred points: 225
  charters, rodney points: 295
  cassar, john points: 321
  stollings, pamela points: 452

Median of evenClass = 171.5
Successfully preserved sort key.
Median of oddClass = 148.0
Median of smallClass = 95.0
Median of noClass = 0.0

*/
