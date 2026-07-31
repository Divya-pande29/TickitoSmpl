package com.sunbeam.tikito.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	String uploadPoster(MultipartFile file) throws IOException;

	String uploadProfile(MultipartFile file) throws IOException;

}
