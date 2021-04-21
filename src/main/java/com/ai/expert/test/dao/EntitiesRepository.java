package com.ai.expert.test.dao;

import com.ai.expert.test.entity.response.Entity;
import org.springframework.stereotype.Repository;

@Repository
public interface EntitiesRepository extends GenericRepository<Entity, Long> {
}
