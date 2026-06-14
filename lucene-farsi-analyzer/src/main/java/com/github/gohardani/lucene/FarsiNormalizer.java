package com.github.gohardani.lucene;

import jhazm.Normalizer;

import java.io.IOException;

public class FarsiNormalizer {


	public static String normalize(String term) throws IOException {
		Normalizer normalizer = new Normalizer();
		return normalizer.run(term);
	}
}
