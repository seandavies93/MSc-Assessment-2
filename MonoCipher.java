/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);

			//create a cipher array
			cipher=new char[26];
			int k=25;
			int match=0;
			int i=0;
			
			//generate the cipher array based on the keyword and the monocipher scheme
			while(i<SIZE)
			{
				if(i<keyword.length())//while the array index is within the bounds of the keyword populate the array with the correspond character in the keyword
				{
					cipher[i]=keyword.charAt(i);
					System.out.print(cipher[i]);
					i++;
				}
				else
				{
					for(int m=0; m<keyword.length(); m++)
					{
						if(keyword.charAt(m)==alphabet[k])
						{
							match++;
							break;
						}
					}
					if(match==0)
					{
						cipher[i]=alphabet[k];
						System.out.print(cipher[i]);
						i++;
					}
					k--;
				}

				match=0;
			}

		}
		// create first part of cipher from keyword
		// create remainder of cipher from the remaining characters of the alphabet
		// print cipher array for testing and tutors
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		//initialise the index to an impossible array value to indicate whether or not the character was found
		int index=(int)(ch-'A');

	    //check if the character was found in the alphabet and then return the corresponding encrypted character otherwise do nothing
	    if((index>=0)&&(index<26))
	    {
	    	return cipher[index];
	    }
	    else
	    {
	    	return ch;
	    }
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		//initialise the index to an impossible array value to indicate whether or not the character was found
		int index=-1;
		
		//find the index of the input character using a linear search
	    for(int i=0; i<SIZE ; i++)
	    {
	    	if(ch==cipher[i])
	    	{
	    		index=i;
	    		break;
	    	}
	    }
	    
	  //check if the character was found in the cipher alphabet and then return the corresponding decrypted character otherwise do nothing
	    if(index!=-1)
	    {
	    	return alphabet[index];
	    }
	    else
	    {
	    	return ch;
	    }
	}
}