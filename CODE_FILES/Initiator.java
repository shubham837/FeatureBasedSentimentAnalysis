package review.nlp.com;

import java.util.HashMap;


import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Initiator 
{
	public static Sentences productSentences[] = new Sentences[4000];
	public static HashMap<String,Word> productNouns = new HashMap<String,Word>(); 
	public static Sentences genericSentences[] = new Sentences[4000];
	public static HashMap<String,Word> genericNouns = new HashMap<String,Word>();
	public static final String productFileInput ="Canon G3.txt";
	public static final String productFileOutput = "CanonOutPut.xml";
	public static final String genericFileInput = "Nokia 6610.txt";
	public static final String genericFileOutput = "NokiaOutPut.xml";

	public static HashMap<String,String> frequentFeature = new HashMap<String,String>();
	public static FeatureOpinionPolarity featureOpinionPolarity = new FeatureOpinionPolarity();
	public static HashMap<Integer, String> productSentence = new HashMap<Integer, String>();
	public static HashMap<Integer, String> genericSentence = new HashMap<Integer, String>();
	public static HashMap<String, Cluster> positiveFrequentFeature = new HashMap<String, Cluster>();
	public static HashMap<String, Cluster> negativeFrequentFeature = new HashMap<String, Cluster>();
	//private static String pathToSWN = "SentiWordNet_3.0.0.txt";
	private static String positiveSWNFile ="positive-words.txt";
	private static String negativeSWNFile ="negative-words.txt";
	private static HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
	public static HashMap<String,String> negation = new HashMap<String,String>();
	public static HashMap<String, NounGroup> productNounGroup = new HashMap<String,NounGroup>(); 
	public static void main(String[] args) throws Exception 
	{
		MaxentTagger mt = new MaxentTagger("Taggers/bidirectional-distsim-wsj-0-18.tagger");
		PosTagging.TextToXml(mt, productFileInput, productFileOutput, productSentence);
		PosTagging.TextToXml(mt, genericFileInput, genericFileOutput, genericSentence);
		negationWord();
		ParseFeatures.parseFeature(productSentences, productNouns, productFileInput, productFileOutput, negation);
		float productWordCount = WordCount.wordCount(productFileInput);
		ParseFeatures.parseFeature(genericSentences, genericNouns, genericFileInput, genericFileOutput, negation);
		float genericWordCount = WordCount.wordCount(genericFileInput);
		ParseFeatures.nounSynonymGrouping(productNouns, productNounGroup);
		ParseFeatures.genericNounSynonymGrouping(genericNouns);
		//ParseFeatures.freqFeatureExtraction1(productNouns, genericNouns,productWordCount,genericWordCount,frequentFeature);
		ParseFeatures.freqFeatureExtraction(productNounGroup, genericNouns,productWordCount,genericWordCount,frequentFeature);
		ParseFeatures.ReplaceNounsByNounGroup(productSentences, productNounGroup);
		FeatureOpinion.featureOpinion(productSentences, frequentFeature, featureOpinionPolarity);
		WordPolarity.dictionary(dictionary, positiveSWNFile, negativeSWNFile);
		featureOpinionPolarity.polarityDetection(dictionary);
		FeatureClustering.clustering(positiveFrequentFeature, negativeFrequentFeature, featureOpinionPolarity);
		Summarization.positiveFeatureSummary(positiveFrequentFeature,negativeFrequentFeature, productSentence);
		Summarization.negativeFeatureSummary(negativeFrequentFeature, productSentence);
		

	}
	public static void negationWord()
	{
		String negationWord[] = {"no","not","never","doesn't","less","without","barely","hardly","rarely"};
		for(int i=0;i<negationWord.length;i++)
		{
			negation.put(negationWord[i], negationWord[i]);
		}
	}

}
