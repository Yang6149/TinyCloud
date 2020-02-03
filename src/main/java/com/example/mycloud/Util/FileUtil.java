package com.example.mycloud.Util;

import org.junit.Test;

public class FileUtil {
    public static String toReadSize(long size){
        String [] danwei = {"B","K","M","G"};
        int count = 0;
        double d=(double)size;
        while ((d/1024)>1){
            d/=1024;
            count++;
        }
        return String.format("%.1f",d)+danwei[count];
    }

    @Test
    public void test(){
        long size = 425216662;
        System.out.println(toReadSize(size));
    }
}
