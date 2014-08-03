package review.nlp.com;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

class WordPolarity
{
	public static void dictionary(HashMap<String, Integer> dictionary, String positiveSWNPath, String negativeSWNPath) throws FileNotFoundException
	{
		File f1 = new File("/home/chinnu/work/workspace/ReviewSystem/OpinionPolarityFiles/"+positiveSWNPath);
		Scanner s = new Scanner(f1);
		while(s.hasNext())
		{
			String opinion = s.next();
			if(!dictionary.containsKey(opinion))
			{
				dictionary.put(opinion, 1);
			}
		}
		File f2 = new File("/home/chinnu/work/workspace/ReviewSystem/OpinionPolarityFiles/"+negativeSWNPath);
		s = new Scanner(f2);
		while(s.hasNext())
		{
			String opinion = s.next();
			if(!dictionary.containsKey(opinion))
			{
				dictionary.put(opinion, -1);
			}
		}
		
	}
	 
}