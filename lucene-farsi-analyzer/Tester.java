import com.github.msarhan.lucene.ArabicRootExtractorStemmer;

/**
 * @author Bagher Fathi
 */
public class Tester {

    public static void main(String[] args) {
        ArabicRootExtractorStemmer stemmer = new ArabicRootExtractorStemmer();
        System.out.println(stemmer.stem("المُفْلِحُون"));
    }
}
