package com.example.mycloud.dao;

import com.example.mycloud.Model.FileBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<FileBasic,Long> {
    public void deleteByPathAndDir(String path,Boolean isdir);
    public List<FileBasic> findAllByParent(String parent);
}
