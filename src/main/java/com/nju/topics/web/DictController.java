package com.nju.topics.web;

import com.nju.topics.service.Dict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    private final Dict dict;

    public DictController(Dict dict) {
        this.dict = dict;
    }

    @GetMapping("/getDict")
    public List<String> getAllWords() {
        return dict.getAllWords();
    }

    @PostMapping("/addDict/{newSeg}")
    public void addWord(@PathVariable("newSeg") String word) {
        dict.addWord(word);
    }

    @PostMapping("/delete/{seg}")
    public void deleteWord(@PathVariable("seg") String word) {
        dict.deleteWord(word);
    }
}
