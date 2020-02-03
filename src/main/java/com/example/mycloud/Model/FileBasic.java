package com.example.mycloud.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class FileBasic {
    @Id
    @GeneratedValue
    private long id;
    private String Filename;
    private String size;
    private String path;
    private String dm5;
    private boolean dir;
    private String parent;
}
