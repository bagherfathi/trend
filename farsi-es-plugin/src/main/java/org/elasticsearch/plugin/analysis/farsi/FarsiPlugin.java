package org.elasticsearch.plugin.analysis.farsi;

import org.elasticsearch.farsi.FarsiWordTokenizerFilter;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FarsiPlugin extends Plugin implements AnalysisPlugin {
    public static String PLUGIN_NAME = "elasticsearch-analysis-farsi";


    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> extra = new HashMap<>();
        extra.put("farsi_token", FarsiWordTokenizerFilter::new);
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        return Collections.singletonMap("farsi_tokenizer", FarsiTokenizerFactory::new);
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return Collections.singletonMap("farsi_analyzer", FarsiAnalyzerProvider::new);
    }
}
