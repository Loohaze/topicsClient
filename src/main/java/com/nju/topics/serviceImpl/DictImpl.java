package com.nju.topics.serviceImpl;

import com.nju.topics.config.Config;
import com.nju.topics.service.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictImpl implements Dict {

    @Autowired
    private Config config;

    @Autowired
    public DictImpl() {
    }

    @Override
    public void addWord(String dictName,String word) {

        HashMap<String, Integer> dictMap = new HashMap<>();
        File dict = null;
        try {
            String dictPath=config.getDownloadPath()+dictName;
            dict = ResourceUtils.getFile(dictPath);
//            dict = new File("src/main/resources/dictFile/dict.txt");
            if (dict.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(dict), "utf8");
                BufferedReader bufferedReader = new BufferedReader(read);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] dictData = line.split(" ");
                    dictMap.put(dictData[0], Integer.parseInt(dictData[1]));
                }

                if (!dictMap.containsKey(word)) {
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dict,true));
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(word + " " + Config.DEFAULT_NUM+System.getProperty("line.separator"));
                    bufferedWriter.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void addWordList(String dictName, List<String> words) {
        HashMap<String, Integer> dictMap = new HashMap<>();
        File dict = null;
        try {
            String dictPath=config.getDownloadPath()+dictName;
            dict = ResourceUtils.getFile(dictPath);
//            dict = new File("src/main/resources/dictFile/dict.txt");
            if (dict.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(dict), "utf8");
                BufferedReader bufferedReader = new BufferedReader(read);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] dictData = line.split(" ");
                    dictMap.put(dictData[0], Integer.parseInt(dictData[1]));
                }

                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dict,true));
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                for(String word : words) {
                    if (!dictMap.containsKey(word)) {
                        bufferedWriter.write(word + " " + Config.DEFAULT_NUM+System.getProperty("line.separator"));
                    }
                }
                bufferedWriter.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getAllWords(String dictName) {
        List<String> result = new ArrayList<>();
        File dict = null;

        try {
            String dictPath=config.getDownloadPath()+dictName;
            dict = ResourceUtils.getFile(dictPath);
//            dict = new File("src/main/resources/dictFile/dict.txt");
            if (dict.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(dict), "utf8");
                BufferedReader bufferedReader = new BufferedReader(read);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] dictData = line.split(" ");
                    result.add(dictData[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public void deleteWord(String dictName,String word) {
        HashMap<String,Integer> dictMap = new HashMap<>();
        File dict = null;
        try {
            String dictPath=config.getDownloadPath()+dictName;
            dict = ResourceUtils.getFile(dictPath);
//            dict = new File("src/main/resources/dictFile/dict.txt");
            if (dict.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(dict), "utf8");
                BufferedReader bufferedReader = new BufferedReader(read);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] dictData = line.split(" ");
                    dictMap.put(dictData[0], Integer.parseInt(dictData[1]));
                }

                dictMap.remove(word);

                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dict));
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                for (Map.Entry<String,Integer> entry : dictMap.entrySet()) {
                    bufferedWriter.write(entry.getKey()+ " " + entry.getValue() + System.getProperty("line.separator"));
                }
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
