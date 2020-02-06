package com.example.mycloud.service;

import com.example.mycloud.Model.FileBasic;
import com.example.mycloud.Util.FileUtil;
import com.example.mycloud.Util.MD5util;
import com.example.mycloud.dao.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    private FileRepository fileRepository ;
    @Override
    public void saveFile(File f, MultipartFile file,String md5) throws IOException {
        if (file.isEmpty()){
            logger.warn("收到文件为空");
            return;
        }
        String filePath = file.getOriginalFilename();
        logger.info(filePath);
        File temp = new File(f,filePath);
        Path filename = temp.toPath();
        Files.write(filename,file.getBytes());
        FileBasic res = new FileBasic();
        res.setFilename(filePath);
        res.setSize(FileUtil.toReadSize(file.getSize()));
        res.setPath(temp.getPath());
        res.setMd5(md5);
        res.setDir(false);
        res.setParent(f.getPath());
        res.setCreateTime(new Date());

        fileRepository.save(res);
    }

    @Override
    public void saveFile(File f, String md5) throws IOException {
        FileBasic res = new FileBasic();
        res.setFilename(f.getName());
        res.setSize(FileUtil.toReadSize(f.length()));
        res.setPath(f.getPath());
        res.setMd5(md5);
        res.setDir(false);
        res.setParent(f.getParent());
        res.setCreateTime(new Date());
    }

    @Override
    public void deleteById(long i) {
        FileBasic fileBasic = fileRepository.getOne(i);
        File file = new File(fileBasic.getPath());

        if (file.delete()){
            fileRepository.deleteById(i);
        }
    }






    @Override
    public List<List<FileBasic>> getAllDisp(File f) {
        List<FileBasic> list = getAll(f);
        List<List<FileBasic>> res = new ArrayList<>();
        res.add(new ArrayList<FileBasic>());
        res.add(new ArrayList<FileBasic>());
        for (FileBasic fileBasic : list) {
            if (fileBasic.isDir()){
                res.get(0).add(fileBasic);
            }else{
                res.get(1).add(fileBasic);
            }
        }
        return res;
    }

    @Override
    public List<FileBasic> getAll(File f) {

        return fileRepository.findAllByParent(f.getPath());
    }

    @Override
    public void mkdir(File f) {
        if (!f.exists()){
            f.mkdir();
        }
        FileBasic fileBasic =new FileBasic();
        file2Basic(f,fileBasic);
        fileRepository.save(fileBasic);


    }

    @Override
    public FileBasic getByMd5(String dm5) {
        return fileRepository.getByMd5(dm5);
    }


    public void file2Basic(File f,FileBasic fileBasic){
        fileBasic.setDir(f.isDirectory());
        fileBasic.setFilename(f.getName());
        fileBasic.setPath(f.getPath());
        fileBasic.setParent(f.getParent());
        fileBasic.setMd5(MD5util.code(f.getName()));
        fileBasic.setSize(FileUtil.toReadSize(f.length()));
    }
}
