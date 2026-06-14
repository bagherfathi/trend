package org.elasticsearch.farsi;

import jhazm.tokenizer.SentenceTokenizer;
import org.apache.lucene.analysis.Tokenizer;

import java.io.IOException;
import java.util.List;

public class FarsiSentenceTokenizer {


	public FarsiSentenceTokenizer() {
	}

	public static List<String> SentenceTokenize(String sentence) throws IOException {
		SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
		return sentenceTokenizer.tokenize(sentence);
	}
}
