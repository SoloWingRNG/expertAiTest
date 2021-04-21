package com.ai.expert.test.dao;

import com.ai.expert.test.entity.response.Topic;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends GenericRepository<Topic,Long>{
}
