package com.nju.topics.serviceTest;

import com.nju.topics.domain.Segment;
import com.nju.topics.service.Segments;
import com.nju.topics.serviceImpl.SegmentsImpl;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SegmentsTest {

    @Test
    public void getAllSegsTest(){
        Segments segments = new SegmentsImpl();
        List<Segment> segmentList = segments.getAllSegments();
        for (Segment s : segmentList) {
            System.out.println(s.toString());
        }
    }
}
