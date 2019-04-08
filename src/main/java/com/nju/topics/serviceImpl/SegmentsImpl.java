package com.nju.topics.serviceImpl;

import com.nju.topics.config.Config;
import com.nju.topics.domain.Segment;
import com.nju.topics.service.Segments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class SegmentsImpl implements Segments {

    @Autowired
    private Config config;

    @Autowired
    public SegmentsImpl() {
    }

    @Override
    public List<Segment> getAllSegments(String segName) {
        List<Segment> result = new ArrayList<>();
        File segmentsFile = null;
        try {
            String segPath=config.getUploadPath()+segName;
            System.out.println("请求获取分词文件：路径是——"+segPath);
            segmentsFile = ResourceUtils.getFile(segPath);
//            segmentsFile = ResourceUtils.getFile("classpath:documents/segments.txt");
            if (segmentsFile.exists()) {

                InputStreamReader read = new InputStreamReader(new FileInputStream(segmentsFile),"utf8");
                BufferedReader bufferedReader = new BufferedReader(read);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] segData = line.split(" ");
                    String[] segs = segData[1].split(",");

                    Segment segment = new Segment();
                    segment.setTitle(segData[0]);
                    List<String> segments = new ArrayList<>();
                    Collections.addAll(segments, segs);
                    segment.setSegments(segments);
                    result.add(segment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String reRunPy(String segFileName) {
        long oldTime=config.getLastPyRunTime();
        System.out.println("oldTime:"+oldTime);
        long nowTime=System.currentTimeMillis();
        System.out.println("nowTime:"+nowTime);

        long duration=(nowTime-oldTime)/(1000*60);
        if(duration<4){
            return "距离上一次重新分词:"+duration+"分钟，请在 "+(4-duration)+" 分钟后再试一下";
        }
        config.setLastPyRunTime(nowTime);
//        源文件
        String oldSegPath=config.getUploadPath()+segFileName;
        String oldDictPath=config.getDownloadPath()+segFileName;
//        备份文件
        String newSegBackUpPath=config.getSegBackUpPath()+(segFileName.split("\\."))[0]+nowTime+".txt";
        String newDictBackUpPath=config.getDictBackUpPath()+(segFileName.split("\\."))[0]+nowTime+".txt";

        FileReader segReader=null;
        FileWriter segWriter=null;
        FileReader dictReader=null;
        FileWriter dictWriter=null;

        try {
            segReader=new FileReader(oldSegPath);
            segWriter=new FileWriter(newSegBackUpPath);
            dictReader=new FileReader(oldDictPath);
            dictWriter=new FileWriter(newDictBackUpPath);
            int segLen=0;
            while ((segLen=segReader.read())!=-1){
                segWriter.write((char)segLen);
            }

            int dictLen=0;
            while ((dictLen=dictReader.read())!=-1){
                dictWriter.write((char)dictLen);
            }
        }catch (FileNotFoundException fe){
            fe.printStackTrace();
            return "FileNotFound";
        }
        catch (IOException ie){
            ie.printStackTrace();
            return "backUpFailure";
        }
        finally {
            if(segReader!=null){
                try {
                    segReader.close();
                }catch (IOException ie1){
                    ie1.printStackTrace();
                }
            }

            if (segWriter!=null){
                try {
                    segWriter.close();
                }catch (IOException ie2){
                    ie2.printStackTrace();
                }
            }

            if (dictReader!=null){
                try {
                    dictReader.close();
                }catch (IOException ie3){
                    ie3.printStackTrace();
                }
            }


            if (dictWriter!=null){
                try {
                    dictWriter.close();
                }catch (IOException ie4){
                    ie4.printStackTrace();
                }
            }
        }

        String cmd="python "+config.getPyPath()+config.getReRunFileName()+" "+oldSegPath+" "+oldDictPath;
        Process process;
        try {
            System.out.println("开始执行命令:"+cmd);
            process=Runtime.getRuntime().exec(cmd);
            process.waitFor();

        }catch (IOException e){
            e.printStackTrace();
            System.err.println("执行python时发生异常");
            return "执行失败";
        }catch (InterruptedException ie){
            ie.printStackTrace();
            System.err.println("执行waitFor异常");
            return "执行失败";
        }

        return "success";
    }
}
