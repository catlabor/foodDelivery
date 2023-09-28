package com.food.order.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//处理公共字段，如修改人，修改时间
@Slf4j
@Component
public class myMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
      log.info("公共字段填充[Insert]");
      metaObject.setValue("createTime", LocalDateTime.now());
      metaObject.setValue("updateTime", LocalDateTime.now());
      metaObject.setValue("createUser",BaseContext.getCurrentId());
      metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段填充[Update]");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
