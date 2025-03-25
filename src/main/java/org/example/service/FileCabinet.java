package org.example.service;

import org.example.enums.FolderSize;
import org.example.interfaces.Cabinet;
import org.example.interfaces.Folder;
import org.example.interfaces.MultiFolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders = new ArrayList<>();

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
    public List<Folder> findFoldersBySize(FolderSize size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        return folders.stream()
                .filter(Objects::nonNull)
                .filter(folder -> folder.getSize() != null)
                .filter(fl -> size.equals(fl.getSize()))
                .toList();
    }

    // zwraca liczbę wszystkich obiektów tworzących strukturę
    @Override
    public int count() {
        return getAllFolders(folders).size();
    }

    // methods to create flat list
    private List<Folder> getAllFolders(List<Folder> folders) {
        return folders.stream()
                .filter(Objects::nonNull)
                .flatMap(this::flattenFolder)
                .toList();
    }

    private Stream<Folder> flattenFolder(Folder folder) {
        if (folder instanceof MultiFolder multiFolder) {
            return Stream.concat(
                    Stream.of(folder),
                    multiFolder.getFolders().stream()
                            .filter(Objects::nonNull)
                            .flatMap(this::flattenFolder)
            );
        } else {
            return Stream.of(folder);
        }
    }

}
