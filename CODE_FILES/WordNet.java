package review.nlp.com;
//package edu.cmu.lti.ws4j;

import edu.cmu.lti.jawjaw.JAWJAW;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.MatrixCalculator;

public class WordNet extends JAWJAW {
	
        private static ILexicalDatabase db = new NictWordNet();
        
        // similarity calculators
        private static RelatednessCalculator lin = new Lin(db);
        private static RelatednessCalculator wup = new WuPalmer(db);
        private static RelatednessCalculator hso = new HirstStOnge(db);
        private static RelatednessCalculator lch = new LeacockChodorow(db);
        private static RelatednessCalculator jcn = new JiangConrath(db);
        private static RelatednessCalculator lesk = new Lesk(db);
        private static Path path = new Path(db);
        private static RelatednessCalculator res = new Resnik(db);
       
        
       
        public static double runHSO( String word1, String word2 ) {
        	return hso.calcRelatednessOfWords( word1, word2 );
                }
       
        
        public static double runLCH( String word1, String word2 ) {
                return lch.calcRelatednessOfWords( word1, word2 );
        }
       
       
        public static double runRES( String word1, String word2 ) {
                return res.calcRelatednessOfWords( word1, word2 );
        }
       
        
        public static double runJCN( String word1, String word2 ) {
                return jcn.calcRelatednessOfWords( word1, word2 );
        }
       
       
        public static double runLIN( String word1, String word2 ) {
                return lin.calcRelatednessOfWords( word1, word2 );
        }
       
       
        public static double runLESK( String word1, String word2 ) {
                return lesk.calcRelatednessOfWords( word1, word2 );
        }
       
        
        public static double runPATH( String word1, String word2 ) {
        	  
                return path.calcRelatednessOfWords( word1, word2 );
                
        }

       
        public static double runWUP( String word1, String word2 ) {
        	System.out.println(wup.calcRelatednessOfWords( word1, word2 ));
                return wup.calcRelatednessOfWords( word1, word2 );
        }
                               
        public static double[][] getSynonymyMatrix( String[] words1, String[] words2 ) {
                return MatrixCalculator.getSynonymyMatrix( words1, words2 );
        }
       
}
