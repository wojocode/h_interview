package org.example.service;

import org.example.interfaces.Cabinet;
import org.example.interfaces.Folder;
import org.example.interfaces.MultiFolder;
import org.example.utils.FolderUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders;
    private final FolderUtils folderUtils;

    public FileCabinet(List<Folder> folders, FolderUtils folderUtils) {
        this.folders = folders;
        this.folderUtils = folderUtils;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    // zwraca dowolny element o podanej nazwie
    @Override
    public Optional<Folder> findFolderByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Folder name cannot be null");
        }
        return getAllFolders(folders).stream()
                .filter(Objects::nonNull)
                .filter(folder -> folder.getName() != null)
                .filter(folder -> name.equals(folder.getName()))
                .findAny();
    }

    // zwraca wszystkie foldery podanego rozmiaru SMALL/MEDIUM/LARGE
    @Override
    public List<Folder> findFoldersBySize(String size) {
        if (!folderUtils.isValidSize(size)) {
            throw new IllegalArgumentException("Invalid folder size: " + size);
        }
        return folders.stream()
                .filter(Objects::nonNull)
                .filter(folder -> folder.getSize() != null)
                .filter(fl -> size.equalsIgnoreCase(fl.getSize()))
                .toList();
    }

    // zwraca liczbę wszystkich obiektów tworzących strukturę
    @Override
    public int count() {
        return getAllFolders(folders).size();
    }

    private List<Folder> getAllFolders(List<Folder> folders) {
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
