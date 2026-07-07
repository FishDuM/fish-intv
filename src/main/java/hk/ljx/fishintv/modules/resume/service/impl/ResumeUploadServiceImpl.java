package hk.ljx.fishintv.modules.resume.service.impl;

import hk.ljx.fishintv.common.result.Result;
import hk.ljx.fishintv.infrastructure.file.FileStorageService;
import hk.ljx.fishintv.modules.resume.service.ResumeUploadService;
import hk.ljx.fishintv.modules.resume.vo.UploadAndAnalyzeVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ResumeUploadServiceImpl implements ResumeUploadService {

    @Resource
    private FileStorageService fileStorageService;

    /**
     * 上传简历并分析
     * @param file 简历文件
     * @return 分析结果
     */
    public Result<UploadAndAnalyzeVO>  uploadAndAnalyze(MultipartFile file) {
        //todo
        return null;
    }
}
