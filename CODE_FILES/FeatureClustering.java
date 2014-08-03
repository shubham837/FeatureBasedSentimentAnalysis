package review.nlp.com;

import java.util.HashMap;

public class FeatureClustering 
{

	public static void clustering(HashMap<String, Cluster> positiveFrequentFeature,HashMap<String, Cluster> negativeFrequentFeature,FeatureOpinionPolarity featureOpinionPolarity)
	{
		
	
		for(int i=0;featureOpinionPolarity.feature[i] != null;i++)
		{
			Cluster cluster = new Cluster();
			if(featureOpinionPolarity.polarity[i] == 1)
			{
				if(positiveFrequentFeature.containsKey(featureOpinionPolarity.feature[i]))
				{
					cluster = positiveFrequentFeature.get(featureOpinionPolarity.feature[i]);
					cluster.count++;
					cluster.sentenceNumber.add(featureOpinionPolarity.SentenceNum[i]);
					positiveFrequentFeature.put(featureOpinionPolarity.feature[i], cluster);
				}
				else
				{
					cluster.count = 1;
					cluster.sentenceNumber.add(featureOpinionPolarity.SentenceNum[i]);
					cluster.feature = featureOpinionPolarity.feature[i];
					positiveFrequentFeature.put(featureOpinionPolarity.feature[i], cluster);
				}
			}
			else if(featureOpinionPolarity.polarity[i] == -1)
			{
				if(negativeFrequentFeature.containsKey(featureOpinionPolarity.feature[i]))
				{
					cluster = negativeFrequentFeature.get(featureOpinionPolarity.feature[i]);
					cluster.count--;
					cluster.sentenceNumber.add(featureOpinionPolarity.SentenceNum[i]);
					negativeFrequentFeature.put(featureOpinionPolarity.feature[i], cluster);
				}
				else
				{
					cluster.count = -1;
					cluster.sentenceNumber.add(featureOpinionPolarity.SentenceNum[i]);
					cluster.feature = featureOpinionPolarity.feature[i];
					negativeFrequentFeature.put(featureOpinionPolarity.feature[i], cluster);
				}
			}
		}
	}
}
