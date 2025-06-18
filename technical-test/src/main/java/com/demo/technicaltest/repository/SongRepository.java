package com.demo.technicaltest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.technicaltest.entity.SongEntity;

public interface SongRepository extends JpaRepository <SongEntity, Long> {
    
}
