package com.example.mycloud.service;


import com.example.mycloud.Model.FileBasic;
import com.example.mycloud.dao.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public interface FileService {

    //保存
    public void saveFile(File f, MultipartFile file) throws IOException;
    //删除
    public void deleteById(long i);
    //getFile
    //get One
    public List<List<FileBasic>> getAllDisp(File f);
    public List<FileBasic> getAll(File f);
    public void mkdir(File f);


}
