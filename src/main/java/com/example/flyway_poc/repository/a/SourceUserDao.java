package com.example.flyway_poc.repository.a;

import com.example.flyway_poc.entity.a.SourceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceUserDao extends JpaRepository<SourceUser,Integer> {
}
