package org.elasticsearch.farsi;

import jhazm.Lemmatizer;

import java.io.IOException;

public class FarsiLemmatizer {

	public static String lemataize(String term) throws IOException {
		Lemmatizer lemmatizer = new Lemmatizer();
		return lemmatizer.lemmatize(term);
	}
}
