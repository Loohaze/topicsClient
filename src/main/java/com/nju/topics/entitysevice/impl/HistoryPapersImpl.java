package com.nju.topics.entitysevice.impl;

import com.nju.topics.domain.TreeDataInfo;
import com.nju.topics.entity.HistoryPapersEntity;
import com.nju.topics.entitysevice.HistoryPapersSerivce;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryPapersImpl implements HistoryPapersSerivce {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

//    @Autowired
//    private HistoryPapersRepository historyPapersRepository;

    @Override
    public List<HistoryPapersEntity> getHistoryPaperByName(String paperName) {
        List<HistoryPapersEntity> historyPapersEntities=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.wildcardQuery("LYPM.keyword", "*"+paperName+"*"));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(100); // 获取记录数，默认10
//        sourceBuilder.fetchSource(new String[] { "id", "name" }, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
        SearchRequest searchRequest = new SearchRequest("historypapers");
//        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        SearchResponse response=new SearchResponse();
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("search: " + (response).toString());
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit s:searchHits){
            HistoryPapersEntity historyPapersEntity=new HistoryPapersEntity();
            historyPapersEntity.setSNO(String.valueOf(s.getSourceAsMap().get("SNO")));
            historyPapersEntity.setLYPM(String.valueOf(s.getSourceAsMap().get("LYPM")));
            historyPapersEntity.setBLPM(String.valueOf(s.getSourceAsMap().get("BLPM")));
            historyPapersEntity.setQKNO(String.valueOf(s.getSourceAsMap().get("QKNO")));
            historyPapersEntity.setBYC(String.valueOf(s.getSourceAsMap().get("BYC")));
            historyPapersEntity.setNIAN(String.valueOf(s.getSourceAsMap().get("NIAN")));
            historyPapersEntities.add(historyPapersEntity);

        }
        return historyPapersEntities;
    }

    @Override
    public List<HistoryPapersEntity> getHistoryPapersByFuzzyName(String fuzzyName) {
        List<HistoryPapersEntity> historyPapersEntities=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        boolBuilder.must(QueryBuilders.fuzzyQuery("LYPM.keyword", fuzzyName));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);

        sourceBuilder.from(0);
        sourceBuilder.size(100); // 获取记录数，默认10

//        sourceBuilder.fetchSource(new String[] { "id", "name" }, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
        SearchRequest searchRequest = new SearchRequest("historypapers");
//        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        SearchResponse response=new SearchResponse();
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
//        System.out.println("search: " + (response).toString());

        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit s:searchHits){
            HistoryPapersEntity historyPapersEntity=new HistoryPapersEntity();
            historyPapersEntity.setSNO(String.valueOf(s.getSourceAsMap().get("SNO")));
            historyPapersEntity.setLYPM(String.valueOf(s.getSourceAsMap().get("LYPM")));
            historyPapersEntity.setBLPM(String.valueOf(s.getSourceAsMap().get("BLPM")));
            historyPapersEntity.setQKNO(String.valueOf(s.getSourceAsMap().get("QKNO")));
            historyPapersEntity.setBYC(String.valueOf(s.getSourceAsMap().get("BYC")));
            historyPapersEntity.setNIAN(String.valueOf(s.getSourceAsMap().get("NIAN")));
            historyPapersEntities.add(historyPapersEntity);

        }
        return historyPapersEntities;
    }

    @Override
    public List<HistoryPapersEntity> getHistoryPapersByAuthor(String author) {
        List<HistoryPapersEntity> historyPapersEntities=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        boolBuilder.must(QueryBuilders.fuzzyQuery("ZZMC.keyword", author));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);

        sourceBuilder.from(0);
        sourceBuilder.size(200); // 获取记录数，默认10

//        sourceBuilder.fetchSource(new String[] { "id", "name" }, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
        SearchRequest searchRequest = new SearchRequest("historyauthors");
//        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        SearchResponse response=new SearchResponse();
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
//        System.out.println("search: " + (response).toString());

        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        ArrayList<String> snos=new ArrayList<>();
        for (SearchHit s:searchHits){
            snos.add(String.valueOf(s.getSourceAsMap().get("SNO")));
        }
        historyPapersEntities=getHistoryPapersBySNOS(snos);
        return historyPapersEntities;
    }

    public List<HistoryPapersEntity> getHistoryPapersBySNOS(ArrayList<String> snos){
        List<HistoryPapersEntity> historyPapersEntities=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        boolBuilder.must(QueryBuilders.termsQuery("SNO.keyword", snos));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);

        sourceBuilder.from(0);
        sourceBuilder.size(200); // 获取记录数，默认10

//        sourceBuilder.fetchSource(new String[] { "id", "name" }, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
        SearchRequest searchRequest = new SearchRequest("historypapers");
//        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        SearchResponse response=new SearchResponse();
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
//        System.out.println("search: " + (response).toString());

        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit s:searchHits){
            HistoryPapersEntity historyPapersEntity=new HistoryPapersEntity();
            historyPapersEntity.setSNO(String.valueOf(s.getSourceAsMap().get("SNO")));
            historyPapersEntity.setLYPM(String.valueOf(s.getSourceAsMap().get("LYPM")));
            historyPapersEntity.setBLPM(String.valueOf(s.getSourceAsMap().get("BLPM")));
            historyPapersEntity.setQKNO(String.valueOf(s.getSourceAsMap().get("QKNO")));
            historyPapersEntity.setBYC(String.valueOf(s.getSourceAsMap().get("BYC")));
            historyPapersEntity.setNIAN(String.valueOf(s.getSourceAsMap().get("NIAN")));
            historyPapersEntities.add(historyPapersEntity);

        }
        return historyPapersEntities;
    }

    @Override
    public TreeDataInfo getHistoryPapersByAuthorAggratedByYear(String author) {
        TreeDataInfo resTreeData=new TreeDataInfo();
        resTreeData.setName(author);
        ArrayList<TreeDataInfo> resChildren=new ArrayList<>();

        List<HistoryPapersEntity> historyPapersEntities=getHistoryPapersByAuthor(author);
        ArrayList<String> years=new ArrayList<>();
//        获取所有有文章发表的年份
        for (HistoryPapersEntity hpe:historyPapersEntities){
            String year=hpe.getNIAN();
            boolean hasNian=false;
            if (years!=null && years.size()>0){
                for(String existYear:years){
                    if (existYear.equals(year)){
                        hasNian=true;
                        break;
                    }
                }
            }

            if (!hasNian){
                years.add(year);
            }
        }

        for(String eachYear:years){
            System.err.println(eachYear);
            TreeDataInfo yearTreeData=new TreeDataInfo();
            yearTreeData.setName(eachYear);
            ArrayList<TreeDataInfo> yearChildren=new ArrayList<>();
            for(HistoryPapersEntity hpeByYear:historyPapersEntities){
                if (hpeByYear.getNIAN().equals(eachYear)){
                    TreeDataInfo specialNianTreeData=new TreeDataInfo();
                    specialNianTreeData.setName(hpeByYear.getLYPM());
                    specialNianTreeData.setChildren(new ArrayList<>());
                    yearChildren.add(specialNianTreeData);
                }
            }
            yearTreeData.setChildren(yearChildren);
            resChildren.add(yearTreeData);
        }

        resTreeData.setChildren(resChildren);
        return resTreeData;
    }
}
