/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	private String keyword;
        // more instance variables
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
	    this.keyword=keyword;
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch, int keywordletterindex)
	{
		//get the indices of the current keyword character and the current message character
		int keyindex=(int)(keyword.charAt(keywordletterindex)-'A');
		int messindex=(int)(ch-'A');
		char encoded=' ';
		
		//check that the characters corresponding to these indices are capital letters, if they are encode them otherwise do nothing
		if(((keyindex>=0)&&(keyindex<26))&&((messindex>=0)&&(messindex<26)))
		{
			encoded=alphabet[(keyindex+messindex)%26];
		}
		else
		{
			encoded=ch;
		}
	    return encoded;
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch, int keywordletterindex)
	{
		//get the indices of the current keyword character and the current message character
		int keyindex=(int)(keyword.charAt(keywordletterindex)-'A');
		int messindex=(int)(ch-'A');
		char decoded=' ';
		
		//check that the characters corresponding to these indices are capital letters, if they are encode them otherwise do nothing
		if(((keyindex>=0)&&(keyindex<26))&&((messindex>=0)&&(messindex<26)))
		{
			decoded=alphabet[(messindex-keyindex+26)%26];
		}
		else
		{
			decoded=ch;
		}
			
	    return decoded;
	}
}