package com.sm.dao;

import com.sm.domain.MoTeacher;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2019/3/28.
 */
@Repository
public interface TeacherRepository extends ElasticsearchRepository<MoTeacher, Long> {
}
