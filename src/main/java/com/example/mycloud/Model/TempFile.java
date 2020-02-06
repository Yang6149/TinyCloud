package com.example.mycloud.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class TempFile {
    @Id
    @GeneratedValue
    private long TID;
    private String title;
    private String TDM5;
    private String path;
}
