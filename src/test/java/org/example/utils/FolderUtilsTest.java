package org.example.utils;

import org.example.interfaces.Folder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FolderUtilsTest {
    FolderUtils folderUtils;

    @Before
    public void setUp() {
        folderUtils = new FolderUtils();
    }


    @Test
    public void shouldReturnTrueWhenSizeIsValid() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getSize()).thenReturn("SMALL");

        //when && then
        assertTrue(folderUtils.isValidSize(folder.getSize()));
    }

    @Test
    public void shouldReturnTrueWhenSizeIsValidDiffCase() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getSize()).thenReturn("SMAll");

        //when && then
        assertTrue(folderUtils.isValidSize(folder.getSize()));
    }

    @Test
    public void shouldReturnFalseWhenSizeIsInvalid() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getSize()).thenReturn("SMAl");

        //when && then
        assertFalse(folderUtils.isValidSize(folder.getSize()));
    }

    @Test
    public void shouldReturnFalseWhenSizeIsNull() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getSize()).thenReturn(null);

        //when && then
        assertFalse(folderUtils.isValidSize(folder.getSize()));
    }

    @Test
    public void shouldReturnFalseWhenSizeIsEmpty() {
        //given
        Folder folder = mock(Folder.class);
        when(folder.getSize()).thenReturn("");

        //when && then
        assertFalse(folderUtils.isValidSize(folder.getSize()));
    }

}