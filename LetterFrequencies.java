/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	private int maxindex;
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
	    alphaCounts=new int[SIZE];
		for (int i = 0; i < SIZE; i++)
			alphaCounts[i] = 0;
		
	    alphabet=new char[SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		//calculate the index of the character within the alphabet array
		int index=(int)(ch-'A');
		
		//check that the character is an uppercase letter and if it is then increment the appropriate element of the  count array
		if(!((index<0)||(index>25)))
		{
	    	totChars++;
	    	alphaCounts[index]++;
		}
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
    {
		int max=0;//set the max to zero intially
		
		for(int i=0; i<SIZE; i++)
		{
			if(alphaCounts[i]>=max)//using greater or equal than ensures that the variable max is updated even when a particular characters count is the same as the current maximum; as a result the maxindex will be the last in the alphabet with a character count equal to the max
			{
				max=alphaCounts[i];//if the current element of the array is greater than the current maximum then set the maximum to that value
				maxindex=i;
			}
		}
		
	    return max*100/(double)totChars;
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{	
		//the title and column names are set out at the beginning before the loop goes through and adds to the string called report
		//the string will then be printed to a file in the cipherGUI class
		String report="LETTER ANALYSIS\r\nLetter\tFreq\tFreq%\tAvgFreq%\tDiff\r\n";
		for (int j=0; j<SIZE; j++)
		{
			report+=String.format("%c\t%d\t%.2f\t%.2f\t\t%.2f%n",alphabet[j], alphaCounts[j], alphaCounts[j]*100/((double)totChars), avgCounts[j], alphaCounts[j]*100/((double)totChars)-avgCounts[j]);
		}
		
		double maxpc=getMaxPC();
		report+=String.format("The character with the largest percentage is %c with a percentage frequency of %.2f", alphabet[maxindex], maxpc);
	    return report;  // replace with your code
	}
}