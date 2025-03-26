package org.example.utils;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FolderUtilsTest {
    FolderUtils folderUtils;


    @Before
    public void setUp() {
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

}