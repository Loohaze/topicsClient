package com.nju.topics.web;

import com.nju.topics.domain.TagInfo;
import com.nju.topics.entitysevice.HistoryPapersSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private HistoryPapersSerivce historyPapersSerivce;

    /***
     *
     * @param
     * @return
     */
    @RequestMapping("/bulkAddTags/{addInfo}")
    @ResponseBody
    public String addTags(@PathVariable("addInfo")String tagInfo){

        String[] tagInfoSplit=tagInfo.split(":");
        String tag=tagInfoSplit[0];
        String origins=tagInfoSplit[1];

        ArrayList<String> originList=new ArrayList<>();
        String[] originSplit=origins.split("-");
        for(String eachOriginSplit:originSplit){

            if (eachOriginSplit.length()>0){
                originList.add(eachOriginSplit);
            }
        }
        historyPapersSerivce.bulkUpdateTags(originList,tag);
        return "success";
    }

    @RequestMapping("/addTitleTag/{titleTagInfo}")
    @ResponseBody
    public String addTitleTag(@PathVariable("titleTagInfo")String  titleTagInfo){
        String[] titleTagInfoSplit=titleTagInfo.split(":");
        String titleName=titleTagInfoSplit[0];
        String[] titleTagSplit=titleTagInfoSplit[1].split("-");
        ArrayList<String> tagList=new ArrayList<>();
        for(String eachTagSplit:titleTagSplit){
            if (eachTagSplit.length()>0){
                tagList.add(eachTagSplit);
            }
        }

        historyPapersSerivce.bulkUpdateTagsByTitle(titleName,tagList);
        return "success";
    }

    @RequestMapping("/getAllTags")
    @ResponseBody
    public ArrayList<TagInfo> getAllTags(){
        return historyPapersSerivce.getAllTagInfos();
    }

    @RequestMapping("/getTagInfosByTag/{tagName}")
    @ResponseBody
    public ArrayList<TagInfo> getTagInfosByTagName(@PathVariable("tagName")String tagName){
        return historyPapersSerivce.getTagInfosByTag(tagName);
    }

    @RequestMapping("/getTagList")
    @ResponseBody
    public ArrayList<String> getTagList(){
        return historyPapersSerivce.getSomeTags();
    }
}
