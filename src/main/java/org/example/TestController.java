package org.example;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@CrossOrigin
public class TestController {

    @PostMapping("/write")
    public boolean writeFile(@RequestBody FileParam param) throws IOException {
        Files.writeString(Path.of("/Users/shuangbofu/Documents/project/vue/luxun-street-front/pages/InstitutionalBlock/other/components/preview.vue"), param.getContent());
        return true;
    }

    @Data
    public static class FileParam {
        private String content;
    }
}
