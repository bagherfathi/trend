package org.elasticsearch.farsi;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.TokenFilterFactory;

/**
 * Factory for {@link FarsiStemFilter}.
 *
 * @author Mouaffak A. Sarhan &lt;mouffaksarhan@gmail.com&gt;
 */
public class FarsiWordTokenizerFilterFactory extends TokenFilterFactory {

    public FarsiWordTokenizerFilterFactory(Map<String, String> args) {
        super(args);
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Unknown parameters: " + args);
        }
    }

    @Override
    public FarsiWordTokenizerFilter create(TokenStream input) {
        return new FarsiWordTokenizerFilter(input);
    }
}
