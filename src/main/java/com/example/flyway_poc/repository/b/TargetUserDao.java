package com.example.flyway_poc.repository.b;

import com.example.flyway_poc.entity.b.TargetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetUserDao extends JpaRepository<TargetUser,Integer> {
}
