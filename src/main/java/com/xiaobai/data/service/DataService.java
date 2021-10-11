package com.xiaobai.data.service;

import com.xiaobai.data.entity.DataEntity;

import java.util.List;

/**
 * @author Administrator
 */
public interface DataService {

    /**
     * 插入数据
     * @param dataEntity
     */
    void insertData(DataEntity dataEntity);

    List<DataEntity> getData(String path);
}
