package com.github.gohardani.lucene;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

import java.io.IOException;
import java.util.Arrays;

public class FarsiWordTokenizerFilter extends TokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

	public FarsiWordTokenizerFilter(TokenStream input) {
		super(input);
	}


	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			termAtt.setEmpty().append(FarsiWordTokenizer.WordTokenize(Arrays.toString(termAtt.buffer())).get(0));
			return true;
		} else {
			return false;
		}
	}

}
