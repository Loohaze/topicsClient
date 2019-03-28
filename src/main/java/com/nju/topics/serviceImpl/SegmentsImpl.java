package com.nju.topics.serviceImpl;

import com.nju.topics.domain.Segment;
import com.nju.topics.service.Segments;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SegmentsImpl implements Segments {

    @Override
    public List<Segment> getAllSegments() {
        List<Segment> result = new ArrayList<>();
        File segmentsFile = null;
        try {
            segmentsFile = ResourceUtils.getFile("classpath:documents/segments.txt");
            if (segmentsFile.exists()) {

                InputStreamReader read = new InputStreamReader(new FileInputStream(segmentsFile),"utf8");
                BufferedReader bufferedReader = new BufferedReader(read);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] segData = line.split(" ");
                    String[] segs = segData[1].split(",");

                    Segment segment = new Segment();
                    segment.setTitle(segData[0]);
                    List<String> segments = new ArrayList<>();
                    Collections.addAll(segments, segs);
                    segment.setSegments(segments);
                    result.add(segment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
