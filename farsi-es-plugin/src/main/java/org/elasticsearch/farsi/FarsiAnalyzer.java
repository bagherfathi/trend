package org.elasticsearch.farsi;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.core.DecimalDigitFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.IOUtils;

/**
 * {@link Analyzer} for Farsi.
 *
 * <p>This analyzer implements light-stemming as specified by: <i> Light Stemming for Farsi
 * Information Retrieval </i> hazm
 *
 * <p>The analysis package contains three primary components:
 *
 * <ul>
 *   <li>{@link FarsiNormalizerFilter}: Farsi orthographic normalization.
 *   <li>{@link FarsiStemFilter}: Farsi light stemming
 *   <li>Farsi stop words file: a set of default Farsi stop words.
 * </ul>
 *
 * @since 3.1
 */
public final class FarsiAnalyzer extends StopwordAnalyzerBase {

    /**
     * File containing default Farsi stopwords.
     *
     * <p>Default stopword list is from hazm The
     * stopword list is BSD-Licensed.
     */
    public static final String DEFAULT_STOPWORD_FILE = "stopwords.txt";

    /**
     * Returns an unmodifiable instance of the default stop-words set.
     *
     * @return an unmodifiable instance of the default stop-words set.
     */
    public static CharArraySet getDefaultStopSet() {
        return DefaultSetHolder.DEFAULT_STOP_SET;
    }

    /**
     * Atomically loads the DEFAULT_STOP_SET in a lazy fashion once the outer class accesses the
     * static final set the first time.;
     */
    private static class DefaultSetHolder {
        static final CharArraySet DEFAULT_STOP_SET;

        static {
            try {
                DEFAULT_STOP_SET =
                        WordlistLoader.getWordSet(
                                IOUtils.requireResourceNonNull(
                                        FarsiAnalyzer.class.getResourceAsStream(DEFAULT_STOPWORD_FILE),
                                        DEFAULT_STOPWORD_FILE),
                                "#");
            } catch (IOException ex) {
                // default set should always be present as it is part of the
                // distribution (JAR)
                throw new UncheckedIOException("Unable to load default stopword set", ex);
            }
        }
    }

    private final CharArraySet stemExclusionSet;

    /** Builds an analyzer with the default stop words: {@link #DEFAULT_STOPWORD_FILE}. */
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
     * this analyzer will add a {@link SetKeywordMarkerFilter} before {@link FarsiStemFilter}.
     *
     * @param stopwords a stopword set
     * @param stemExclusionSet a set of terms not to be stemmed
     */
    public FarsiAnalyzer(CharArraySet stopwords, CharArraySet stemExclusionSet) {
        super(stopwords);
        this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionSet));
    }

    /**
     * Creates {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents} used to tokenize all
     * the text in the provided {@link Reader}.
     *
     * @return {@link org.apache.lucene.analysis.Analyzer.TokenStreamComponents} built from an {@link
     *     StandardTokenizer} filtered with {@link LowerCaseFilter}, {@link DecimalDigitFilter},
     *     {@link StopFilter}, {@link FarsiNormalizerFilter}, {@link SetKeywordMarkerFilter} if a
     *     stem exclusion set is provided and {@link FarsiStemFilter}.
     */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer source = new StandardTokenizer();
        TokenStream result = new LowerCaseFilter(source);
        result=new FarsiSentenceTokenizerFilter(result);
        result=new FarsiTokenFilter(result);
        result = new DecimalDigitFilter(result);
        result=new FarsiNormalizerFilter(result);
        result = new StopFilter(result, stopwords);
        // TODO maybe we should make FarsiNormalization filter also KeywordAttribute aware?!
        result = new FarsiNormalizerFilter(result);
        if (!stemExclusionSet.isEmpty()) {
            result = new SetKeywordMarkerFilter(result, stemExclusionSet);
        }
        return new TokenStreamComponents(source, new FarsiLemmatizerFilter(result));
    }

    @Override
    protected TokenStream normalize(String fieldName, TokenStream in) {
        TokenStream result = new LowerCaseFilter(in);
        result = new DecimalDigitFilter(result);
        result = new FarsiNormalizerFilter(result);
        return result;
    }
}