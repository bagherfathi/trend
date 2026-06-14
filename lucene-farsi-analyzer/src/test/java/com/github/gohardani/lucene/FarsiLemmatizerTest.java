package com.github.gohardani.lucene;

import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author Bagher Fathi &lt;Bagher.Fathi@gmail.com&gt;
 */
public class FarsiLemmatizerTest {

    @Test
    public void stem() throws IOException {
        //System.out.println(stemmer.stem("أَبَدًا"));
        System.out.println("FarsiLemmatizerTest startrd");
        System.out.println(FarsiLemmatizer.lemataize("گاوها"));

//        System.out.println(FarsiNormalizer.normalize("تغییر ها"));

//        System.out.println(FarsiNormalizer.normalize("کتاب ها"));
    }
}
