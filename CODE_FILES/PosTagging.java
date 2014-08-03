package review.nlp.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.XMLUtils;


public class PosTagging 
{
	public static String getXMLWords(ArrayList<TaggedWord> taggedSentence, int sentNum) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("<").append(XMLUtils.escapeTextAroundXMLTags("sentence")).append(" id=\"").append(XMLUtils.escapeAttributeXML(""+sentNum)).append("\">");
		sb.append("\n");
		for (int i = 0, sz = ((List) taggedSentence).size(); i < sz; i++) 
		{
			String word = ((HasWord) ((List) taggedSentence).get(i)).word();
			String tag = ((TaggedWord) ((List) taggedSentence).get(i)).tag();
			sb.append("<").append(XMLUtils.escapeTextAroundXMLTags("word")).append(" wid=\"").append(XMLUtils.escapeAttributeXML(""+i)).append("\"").append(" pos=\"").append(XMLUtils.escapeAttributeXML(tag)).append("\">").append(XMLUtils.escapeElementXML(word)).append("<").append(XMLUtils.escapeTextAroundXMLTags("/word")).append(">").append("\n");
		}
		sb.append("<").append(XMLUtils.escapeXML("/sentence")).append(">");
		return sb.toString();
	}
	public static void TextToXml(MaxentTagger mt, String input, String output, HashMap<Integer, String> sentences) throws IOException
	{
		String str;
		File fc=new File("/home/chinnu/work/workspace/ReviewSystem/ReviewFiles/"+input);
		FileWriter file = new FileWriter("/home/chinnu/work/workspace/ReviewSystem/ReviewFiles/"+output);
		FileReader fl=new FileReader(fc);
		BufferedReader bf=new BufferedReader(fl);
		StringReader reader;
		int sentenceNum = 0;
		file.write("<");
		file.write(XMLUtils.escapeElementXML("Text"));
		file.write(">");

		while((str=bf.readLine())!=null)
		{
			sentences.put(sentenceNum, str);
			reader = new StringReader(str);
			for(List sentence : mt.tokenizeText(reader))
			{
				
				ArrayList<TaggedWord> taggedSentence = mt.tagSentence(sentence);
				file.write(getXMLWords(taggedSentence, sentenceNum)); 
				sentenceNum++;
			}

		}
		file.write("<");
		file.write(XMLUtils.escapeElementXML("/Text"));
		file.write(">");
		file.close();
		fl.close();
	}

	
}
