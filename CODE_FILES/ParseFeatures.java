package review.nlp.com;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ParseFeatures 
{
	private static final boolean True = false;
	static int j=0;
	
	public static void parseFeature(Sentences sentences[], HashMap<String,Word> nouns, String fileInput, String fileOutput, HashMap<String,String> negation) 
	{
		try 
		{ 
			j=0;
			File fXmlFile = new File("/home/chinnu/work/workspace/ReviewSystem/ReviewFiles/"+fileOutput);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("sentence");  
			//int n = nList.getLength();
			for (int temp = 0; temp < nList.getLength(); temp++) 
			{
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
					sentences[j]= new Sentences();
					getTagValue("word", eElement, sentences[j], nouns, negation);
					j++;
				}
			}
			
		}catch (Exception e) 
		 {
			e.printStackTrace();
		 }
	}
 
	private static void getTagValue(String sTag, Element eElement, Sentences sentences, HashMap<String,Word> nouns, HashMap<String,String> negation ) 
	{
		NodeList nlList = eElement.getElementsByTagName(sTag);
		int k=0;
		int c=0;
		int n=0;
		for(int i=0; i<nlList.getLength(); i++)
		{
			Node wd = nlList.item(i);
			Element element = (Element) wd;
			String pos = element.getAttribute("pos");
			
			if(pos.equals("NN")||pos.equals("NNS")||pos.equals(" NNP")||pos.equals("NNPS"))
			{
				if(element.getFirstChild().getNodeValue()!=null)
				{
					String ele = element.getFirstChild().getNodeValue();
					if(!(ele.equals("##") || ele.equals("p") || ele.equals("t") || ele.equals("\\/")))
					{
						if(pos.equals("NNS")|| pos.equals("NNPS"))
						{
							 String str= element.getFirstChild().getNodeValue();
							 n = str.length();
							 str.charAt(n-1);
							 str = str.substring(0, n-1);
							 
							 sentences.nouns[k] = str;
						}
						else
						{
						sentences.nouns[k] = element.getFirstChild().getNodeValue();
						}
						sentences.posNoun[k] = Integer.parseInt(element.getAttribute("wid"));
						
						
						
						
						Word word = nouns.get(sentences.nouns[k]);
						
						if(word == null)
						{
							word = new Word(sentences.nouns[k], 1);
						}
						else
						{
							word.increment();
						}
						nouns.put(sentences.nouns[k], word);
						
						
						k++;
					}
						
				}  
			}
			else if(pos.equals("JJ")||pos.equals("JJS")||pos.equals("JJR"))
			{
				if(element.getFirstChild().getNodeValue()!=null)
				{
					sentences.adjective[c]= element.getFirstChild().getNodeValue();
					sentences.posAdjective[c] = Integer.parseInt(element.getAttribute("wid"));
					
					//System.out.println("Adjective: "+sentences.adjective[c]+" Pos"+sentences.posAdjective[k]);
					c++;
				}
			}
			else if(element.getFirstChild().getNodeValue()!=null)
			{
				String str = element.getFirstChild().getNodeValue();
				if(negation.containsKey(str))
				{
					sentences.negation[n] = str;
					sentences.posNegation[n] = Integer.parseInt(element.getAttribute("wid"));
					//System.out.println("Negation Words :"+str);
					
				}
			}
		
		}
	}
	
	public static void nounSynonymGrouping(HashMap<String, Word> nouns, HashMap<String, NounGroup> nounGroup )
	{
		Collection<Word> words =  nouns.values();
		
		for(Iterator<Word> itr = words.iterator(); itr.hasNext(); )
		{
			Word word  = itr.next();
			//System.out.println(word.noun+"   =   "+word.count);
		}
		
		int clusterCount = 0;
		int k = 1;
		Iterator<Word> itr = words.iterator();
		
		for(;itr.hasNext();)
		{
			Word word1 = null;
			
			for(int i=0; i< k ; i++)
			{
				word1 = itr.next();
					
			}
			
			if(itr.hasNext() == false)
			{
				break;
			}
				
				int nounCountInEachCluster = 0;
				NounGroup noun = new NounGroup();
				noun.nouns[nounCountInEachCluster++] = word1.noun;
				noun.count = word1.count;
				nounGroup.put(word1.noun, noun);
				
				itr = words.iterator();
				for(;itr.hasNext();)
				{
					Word word2 = itr.next(); 
					if(word1.noun == word2.noun)
					{
						continue;
					}
					
					
						double n = WordNet.runPATH(word1.noun, word2.noun);
						if(n>=.33)
						{
							if(word1.count > word2.count)
							{
								
							
								//System.out.println(word1.noun+"....."+word2.noun);
								NounGroup localNoun = nounGroup.get(word1.noun);
								localNoun.nouns[nounCountInEachCluster++] = word2.noun;
								localNoun.count = localNoun.count + word2.count;
								nounGroup.put(word1.noun, localNoun);
							
							}
							else
							{
								nounGroup.remove(word1.noun);
								break;
								
								
							}
							
						}
					
				}
				clusterCount++;
			k++;
			itr = words.iterator();
		}
		/*if(words[i].checked == false)
		{
			NounGroup noun = new NounGroup();
			noun.nouns[0] = words[i].noun;
			noun.count = words[i].count;
			nounGroup.put(words[i].noun, noun);
		}
		*/
		Collection<NounGroup> group = nounGroup.values();
		int clusterNo = 1;
		for(Iterator<NounGroup> itr1 = group.iterator(); itr1.hasNext() ;)
		{
			NounGroup noungroup = itr1.next();
			
			System.out.println("Noun Based Cluster no. :"+ clusterNo);
			for(int j=0; noungroup.nouns[j]!=null ;j++)
			{
				System.out.println(noungroup.nouns[j]);
			}
			System.out.println("total Noun Count: "+noungroup.count);
			clusterNo++;
		}
	}
	public static void genericNounSynonymGrouping(HashMap<String, Word> genericNouns)
	{
		Collection<Word> words =  genericNouns.values();
		HashMap<String, Word> nouns = new HashMap<String, Word>();
		for(Iterator<Word> itr = words.iterator(); itr.hasNext();)
		{ 
			Word word = itr.next();
			nouns.put(word.noun, word);
		}
		
		int k = 1;
		
		Iterator<Word> itr = words.iterator();
		
		for(;itr.hasNext();)
		{
			Word word1 = null;
			
			for(int i=0; i< k ; i++)
			{
				word1 = itr.next();
					
			}
			
			if(itr.hasNext() == false)
			{
				break;
			}
			if(word1.checked == false)
			{
			
				int count = nouns.get(word1.noun).count;
				for(;itr.hasNext();)
				{
					Word word2 = itr.next(); 
					double n = WordNet.runPATH(word1.noun, word2.noun);
						if(n>=.33)
						{
						
							//System.out.println(word1.noun+"....."+word2.noun);
							word1.count += word2.count;	
							word2.count +=count;
							genericNouns.put(word2.noun, word2);
						}
				}
				genericNouns.put(word1.noun, word1);
				
			}
			k++;
			itr = words.iterator();
		}
		
	}

	public static void freqFeatureExtraction1(HashMap<String,Word> productNouns,HashMap<String,Word> genericNouns, float productWordCount, float genericWordCount,HashMap<String,String> frequentFeature)
	{
		Collection<Word> words = productNouns.values();
		int i=0;
		System.out.println("Most Important Features of  Canon Camera:");
		for(Iterator<Word> itr = words.iterator();itr.hasNext();)
		{
			
			Word productWord =  itr.next();
			/*k++;
			if(k%15 == 0)
			{
				System.out.println();
			}
			System.out.print(productWord.noun+"    ");
			*/
			Word genericWord = genericNouns.get(productWord.noun);
			
			//if(productWord.count>1)
			//{
				if(genericWord != null)
				{
					float productFreq = (float)productWord.count/productWordCount*1000;
					float genericFreq = (float)genericWord.count/genericWordCount*1000;
					float theta = productFreq - genericFreq;											
					if(theta>1)
					{
						i++;
						frequentFeature.put(productWord.noun, productWord.noun);
						//System.out.println(i+" : "+productWord.noun+"="+theta);
					}
				}
				else
				{
					float theta = (float)productWord.count/productWordCount*1000;
					if(theta>1)
					{
						i++;
						frequentFeature.put(productWord.noun, productWord.noun);
						//System.out.println(i+" : "+productWord.noun+"="+theta);
					}
					
				}
				
			//}
		}
	}
	
	public static void freqFeatureExtraction(HashMap<String,NounGroup> productNounGroup,HashMap<String,Word> genericNouns, float productWordCount, float genericWordCount,HashMap<String,String> frequentFeature)
	{
		Collection<NounGroup> words = productNounGroup.values();
		int i=0;
		System.out.println("Most Important Features of  Canon Camera:");
		for(Iterator<NounGroup> itr = words.iterator();itr.hasNext();)
		{
			NounGroup productWord =  itr.next();
			Word genericWord = genericNouns.get(productWord.nouns[0]);
			
			if(productWord.count>10)
			{
				//System.out.println(productWord.nouns[0]);
				if(genericWord != null)
				{
					float productFreq = (float)productWord.count/productWordCount*1000;
					float genericFreq = (float)genericWord.count/genericWordCount*1000;
					float theta = productFreq - genericFreq;											
					if(theta>1)
					{
						i++;
						frequentFeature.put(productWord.nouns[0], productWord.nouns[0]);
						System.out.println(i+" : "+productWord.nouns[0]+"="+theta);
					}
				}
				else
				{
					float theta = (float)productWord.count/productWordCount*1000;
					if(theta>1)
					{
						i++;
						frequentFeature.put(productWord.nouns[0], productWord.nouns[0]);
						System.out.println(i+" : "+productWord.nouns[0]+"="+theta);
					}
					
				}
				
			}
		}
	}
	public static void ReplaceNounsByNounGroup(Sentences sentence[], HashMap<String,NounGroup> productNounGroup )
	{
		Collection<NounGroup> nounsGroup = productNounGroup.values();
		
		for(int i=0; sentence[i]!= null; i++)
		{
			
			for(Iterator<NounGroup> itr = nounsGroup.iterator(); itr.hasNext();)
			{
				NounGroup nouns = itr.next();
				for(int j=1; nouns.nouns[j] != null; j++)
				{
					for(int k=0; sentence[i].nouns[k] != null; k++)
					{
						if(sentence[i].nouns[k] == nouns.nouns[j])
						{
							sentence[i].nouns[k] = nouns.nouns[0];
						}
					}
				}
			}
		}
	}
}