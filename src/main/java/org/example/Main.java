package org.example;

import org.example.enums.FolderSize;
import org.example.interfaces.MultiFolder;
import org.example.service.FileCabinet;
import org.example.interfaces.Folder;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        FileCabinet fileCabinet = new FileCabinet();
        System.out.println(fileCabinet.count());
        Folder MultiFolder = new MultiFolder() {
            @Override
            public List<Folder> getFolders() {
                return List.of();
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public FolderSize getSize() {
                return null;
            }
        };
        Folder Folder = new Folder() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public FolderSize getSize() {
                return null;
            }
        };
        List<Folder> folderList = List.of(MultiFolder, Folder);
        for (Folder f : folderList) {
            if (f instanceof MultiFolder mf) {
                System.out.println("to multifolder");
            } else {
                System.out.println("to folder");
            }
        }
    }
    //W odpowiedzi na zainteresowanie naszą ofertą pracy chcielibyśmy zaprosić do pierwszego etapu rekrutacji na stanowisko Junior Java Developer w firmie Horus.

    // Poniżej przekazujemy zadanie z prośbą o analizę poniższego kodu i samodzielne zaimplementowanie metod findFolderByName, findFolderBySize, count w klasie FolderCabinet -
    // najchętniej unikając powielania kodu i umieszczając całą logikę w klasie FolderCabinet. Z uwzględnieniem w analizie i implementacji interfejsu MultiFolder!
    //
    //interface Cabinet {
    //// zwraca dowolny element o podanej nazwie
    //Optional<Folder> findFolderByName(String name);
    //
    //// zwraca wszystkie foldery podanego rozmiaru SMALL/MEDIUM/LARGE
    //List<Folder> findFoldersBySize(String size);
    //
    ////zwraca liczbę wszystkich obiektów tworzących strukturę
    //int count();
    //}
    //
    //public class FileCabinet implements Cabinet {
    //private List<Folder> folders;
    //}
    //
    //interface Folder {
    //String getName();
    //String getSize();
    //}
    //
    //interface MultiFolder extends Folder {
    //List<Folder> getFolders();
    //}

}