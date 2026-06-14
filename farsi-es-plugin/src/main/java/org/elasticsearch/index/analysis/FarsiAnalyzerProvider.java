package org.elasticsearch.index.analysis;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.farsi.FarsiAnalyzer;
import org.elasticsearch.index.IndexSettings;
// import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

public class FarsiAnalyzerProvider extends AbstractIndexAnalyzerProvider<FarsiAnalyzer> {
    private final FarsiAnalyzer analyzer;
    public FarsiAnalyzerProvider(IndexSettings indexSettings, Environment environment, String s, Settings settings) {
        super(s, settings);
        analyzer = new FarsiAnalyzer();
    }

    @Override
    public FarsiAnalyzer get() {
        return analyzer;
    }
}
