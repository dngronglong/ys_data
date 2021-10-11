package com.xiaobai.data.service.impl;

import com.google.gson.Gson;
import com.xiaobai.data.entity.DataEntity;
import com.xiaobai.data.entity.ResultEntity;
import com.xiaobai.data.mapper.DataSync;
import com.xiaobai.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
public class DataServiceImpl implements DataService {

    @Value("${url}")
    private String url;

    @Value("${user}")
    private String user;

    @Value("${passwd}")
    private String passwd;

    @Resource
    RestTemplate restTemplate;

    private String nextToken="";

    private Integer pageIndex=0;

    @Autowired
    private DataSync dataSync;

    private List<DataEntity> list=new ArrayList<>();

    ExecutorService executorService = new ThreadPoolExecutor(30, Integer.MAX_VALUE, 2, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    @Override
    public void insertData(DataEntity dataEntity) {

    }

    @Override
    public List<DataEntity> getData(String uri){
        String path=uri+"/";
        System.out.println(path);
        String userInfo=user+":"+passwd;
        String encoded = Base64.getEncoder().encodeToString(userInfo.getBytes(StandardCharsets.UTF_8));
        String reqJsonStr = "{\"q\": \"\", \"password\": null, \"page_token\": \""+nextToken+"\", \"page_index\": "+pageIndex+"}";
        System.out.println(reqJsonStr);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION,"Basic "+encoded);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity(reqJsonStr,headers);
        ResponseEntity<String> resp=restTemplate.exchange("https://"+url+path, HttpMethod.POST,entity, String.class);
        ResultEntity resultEntity=new Gson().fromJson(resp.getBody(),ResultEntity.class);
        if (resultEntity.getNextPageToken()!=null){
            pageIndex++;
            nextToken=resultEntity.getNextPageToken();
        }else{
            pageIndex=0;
            nextToken="";
        }
        resultEntity.getData().getFiles().forEach(i->{
            if (i.getMimeType().equals("application/vnd.google-apps.folder")){
                this.getData(path+i.getName());
            }else if (i.getMimeType().equals("video/mp4")){
                DataEntity dataEntity=new DataEntity();
                dataEntity.setImg(i.getThumbnailLink());
                dataEntity.setTitle(i.getName());
                dataEntity.setUrl("https://"+userInfo+"@"+url+path);
                list.add(dataEntity);
                if (nextToken!=null){
                    this.getData(path);
                }else{
                    this.getData(path+i.getName());
                    return;
                }
            }

        });
        list.forEach(j->{
            System.out.println(j);
        });
        return list;
    }

}
