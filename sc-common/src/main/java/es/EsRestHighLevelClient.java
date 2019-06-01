package es;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.core.util.Assert;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import util.IpHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/4/17.
 */
public class EsRestHighLevelClient {

    /**
     * new HttpHost("192.168.181.44", 9200, "http")
     */
    private HttpHost[] hosts;
    private String index;
    private String type;
    private String id;

    public EsRestHighLevelClient(String index, String type, String id, HttpHost[] hosts) {
        this.hosts = hosts;
        this.index = index;
        this.type = type;
        this.id = id;
    }

    /**
     * @param index
     * @param type
     * @param hosts
     */
    public EsRestHighLevelClient(String index, String type, String... hosts) {
        this.hosts = IpHelper.getHostArrByStr(hosts);
        this.index = index;
        this.type = type;
    }

    public EsRestHighLevelClient(String hosts) {
        this("", "", hosts);
    }

    public RestHighLevelClient client() {
        Assert.requireNonEmpty(this.hosts, "无效的es连接");
        return new RestHighLevelClient(
                RestClient.builder(this.hosts).
                        setMaxRetryTimeoutMillis(60 * 1000).  //设置http客户请求时长
                        build()
        );
    }

    public IndexRequest indexRequest() {
        return new IndexRequest(this.index, this.type, this.id);
    }

    public RestStatus createIndex(Map<String, Object> map) throws IOException {
        return client().
                index(this.indexRequest().source(map)).
                status();
    }

    public List<Map<String, Object>> searchIndex(String index, int from, Map<String, Object> where) {
        return searchIndex(index, from, where, null, null, null);
    }

    public List<Map<String, Object>> searchIndex(String index, int from, Map<String, Object> where,
                                                 Map<String, Boolean> sortFieldsToAsc, String[] includeFields, String[] excludeFields) {
        return searchIndex(index, from, 15, where, sortFieldsToAsc, includeFields, excludeFields, 60);
    }

    /**
     * @param index
     * @param from
     * @param size
     * @param where
     * @param sortFieldsToAsc
     * @param includeFields
     * @param excludeFields
     * @param timeOut
     * @return
     */
    public List<Map<String, Object>> searchIndex(String index, int from, int size, Map<String, Object> where,
                                                 Map<String, Boolean> sortFieldsToAsc, String[] includeFields, String[] excludeFields,
                                                 int timeOut) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            //条件
            if (where != null && !where.isEmpty()) {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                where.forEach((k, v) -> {
                    if (v instanceof Map) {
                        //范围选择map  暂定时间
                        Map<String, Date> mapV = (Map<String, Date>) v;
                        if (mapV != null) {
                            boolQueryBuilder.must(
                                    QueryBuilders.rangeQuery(k).
                                            gte(format.format(mapV.get("start"))).
                                            lt(format.format(mapV.get("end"))));
                        }
                    } else {
                        //普通模糊匹配
                        boolQueryBuilder.must(QueryBuilders.wildcardQuery(k, v.toString()));
                    }
                });
                sourceBuilder.query(boolQueryBuilder);
            }

            //分页
            from = from <= -1 ? 0 : from;
            size = size >= 1000 ? 1000 : size;
            size = size <= 0 ? 15 : size;
            sourceBuilder.from(from);
            sourceBuilder.size(size);

            //超时
            sourceBuilder.timeout(new TimeValue(timeOut, TimeUnit.SECONDS));

            //排序
            if (sortFieldsToAsc != null && !sortFieldsToAsc.isEmpty()) {
                sortFieldsToAsc.forEach((k, v) -> {
                    sourceBuilder.sort(new FieldSortBuilder(k).order(v ? SortOrder.ASC : SortOrder.DESC));
                });
            } else {
                sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
            }

            //返回和排除列
            if (!CollectionUtils.isEmpty(includeFields) || !CollectionUtils.isEmpty(excludeFields)) {
                sourceBuilder.fetchSource(includeFields, excludeFields);
            }

            SearchRequest rq = new SearchRequest();
            //索引
            rq.indices(index);
            //各种组合条件
            rq.source(sourceBuilder);

            //请求
            System.out.println(rq.source().toString());
            SearchResponse rp = client().search(rq);

            //解析返回
            if (rp.status() != RestStatus.OK || rp.getHits().getTotalHits() <= 0) {
                return Collections.emptyList();
            }

            //获取source
            return Arrays.stream(rp.getHits().getHits()).map(b -> {
                return b.getSourceAsMap();
            }).collect(Collectors.toList());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

}