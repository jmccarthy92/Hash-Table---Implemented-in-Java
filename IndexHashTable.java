/*
CS-3810	Data Structures and Algorithms
Assignment #4   12.7.16
James McCarthy
This is the index hash table applied in the HashInterface application
I have a couple integer variables to take in the number of blocks and number of records
entered by the user, which is than used for the hashing in the insert, delete, and find operations.

I have two arrays one for the hash table, and one to take in the number of records in each block
as we carry out the operations. I also include a method so the application can retrieve the values from the array in a string.

I use a method fill hash table in the constructor to insure that the hash table array has random values
distributed, and that no value distributed in the hash table is a duplicate.

*/

import java.util.*;
import java.io.*;
import java.io.RandomAccessFile;

public class IndexHashTable
{
	private int[] hashTbl;
	private int [] blockCounter;
	private int blockLength;
	private int blockRecs;
	private int numberOfBlocks;
	private final Record DELETED = new Record("-1","DELETED");

	public IndexHashTable(int numBlocks, int numRecords)
	{
		hashTbl = new int[numBlocks];
		fillHashTable();
		blockRecs = numRecords;
		numberOfBlocks = numBlocks;
		blockLength = 40 * blockRecs * 2;
		blockCounter = new int [hashTbl.length];
	}

	public int hashFunction( int key)
	{
		return key % hashTbl.length;
	}

	public int insert(RandomAccessFile file, Record rec)
	{
		int key = Integer.parseInt(rec.getId());
		int hashVal = hashFunction(key);
		int blockInd = hashTbl[hashVal];
		int fileSeek = blockInd * blockLength;
		Record readFile = new Record();
			try
			{
				file.seek(fileSeek);
				readFile.read(file);
				for( int j = 0; j < blockRecs *2 ; j++)
				{
					if( blockCounter[blockInd] == blockRecs )
					{
						break;
					}

					if(readFile.getId().trim().equals(rec.getId()))
					{
						return 1;
					}
					if( "".equals(readFile.getId().trim()) || DELETED.getId().trim().equals(readFile.getId().trim()) )
					{
						file.seek(file.getFilePointer() - 40);
						rec.write(file);
						blockCounter[blockInd]++;
						return 0;
					}
					readFile.read(file);
				}
			}
			catch(IOException ioe)
			{
				try
				{
					rec.write(file);
					blockCounter[blockInd]++;
					return 0;
				}
				catch(IOException ioee)	{}
			}
			return -1;
	}

	public Record delete(RandomAccessFile file, String id)
	{
		int key = Integer.parseInt(id);
		int hashVal = hashFunction(key);
		int blockInd = hashTbl[hashVal];
		Record temp = null;
		try
		{
			Record readFile = new Record();
			file.seek(blockInd * blockLength);
			readFile.read(file);
			for( int j = 0 ; j < blockRecs * 2; j++)
			{

				if( readFile.getId().equals(id))
				{
					temp = readFile;
					file.seek(file.getFilePointer() - 40);
					DELETED.write(file);
					blockCounter[blockInd]--;
					return temp;
				}
				readFile.read(file);
			}

		}
		catch(IOException ioe){}
		return null;
	}

	public Record find(RandomAccessFile file, String id)
	{
		int key = Integer.parseInt(id);
		int hashVal = hashFunction(key);
		int blockInd = hashTbl[hashVal];
		try
		{
			Record readFile = new Record();
			file.seek(blockInd * blockLength);
			readFile.read(file);
			for ( int j = 0; j < blockRecs * 2 ; j++ )
			{
				if( readFile.getId().equals(id))
				{
					return readFile;
				}
				readFile.read(file);
			}
		}
		catch(IOException ioe){}
		return null;
	}

	public void fillHashTable()
	{
		Random rand = new Random();
		int randNumb = 0;
		boolean notUnique;

		for(int i=0; i < hashTbl.length - 1; i++)
		{
			notUnique = true;
			while(notUnique == true)
			{
				notUnique = false;
				randNumb = rand.nextInt(hashTbl.length);

				for( int j = 0; j<i; j++)
				{
					if(hashTbl[j] == randNumb)
						notUnique = true;
				}
			}
		hashTbl[i] = randNumb;
		}
	}
	public int[] getBlockCounter()
	{
		return blockCounter;
	}

	public String getBlockRecsAmount()
	{
		String returner = "";
		for(int  j = 0 ; j < blockCounter.length ; j++)
		{
			returner += "Block"+j+ " : " + blockCounter[j] + "\n";
		}
		return returner;
	}

}
