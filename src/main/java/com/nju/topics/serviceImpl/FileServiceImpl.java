package com.nju.topics.serviceImpl;

import com.nju.topics.config.Config;
import com.nju.topics.domain.DictFileInfo;
import com.nju.topics.service.Dict;
import com.nju.topics.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


@Component
public class FileServiceImpl implements FileService {

    @Autowired
    private Config config;

    @Override
    public ArrayList<String> getAllSegmentFile() {
        ArrayList<String> segFileNames=new ArrayList<>();
        String folder=config.getUploadPath();
        File segFolder=new File(folder);
        if (!segFolder.exists() || !segFolder.isDirectory()){
            segFolder.mkdirs();

        }

        File[] files=segFolder.listFiles();
        for (File file:files){
            segFileNames.add(file.getName());
        }
        return segFileNames;
    }

    @Override
    public ArrayList<DictFileInfo> getAllDictDile() {
        ArrayList<DictFileInfo> dictFileInfos=new ArrayList<>();
        String folder=config.getDownloadPath();
        File dictFolder=new File(folder);
        if(!dictFolder.exists() || !dictFolder.isDirectory()){
            dictFolder.mkdirs();

        }

        File[] files=dictFolder.listFiles();
        for(File file:files){
            String name=file.getName();
            long byteLen=file.length();
            long kbLen=byteLen/1024;
            String path=file.getAbsolutePath();
            DictFileInfo dictFileInfo=new DictFileInfo(name,kbLen,path);
            dictFileInfos.add(dictFileInfo);
        }
        return dictFileInfos;
    }

    @Override
    public String uploadSegmentFile(MultipartFile file) {
        String name=file.getOriginalFilename();
        System.out.println(name);
        ArrayList<String> existFileNames=getAllSegmentFile();
        boolean flag=false;
        if(existFileNames!=null && existFileNames.size()>0){
            for(int i=0;i<existFileNames.size();i++){
                if (name.equals(existFileNames.get(i))){
                    flag=true;
                    break;
                }
            }
        }

        if (flag){
            String originFilePath=config.getUploadPath()+name;
            File originFile=new File(originFilePath);
            originFile.delete();
        }
        String filePath=config.getUploadPath()+name;
        File newSegFile=new File(filePath);

        String folder=config.getDownloadPath();
        File dictFolder=new File(folder);
        if(!dictFolder.exists() || !dictFolder.isDirectory()){
            dictFolder.mkdirs();

        }
        String dictPath=config.getDownloadPath()+name;
        File dictFile=new File(dictPath);
        try {
            dictFile.createNewFile();
            file.transferTo(newSegFile);
            return "success";
        }catch (IOException e){
            e.printStackTrace();
        }
        return "error";
    }

    @Override
    public String deleteSegmentFile(String name) {
        String path=config.getUploadPath()+name;
        File file=new File(path);
        if (file.exists()){
            file.delete();
        }
        return "success";
    }
}
