import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done

	private String keyword;
	private String messagefilename;
	boolean encodeordecode;
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
	    if (e.getSource().equals(monoButton))//response in the case that the monocipher button is pressed
	    {
	    	if(!(getKeyword()&&processFileName()&&processFile(false)&&checkDuplicateChar()))//process the file and get the keyword and also validate the data checking that the keyword does not  have duplicate characters
	    	{
	    		JOptionPane.showMessageDialog(this, "Please enter a valid keyword or a valid file name. A valid keyord has only capital letters and no duplicate characters");
	    	}
	    }
	    else if(e.getSource().equals(vigenereButton))//response in the case that the Vigenere cipher is pressed
	    {
	    	if(!(getKeyword()&&processFileName()&&processFile(true)))//process the file and get the keyword and also validate the data
	    	{
	    		JOptionPane.showMessageDialog(this, "Please enter a valid keyword or a valid file name. A valid keyord has only capital letters");
	    	}
	    }
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
    	keyword=keyField.getText();
    	int index=0;
    	//check for that the keyword is all uppercase
    	for (int i=0; i<keyword.length(); i++)
    	{
    		index=(int)((keyword.charAt(i))-'A');
    		if((index<0)||(index>25))
    		{
    			return false;
    		}
    	}
    	
    	//check that there is a keyword
		if((keyword!=null)&&(keyword!=""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		messagefilename=messageField.getText();
		
		
		//check whether or not to perform an encryption or decryption based on the filename otherwise it informs the user that the filename does not follow the convention
		if((messagefilename.charAt(messagefilename.length()-1))=='P')
		{
			encodeordecode=true;
		}
		else if((messagefilename.charAt(messagefilename.length()-1))=='C')
		{
			encodeordecode=false;
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Please use the correct file name convention");
			return false;
		}
		
		//check if there is a message name
		if((messagefilename!=null)&&(messagefilename!=""))
		{
			return true;
		}
		else
		{
			return false;
		}
		

	}
	
	//method to check for duplicate characters in a keyword
	//returns true is there are no duplicate characters and false if there are
	//this is only a necessary check for the monocipher
	private boolean checkDuplicateChar()
	{
    	char[] alphabetcounts=new char[26];
    	
		//create alphabet
		alphabetcounts = new char [26];
		for (int i = 0; i < 26; i++)
			alphabetcounts[i] = 0;
		
		//count the each of the characters in keyword
		for (int j=0; j<keyword.length(); j++)
		{
			if(((int)(keyword.charAt(j)-'A')>=0)&&(((int)(keyword.charAt(j)-'A')<26)))
			{
				alphabetcounts[(int)(keyword.charAt(j)-'A')]++;
			}
		}
		
		//check for duplicate characters in the keyword
		
		boolean duplicatenotpresent=true;
		
		for (int j=0; j<26; j++)
		{
			if(alphabetcounts[j]>1)// if a character  occurs more than once in the keyword then we have a duplication that the monoalphabetic cipher cannot handle
			{
				duplicatenotpresent=false;
			}
		}
		return duplicatenotpresent;
	}
	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		
		FileReader reader;
		FileWriter writercipher;
		FileWriter writereport;
		try {
				
			writereport = new FileWriter(messagefilename.substring(0, messagefilename.length()-1)+"F.txt");

			if(vigenere)
			{
				//check if the user wants to encode or decode and create the appropriate file readers and writers
				if(encodeordecode)
				{
					reader=new FileReader(messagefilename.substring(0, messagefilename.length()-1)+"P.txt");
					writercipher=new FileWriter(messagefilename.substring(0, messagefilename.length()-1)+"C.txt");
				}
				else
				{
					reader=new FileReader(messagefilename.substring(0, messagefilename.length()-1)+"C.txt");
					writercipher=new FileWriter(messagefilename.substring(0, messagefilename.length()-1)+"D.txt");
				}
				
				
					//set up the vigenere cipher instance and the letterfrequencies instance
					VCipher vcipher=new VCipher(keyword);
					LetterFrequencies rep=new LetterFrequencies();
				
					char currentchar=' ';
					char currentcipherchar=' ';
				
					int c=' ';
					int i=0;
					int keywordletterindex=0;
				
					while(true)
					{

						
						c=reader.read();
						
						if(c==-1)
						{
							break;
						}
					
						//convert the character read in integer format into char format
						currentchar=(char) c;
					
						//integer to check if the character is an upper-case letter if not  then don't increment through the keyword
						int relativeASCII=(int)(currentchar-'A');
						if((relativeASCII>=0)&&(relativeASCII<26))
						{
							keywordletterindex=i%(keyword.length());
							i++;
						}
					
						//encode or decode the current character
						if(encodeordecode)
						{
							currentcipherchar=vcipher.encode(currentchar, keywordletterindex);
						}
						else
						{
							currentcipherchar=vcipher.decode(currentchar, keywordletterindex);
						}
					
						rep.addChar(currentcipherchar);
					
						//write to the cipher file or plaintext file
						writercipher.write(currentcipherchar);
					}
				
					//generate the report string
					String report=rep.getReport();
				
					//write the string to the file that was created
					writereport.write(report);
				
					//close the various readers and writers
					reader.close();
					writereport.close();
					writercipher.close();
					return true;
			}
			else
			{
					//check if the user wants to encode or decode and create the appropriate file readers and writers
					if(encodeordecode)
					{
						reader=new FileReader(messagefilename.substring(0, messagefilename.length()-1)+"P.txt");
						writercipher=new FileWriter(messagefilename.substring(0, messagefilename.length()-1)+"C.txt");
					}
					else
					{
						reader=new FileReader(messagefilename.substring(0, messagefilename.length()-1)+"C.txt");
						writercipher=new FileWriter(messagefilename.substring(0, messagefilename.length()-1)+"D.txt");
					}
				
					//set up the monocipher and the letterfrequencies instances
					MonoCipher mcipher=new MonoCipher(keyword);
					LetterFrequencies rep=new LetterFrequencies();
				
					//initialise character variables
					char currentchar=' ';
					char currentcipherchar=' ';
				
					int c=' ';
				
					while(true)
					{
						c=reader.read();
					
						if(c==-1)
						{
							break;
						}
					
						//convert the character read in integer format into char format
						currentchar=(char) c;
					
						//encode and then decode the current character
						if(encodeordecode)
						{
							currentcipherchar=mcipher.encode(currentchar);
						}
						else
						{
							currentcipherchar=mcipher.decode(currentchar);
						}
					
						//add to the appropriate character count 
						rep.addChar(currentcipherchar);

						//write to the cipher file or plaintext file
						writercipher.write(currentcipherchar);
					}
				
					//generate the report string
					String report=rep.getReport();
				
					//write the string to the file that was created
					writereport.write(report);
				
					//close the various readers and writers
					reader.close();
					writereport.close();
					writercipher.close();
					return true;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
	}
