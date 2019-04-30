package com.nju.topics.dao;

import com.nju.topics.domain.TagInfo;
import com.nju.topics.domain.TreeDataInfo;
import com.nju.topics.entity.HistoryPapersEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HistoryPapersSerivce {

    public List<HistoryPapersEntity> getAllPapers();

    public List<HistoryPapersEntity> getHistoryPaperByName(String paperName);

    public List<HistoryPapersEntity> getHistoryPapersByFuzzyName(String fuzzyName);

    public List<HistoryPapersEntity> getHistoryPapersByAuthor(String author);

    public TreeDataInfo getHistoryPapersByAuthorAggratedByYear(String author);

    public void bulkUpdateTags(ArrayList<String> origin,String tag);

    public void updateTagByPaperTitle(String title,String tag);

    public ArrayList<TagInfo> getAllTagInfos();

    public ArrayList<TagInfo> getTagInfosByTag(String tagName);

    public ArrayList<TagInfo> getTagInfosByBeginEnd(int begin,int end);

    public void bulkUpdateTagsByTitle(String originName,ArrayList<String> tags);

    public ArrayList<String> getSomeTags();

    public Map<String,Integer> getAllTagsAndTimes();

    public long getTotalRecordsNum(String index);
}
