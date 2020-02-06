package com.example.mycloud.dao;

import com.example.mycloud.Model.TempFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempFileRepository extends JpaRepository<TempFile,Long> {
    public TempFile findByTDM5(String md5);
    public void removeByTitleAndPath(String title,String Path);
}
