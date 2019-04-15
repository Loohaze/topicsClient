package com.nju.topics.service;

import com.nju.topics.domain.DictFileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;

public interface FileService {
    public ArrayList<String> getAllSegmentFile();

    public ArrayList<DictFileInfo> getAllDictDile();

    public String uploadSegmentFile(MultipartFile file);

    public String deleteSegmentFile(String name);

    public String getDictLog(String name);
}
