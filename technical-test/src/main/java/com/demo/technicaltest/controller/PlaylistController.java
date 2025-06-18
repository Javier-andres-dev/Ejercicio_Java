package com.demo.technicaltest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.demo.technicaltest.services.PlayListService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.technicaltest.entity.PlaylistEntity;




import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")

public class PlaylistController {

    @Autowired
    private PlayListService playListService;

    @GetMapping
    public ResponseEntity<List<PlaylistEntity>> getAllPlaylists() {
        return ResponseEntity.ok(playListService.getAll());
    }

    
    @GetMapping("/{nombre}")
    public ResponseEntity<?> getPlaylist(@PathVariable String nombre) {
        return playListService.getByName(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String nombre) {
        if (playListService.getByName(nombre).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        playListService.deleteByName(nombre);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistEntity playlist) {
        try {
            PlaylistEntity created = playListService.createPlayList(playlist);
            return ResponseEntity.created(URI.create("/lists/" + created.getNombre())).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
