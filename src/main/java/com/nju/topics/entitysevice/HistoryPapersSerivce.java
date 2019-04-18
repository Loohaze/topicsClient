package com.nju.topics.entitysevice;

import com.nju.topics.domain.TagInfo;
import com.nju.topics.domain.TreeDataInfo;
import com.nju.topics.entity.HistoryPapersEntity;

import java.util.ArrayList;
import java.util.List;

public interface HistoryPapersSerivce {

    public List<HistoryPapersEntity> getHistoryPaperByName(String paperName);

    public List<HistoryPapersEntity> getHistoryPapersByFuzzyName(String fuzzyName);

    public List<HistoryPapersEntity> getHistoryPapersByAuthor(String author);

    public TreeDataInfo getHistoryPapersByAuthorAggratedByYear(String author);

    public void bulkUpdateTags(ArrayList<String> origin,String tag);

    public void updateTagByPaperTitle(String title,String tag);

    public ArrayList<TagInfo> getAllTagInfos();

    public ArrayList<TagInfo> getTagInfosByTag(String tagName);

    public void bulkUpdateTagsByTitle(String originName,ArrayList<String> tags);

    public ArrayList<String> getSomeTags();
}
