package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.*;

//import java.io.Reader;

public class FarsiAnalyzer extends Analyzer {
    public FarsiAnalyzer() {
        super();
    }

    @Override
    protected TokenStreamComponents createComponents(final String fieldName) {
        Tokenizer tokenizer = new FarsiNlpTokenizer();
        TokenStream stream = new FarsiTokenFilter(tokenizer);
        return new TokenStreamComponents(tokenizer, stream);
    }
}
