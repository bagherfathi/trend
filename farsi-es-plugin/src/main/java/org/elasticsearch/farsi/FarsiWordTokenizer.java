package org.elasticsearch.farsi;

import jhazm.tokenizer.WordTokenizer;
import org.apache.lucene.analysis.Tokenizer;

import java.io.IOException;
import java.util.List;

public class FarsiWordTokenizer extends Tokenizer {

	public static List<String> WordTokenize(String term) throws IOException {
		WordTokenizer wordTokenizer = new WordTokenizer();
		return wordTokenizer.tokenize(term);
	}

	@Override
	public boolean incrementToken() throws IOException {
		return true;
	}
}
