package com.food.order;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class foodApplication {
    public static void main(String[] args) {
        SpringApplication.run(foodApplication.class,args);
        log.info("start");
    }

 //   连接数据库 FOR JDK 17 NOW USING JDK 1.8
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/datastartproject");
//        dataSource.setUsername("username");
//        dataSource.setPassword("password");
//        return dataSource;
//    }
}
