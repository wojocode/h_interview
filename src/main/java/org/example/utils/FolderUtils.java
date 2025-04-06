package org.example.utils;

import org.example.interfaces.Folder;
import org.example.interfaces.MultiFolder;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
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
                .flatMap(this::flattenFolderRecursive)
                .toList();
    }


    public Optional<Folder> findFolderNameWithFlattenSearch(Folder folder, String name) {
        Deque<Folder> deque = new ArrayDeque<>();
        deque.push(folder);

        while (!deque.isEmpty()) {
            Folder current = deque.pop();
            if (name.equals(current.getName())) {
                return Optional.of(current);
            }

            if (current instanceof MultiFolder multiFolder) {
                List<Folder> folderList = Optional.ofNullable(multiFolder.getFolders()).orElse(Collections.emptyList());
                for (int i = folderList.size() - 1; i >= 0; i--) {
                    deque.push(folderList.get(i));
                }
            }
        }

        return Optional.empty();
    }

    private Stream<Folder> flattenFolderRecursive(Folder folder) {
        if (folder instanceof MultiFolder multiFolder) {
            List<Folder> subfolders = Optional.ofNullable(multiFolder.getFolders())
                    .orElse(List.of());
            return Stream.concat(
                    Stream.of(folder),
                    subfolders.stream()
                            .filter(Objects::nonNull)
                            .flatMap(this::flattenFolderRecursive)
            );
        } else {
            return Stream.of(folder);
        }
    }
}