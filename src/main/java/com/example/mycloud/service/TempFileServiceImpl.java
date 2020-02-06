package com.example.mycloud.service;

import com.example.mycloud.Model.TempFile;
import com.example.mycloud.dao.TempFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TempFileServiceImpl implements TempFileService {
    @Autowired
    TempFileRepository tempFileRepository;

    static {
        File piece = new File("piece");
        if (!piece.exists()){
            piece.mkdir();
        }
    }
    @Override
    public void saveTempFile(File file, MultipartFile multipartFile, String md5, int chunk ,String name) {
        File dir = new File("piece/" + name +".tmp");
        if (!dir.exists()) {//如果文件夹不存在
            dir.mkdir();//创建文件夹
        }
        byte[] bytes = new byte[0];
        try {
            bytes = multipartFile.getBytes();
            Path path = Paths.get("piece/" + name +".tmp/" + chunk);
            Files.write(path,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //插入数据库分片表记录，用于断点续传时读取
        TempFile tempFile = new TempFile();
        tempFile.setTitle(Integer.toString(chunk));
        tempFile.setTDM5(md5);
        tempFile.setPath(file.getPath()+"/"+name);
        tempFileRepository.save(tempFile);

    }

    @Override
    public void removeByTitleAndPath(String title, String path) {
        tempFileRepository.removeByTitleAndPath(title,path);
    }


    @Override
    public TempFile findByMD5(String md5) {

        return tempFileRepository.findByTDM5(md5);
    }
}
