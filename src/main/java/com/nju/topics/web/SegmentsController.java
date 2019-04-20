package com.nju.topics.web;

import com.nju.topics.domain.Segment;
import com.nju.topics.service.Segments;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seg")
public class SegmentsController {

    private final Segments segments;

    public SegmentsController(Segments segments) {
        this.segments = segments;
    }

    @GetMapping("/getSegments/{segName}")
    public List<Segment> getSegments(@PathVariable("segName")String segName){
        return segments.getAllSegments(segName);
    }

    @GetMapping("/getPreSegments/{segName}")
    public List<Segment> getPreSegments(@PathVariable("segName")String segName){
        return segments.getPreSegments(segName);
    }

    @RequestMapping("/rerun/{segFileName}")
    public String reRunPy(@PathVariable("segFileName")String segFileName){
        return segments.reRunPy(segFileName);
    }
}
