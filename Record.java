/* [Prepared by the instructor]
 * Record class
This class defines a record which is specifically for storing in
a Java random access file. See API of RandomAccessFile class in Java.

Attributes:
	name - String, the max length is 16 chars
	id - String, the number of digits in id is 4.
 *
*/

import java.util.*;
import java.io.*;

public class Record
{
	private String name;
	private String id;
	private int NAMELENGTH=16;
	private int IDLENGTH=4;
	

	public Record()
	{
		id = " ";
		name = " ";
	}

	public Record(String newId, String  newName)
	{
		id = newId;
		name = newName;
	}

	//Read a 'name' and the related 'id' from the current position of
	//the given file to the instance variables 'name' and 'id'
	public void read(RandomAccessFile file) throws IOException
	{
		id = readString(file, IDLENGTH);
		name = readString(file, NAMELENGTH);
	}

	//Write the value of 'this' object to the given file
	public void write(RandomAccessFile file) throws IOException
	{
		writeStr(file, id, IDLENGTH);
		writeStr(file, name, NAMELENGTH);
	}

	public boolean isEmpty()
	{
		if( id == "     ")
			return true;
		return false;
	}
	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	//Help method: read a string with length of strLength from the file
	//             Return the string to the caller
	private String readString (RandomAccessFile file, int strLength) throws IOException
	{
		char[] chs = new char[strLength];

		for (int i = 0; i < chs.length; i++)
		{
			chs[i] = file.readChar();
		}

		String theStr = new String(chs);

		return theStr;
	}

	//Help method: write a string with length of strLength to the file
	private void writeStr( RandomAccessFile file, String str, int strLength ) throws IOException
	{
		StringBuffer buffer = null;

		if ( str != null )
			buffer = new StringBuffer( str );
		else
			buffer = new StringBuffer( strLength );

		buffer.setLength( strLength );
		file.writeChars( buffer.toString() );

	} // end method writeName

	public String toString()
	{
		return "\nID: " + id + "\nName: " + name;
	}
}
