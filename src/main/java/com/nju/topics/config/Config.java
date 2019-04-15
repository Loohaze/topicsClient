package com.nju.topics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
//import org.springframework.context.annotation.Configuration;

@Component
public class Config {
    public static final int DEFAULT_NUM = 10;

    @Value("${seg.segments.path}")
    private String segmentsPath;

    @Value("${seg.dict.path}")
    private String dictPath;

    @Value("${seg.py.path}")
    private String pyPath;

    @Value("${web.upload.file}")
    private String uploadPath;

    @Value("${web.download.file}")
    private String downloadPath;

    @Value("${seg.backup.path}")
    private String segBackUpPath;

    @Value("${dict.backup.path}")
    private String dictBackUpPath;

    @Value("${rerun.file.name}")
    private String reRunFileName;

    @Value("${statistics.parent.folder}")
    private String statisticsFolder;

    @Value("${dict.log.path}")
    private String dictLogPath;

    @Value("${rerun.interval.minutes}")
    private int intervalTime;

    public String[] noStatisticsWords={"的","发展","研究","新","史学","历史","中","年","中心","时期","综述","史","笔谈","关系","°","世纪","º","ƒ","年代","古代",
    "∑","µ","±","Œ","÷","与","–","体系", "相关","∂","区域", "∞","π","Ω", "Ã", "™","Ø","≥","∫","ø","¡","œ","2013","˙","À","∆","ÿ"};

    private Long lastPyRunTime=new Date("1999/01/01 00:00:00").getTime();


    public String getSegmentsPath() {
        return segmentsPath;
    }

    public String getDictPath() {
        return dictPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public String getPyPath() {
        return pyPath;
    }

    public String getDictBackUpPath() {
        return dictBackUpPath;
    }

    public String getSegBackUpPath() {
        return segBackUpPath;
    }

    public Long getLastPyRunTime() {
        return lastPyRunTime;
    }

    public void setLastPyRunTime(Long lastPyRunTime) {
        this.lastPyRunTime = lastPyRunTime;
    }

    public String getReRunFileName() {
        return reRunFileName;
    }

    public String getStatisticsFolder() {
        return statisticsFolder;
    }

    public String getDictLogPath() {
        return dictLogPath;
    }

    public int getIntervalTime() {
        return intervalTime;
    }
}
