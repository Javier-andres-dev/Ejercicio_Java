package com.demo.technicaltest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.technicaltest.repository.PlaylistRepository;
import com.demo.technicaltest.entity.PlaylistEntity;
import java.util.List;
import java.util.Optional;



@Service
public class PlayListService {
    @Autowired
    private PlaylistRepository playlistRepository;
    
    public List<PlaylistEntity> getAll() {
        return playlistRepository.findAll();
    }

    public Optional<PlaylistEntity> getByName(String nombre) {
        return playlistRepository.findById(nombre);
    }

    public void deleteByName(String nombre) {
        playlistRepository.deleteById(nombre);
    }

    public PlaylistEntity createPlayList(PlaylistEntity playlist) {
    
    if (playlist.getNombre() == null || playlist.getNombre().trim().isEmpty()) {
        throw new IllegalArgumentException("Playlist name cannot be empty");
    }

    
    if (playlist.getDescripcion() == null || playlist.getDescripcion().trim().isEmpty()) {
        throw new IllegalArgumentException("Playlist descriptions cannot be empty.");
    }

    
    if (playlist.getCanciones() == null || playlist.getCanciones().isEmpty()) {
        throw new IllegalArgumentException("The playlist must have at least one song");
    }
        PlaylistEntity newPlayList = playlistRepository.save(playlist);
        return newPlayList;
    }
}
