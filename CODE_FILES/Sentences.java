package review.nlp.com;

public class Sentences {
	public String nouns[] = new String[30]; 
	String adjective[] = new String[30];
	int posNoun[] = new int[30] ;
	int posAdjective[] = new int[30];
	String negation[] = new String[30];
	int posNegation[] = new int[30];
	Sentences()
	{
		
	}
	Sentences(String nw, int i )
	{
		nouns[i] = nw;
	}
	
	void setNouns(String nw, int i)
	{
		nouns[i] = nw; 
	}
	
	String getNouns(int i)
	{
		return nouns[i];
	}

}


