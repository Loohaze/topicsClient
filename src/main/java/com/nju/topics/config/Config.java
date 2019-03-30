package com.nju.topics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import org.springframework.context.annotation.Configuration;

@Component
public class Config {
    public static final int DEFAULT_NUM = 10;

    @Value("${seg.segments.path}")
    private String segmentsPath;

    @Value("${seg.dict.path}")
    private String dictPath;

    public String getSegmentsPath() {
        return segmentsPath;
    }

    public String getDictPath() {
        return dictPath;
    }
}
