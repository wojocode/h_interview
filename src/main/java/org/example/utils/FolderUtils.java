package org.example.utils;

import java.util.Set;

public class FolderUtils {
    private static final Set<String> FOLDER_VALID_SIZES = Set.of("SMALL", "MEDIUM", "LARGE");

    public boolean isValidSize(String size) {
        return size != null && FOLDER_VALID_SIZES.contains(size.toUpperCase());
    }
}