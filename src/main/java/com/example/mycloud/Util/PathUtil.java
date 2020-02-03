package com.example.mycloud.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathUtil {
    public static List<List<File>> getChildDirectoryAndFile(File parent){
        List<File> directorys = new ArrayList<>();
        List<File> files = new ArrayList<>();
        for (File file : parent.listFiles()) {
            if (file.isDirectory()){
                directorys.add(file);
            }else {
                files.add(file);
            }
        }
        List<List<File>> res = new ArrayList<>();
        res.add(directorys);
        res.add(files);
        return res;
    }

    public static File getOneDirect(File parent,String name){
        for (File file : parent.listFiles()) {
            if (file.isDirectory()&&file.getName().equals(name))
                return file;
        }
        return null;
    }

//    public static void deleteFile(File parent){
//
//    }
}
