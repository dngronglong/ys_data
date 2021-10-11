package com.xiaobai.data.entity;

import lombok.Data;

@Data
public class Files {

    private String id;

    private String name;

    private String mimeType;

    private String modifiedTime;

    private String thumbnailLink;

    private String size;
}
