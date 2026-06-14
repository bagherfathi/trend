/*
 * The MIT License
 *
 * Copyright 2015 Bagher Fathi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.gohardani.lucene;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.DecimalDigitFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

/**
 * {@link Analyzer} for Farsi language. <p> This analyzer created base on JHAZM(a java wrapper for HAZM) <p> The analysis package contains two primary components: <ul> <li>{@link
 * FarsiLemmatizerFilter}: Farsi root extraction. <li>Farsi
 * stop words file: a set of default Farsi stop words. </ul>
 *
 * @author Bagher Fathi &lt;bagher.fathi@gmail.com&gt;
 */
public final class FarsiAnalyzer extends StopwordAnalyzerBase {

    /**
     * File containing default Farsi stopwords.
     */
    public final static String DEFAULT_STOPWORD_FILE = "stopwords.txt";

    private final CharArraySet stemExclusionSet;

    /**
     * Builds an analyzer with the default stop words: {@link #DEFAULT_STOPWORD_FILE}.
     */
    public FarsiAnalyzer() {
        this(DefaultSetHolder.DEFAULT_STOP_SET);
    }

    /**
     * Builds an analyzer with the given stop words
     *
     * @param stopwords a stopword set
     */
    public FarsiAnalyzer(CharArraySet stopwords) {
        this(stopwords, CharArraySet.EMPTY_SET);
    }

    /**
     * Builds an analyzer with the given stop word. If a none-empty stem exclusion set is provided
     * this analyzer will add a {@link SetKeywordMarkerFilter} before {@link
     * FarsiAnalyzer}.
     *
     * @param stopwords        a stopword set
     * @param stemExclusionSet a set of terms not to be stemmed
     */
    public FarsiAnalyzer(CharArraySet stopwords, CharArraySet stemExclusionSet) {
        super(stopwords);
        this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionSet));
    }

    /**
     * Returns an unmodifiable instance of the default stop-words set.
     *
     * @return an unmodifiable instance of the default stop-words set.
     */
    public static CharArraySet getDefaultStopSet() {
        return DefaultSetHolder.DEFAULT_STOP_SET;
    }

    /**
     * Creates {@link TokenStreamComponents} used to tokenize
     * all the text in the provided {@link Reader}.
     *
     * @return {@link TokenStreamComponents} built from an
     * {@link StandardTokenizer} filtered with {@link LowerCaseFilter}, {@link DecimalDigitFilter},
     * {@link StopFilter}, {@link FarsiLemmatizerFilter}, {@link SetKeywordMarkerFilter}
     */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer source = new FarsiWordTokenizer();
        TokenStream result = new LowerCaseFilter(source);
        result = new DecimalDigitFilter(result);
        result = new FarsiNormalizerFilter(result);
        result = new StopFilter(result, stopwords);
        if (!stemExclusionSet.isEmpty()) {
            result = new SetKeywordMarkerFilter(result, stemExclusionSet);
        }
        result = new FarsiLemmatizerFilter(result);
        return new TokenStreamComponents(source, result);
    }

    /**
     * Atomically loads the DEFAULT_STOP_SET in a lazy fashion once the outer class accesses the
     * static final set the first time.;
     */
    private static class DefaultSetHolder {

        static final CharArraySet DEFAULT_STOP_SET;

        static {
            try {
                DEFAULT_STOP_SET = loadStopwordSet(Path.of(DEFAULT_STOPWORD_FILE));
            } catch (IOException ex) {
                // default set should always be present as it is part of the
                // distribution (JAR)
                throw new RuntimeException("Unable to load default stopword set, path is:" + Path.of(DEFAULT_STOPWORD_FILE));
            }
        }
    }

}
