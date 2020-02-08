package com.example.mycloud.schedual;

import com.example.mycloud.service.TempFileService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CleanPieceSchedual {
    Logger logger = LoggerFactory.getLogger(CleanPieceSchedual.class);
    @Autowired
    TempFileService tempFileService;
    @Scheduled(cron = "0 0 4 ? * 1 ")//每周日的凌晨4点进行清除
    public void cleanPiece(){
        logger.info("执行清除");
        File piece = new File("piece");
        File [] files = piece.listFiles();
        for (File dir : files) {
            if (dir.isDirectory()){
                File [] childFile = dir.listFiles();
                for (File file : childFile) {
                    System.out.println(file);
                    logger.info(file.getPath());
                    System.out.println(tempFileService);
                    tempFileService.removeByTempPath(file.getPath());
                    file.delete();
                }
                dir.delete();
            }
        }
    }

    @Test
    public void test(){
        cleanPiece();
    }
}
