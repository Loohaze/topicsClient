package com.nju.topics.entitysevice.impl;

import com.nju.topics.config.Config;
import com.nju.topics.domain.TagInfo;
import com.nju.topics.domain.TreeDataInfo;
import com.nju.topics.entity.HistoryPapersEntity;
import com.nju.topics.entitysevice.HistoryPapersSerivce;
import com.nju.topics.utils.MapValueComparator;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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

import java.io.*;
import java.util.*;

@Component
public class HistoryPapersImpl implements HistoryPapersSerivce {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private Config config;

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

    @Override
    public void bulkUpdateTags(ArrayList<String> origin, String tag) {

        String pairFilePath=config.getPairFilePath();
        File pairFile=new File(pairFilePath);
        try {
            if (!pairFile.exists()){
                pairFile.createNewFile();
            }
            OutputStreamWriter pairWriter = new OutputStreamWriter(new FileOutputStream(pairFile,true));
            BufferedWriter pairBufferedWriter = new BufferedWriter(pairWriter);
            pairBufferedWriter.write(tag+":");
            for(String originS:origin){
                pairBufferedWriter.write(originS+",");
            }
            pairBufferedWriter.write("\r\n");
            pairBufferedWriter.flush();
            pairBufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }


        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        for(String originName:origin){
            boolBuilder.should(QueryBuilders.wildcardQuery("LYPM.keyword", "*"+originName+"*"));
            boolBuilder.should(QueryBuilders.wildcardQuery("BYC.keyword", "*"+originName+"*"));

        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);


        sourceBuilder.from(0);
        sourceBuilder.size(2000); // 获取记录数，默认10

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
        ArrayList<String> snos=new ArrayList<>();
        BulkRequest updateBulkRequest=new BulkRequest();
        boolean hasNew=false;
        for (SearchHit s:searchHits){
            if (s.getSourceAsMap().get("Tags")==null || String.valueOf(s.getSourceAsMap().get("Tags")).contains(tag)==false){
                try {
                    XContentBuilder newTagBuilder= XContentFactory.jsonBuilder();
                    newTagBuilder.startObject();
                    {
                        if (s.getSourceAsMap().get("Tags")!=null){
                            newTagBuilder.field("Tags",(s.getSourceAsMap().get("Tags"))+tag+"/");
                        }else{
                            newTagBuilder.field("Tags",tag+"/");
                        }
                    }
                    hasNew=true;
                    newTagBuilder.endObject();
                    UpdateRequest updateRequest=new UpdateRequest(config.getHistoryPaperIndex(),config.getHistoryPaperType(),s.getId())
                            .doc(newTagBuilder);
                    updateBulkRequest.add(updateRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

        if (hasNew){
            try {
                BulkResponse updateBulkResponse=restHighLevelClient.bulk(updateBulkRequest);
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }

    }

    @Override
    public void updateTagByPaperTitle(String title, String tag) {

    }

    @Override
    public ArrayList<TagInfo> getAllTagInfos() {

        ArrayList<TagInfo> resTagInfos=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.matchAllQuery());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);
        sourceBuilder.from(new Random(25).nextInt(9000));
        resTagInfos=dealTagInfoTool(sourceBuilder);
        return resTagInfos;
    }

    @Override
    public ArrayList<TagInfo> getTagInfosByTag(String tagName) {
        ArrayList<TagInfo> resTagInfos=new ArrayList<>();

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.should(QueryBuilders.wildcardQuery("BYC.keyword","*"+tagName+"*"));
        boolBuilder.should(QueryBuilders.wildcardQuery("Tags","*"+tagName+"*"));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);
        sourceBuilder.from(0);

        resTagInfos=dealTagInfoTool(sourceBuilder);
        return resTagInfos;
    }

    public ArrayList<TagInfo> dealTagInfoTool(SearchSourceBuilder sourceBuilder){

        ArrayList<TagInfo> resTagInfos=new ArrayList<>();

        sourceBuilder.size(3000); // 获取记录数，默认10
        sourceBuilder.fetchSource(new String[] { "LYPM", "BYC","Tags" }, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
        SearchRequest searchRequest = new SearchRequest("historypapers");
//        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        SearchResponse response=new SearchResponse();
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }

        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit s:searchHits){
            TagInfo tagInfo=new TagInfo();
            tagInfo.setTitle(String.valueOf(s.getSourceAsMap().get("LYPM")));

            String BYC=String.valueOf(s.getSourceAsMap().get("BYC"));
            String Tags=String.valueOf(s.getSourceAsMap().get("Tags"));
            String[] BYCSplit=BYC.split("/");
            HashMap<String,Integer> tempHashMap=new HashMap<>();
            for(String eachByc:BYCSplit){
                if (eachByc!=null && eachByc.length()>0 && !eachByc.equals("null")){
                    tempHashMap.put(eachByc,1);
                }
            }
            if (Tags!=null && Tags.length()>0){
                String[] TagsSplit=Tags.split("/");
                for(String eachTag:TagsSplit){
                    if (eachTag!=null && eachTag.length()>0 && !eachTag.equals("null")){
                        tempHashMap.put(eachTag,1);
                    }
                }
            }

            ArrayList<String> tags=new ArrayList<>();
            for(Map.Entry<String ,Integer> entry:tempHashMap.entrySet()){
                tags.add(entry.getKey());
            }

            tagInfo.setTags(tags);
            resTagInfos.add(tagInfo);
        }
        return resTagInfos;
    }

    @Override
    public void bulkUpdateTagsByTitle(String originName, ArrayList<String> tags) {
        String tagString="";
        for(String eachTag:tags){
            tagString=tagString+eachTag+"/";
        }

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();


        boolBuilder.should(QueryBuilders.wildcardQuery("LYPM.keyword", "*"+originName+"*"));


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);


        sourceBuilder.from(0);
        sourceBuilder.size(2000); // 获取记录数，默认10

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
        ArrayList<String> snos=new ArrayList<>();
        BulkRequest updateBulkRequest=new BulkRequest();
        boolean hasNew=false;
        for (SearchHit s:searchHits){
            try {
                XContentBuilder newTagBuilder= XContentFactory.jsonBuilder();
                newTagBuilder.startObject();
                {
                    if (s.getSourceAsMap().get("Tags")!=null){
                        newTagBuilder.field("Tags",(s.getSourceAsMap().get("Tags"))+tagString);
                    }else{
                        newTagBuilder.field("Tags",tagString);
                    }
                }
                hasNew=true;
                newTagBuilder.endObject();
                UpdateRequest updateRequest=new UpdateRequest(config.getHistoryPaperIndex(),config.getHistoryPaperType(),s.getId())
                        .doc(newTagBuilder);
                updateBulkRequest.add(updateRequest);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        if (hasNew){
            try {
                BulkResponse updateBulkResponse=restHighLevelClient.bulk(updateBulkRequest);
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }

    }

    @Override
    public ArrayList<String> getSomeTags() {

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.matchAllQuery());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(20000); // 获取记录数，默认10
        sourceBuilder.fetchSource(new String[] { "BYC","Tags" }, new String[] {}); // 第一个是获取字段，第二个是过滤的字段，默认获取全部
        SearchRequest searchRequest = new SearchRequest("historypapers");
//        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        SearchResponse response=new SearchResponse();
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }

        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        Map<String,Integer> countMap=new HashMap<>();
        for (SearchHit s:searchHits){
            String BYC=String.valueOf(s.getSourceAsMap().get("BYC"));
            String Tags=String.valueOf(s.getSourceAsMap().get("Tags"));
            String[] BYCSplit=BYC.split("/");
            HashMap<String,Integer> tempHashMap=new HashMap<>();
            for(String eachByc:BYCSplit){
                if (eachByc!=null && eachByc.length()>0 && !eachByc.equals("null")){
                    tempHashMap.put(eachByc,1);
                }
            }
            if (Tags!=null && Tags.length()>0){
                String[] TagsSplit=Tags.split("/");
                for(String eachTag:TagsSplit){
                    if (eachTag!=null && eachTag.length()>0 && !eachTag.equals("null")){
                        tempHashMap.put(eachTag,1);
                    }
                }
            }


            for(Map.Entry<String ,Integer> entry:tempHashMap.entrySet()){
                if (countMap.containsKey(entry.getKey())){
                    countMap.put(entry.getKey(),countMap.get(entry.getKey())+entry.getValue());
                }else{
                    countMap.put(entry.getKey(),entry.getValue());
                }
            }

        }

        ArrayList<String> tags=new ArrayList<>();
        Map<String,Integer> sortMap=new TreeMap<String,Integer>(new MapValueComparator(countMap));
        sortMap.putAll(countMap);
        for (Map.Entry<String,Integer> sortEntry:sortMap.entrySet()){
            tags.add(sortEntry.getKey());
        }
        return tags;
    }
}
