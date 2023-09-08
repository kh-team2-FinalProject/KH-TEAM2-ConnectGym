package com.khteam2.connectgym.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadProfileFile(MultipartFile file, String trainerId) throws IOException {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDateTime = now.format(dateTimeFormatter);

        String s3FileName = formattedDateTime + "-" + file.getOriginalFilename();

        //메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket + "/profile/" + trainerId, s3FileName, file.getInputStream(), metadata);

        return amazonS3Client.getUrl(bucket, s3FileName).toString();

    }


    public String uploadLessonFile(MultipartFile file, String lessonCode) throws IOException {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDateTime = now.format(dateTimeFormatter);

        System.out.println("file.getOriginalFilename=================" + file.getOriginalFilename());
        String s3FileName = formattedDateTime + file.getOriginalFilename();

        //메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket + "/profile/" + lessonCode, s3FileName, file.getInputStream(), metadata);

        return amazonS3Client.getUrl(bucket + "/profile/" + lessonCode, s3FileName).toString();

    }

    public String uploadReviewFile(MultipartFile file, Long userNo) throws IOException {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formattedDateTime = now.format(dateTimeFormatter);

        String s3FileName = formattedDateTime + "-" + file.getOriginalFilename();

        //메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket + "/review/" + userNo, s3FileName, file.getInputStream(), metadata);

        return amazonS3Client.getUrl(bucket, s3FileName).toString();

    }


}
