package hk.ljx.fishintv.modules.resume.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadAndAnalyzeVO {

    private String analysis; // 分析结果

    private String fileKey;

    private String fileUrl;

    private String resumeId;

    private boolean duplicate; // 是否重复
}
