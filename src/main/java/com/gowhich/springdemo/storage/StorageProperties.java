package com.gowhich.springdemo.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
    /*
     * 存储文件的文件夹位置
     */
    private String location = "upload_dir";

    public String getLocation() {
        return location;
    }

    public void setLocation() {
        this.location = location;
    }
}
