package hk.ljx.fishintv.infrastructure.file;

import hk.ljx.fishintv.common.config.StorageConfigProperties;
import hk.ljx.fishintv.common.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static hk.ljx.fishintv.common.exception.ErrorCode.STORAGE_UPLOAD_FAILED;

@Slf4j
@Service
public class FileStorageService {

    @Resource
    private S3Client s3Client;

    @Resource
    private StorageConfigProperties storageConfigProperties;

    /**
     * 上传简历
     * @param file 简历文件
     * @return 简历key
     */
    public String uploadResume(MultipartFile file) {
        return uploadFile(file, "resume");
    }

    /**
     * 删除简历
     * @param fileKey 简历文件key
     */
    public void deleteResume(String fileKey) {
        deleteFile(fileKey);
    }

    /**
     * 下载简历
     * @param fileKey 简历key
     * @return 简历字节数组
     */
    public byte[] downloadResume(String fileKey) {
        return downloadFile(fileKey);
    }

    /**
     * 上传知识库文件
     * @param file 知识库文件
     * @return 文件key
     */
    public String uploadKnowledgeBase(MultipartFile file) {
        return uploadFile(file, "knowledgebases");
    }

    /**
     * 删除知识库文件
     * @param fileKey 文件key
     */
    public void deleteKnowledgeBase(String fileKey) {
        deleteFile(fileKey);
    }


    /**
     * 通用文件上传
     * @param file 原始文件名
     * @param prefix 业务前缀
     * @return 存储 key
     */
    private String uploadFile(MultipartFile file, String prefix) {
        String originalFilename = file.getOriginalFilename();
        String fileKey = generateFileKey(originalFilename, prefix);
        try{
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(storageConfigProperties.getBucket())
                    .key(fileKey)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();
            s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("文件上传成功, 上传键: {}", fileKey);
            return fileKey;
        } catch (IOException e) {
            log.error("读取上传文件失败, 失败信息: {}", e.getMessage());
            throw new BusinessException(STORAGE_UPLOAD_FAILED);
        } catch (S3Exception e){
            log.error("上传文件到RustFS失败: {}", e.getMessage());
            throw new BusinessException(STORAGE_UPLOAD_FAILED);
        }
    }

    /**
     * 通用下载方法
     * @param fileKey 文件key
     * @return 文件字节数组
     */
    private byte[] downloadFile(String fileKey) {
        // 校验文件是否存在
        if (!fileExists(fileKey)) {
            throw new BusinessException(STORAGE_UPLOAD_FAILED);
        }
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(storageConfigProperties.getBucket())
                    .key(fileKey)
                    .build();
            return s3Client.getObjectAsBytes(getRequest).asByteArray();
        } catch (S3Exception e){
            log.error("文件下载失败, 下载文件key: {}, 下载文件信息: {}", fileKey, e.getMessage());
            throw new BusinessException(STORAGE_UPLOAD_FAILED);
        }
    }

    /**
     * 通用删除文件方法
     * @param fileKey 文件key
     */
    private void deleteFile(String fileKey) {
        // 判断key是否为空
        if (fileKey == null || fileKey.isEmpty()) {
            log.warn("文件key: {} 为空, 已跳过", fileKey);
            return;
        }
        // 判断文件是否存在
        if (!fileExists(fileKey)) {
            log.warn("文件key: {} 不存在, 已跳过", fileKey);
            return;
        }
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(storageConfigProperties.getBucket())
                    .key(fileKey)
                    .build();
            s3Client.deleteObject(deleteRequest);
        } catch (S3Exception e){
            log.error("删除文件key: {} 失败, 失败信息: {}", fileKey, e.getMessage());
            throw new BusinessException(STORAGE_UPLOAD_FAILED);
        }
    }

    /**
     * 判断存储桶是否存在
     */
    public void ensureBucketExists(){
        try {
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(storageConfigProperties.getBucket())
                    .build();
            s3Client.headBucket(headBucketRequest);
            log.info("存储桶已存在: {}", storageConfigProperties.getBucket());
        } catch (NoSuchBucketException e){
            log.warn("存储桶: {} 不存在, 正在自动创建", storageConfigProperties.getBucket());
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(storageConfigProperties.getBucket())
                    .build();
            s3Client.createBucket(createBucketRequest);
            log.info("存储桶创建成功: {}", storageConfigProperties.getBucket());
        } catch (S3Exception e) {
            log.error("检查存储桶失败, 桶名: {}, 失败信息: {}",storageConfigProperties.getBucket() , e.getMessage());
        }
    }

    /**
     * 生成文件存储建
     * @param originalFilename 原始文件名
     * @param prefix 路径前缀
     * @return 文件存储建
     */
    private String generateFileKey(String originalFilename, String prefix) {
        // 存储键样例 {prefix}/{yyyy/MM/dd}/{uuid}_{sanitized_filename}
        LocalDateTime now = LocalDateTime.now();
        String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String safeName = sanitizeFilename(originalFilename);
        return String.format("%s/%s/%s_%s", prefix, datePath, uuid, safeName);
    }

    /**
     * 清除文件不当格式
     * @param originalFilename 原始文件名
     * @return 安全文件名
     */
    private String sanitizeFilename(String originalFilename) {
        if (originalFilename == null) {
            return "unknown";
        }
        return originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    /**
     * 判断文件是否存在
     * @param fileKey 文件key
     * @return 是否存在
     */
    private boolean fileExists(String fileKey) {
        try {
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(storageConfigProperties.getBucket())
                    .key(fileKey)
                    .build();
            s3Client.headObject(headRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (S3Exception e) {
            log.error("没有找到文件, key: {}", fileKey);
            return false;
        }
    }
}
