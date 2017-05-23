
/*CS-3810	Data Structures and Algorithms
Assignment #4   12.7.16
James McCarthy
This is the interface application that puts the IndexHashTable to use.
The application class provides a convenient interface for the user to
insert, delete, search, or exit.

The insert will print a message to the user depending on what integer is returned by the insert method.

THe delete method will delete the record from the file(database) and return and print the record you have
deleted. If the record is not found the method will return null and the application will print a message accordingly.

The find method will look for the record amongst the block it has been hashed too if the block is empty or the record
was not found in the linear probe the method will return null and the application will print a message accordingly.
if the record is found the application will print the record (id and name) to the user.

Finally the exit choice will print how many records are in each block upon exit from the application.
*/

import java.io.*;
import java.util.*;

public class HashInterface
{
	@SuppressWarnings("resource")
	public static void main (String [] args)
	{
		Scanner scan = new Scanner (System.in);
		System.out.println("Please enter the size of the in memory hash table : ");
		String memSizeStr = scan.nextLine();
		int memSize = HashInterface.intCheck(memSizeStr);
		System.out.println("Please enter the number of records allowed in each block : ");
		String blockSizeStr = scan.nextLine();
		int blockSize = HashInterface.intCheck(blockSizeStr);
		System.out.println(" Size of the hash table will be : " + memSize
				           + "\tNumbe of records allowed in : " + blockSize); 
		IndexHashTable hashTbl = new IndexHashTable(memSize, blockSize);
		RandomAccessFile f = null;
		boolean powerOn = true;
	
		System.out.println("\nYou have enter the student department external hashing menu :"
						+  "\n***************************************************************" );

		String menuList = ("\n***************************************************"
						+  "\nChoose 1 to Insert a Student into the file"
						+  "\nChoose 2 to Delete a Student from the file by student ID"
						+  "\nChoose 3 to search the file by student ID"
						+  "\nChoose 4 to quit the program "
						+  "\n****************************************************");
		try
		{
			f = new RandomAccessFile("output.txt","rw");
			while(powerOn == true)
			{
			
				System.out.println(menuList);
				String choiceStr = scan.nextLine();
				int choiceInt = HashInterface.intCheck(choiceStr);
			
				switch(choiceInt)
				{
					case 1:
					{
						System.out.println("Enter Student information"
									  +"Enter Student ID from (0000 to 9999) : ");
						String IDstr = scan.nextLine();
						String id = HashInterface.intCheck2(IDstr);
						System.out.println("Enter Student name : " );
						String name = scan.nextLine();
						Record rec = new Record(id,name);
						int inserted = hashTbl.insert(f,rec);
						if( inserted == 0)
							System.out.println(" Record has been inserted into the database successfully !  ");
						else if( inserted == 1)
							System.out.println(" The record you entered had the same ID as another record in the database !");
						else if(inserted == -1)
							System.out.println(" The block is full ! ");
						
						break;
					}
				
					case 2:
					{
						System.out.println("Enter ID# of the Student you would like to delete from the file :");
						String id = HashInterface.intCheck2(scan.nextLine());
						Record removed = hashTbl.delete(f, id);
						if ( removed != null)
							System.out.println("Student \t" + removed.toString() + "\t was removed");
						else if (removed == null)
							System.out.println("Student with the ID# you input was not found");
						break;
					}
				
					case 3:
					{
						System.out.println("Enter ID# of the student you are searching for :");
						String id = HashInterface.intCheck2(scan.nextLine());
						Record found = hashTbl.find(f, id);
						if(found != null)
						{
							System.out.println("Student \t" + found.toString() + "\t was found ! ");
						}
						else if( found == null)
							System.out.println("The student with the ID# you input was not found!");
						break;
					}
					case 4:
					{
						System.out.println("Thank you for using the Student department external Hashing"
								       +"Displaying number of records in each block");
						System.out.println(hashTbl.getBlockRecsAmount());
						powerOn = false;
						f.close();
						break;
					}
					default:
					{
						System.out.println("Invalid entry ");
					}
				}

			}
		}
		catch(IOException e)
		{
			System.out.println("Cannot read \n" + e);
		}
	}
	

	@SuppressWarnings("resource")
	public static String intCheck2( String i) 
	{
		Scanner scan = new Scanner(System.in);
		
		int o;
		while(true)
		{
			try
			{
				o = Integer.parseInt(i);
				if( o > 9999 || o < 0000)
				{
					System.out.println("Please enter valid integers between (0000 and 9999)"); 
					i = scan.nextLine();
					continue;
				}
			
				if ( i.length() < 2)
					i = "000" + i;	
				if(i.length() < 3)
					i ="00" + i;
				if(i.length() < 4)
					i = "0" + i;
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.println( e.getMessage() + " There was  non-numeric letter(s) ");
				System.out.println(" PLEASE ENTER  INTEGERS!! :");
				i = scan.nextLine();
			}
			catch(Exception e)
			{
				System.out.print("Enter your next digits: ");
				i = scan.nextLine();
			}
		}
		return i;
	}
	
	@SuppressWarnings("resource")
	public static int intCheck( String i) 
	{
		Scanner scan = new Scanner(System.in);
		int o;
		while(true)
		{
			try
			{
				o = Integer.parseInt(i);
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.println( e.getMessage() + " There was  non-numeric letter(s) ");
				System.out.println(" PLEASE ENTER  INTEGERS!! :");
				i = scan.nextLine();
			}
			catch(Exception e)
			{
				System.out.print("Enter your next digits: ");
				i = scan.nextLine();
			}
		}
		return o;
	}

}
