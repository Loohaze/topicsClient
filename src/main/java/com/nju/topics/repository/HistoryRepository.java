package com.nju.topics.repository;

import com.nju.topics.entity.HistoryPaperInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface HistoryRepository extends ElasticsearchRepository<HistoryPaperInfo,String> {
}
