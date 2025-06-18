package com.demo.technicaltest.services;

import com.demo.technicaltest.entity.PlaylistEntity;
import com.demo.technicaltest.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayListServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlayListService playListService;

    @Test
    public void testGetAll() {
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setNombre("Rock");
        playlist.setDescripcion("Rock clásico");

        when(playlistRepository.findAll()).thenReturn(Arrays.asList(playlist));

        List<PlaylistEntity> result = playListService.getAll();

        assertEquals(1, result.size());
        assertEquals("Rock", result.get(0).getNombre());
    }
}
