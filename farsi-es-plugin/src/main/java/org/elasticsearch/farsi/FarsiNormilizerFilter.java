package org.elasticsearch.farsi;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class FarsiNormilizerFilter extends TokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

	protected FarsiNormilizerFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			for(int i=0; i<termAtt.length(); i++) 
				if(termAtt.buffer()[i] == '\u200C')
					termAtt.buffer()[i] = '\u0020';
			
			return true;
		}
		return false;
	}

}
