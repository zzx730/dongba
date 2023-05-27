package com.cy.excel;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

public class UserInfoDataListener extends AnalysisEventListener<UserInfo> {

    Logger logger = LoggerFactory.getLogger(UserInfoDataListener.class);

    //每次读取100条数据就进行保存操作
    private static final int BATCH_COUNT = 100;
    //由于每次读都是新new UserInfoDataListener的，所以这个list不会存在线程安全问题
    List<UserInfo> list = new ArrayList<>();

    //这个组件是Spring中的组件，这边推荐两种方法注入这个组件
    //第一种就是提供一个UserInfoDataListener的构造方法，这个方法提供一个参数是UserInfoDataListener类型
    //另外一种方法就是将 UserInfoDataListener 这个类定义成 UserService 实现类的内部类（推荐这种方式）
    //private UserService userService;

    @Override
    public void invoke(UserInfo data, AnalysisContext analysisContext) {
        logger.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        logger.info("所有数据解析完成！");
    }

    private void saveData() {
        logger.info("{}条数据，开始存储数据库！", list.size());
        //保存数据
        //userService.save(list);
        logger.info("存储数据库成功！");
    }

}