package org.example.model;

import org.example.enums.FoldersSize;
import org.example.interfaces.Cabinet;
import org.example.interfaces.MultiFolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    // zwraca dowolny element o podanej nazwie
    public Optional<Folder> findFolderByName(String name) {
        return Optional.ofNullable(name)
                .map(n -> folders.stream()
                        .filter(Objects::nonNull)
                        .filter(folder -> n.equals(folder.getName()))
                        .findAny()
                )
                .orElseThrow(() -> new IllegalArgumentException("Folder name cannot be null"));
    }

    // zwraca wszystkie foldery podanego rozmiaru SMALL/MEDIUM/LARGE
    @Override
    public List<Folder> findFoldersBySize(FoldersSize size) {
        return folders.stream()
                .filter(Objects::nonNull)
                .filter(fl -> size.equals(fl.getSize()))
                .toList();
    }

    //zwraca liczbę wszystkich obiektów tworzących strukturę
    @Override
    public int count() {
        return 1 + getAllFoldersRecursive(folders).size();
    }

    // methods to create flat list
    private List<Folder> getAllFoldersRecursive(List<Folder> folders) {
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
