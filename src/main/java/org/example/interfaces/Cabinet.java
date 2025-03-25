package org.example.interfaces;

import org.example.enums.FoldersSize;
import org.example.model.Folder;

import java.util.List;
import java.util.Optional;

public interface Cabinet {
    Optional<Folder> findFolderByName(String name);


    List<Folder> findFoldersBySize(FoldersSize size);

    int count();
}
