package com.nju.topics.service;

import java.util.List;

public interface Dict {

    public void addWord(String dictName,String word);

    public void addWordList(String dictName, List<String> words);

    public List<String> getAllWords(String dictName);

    public void deleteWord(String dictName,String word);

}
