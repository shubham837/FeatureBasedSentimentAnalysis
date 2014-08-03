package review.nlp.com;

import java.util.HashMap;

public class FeatureOpinion
{
	
	private static final int INT_MAX = 100;

	public static void featureOpinion(Sentences sentence[], HashMap<String,String> feature, FeatureOpinionPolarity featureOpinionPolarity)
	{
		int i=0;
		int c=0;
		String noun = null;
		while(sentence[i] != null)
		{
			int j=0;
			while(sentence[i].adjective[j] != null)
			{
				int k=0;
				int l=0;
				int min = INT_MAX; // Maximum integer value
				int flag = 0;
				int found = 0;
				for(;sentence[i].nouns[k] !=null;k++)
				{
					if(feature.containsKey(sentence[i].nouns[k]))
					{
						flag = 1;
						int m = Math.abs(sentence[i].posAdjective[j]-sentence[i].posNoun[k]);
						if(m < min)
						{
							min = m;
							noun = sentence[i].nouns[k];
						}
					}
				}
				for(;sentence[i].negation[l] !=null;l++)
				{
					int m = Math.abs(sentence[i].posAdjective[j]-sentence[i].posNegation[l]);
					if(m <=  3)
					{
						found = 1;
					}
				}
				if(flag==1)
				{
					featureOpinionPolarity.feature[c] = noun;
					featureOpinionPolarity.opinion[c] = sentence[i].adjective[j];
					featureOpinionPolarity.SentenceNum[c] = i;
					if(found == 1)
					{
						featureOpinionPolarity.polarity[c] = -1;
					}
					else
					{
						featureOpinionPolarity.polarity[c] = 0;
					}
					//System.out.println(featureOpinionPolarity.feature[c]+"/"+featureOpinionPolarity.opinion[c]+"---"+featureOpinionPolarity.SentenceNum[c]+"polarity :"+featureOpinionPolarity.polarity[c]);
					c++;
				}
				j++;
			}
			i++;
		}
	}

}
