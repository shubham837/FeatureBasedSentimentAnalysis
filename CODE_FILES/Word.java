package review.nlp.com;

public class Word 
{
	String noun;
	int count;
	boolean checked;
	Word(String noun, int count)
	{
		this.noun = noun;
		this.count = count;
		this.checked = false;
	}
	void increment()
	{
		count++;
	}

}