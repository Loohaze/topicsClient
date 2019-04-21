package com.nju.topics.entitysevice.impl;

import com.nju.topics.config.Config;
import com.nju.topics.domain.StatisticsInfo;
import com.nju.topics.entity.HistoryAuthorEntity;
import com.nju.topics.entity.HistoryPapersEntity;
import com.nju.topics.entitysevice.HistoryAuthorsService;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class HistoryAuthorsImpl implements HistoryAuthorsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private Config config;

    @Autowired
    private HistoryPapersSerivce historyPapersSerivce;

    @Override
    public ArrayList<StatisticsInfo> getRelativeAuthorByAuthor(String authorName) {

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        boolBuilder.must(QueryBuilders.matchQuery("ZZMC.keyword", authorName));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);

        sourceBuilder.from(0);
        sourceBuilder.size(100); // 获取记录数，默认10

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
            boolean hasExistSno=false;
            String sno=String.valueOf(s.getSourceAsMap().get("SNO"));
            for(String existSNO:snos){
                if (existSNO.equals(sno)){
                    hasExistSno=true;
                    break;
                }
            }
            if (!hasExistSno){

                snos.add(sno);
            }

        }

        BoolQueryBuilder boolBuilder2 = QueryBuilders.boolQuery();

        boolBuilder2.must(QueryBuilders.termsQuery("SNO.keyword", snos));
        boolBuilder2.mustNot(QueryBuilders.matchQuery("ZZMC.keyword", authorName));

        SearchSourceBuilder sourceBuilder2 = new SearchSourceBuilder();
        sourceBuilder2.query(boolBuilder2);

        sourceBuilder2.from(0);
        sourceBuilder2.size(200); // 获取记录数，默认10

        SearchRequest searchRequest2 = new SearchRequest("historyauthors");
        searchRequest2.source(sourceBuilder2);
        SearchResponse response2=new SearchResponse();
        try {
            response2 = restHighLevelClient.search(searchRequest2, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
//        System.out.println("search: " + (response).toString());

        ArrayList<StatisticsInfo> relativeAuthors=new ArrayList<>();

        SearchHits hits2 = response2.getHits();
        SearchHit[] searchHits2 = hits2.getHits();
        List<HashMap<String,String>> snoAuthorMapList=new ArrayList<>();
        for(SearchHit otherAuthor:searchHits2){
            boolean hasSameAuthor=false;
            String otherAuthorSNO=String.valueOf(otherAuthor.getSourceAsMap().get("SNO"));
            String otherAuthorName=String.valueOf(otherAuthor.getSourceAsMap().get("ZZMC"));
            HashMap<String,String> tempMap=new HashMap<>();
            tempMap.put(otherAuthorSNO,otherAuthorName);
            boolean hasSameMap=false;
            if (snoAuthorMapList!=null && snoAuthorMapList.size()>0){
                for(HashMap<String,String> existMap:snoAuthorMapList){
                    if (existMap.containsKey(otherAuthorSNO) && existMap.containsValue(otherAuthorName)){
                        hasSameMap=true;
                        break;
                    }
                }
            }

            if (!hasSameMap){
                snoAuthorMapList.add(tempMap);
                if (relativeAuthors!=null && relativeAuthors.size()>0){
                    for(StatisticsInfo st:relativeAuthors){
                        if ((st.getKey()).equals(otherAuthorName)){
                            hasSameAuthor=true;
                            st.setNum(st.getNum()+1);
                            break;
                        }
                    }

                    if (!hasSameAuthor){
                        StatisticsInfo statisticsInfo=new StatisticsInfo(otherAuthorName,1);
                        relativeAuthors.add(statisticsInfo);
                    }
                }else{
                    StatisticsInfo statisticsInfo=new StatisticsInfo(otherAuthorName,1);
                    relativeAuthors.add(statisticsInfo);
                }
            }




        }

        return relativeAuthors;
    }

    @Override
    public List<HistoryAuthorEntity> getAuthorByHistoryPaper(String keyWord) {
        List<HistoryAuthorEntity> historyAuthorEntities=new ArrayList<>();

        ArrayList<String> snos=new ArrayList<>();

        List<HistoryPapersEntity> paperList = historyPapersSerivce.getHistoryPaperByName(keyWord);

        for (HistoryPapersEntity paper: paperList){
            snos.add(paper.getSNO());
        }

        historyAuthorEntities=getHistoryAuthorsBySNOs(snos);
        return historyAuthorEntities;
    }

    public List<HistoryAuthorEntity> getHistoryAuthorsBySNOs(ArrayList<String> snos){
        List<HistoryAuthorEntity> historyAuthorEntities=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        boolBuilder.must(QueryBuilders.termsQuery("SNO.keyword", snos));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);

        sourceBuilder.from(0);
        sourceBuilder.size(500); // 获取记录数，默认10

        SearchRequest searchRequest = new SearchRequest("historyauthors");
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
            HistoryAuthorEntity authorEntity=new HistoryAuthorEntity();
            authorEntity.setAuthorName(String.valueOf(s.getSourceAsMap().get("ZZMC")));
            authorEntity.setAuthorInstitution(String.valueOf(s.getSourceAsMap().get("JGMC")));
            authorEntity.setAuthorDepartment(String.valueOf(s.getSourceAsMap().get("TXDZ")));
            boolean hasSameAuthor=false;
            if (historyAuthorEntities!=null && historyAuthorEntities.size()>0){
                for (HistoryAuthorEntity ha:historyAuthorEntities){
                    if (ha.getAuthorName().equals(authorEntity.getAuthorName()) &&
                            ha.getAuthorDepartment().equals(authorEntity.getAuthorDepartment()) &&
                            ha.getAuthorInstitution().equals(authorEntity.getAuthorInstitution())){
                        hasSameAuthor=true;
                        break;
                    }
                }
            }
            if (!hasSameAuthor){
                historyAuthorEntities.add(authorEntity);
            }
        }
        return historyAuthorEntities;
    }
    @Override
    public List<HistoryAuthorEntity> getAuthorInfoByName(String authorName) {
        List<HistoryAuthorEntity> authorEntities=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        boolBuilder.must(QueryBuilders.matchQuery("ZZMC.keyword", authorName));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);

        sourceBuilder.from(0);
        sourceBuilder.size(20); // 获取记录数，默认10

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

        for (SearchHit s:searchHits){
            HistoryAuthorEntity authorEntity=new HistoryAuthorEntity();
            authorEntity.setAuthorName(String.valueOf(s.getSourceAsMap().get("ZZMC")));
            authorEntity.setAuthorInstitution(String.valueOf(s.getSourceAsMap().get("JGMC")));
            authorEntity.setAuthorDepartment(String.valueOf(s.getSourceAsMap().get("TXDZ")));
            boolean hasSameAuthor=false;
            if (authorEntities!=null && authorEntities.size()>0){
                for (HistoryAuthorEntity ha:authorEntities){
                    if (ha.getAuthorName().equals(authorEntity.getAuthorName()) &&
                    ha.getAuthorDepartment().equals(authorEntity.getAuthorDepartment()) &&
                    ha.getAuthorInstitution().equals(authorEntity.getAuthorInstitution())){
                        hasSameAuthor=true;
                        break;
                    }
                }
            }
            if (!hasSameAuthor){
                authorEntities.add(authorEntity);
            }
        }
        return authorEntities;
    }
}
