package com.ssafy.TogetherBuyChat.global.AWS;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {
    @Value("${cloud.aws.bucket-name}")
    private String bucketName;  // S3 버킷 이름 (사용자 설정 필요)

    private final AmazonS3 amazonS3;  // AWS S3에 접근하기 위한 Amazon S3 객체

    /**
     * 파일을 S3에 업로드하고, 해당 파일의 URL을 반환합니다.
     * @param file MultipartFile 형식의 업로드 파일
     * @return S3에 저장된 파일의 URL
     */
    public String uploadFile(MultipartFile file, Long entityId) {
        String fileName = entityId.toString() + "_" + UUID.randomUUID() + "_" + file.getOriginalFilename();  // 고유한 파일 이름 생성
        try {
            // S3에 파일 업로드
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));  // 파일을 public 읽기 권한으로 설정
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);  // 업로드 실패 시 예외 처리
        }
        // 업로드된 파일의 URL 반환
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    public void deleteFile(String fileName) {
        try {
            amazonS3.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 중 오류 발생", e);
        }
    }


}
