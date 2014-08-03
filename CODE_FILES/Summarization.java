package review.nlp.com;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class Summarization {

	public static void positiveFeatureSummary(HashMap<String, Cluster> positiveFrequentFeature, HashMap<String, Cluster> negativeFrequentFeature, HashMap<Integer, String> productSentence)
	{
		Collection<Cluster> clustr = positiveFrequentFeature.values();
		Cluster[] cluster = new Cluster[100];
		int k = 0;
		for(Iterator itr = clustr.iterator();itr.hasNext();)
		{
			cluster[k] = (Cluster) itr.next();
			k++;
		}
		for(int i=0;cluster[i] != null; i++) // Sorting in descending  is performed based on Positive Feature Review Frequency
		{
			int max = i;
			for(int j=i+1;cluster[j] !=null; j++)
			{
				if(cluster[j].count>cluster[max].count)
				{
					max = j;
				}
			}
			Cluster temp = cluster[i];
			cluster[i] = cluster[max];
			cluster[max] = temp;
		}
		System.out.println("");
		System.out.println(" __________________________________________________");
		System.out.println("|Top 5 Positive Features Reviews for Canon Camera :|");
		System.out.println("|__________________________________________________|");
		//System.out.println("Top 5 Positive Features for Canon Camera");
		float productRating;
		float totalRating =0;
		for(int i = 0; i < 10 ; i++)
		{
			float stars =0;
			float count = cluster[i].count;
			float total;
			if(negativeFrequentFeature.get(cluster[i].feature) != null)
			{
				
				total = count - negativeFrequentFeature.get(cluster[i].feature).count;
				
			}
			else
			{
				total = count;
			}
			stars = count/total*5;
			totalRating = totalRating + stars;
			
			/*	
			System.out.println("");
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println("Most Important Positive part(Excerpts) in Reviews for the Feature :"+cluster[i].feature+" : "+stars+" stars");
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println("");
			*/
			System.out.println(cluster[i].feature+"\t :"+stars+" Stars");
			
			//System.out.println(cluster[i].feature+" with a count "+cluster[i].count);
			int lineNo = 1;
			for(Iterator itr = cluster[i].sentenceNumber.iterator();itr.hasNext();)
			{
			
				int n = (Integer) itr.next();
				//System.out.println(lineNo+"  "+productSentence.get(n));
				//System.out.println("");
				lineNo++;
			}
		}
		productRating = totalRating/10;
		System.out.println("OverAll Rating of Cannon G3 PowerShot is : "+productRating+" Out of 5 Stars");
		
	}
	public static void negativeFeatureSummary(HashMap<String, Cluster> negativeFrequentFeature, HashMap<Integer, String> productSentence)
	{
		Collection<Cluster> clustr = negativeFrequentFeature.values();
		Cluster[] cluster = new Cluster[100];
		int k = 0;
		for(Iterator itr = clustr.iterator();itr.hasNext();)
		{
			cluster[k] = (Cluster) itr.next();
			k++;
		}
		for(int i=0;cluster[i] != null; i++)
		{
			int max = i;
			for(int j=i+1;cluster[j] !=null; j++)
			{
				if(cluster[j].count<cluster[max].count)
				{
					max = j;
				}
			}
			Cluster temp = cluster[i];
			cluster[i] = cluster[max];
			cluster[max] = temp;
		}
		System.out.println("");
		System.out.println(" __________________________________________________");
		System.out.println("|Top 5 Negative Features Reviews for Canon Camera :|");
		System.out.println("|__________________________________________________|");
		//System.out.println("Top 5 Negative Features for Canon Camera");
		
		for(int i = 0; i < 5 ; i++)
		{
			System.out.println("");
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println("Most Important Negative part(Excerpts) in Reviews for the Feature :"+cluster[i].feature);
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println("");
			//System.out.println(cluster[i].feature+" with a count "+cluster[i].count);
			int lineNo = 1;
			for(Iterator itr = cluster[i].sentenceNumber.iterator();itr.hasNext();)
			{
		
				int n = (Integer) itr.next();
				System.out.println(lineNo+"  "+productSentence.get(n));
				System.out.println("");
				lineNo++;
			}
		}
	}
}
