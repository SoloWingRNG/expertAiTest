package com.ai.expert.test.dao;

import com.ai.expert.test.entity.response.Property;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends  GenericRepository<Property, Long>{
}
