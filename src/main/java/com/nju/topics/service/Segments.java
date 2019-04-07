package com.nju.topics.service;

import com.nju.topics.domain.Segment;

import java.util.List;

public interface Segments {

    public List<Segment> getAllSegments(String segName);

    public String reRunPy(String segFileName);
}
