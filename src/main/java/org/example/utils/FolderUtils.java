package org.example.utils;

import org.example.interfaces.Folder;
import org.example.interfaces.MultiFolder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class FolderUtils {
    private static final Set<String> FOLDER_VALID_SIZES = Set.of("SMALL", "MEDIUM", "LARGE");

    public boolean isValidSize(String size) {
        return size != null && FOLDER_VALID_SIZES.contains(size.toUpperCase());
    }


    public List<Folder> getAllFolders(List<Folder> folders) {
        return Optional.ofNullable(folders)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .flatMap(this::flattenFolder)
                .toList();
    }

    private Stream<Folder> flattenFolder(Folder folder) {
        if (folder instanceof MultiFolder multiFolder) {
            List<Folder> subfolders = Optional.ofNullable(multiFolder.getFolders()).orElse(List.of());
            return Stream.concat(
                    Stream.of(folder),
                    subfolders.stream()
                            .filter(Objects::nonNull)
                            .flatMap(this::flattenFolder)
            );
        } else {
            return Stream.of(folder);
        }
    }
}