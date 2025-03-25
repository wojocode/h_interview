package org.example.interfaces;

import org.example.model.Folder;

import java.util.List;

public interface MultiFolder extends Folder {
    List<Folder> getFolders();
}
