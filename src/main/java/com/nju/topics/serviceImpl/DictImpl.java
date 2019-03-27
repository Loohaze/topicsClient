package com.nju.topics.serviceImpl;

import com.nju.topics.service.Dict;
import org.springframework.util.ResourceUtils;

import java.io.*;

public class DictImpl implements Dict {

    @Override
    public void addWord(String word) {
        File dict = null;
        try {
            dict = ResourceUtils.getFile("classpath:documents/dict.txt");
            System.out.println(dict.getName());
            if (dict.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(dict),"utf8");
                BufferedReader bufferedReader = new BufferedReader(read);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                        
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
