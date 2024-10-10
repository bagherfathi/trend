package org.elasticsearch.farsi;

import jhazm.tokenizer.WordTokenizer;

import java.io.IOException;
import java.util.List;

public class FarsiWordTokenizer {
	

	public static List<String> WordTokenize(String term) throws IOException {
		WordTokenizer wordTokenizer = new WordTokenizer();
		return wordTokenizer.tokenize(term);
	}
}
