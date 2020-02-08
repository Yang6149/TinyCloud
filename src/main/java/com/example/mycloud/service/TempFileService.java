package com.example.mycloud.service;

import com.example.mycloud.Model.TempFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface TempFileService {
    //保存
    public void saveTempFile(File file, MultipartFile multipartFile,String md5,int chunk,String name);
    //删除
    public void removeByTitleAndPath(String title,String path);
    //根据md5查找对象
    public TempFile findByMD5(String md5);

    public TempFile findByCurPath(String path);

    public void removeByTempPath(String temp_path);
}
