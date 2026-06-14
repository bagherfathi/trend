package org.elasticsearch.farsi;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

import java.io.IOException;
import java.util.Arrays;

public class FarsiNormalizerFilter extends TokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

	protected FarsiNormalizerFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
//			String s=FarsiNormalizer.normalize(termAtt.buffer().toString());
//			termAtt.setLength(s.length());
			termAtt.setEmpty().append(FarsiNormalizer.normalize(Arrays.toString(termAtt.buffer())));
			return true;
		} else {
			return false;
		}
	}

}
