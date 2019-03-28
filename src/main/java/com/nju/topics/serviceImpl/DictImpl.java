package com.nju.topics.serviceImpl;

import com.nju.topics.config.Config;
import com.nju.topics.service.Dict;

import java.io.*;
import java.util.HashMap;

public class DictImpl implements Dict {

    @Override
    public void addWord(String word) {

        HashMap<String, Integer> dictMap = new HashMap<>();
        File dict = null;
        try {
            dict = new File("src/main/resources/dictFile/dict.txt");
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
}
