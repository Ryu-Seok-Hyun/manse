package com.keduit.shop.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

//2024년 09월 23일 14:31
@Service
@Log
public class FileService {

  public String uploadFile(String uploadPath, String originalFileName, byte[] fileData)
      throws Exception {

    // Universally Unique Identifier : 범용 고유 식별자, 중복되지 않는고유의 값을 구성할 때 사용.
    UUID uuid = UUID.randomUUID();
    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
    String savedFileName = uuid.toString() + extension;
    String fileUPloadFullUrl = uploadPath + "/" + savedFileName;
    FileOutputStream fos = new FileOutputStream(fileUPloadFullUrl);
    fos.write(fileData);
    fos.close();
    return savedFileName;
  }

  public void deleteFile(String filePath) throws Exception {
    //저장된 파일의 경로를 이용하여 파일 객체를 생성
    File file = new File(filePath);

    //저장된 파일의 경로를 이용하여 파일 객체를 생성
    File deleteFile = new File(filePath);
    //해당 파일이 잇으면 삭제
    if (deleteFile.exists()) {
      deleteFile.delete();
      log.info("파일을 삭제하엿습니다");
    } else {
      log.info("파일이 존재하지 않습니다");
    }

  }
}