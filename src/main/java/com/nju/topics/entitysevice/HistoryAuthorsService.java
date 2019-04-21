package com.nju.topics.entitysevice;

import com.nju.topics.domain.StatisticsInfo;
import com.nju.topics.entity.HistoryAuthorEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface HistoryAuthorsService {

    public List<HistoryAuthorEntity> getAuthorInfoByName(String authorName);

    public ArrayList<StatisticsInfo> getRelativeAuthorByAuthor(String authorName);

    public List<HistoryAuthorEntity> getAuthorByHistoryPaper(String keyWord);
}
