package com.xiaobai.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@TableName(value = "ap_video")
public class DataEntity {

    @TableId(type=IdType.AUTO)
    private Integer id;

    private String title;

    private String img;

    private String url;
}
