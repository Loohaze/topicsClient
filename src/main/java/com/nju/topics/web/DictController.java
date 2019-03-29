package com.nju.topics.web;

import com.nju.topics.service.Dict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    private final Dict dict;

    public DictController(Dict dict) {
        this.dict = dict;
    }

    @GetMapping()
    public List<String> getAllWords() {
        return dict.getAllWords();
    }

    @PostMapping()
    public void addWord(String word) {
        dict.addWord(word);
    }

    @PostMapping("/delete")
    public void deleteWord(String word) {
        dict.deleteWord(word);
    }
}
