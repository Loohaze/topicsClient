package com.nju.topics.serviceTest;

import com.nju.topics.service.Dict;
import com.nju.topics.serviceImpl.DictImpl;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DictTest {

    @Test
    public void addWordsTest(){
        Dict dict = new DictImpl();
        dict.addWord();
    }
}
