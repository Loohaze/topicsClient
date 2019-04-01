package com.nju.topics.web;

import com.nju.topics.config.Config;
import com.nju.topics.domain.DictFileInfo;
import com.nju.topics.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private Config config;

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        return fileService.uploadSegmentFile(file);
    }

    @RequestMapping("/getAllSegmentFiles")
    @ResponseBody
    public ArrayList<String> getAllSegmentFiles(){
        return fileService.getAllSegmentFile();
    }

    @RequestMapping("/getAllDictFiles")
    @ResponseBody
    public ArrayList<DictFileInfo> getAllDictFiles(){
        return fileService.getAllDictDile();
    }

    @RequestMapping("/download/{fileName}")
    @ResponseBody
    public void download( HttpServletResponse response,@PathVariable("fileName")String fileName){
        String filePath=config.getDownloadPath()+fileName;
        File file=new File(filePath);
        //当文件存在
        if(file.exists()){
            //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
            response.setContentType("application/force-download");
            //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
            response.addHeader("Content-Disposition",String.format("attachment; filename=\"%s\"", file.getName()));
            //进行读写操作
            byte[]buffer=new byte[1024];
            FileInputStream fis=null;
            BufferedInputStream bis=null;
            try{
                fis=new FileInputStream(file);
                bis=new BufferedInputStream(fis);
                OutputStream os=response.getOutputStream();
                //从源文件中读
                int i=bis.read(buffer);
                while(i!=-1){
                    //写到response的输出流中
                    os.write(buffer,0,i);
                    i=bis.read(buffer);
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                //善后工作，关闭各种流
                try {
                    if(bis!=null){
                        bis.close();
                    }
                    if(fis!=null){
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
