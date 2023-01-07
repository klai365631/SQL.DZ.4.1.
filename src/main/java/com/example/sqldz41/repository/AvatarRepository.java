package com.example.sqldz41.repository;

import com.example.sqldz41.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {
}
