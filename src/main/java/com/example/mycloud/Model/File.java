package com.example.mycloud.Model;

import lombok.Data;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data()
@Table
@Entity
public class File {
    @Id
    private String fileName;
    private String DM5;
    private String uploadDate;

    public File(String fileName, String DM5, String uploadDate) {
        this.fileName = fileName;
        this.DM5 = DM5;
        this.uploadDate = uploadDate;
    }

    @Test
    public void test(){

    }
}
