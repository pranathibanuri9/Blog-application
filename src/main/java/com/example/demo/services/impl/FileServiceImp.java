package com.example.demo.services.impl;

import com.example.demo.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
@Service
public class FileServiceImp implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
//        String relativepath="blog-app-apis"+File.separator+"images";
//        String randomId= UUID.randomUUID().toString();
//        String filename1=randomId.concat(name.substring(name.lastIndexOf(".")));


//        String filePath = path + File.separator + name;
        String filePath = path + File.separator + name;


        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();

        }
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return name;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullPath=path+File.separator+filename;
        InputStream is=new FileInputStream(fullPath);

        return is;
    }
}
