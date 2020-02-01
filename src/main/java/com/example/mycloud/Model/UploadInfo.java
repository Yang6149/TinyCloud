package com.example.mycloud.Model;

import lombok.Data;
import org.junit.Test;

@Data
public class UploadInfo {
    private String dm5;
    private String chunks;
    private String chunk;
    private String path;
    private String fileName;
    private String ext;

    public UploadInfo(String dm5, String chunks, String chunk, String path, String fileName, String ext) {
        this.dm5 = dm5;
        this.chunks = chunks;
        this.chunk = chunk;
        this.path = path;
        this.fileName = fileName;
        this.ext = ext;
    }

    @Test
    public void test(){

    }
}
