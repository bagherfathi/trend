package org.elasticsearch.farsi;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

import java.io.IOException;

public class FarsiLemmatizerFilter extends TokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

	protected FarsiLemmatizerFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			if (!keywordAttr.isKeyword()) {
				termAtt.setEmpty().append(FarsiLemmatizer.lemataize(termAtt.toString()));
			}
			return true;
		} else {
			return false;
		}
	}

}
