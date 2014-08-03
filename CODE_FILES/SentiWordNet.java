package review.nlp.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class SentiWordNet 
{
	public static void  dictionary(HashMap<String, Integer> dictionary, String pathToSWN)
	{
		HashMap<String, Vector<Double>> temp = new HashMap<String, Vector<Double>>();
		try{
			BufferedReader csv =  new BufferedReader(new FileReader("/home/chinnu/Desktop/"+pathToSWN));
			String line = "";			
			while((line = csv.readLine()) != null)
			{
				String[] data = line.split("\t");
				Double score = Double.parseDouble(data[2])-Double.parseDouble(data[3]);
				String[] words = data[4].split(" ");
				for(String w:words)
				{
					String[] w_n = w.split("#");
					//w_n[0] += "#"+data[0];
					int index = Integer.parseInt(w_n[1])-1;
					if(temp.containsKey(w_n[0]))
					{
						Vector<Double> v = temp.get(w_n[0]);
						if(index>v.size())
							for(int i = v.size();i<index; i++)
								v.add(0.0);
						v.add(index, score);
						temp.put(w_n[0], v);
					}
					else
					{
						Vector<Double> v = new Vector<Double>();
						for(int i = 0;i<index; i++)
							v.add(0.0);
						v.add(index, score);
						temp.put(w_n[0], v);
					}
				}
			}
			Set<String> opinion = temp.keySet();
			for (Iterator<String> iterator = opinion.iterator(); iterator.hasNext();) {
				String word = (String) iterator.next();
				Vector<Double> v = temp.get(word);
				double score = 0.0;
				double sum = 0.0;
				for(int i = 0; i < v.size(); i++)
					score += ((double)1/(double)(i+1))*v.get(i);
				for(int i = 1; i<=v.size(); i++)
					sum += (double)1/(double)i;
				score /= sum;
				Integer polarity = 10;				
				if(score>=0.75)
				{
					polarity = 3;
				}
				else
				if(score > 0.25 && score<0.75)
				{
					polarity = 2;
				}
				else
				if(score > 0 && score<=0.25)
				{
					polarity = 1;
				}
				else
				if(score < 0 && score>=-0.25)
				{
					polarity = -1;
				}
				else
				if(score < -0.25 && score>-0.75)
				{
					polarity = -2;
				}
				else
				if(score<=-0.75)
				{
					polarity = -3;
				}
				else if(score == 0)
				{
					polarity = 0;
				}
				dictionary.put(word, polarity);
			}
		}
		catch(Exception e){e.printStackTrace();}	
		
	}
}