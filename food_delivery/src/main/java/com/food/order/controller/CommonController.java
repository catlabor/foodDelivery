package com.food.order.controller;

import com.food.order.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${Photo.storage}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info(file.toString());

        String filename= file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        String fileFullName= UUID.randomUUID().toString()+suffix;

        try {
            file.transferTo(new File(basePath+fileFullName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileFullName);

    }

    @GetMapping("/download")
    public void download(String name,HttpServletResponse response){


        try {
            FileInputStream inputStream=new FileInputStream(basePath+name);
            ServletOutputStream outputStream=response.getOutputStream();
            response.setContentType("image/jpeg");
            int i=0;
            byte[] bytes=new byte[1024];
            while ((i=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,i);
                outputStream.flush();
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}