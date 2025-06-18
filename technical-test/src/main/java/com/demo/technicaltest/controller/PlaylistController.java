package com.demo.technicaltest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.demo.technicaltest.services.PlayListService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.technicaltest.entity.PlaylistEntity;



import java.util.Optional;
import java.net.URI;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/lists")

public class PlaylistController {

    @Autowired
    private PlayListService playListService;

    @GetMapping
    public ResponseEntity<List<PlaylistEntity>> getAllPlaylists() {
        return ResponseEntity.ok(playListService.getAll());
    }

    
    @GetMapping("/{listName}")
    public ResponseEntity<?> getPlaylist(@PathVariable String listName) {
        return playListService.getByName(listName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String listName) {
        Optional<PlaylistEntity> existing = playListService.getByName(listName);
        
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        playListService.deleteByName(listName);
        return ResponseEntity.noContent().build(); // 204 No Content
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
