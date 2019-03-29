package com.nju.topics.service;

import java.util.List;

public interface Dict {

    public void addWord(String word);

    public List<String> getAllWords();

    public void deleteWord(String word);

}
