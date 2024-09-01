/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fff.trendmanager.trend;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import client.LocalhostClient;
import java.io.IOException;
import java.util.Map;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.search.suggest.Suggest;
/**
 *
 * @author baghe
 */
public class Search {
    public static void main(String[] args) throws IOException {
        Search s=new Search();
        s.matchAll();
    }

    public SearchHit[] matchAll() throws IOException {
        final RestHighLevelClient client = LocalhostClient.create();
        SearchRequest searchRequest = new SearchRequest("fetchednews");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        long numHits = totalHits.value;
        System.out.println(numHits);
// whether the number of hits is accurate (EQUAL_TO) or a lower bound of the total (GREATER_THAN_OR_EQUAL_TO)
        TotalHits.Relation relation = totalHits.relation;
        float maxScore = hits.getMaxScore();
        SearchHit[] searchHits = hits.getHits();
//        for (SearchHit hit : searchHits) {
//            // do something with the SearchHit
//            Map<String, Object> map = hit.getSourceAsMap();
//            System.out.println(map.get("title").toString());
//        }
//        client.close();
        return searchHits;
    }
    public SearchResponse multiMatch(String q,int from) throws IOException {
        final RestHighLevelClient client = LocalhostClient.create();
        SearchRequest searchRequest = new SearchRequest("fetchednews");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder termSuggestionBuilder =
                SuggestBuilders.termSuggestion("content").text(q.trim());
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_content", termSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);
//        -------highliter--------
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightContent =
                new HighlightBuilder.Field("content");
        highlightContent.highlighterType("unified");
        highlightBuilder.field(highlightContent);
        searchSourceBuilder.highlighter(highlightBuilder);
//        -------end highlither---
//        -------sort-------------
        searchSourceBuilder.sort(new FieldSortBuilder("publishtime").order(SortOrder.DESC));
//        -------end sort---------
        if(q==null)
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        else if(q.trim().equals(""))
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        else
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(q.trim(),"title","description","content"));
        searchRequest.source(searchSourceBuilder);
//        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        searchSourceBuilder.from(from);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        long numHits = totalHits.value;
        System.out.println("numhits: " + numHits);
        Suggest suggest = searchResponse.getSuggest();
        TermSuggestion termSuggestion = suggest.getSuggestion("suggest_content");
        for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
            for (TermSuggestion.Entry.Option option : entry) {
                String suggestText = option.getText().string();
                System.out.println(suggestText);
            }
        }
// whether the number of hits is accurate (EQUAL_TO) or a lower bound of the total (GREATER_THAN_OR_EQUAL_TO)
        TotalHits.Relation relation = totalHits.relation;
        float maxScore = hits.getMaxScore();
        SearchHit[] searchHits = hits.getHits();
//        for (SearchHit hit : searchHits) {
//            // do something with the SearchHit
//            Map<String, Object> map = hit.getSourceAsMap();
//            System.out.println(map.get("title").toString());
//        }
//        client.close();
        return searchResponse;
    }
    public SearchResponse matchById(String id) throws IOException {
        final RestHighLevelClient client = LocalhostClient.create();
        SearchRequest searchRequest = new SearchRequest("fetchednews");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(id.trim(),"_id"));
        searchRequest.source(searchSourceBuilder);
//        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        long numHits = totalHits.value;
        System.out.println("numhits: " + numHits);
// whether the number of hits is accurate (EQUAL_TO) or a lower bound of the total (GREATER_THAN_OR_EQUAL_TO)
        TotalHits.Relation relation = totalHits.relation;
        float maxScore = hits.getMaxScore();
        SearchHit[] searchHits = hits.getHits();
//        for (SearchHit hit : searchHits) {
//            // do something with the SearchHit
//            Map<String, Object> map = hit.getSourceAsMap();
//            System.out.println(map.get("title").toString());
//        }
//        client.close();
        return searchResponse;
    }
}
