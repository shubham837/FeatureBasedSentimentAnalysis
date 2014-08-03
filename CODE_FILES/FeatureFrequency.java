package review.nlp.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FeatureFrequency {
	
	public static void wordCount(Sentences Sent[], String filename) throws FileNotFoundException
	{
		float totalCount = 0;
		float wordCount = 0;
		File fl = new File("/home/chinnu/work/workspace/ReviewSystem/ReviewFiles/"+filename);
		int i=0;
			while(Sent[i] != null)
			{
				int j=0;
				while(Sent[i].nouns[j] != null)
				{
					Scanner s = new Scanner(fl);
					while (s.hasNext()) 
					{
						totalCount++;
						if (s.next().equals(Sent[i].nouns[j]))
						{	
							wordCount++;
						}
					}
					j++;
					wordCount = 0;
					totalCount = 0;
				}
				i++;
			}
		
		
	}

}
