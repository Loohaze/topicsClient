package com.nju.topics.web;

import com.nju.topics.domain.StatisticsInfo;
import com.nju.topics.domain.TreeDataInfo;
import com.nju.topics.entity.HistoryAuthorEntity;
import com.nju.topics.entity.HistoryPapersEntity;
import com.nju.topics.entitysevice.HistoryAuthorsService;
import com.nju.topics.entitysevice.HistoryPapersSerivce;
import com.nju.topics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;
    @Autowired
    private HistoryPapersSerivce historyPapersSerivce;
    @Autowired
    private HistoryAuthorsService historyAuthorsService;

    @RequestMapping("/getAllDirections")
    @ResponseBody
    public ArrayList<String> getAllDirections(){
        return statisticsService.getAllStatisticsDictNames();
    }

    @RequestMapping("/getAllKeyWords/{dictName}/{attribute}")
    @ResponseBody
    public ArrayList<StatisticsInfo> getAllKeyWords(@PathVariable("dictName")String dictName, @PathVariable("attribute")String attribute){
        return statisticsService.getAllKeyWords(dictName,attribute);
    }

    @RequestMapping("/getOneKeyAttribute/{dictName}/{attributeName}/{keyword}")
    @ResponseBody
    public ArrayList<StatisticsInfo> getOneKeyAttribute(@PathVariable("dictName")String dictName, @PathVariable("attributeName")String attributeName,
                                                        @PathVariable("keyword")String keyword){
        return statisticsService.getOneAttribute(dictName,attributeName,keyword);
    }

    @RequestMapping("/getPapersByName/{paperName}")
    @ResponseBody
    public List<HistoryPapersEntity> getPapersByName(@PathVariable("paperName")String paperName){
        return historyPapersSerivce.getHistoryPaperByName(paperName);
    }

    @RequestMapping("/getPapersByAuthor/{author}")
    @ResponseBody
    public List<HistoryPapersEntity> getPapersByAuthor(@PathVariable("author")String author){
        return historyPapersSerivce.getHistoryPapersByAuthor(author);
    }

    @RequestMapping("/getAuthorInfo/{author}")
    @ResponseBody
    public List<HistoryAuthorEntity> getAuthorInfo(@PathVariable("author")String author){
        return historyAuthorsService.getAuthorInfoByName(author);
    }

    @RequestMapping("/getAuthorPapersTreeData/{author}")
    @ResponseBody
    public TreeDataInfo getAuthorInfoTreeData(@PathVariable("author")String author){
        return historyPapersSerivce.getHistoryPapersByAuthorAggratedByYear(author);
    }

    @RequestMapping("/getRelativeAuthors/{author}")
    @ResponseBody
    public ArrayList<StatisticsInfo> getRelativeAuthors(@PathVariable("author")String author){
        return historyAuthorsService.getRelativeAuthorByAuthor(author);
    }

}
