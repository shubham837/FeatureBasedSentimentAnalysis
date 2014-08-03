package review.nlp.com;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class WordCount 
{
        public static float wordCount(String fileName) throws Exception 
        {
                BufferedReader br = new BufferedReader(new FileReader("/home/chinnu/work/workspace/ReviewSystem/ReviewFiles/"+fileName));
                String line = "", str = "";
                float count = 0;
                while ((line = br.readLine()) != null) 
                {
                        str += line + " ";
                }
                StringTokenizer st = new StringTokenizer(str);
                while (st.hasMoreTokens()) 
                {
                        String s = st.nextToken();
                        count++;
                }
                return count;
        }
}