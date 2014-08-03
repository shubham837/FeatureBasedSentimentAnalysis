package review.nlp.com;

import java.util.HashMap;

public class FeatureOpinionPolarity 
{
	String feature[] = new String[3000];
	String opinion[] = new String[3000];
	int SentenceNum[] = new int[3000];
	int polarity[] = new int[3000];
	
	public void polarityDetection(HashMap<String, Integer> dictionary)
	{
		int i=0;
		while(opinion[i]!=null)
		{
			if(dictionary.containsKey(opinion[i]))
			{
				if(dictionary.get(opinion[i])==1)
				{
					if(polarity[i] == 0)
					{
						polarity[i] = dictionary.get(opinion[i]);
					}
					else
					{
						polarity[i] = -1;
					}
				}
				else
				{
					if(polarity[i] == 0)
					{
						polarity[i] = dictionary.get(opinion[i]);
					}
					else
					{
						polarity[i] = 1;
					}
				}
			}
			//System.out.println("feature/Opinion : polarity ="+feature[i]+"/"+opinion[i]+" : "+polarity[i]+" SentenceNum "+SentenceNum[i] );
			i++;
		}
	}
}
