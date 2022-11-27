package com.gloddy.server.Mate.repository;


import com.gloddy.server.Mate.entity.Mate;
import com.gloddy.server.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateJpaRepository extends JpaRepository<Mate, Long> {
    Long countMateByUser(User user);
}
