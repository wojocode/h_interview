package org.example.utils;


import org.example.interfaces.Folder;
import org.example.interfaces.MultiFolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FolderUtilsTest {
    FolderUtils folderUtils;

    @Mock
    Folder folder;

    @Mock
    MultiFolder multiFolder;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        folderUtils = new FolderUtils();
    }

    @Test
    public void shouldReturnTrueWhenSizeIsValid() {
        assertTrue(folderUtils.isValidSize("SMALL"));
    }

    @Test
    public void shouldReturnTrueWhenSizeIsValidDiffCase() {
        assertTrue(folderUtils.isValidSize("SMAll"));
    }

    @Test
    public void shouldReturnFalseWhenSizeIsInvalid() {
        assertFalse(folderUtils.isValidSize("SMAl"));
    }

    @Test
    public void shouldReturnFalseWhenSizeIsNull() {
        assertFalse(folderUtils.isValidSize(null));
    }

    @Test
    public void shouldReturnFalseWhenSizeIsEmpty() {
        assertFalse(folderUtils.isValidSize(""));
    }


    @Test
    public void shouldReturnEmptyListWhenInputIsEmpty() {
        List<Folder> result = folderUtils.getAllFolders(Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnSingleFolderWhenSimpleFolderProvided() {
        List<Folder> result = folderUtils.getAllFolders(List.of(folder));
        assertEquals(1, result.size());
        assertEquals(folder, result.get(0));
    }

    @Test
    public void shouldReturnMultiFolderAndItsSubfolder() {
        when(multiFolder.getFolders()).thenReturn(List.of(folder));

        List<Folder> result = folderUtils.getAllFolders(List.of(multiFolder));
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(multiFolder, folder)));
    }

    @Test
    public void shouldFlattenNestedMultiFolders() {
        Folder deep = mock(Folder.class);
        MultiFolder level2 = mock(MultiFolder.class);
        when(level2.getFolders()).thenReturn(List.of(deep));

        MultiFolder level1 = mock(MultiFolder.class);
        when(level1.getFolders()).thenReturn(List.of(level2));

        List<Folder> result = folderUtils.getAllFolders(List.of(level1));

        assertEquals(3, result.size());
        assertTrue(result.containsAll(List.of(level1, level2, deep)));
    }


    @Test
    public void shouldHandleNullReturnedByGetFolders() {
        when(multiFolder.getFolders()).thenReturn(null);

        List<Folder> result = folderUtils.getAllFolders(List.of(multiFolder));
        assertEquals(1, result.size());
        assertEquals(multiFolder, result.get(0));
    }

    @Test
    public void shouldHandleEmptySubfolderList() {
        when(multiFolder.getFolders()).thenReturn(Collections.emptyList());

        List<Folder> result = folderUtils.getAllFolders(List.of(multiFolder));
        assertEquals(1, result.size());
        assertEquals(multiFolder, result.get(0));
    }

    @Test
    public void shouldFlattenMultipleMultiFolders() {
        Folder sub1 = mock(Folder.class);
        Folder sub2 = mock(Folder.class);

        MultiFolder mf1 = mock(MultiFolder.class);
        MultiFolder mf2 = mock(MultiFolder.class);
        when(mf1.getFolders()).thenReturn(List.of(sub1));
        when(mf2.getFolders()).thenReturn(List.of(sub2));

        List<Folder> result = folderUtils.getAllFolders(List.of(mf1, mf2));

        assertEquals(4, result.size());
        assertTrue(result.containsAll(List.of(mf1, sub1, mf2, sub2)));
    }

}