package com.nju.topics.entitysevice;

import com.nju.topics.domain.TreeDataInfo;
import com.nju.topics.entity.HistoryPapersEntity;

import java.util.List;

public interface HistoryPapersSerivce {

    public List<HistoryPapersEntity> getHistoryPaperByName(String paperName);

    public List<HistoryPapersEntity> getHistoryPapersByFuzzyName(String fuzzyName);

    public List<HistoryPapersEntity> getHistoryPapersByAuthor(String author);

    public TreeDataInfo getHistoryPapersByAuthorAggratedByYear(String author);
}
