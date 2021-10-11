package com.xiaobai.data.entity;

import java.util.List;

@lombok.Data
public class Data {

    private List<Files> files;

    private String nextPageToken;
}
