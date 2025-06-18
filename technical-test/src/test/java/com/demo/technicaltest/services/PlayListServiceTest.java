package com.demo.technicaltest.services;

import com.demo.technicaltest.entity.PlaylistEntity;
import com.demo.technicaltest.entity.SongEntity;
import com.demo.technicaltest.repository.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayListServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlayListService playListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        PlaylistEntity playlist = new PlaylistEntity("test", "desc", Collections.emptyList());
        when(playlistRepository.findAll()).thenReturn(List.of(playlist));

        List<PlaylistEntity> result = playListService.getAll();

        assertEquals(1, result.size());
        verify(playlistRepository, times(1)).findAll();
    }

    @Test
    public void testGetByName() {
        PlaylistEntity playlist = new PlaylistEntity("test", "desc", Collections.emptyList());
        when(playlistRepository.findById("test")).thenReturn(Optional.of(playlist));

        Optional<PlaylistEntity> result = playListService.getByName("test");

        assertTrue(result.isPresent());
        assertEquals("test", result.get().getNombre());
        verify(playlistRepository).findById("test");
    }

    @Test
    public void testDeleteByName() {
        doNothing().when(playlistRepository).deleteById("test");
        playListService.deleteByName("test");

        verify(playlistRepository).deleteById("test");
    }

    @Test
    public void testCreatePlayList_Success() {
        PlaylistEntity playlist = new PlaylistEntity("test", "desc", List.of(new SongEntity()));
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        PlaylistEntity result = playListService.createPlayList(playlist);

        assertEquals("test", result.getNombre());
        verify(playlistRepository).save(playlist);
    }

    

    @Test
    public void testCreatePlayList_NoSongs_ThrowsException() {
        PlaylistEntity playlist = new PlaylistEntity("test", "desc", Collections.emptyList());

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            playListService.createPlayList(playlist);
        });

        assertEquals("The playlist must have at least one song", e.getMessage());
    }
}
