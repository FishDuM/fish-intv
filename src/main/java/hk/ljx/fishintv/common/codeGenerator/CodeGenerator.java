package hk.ljx.fishintv.common.codeGenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

public class CodeGenerator {

    private static final String url = "jdbc:postgresql://localhost:5432/fish_intv";
    private static final String username = "postgres";
    private static final String password = "123456";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder
                        .author("Fish")
                        .enableSpringdoc()
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java/generator")
                )
                .packageConfig(builder -> builder
                        .parent("com.baomidou.mybatisplus")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder ->
                        builder.addInclude("fish_user")
                        .entityBuilder()
                                .enableLombok()
                                .enableTableFieldAnnotation()
                                .controllerBuilder()
                                .enableRestStyle()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
