package com.ssafy.TogetherBuyMain.global.util;

import java.util.UUID;

public class FileUtil {
    // 파일명을 난수화하기 위해 UUID로 난수 돌리기
    public static String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 확장자 추출
    public static String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new IllegalArgumentException("잘못된 형식의 파일 (" + fileName + ") 입니다.");
        }
    }
}
