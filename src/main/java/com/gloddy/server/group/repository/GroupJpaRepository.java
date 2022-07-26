package com.gloddy.server.group.repository;

import com.gloddy.server.group.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupJpaRepository extends JpaRepository<Group, Long> {
    Page<Group> findBySchoolOrderByIdDesc(Pageable pageable, String school);
}
