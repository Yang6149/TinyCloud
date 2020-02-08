package com.example.mycloud.Controller;

import com.example.mycloud.Model.FileBasic;
import com.example.mycloud.Model.TempFile;
import com.example.mycloud.service.FileService;
import com.example.mycloud.service.TempFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class TestWebUploader {
    Logger logger = LoggerFactory.getLogger(TestWebUploader.class);
    @Autowired
    FileService fileService;

    @Autowired
    TempFileService tempFileService;

    @RequestMapping(value = "/check",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> fileCheck(@RequestParam("fileMD5") String md5){
        //检查是否有相同的dm相同的文件
        Map<String,Object> res = new HashMap<>();
        //if ()返回数据类型response."已存在"  and  response.ture
        FileBasic file = fileService.getByMd5(md5);
        if (file != null){
            res.put("msg","已经存在了");
            res.put("exist",true);
        }else {
            res.put("msg","还没存在");
            res.put("exist",false);
        }
        return res;
    }
    @PostMapping("/checkPiece")
    @ResponseBody
    public Map<String,Object> fileCheckpiece(@RequestParam("fileMD5") String md5){
        //检查是否有相同的dm相同的文件
        Map<String,Object> res = new HashMap<>();
//        //if ()返回数据类型response."已存在"  and  response.ture
        TempFile file = tempFileService.findByMD5(md5);
        if (file != null){
            res.put("msg","已经存在了");
            res.put("exist",true);
        }else {
            res.put("msg","还没存在");
            res.put("exist",false);
        }
        return res;
    }


    //上传，当为单个文件时，fileMD5=chunkMD5
    @PostMapping("/upload")
    @ResponseBody
    public Map<String,String> fileUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "chunks",required = false) int chunks,
                             @RequestParam(value = "chunk") int chunk,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "chunkMD5") String md5,
                             HttpSession session) throws IOException {
        if (chunks == 1){
            fileService.saveFile((File) session.getAttribute("curPath"),file,md5);
            //保存文件
            //上传数据库
        }else{
            //创建一个文件夹并分别保存每一个文件
            //上传到temp数据库
            tempFileService.saveTempFile((File) session.getAttribute("curPath"),file,md5,chunk,name);
        }
        Map<String,String> map = new HashMap<String, String>();

        if(chunks>1){
            map.put("msg","分片上传完成");
        }else{
            map.put("msg","文件上传完成");
        }
        return map;
    }



    //合并文件
    @PostMapping("/merge")
    @ResponseBody
    public Map<String, String> fileMerge(@RequestParam("fileMD5") String md5, @RequestParam("fileName") String fileName){
        File dir = new File("piece/"+fileName+".tmp");
        if (dir.exists()){
            File[] files = dir.listFiles();
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (o1.isDirectory() && o2.isFile())
                        return -1;
                    if (o1.isFile() && o2.isDirectory())
                        return 1;
                    return Integer.parseInt(o1.getName())-Integer.parseInt(o2.getName());
                }
            });
            String outName="";
            if (files.length>1){
                String a=files[0].getPath();

                TempFile b = tempFileService.findByCurPath(a);
                outName=b.getPath();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outName,true));
                for (File file:files){
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                    int len = -1;
                    byte[] bytes = new byte[1*1024*1024];

                    while((len = in.read(bytes))!=-1) {

                        out.write(bytes);
                    }
                    in.close();
                    tempFileService.removeByTitleAndPath(file.getName(),outName);
                    logger.info("融合完成 {}" ,file.getName());

                    file.delete();
                }
                out.close();

                dir.delete();
                File f = new File(outName);
                fileService.saveFile(f,md5);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        Map<String,String> map = new HashMap<String, String>();

        map.put("msg","合并分片完成");

        return map;
    }
}
