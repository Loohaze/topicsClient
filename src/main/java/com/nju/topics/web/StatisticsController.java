package com.nju.topics.web;

import com.nju.topics.domain.StatisticsInfo;
import com.nju.topics.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

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
}
