package com.xiaobai.data.entity;

import lombok.Data;

/**
 * @author Administrator
 * 返回请求结果
 */
@Data
public class ResultEntity {

    private Integer curPageIndex;

    private com.xiaobai.data.entity.Data data;

    private String nextPageToken;
}
