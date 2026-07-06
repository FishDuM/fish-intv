package hk.ljx.fishintv.common.config;

import hk.ljx.fishintv.infrastructure.file.FileStorageService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageInitializer implements ApplicationRunner {

    @Resource
    private FileStorageService fileStorageService;

    @Override
    public void run(ApplicationArguments args) {
        fileStorageService.ensureBucketExists();
    }
}