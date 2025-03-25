package org.example.model;

import org.example.enums.FolderSize;
import org.example.interfaces.Folder;
import org.example.interfaces.MultiFolder;
import org.example.service.FileCabinet;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileCabinetTest {
    public FileCabinet filecabinet;

    @Before
    public void setUp() {
        filecabinet = new FileCabinet();
    }

    @Test
    public void shouldFindFolderByName() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getName()).thenReturn("TestFolder");
        when(folder.getSize()).thenReturn(FolderSize.valueOf("SMALL"));

        MultiFolder multiFolder = mock(MultiFolder.class);
        when(multiFolder.getName()).thenReturn("Parent");
        when(multiFolder.getSize()).thenReturn(FolderSize.valueOf("LARGE"));
        when(multiFolder.getFolders()).thenReturn(List.of(folder));

        FileCabinet cabinet = new FileCabinet();
        cabinet.getFolders().add(multiFolder);

        //when
        Optional<Folder> result = cabinet.findFolderByName("TestFolder");

        //then
        assertTrue(result.isPresent());
        assertEquals("TestFolder", result.get().getName());
    }

    @Test
    public void shouldFindFoldersBySize() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getName()).thenReturn("TestFolder");
        when(folder.getSize()).thenReturn(FolderSize.SMALL);

        MultiFolder multiFolder = mock(MultiFolder.class);
        when(multiFolder.getName()).thenReturn("Parent");
        when(multiFolder.getSize()).thenReturn(FolderSize.LARGE);
        when(multiFolder.getFolders()).thenReturn(List.of(folder));

        filecabinet.getFolders().add(multiFolder);

        //when
        List<Folder> files = filecabinet.findFoldersBySize(FolderSize.LARGE);

        //then
        assertEquals(1, files.size());
        assertEquals(FolderSize.LARGE, files.get(0).getSize());
    }

    @Test
    public void shouldCountAllFolders() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getName()).thenReturn("TestFolder");

        Folder folder1 = mock(Folder.class);
        when(folder.getName()).thenReturn("TestFolder1");

        MultiFolder multiFolder = mock(MultiFolder.class);
        when(multiFolder.getFolders()).thenReturn(List.of(folder, folder1));

        //when
        filecabinet.getFolders().add(multiFolder);

        //then
        assertEquals(3, filecabinet.count());
    }
}