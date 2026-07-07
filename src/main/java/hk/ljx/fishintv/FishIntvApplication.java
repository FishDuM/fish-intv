package hk.ljx.fishintv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("hk.ljx.fishintv.modules.*.mapper")
public class FishIntvApplication {

    public static void main(String[] args) {
        SpringApplication.run(FishIntvApplication.class, args);
    }

}
