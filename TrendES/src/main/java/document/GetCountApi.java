package document;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;

import client.LocalhostClient;

public class GetCountApi {

    public CountResponse getCount(final String index, final String field, String value) {
        try (final RestHighLevelClient client = LocalhostClient.create()) {
            CountRequest countRequest = new CountRequest(index);
            countRequest.query(QueryBuilders.termQuery(field, value));
            CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
            return countResponse;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
