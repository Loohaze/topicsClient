package com.nju.topics.web;

import com.nju.topics.domain.Segment;
import com.nju.topics.service.Segments;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public List<Segment> getSegments(){
        return segments.getAllSegments();
    }
}
