package com.sunbeam.tikito.serviceimpl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.tikito.services.UploadService;
import com.sunbeam.tikito.utils.FileUploadUtil;

@Service
public class UploadServiceImpl implements UploadService {

    private final FileUploadUtil fileUploadUtil;

    public UploadServiceImpl(FileUploadUtil fileUploadUtil) {
        this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public String uploadPoster(MultipartFile file) throws IOException {

        String filename = fileUploadUtil.upload(file);

        return "/posters/" + filename;
    }

}