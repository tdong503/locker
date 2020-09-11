package com.tw.locker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class FileOperator {

    public String fileReadToText(String fileName) throws IOException {
        String path = this.getClass().getResource("").getPath().replace("build/classes/java/test/", "src/test/java/");

        File file = new File(path + "TestData/" + fileName);
        byte[] bytes = new byte[1024];
        StringBuilder sb = new StringBuilder();
        FileInputStream in = new FileInputStream(file);
        int len;
        while ((len = in.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len));
        }
        return sb.toString();
    }
}
