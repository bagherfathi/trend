package org.elasticsearch.farsi;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import jhazm.Stemmer;

public class FarsiStemmer {
	

	public static String stem(String term) {
		Stemmer stemmer = new Stemmer();
		return stemmer.stem(term);
	}
}
