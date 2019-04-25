package com.nju.topics.web;

import com.nju.topics.domain.StatisticsInfo;
import com.nju.topics.domain.TreeDataInfo;
import com.nju.topics.entity.HistoryAuthorEntity;
import com.nju.topics.entity.HistoryPapersEntity;
import com.nju.topics.dao.HistoryAuthorsService;
import com.nju.topics.dao.HistoryPapersSerivce;
import com.nju.topics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        if(dictName.equals("historyES")){
            return statisticsService.getAllKeyWordsByES();
        }else{
            return statisticsService.getAllKeyWords(dictName,attribute);
        }
    }

    @RequestMapping("/getOneKeyAttribute/{dictName}/{attributeName}/{keyword}")
    @ResponseBody
    public ArrayList<StatisticsInfo> getOneKeyAttribute(@PathVariable("dictName")String dictName, @PathVariable("attributeName")String attributeName,
                                                        @PathVariable("keyword")String keyword){
        if(attributeName.equals("author")){
            return statisticsService.getAuthorAttributeByES(keyword);
        }else if(attributeName.equals("institution")){
            return  statisticsService.getInstitutionAttributeByES(keyword);
        }else{
            return null;
        }
//        if(dictName.equals("historyES")){
//            if(attributeName.equals("author")){
//                return statisticsService.getAuthorAttributeByES(keyword);
//            }else if(attributeName.equals("institution")){
//                return  statisticsService.getInstitutionAttributeByES(keyword);
//            }else{
//                return null;
//            }
//        }else{
//            return statisticsService.getOneAttribute(dictName,attributeName,keyword);
//        }

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
