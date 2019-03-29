package com.nju.topics.serviceTest;

import com.nju.topics.service.Dict;
import com.nju.topics.serviceImpl.DictImpl;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DictTest {

    @Test
    public void addWordsTest(){
        Dict dict = new DictImpl();
        dict.addWord("马克思主义");
    }

    @Test
    public void getAllWordsTest() {
        Dict dict = new DictImpl();
        List<String> result = dict.getAllWords();
        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void deleteWordTest(){
        Dict dict = new DictImpl();
        dict.deleteWord("毛泽东思想");
    }
}
