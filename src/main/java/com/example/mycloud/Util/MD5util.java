package com.example.mycloud.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5util {
    public static String code(byte[] bytes){

        try{
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] byteDigest=md.digest();
            int i;
            StringBuffer buffer=new StringBuffer("");
            for(int offset=0;offset<byteDigest.length;offset++){
                i=byteDigest[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16){
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(i));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String code(String str){
        return code(str.getBytes());
    }
    public static void main(String[] args) throws IOException {
        Path f = Paths.get("C:\\Users\\hasaki\\IdeaProjects\\mycloud\\piece\\实战Java高并发程序设计.pdf.tmp\\0");
        byte [] b = new byte[2*1024*1024];
        Files.write(f,b);


        String a="1234";
        System.out.println(code(b));
    }
}
