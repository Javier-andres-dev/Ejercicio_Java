package com.demo.technicaltest.repository;
import com.demo.technicaltest.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String> {
    
}
