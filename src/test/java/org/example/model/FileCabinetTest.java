package org.example.model;

import org.example.interfaces.Folder;
import org.example.interfaces.MultiFolder;
import org.example.service.FileCabinet;
import org.example.utils.FolderUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileCabinetTest {
    public FileCabinet filecabinet;

    @Mock
    public FolderUtils folderUtils;

    @Mock
    public MultiFolder multiFolder;

    @Mock
    public Folder folder;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        filecabinet = new FileCabinet(new ArrayList<>(), folderUtils);
    }

    @Test
    public void shouldFindFolderByName() {
        // given
        when(folder.getName()).thenReturn("TestFolder");
        when(folder.getSize()).thenReturn("SMALL");

        filecabinet.getFolders().add(folder);

        // when
        when(folderUtils.findFolderNameWithFlattenSearch(any(), any())).thenReturn(Optional.of(folder));

        Optional<Folder> result = filecabinet.findFolderByName("TestFolder");

        // then
        assertTrue(result.isPresent());
        assertEquals("TestFolder", result.get().getName());
    }


    //
    @Test
    public void shouldFindFoldersBySize() {
        // given
        when(folder.getName()).thenReturn("TestFolder");
        when(folder.getSize()).thenReturn("SMALL");

        MultiFolder multiFolder = mock(MultiFolder.class);
        when(multiFolder.getName()).thenReturn("Parent");
        when(multiFolder.getSize()).thenReturn("LARGE");
        when(multiFolder.getFolders()).thenReturn(List.of(folder));

        when(folderUtils.getAllFolders(any())).thenReturn(List.of(multiFolder, folder));
        when(folderUtils.isValidSize("LARGE")).thenReturn(true);

        filecabinet.getFolders().add(multiFolder);

        // when
        List<Folder> files = filecabinet.findFoldersBySize("LARGE");

        // then
        assertEquals(1, files.size());
        assertEquals("LARGE", files.get(0).getSize());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenSizeIsIncorrect() {
        //given
        when(folderUtils.isValidSize("SMa")).thenReturn(false);

        String size = "SMa";
        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            filecabinet.findFoldersBySize(size);
        });
    }

    @Test
    public void shouldCountAllFolders() {
        // given
        when(folder.getName()).thenReturn("TestFolder");

        Folder folder1 = mock(Folder.class);
        when(folder1.getName()).thenReturn("TestFolder1");

        MultiFolder multiFolder = mock(MultiFolder.class);
        when(multiFolder.getFolders()).thenReturn(List.of(folder, folder1));

        MultiFolder multiFolder1 = mock(MultiFolder.class);
        when(folderUtils.getAllFolders(any())).thenReturn(List.of(multiFolder, folder, folder1, multiFolder1));

        filecabinet.getFolders().add(multiFolder);
        filecabinet.getFolders().add(multiFolder1);

        // when & then
        assertEquals(4, filecabinet.count());
    }

    @Test
    public void shouldReturnEmptyWhenFolderNotFound() {
        // given
        Folder existingFolder = mock(Folder.class);
        when(existingFolder.getName()).thenReturn("Existing");

        when(folderUtils.getAllFolders(any())).thenReturn(List.of(existingFolder));

        filecabinet.getFolders().add(existingFolder);

        // when
        Optional<Folder> result = filecabinet.findFolderByName("NonExisting");

        // then
        assertTrue(result.isEmpty());
    }
}