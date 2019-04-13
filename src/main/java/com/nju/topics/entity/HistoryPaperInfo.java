package com.nju.topics.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Document(indexName = "historypapers",type = "history")
public class HistoryPaperInfo implements Serializable {
    @Id
    private String paperId;

    private String paperName;
//    private ArrayList<String> paperAuthors;
    private String paperVenueNo;
    private String paperVenueName;

    public HistoryPaperInfo(String id,String name ,String venueNo,String venueName){
        this.paperId=id;
        this.paperName=name;
//        this.paperAuthors=authors;
        this.paperVenueNo=venueNo;
        this.paperVenueName=venueName;
    }

    public HistoryPaperInfo(){

    }

    public String getPaperId() {
        return paperId;
    }

//    public ArrayList<String> getPaperAuthors() {
//        return paperAuthors;
//    }

    public String getPaperName() {
        return paperName;
    }

    public String getPaperVenueNo() {
        return paperVenueNo;
    }

    public String getPaperVenueName() {
        return paperVenueName;
    }

//    public void setPaperAuthors(ArrayList<String> paperAuthors) {
//        this.paperAuthors = paperAuthors;
//    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public void setPaperVenueNo(String paperVenueNo) {
        this.paperVenueNo = paperVenueNo;
    }

    public void setPaperVenueName(String paperVenueName) {
        this.paperVenueName = paperVenueName;
    }
}
