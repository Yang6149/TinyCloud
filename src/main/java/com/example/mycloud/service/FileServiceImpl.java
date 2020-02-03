package com.example.mycloud.service;

import com.example.mycloud.Model.FileBasic;
import com.example.mycloud.Util.FileUtil;
import com.example.mycloud.Util.MD5util;
import com.example.mycloud.dao.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository ;
    @Override
    public void saveFile(File f, MultipartFile file) throws IOException {
        String filePath = file.getOriginalFilename();
        File filename = new File(f,filePath);

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filename));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();

        FileBasic fileBasic = new FileBasic();
        file2Basic(filename,fileBasic);

        fileRepository.save(fileBasic);
    }

    @Override
    public void deleteFile(File f) {
        fileRepository.deleteByPathAndDir(f.getPath(),f.isDirectory());
    }



    @Override
    public FileBasic getOneFile(File parent, String child) {
        File file = new File(parent,child);
        //return fileRepository.getByPathAndDirIsFalse(file.getPath(),false);
        return null;
    }
    @Override
    public FileBasic getOneDir(File parent, String child) {
        File file = new File(parent,child);
        return fileRepository.getByPathAndDir(file.getPath(),true);
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


    public void file2Basic(File f,FileBasic fileBasic){
        fileBasic.setDir(f.isDirectory());
        fileBasic.setFilename(f.getName());
        fileBasic.setPath(f.getPath());
        fileBasic.setParent(f.getParent());
        fileBasic.setDm5(MD5util.code(f.getName()));
        fileBasic.setSize(FileUtil.toReadSize(f.length()));
    }
}
