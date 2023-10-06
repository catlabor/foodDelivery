package com.food.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.food.order.common.R;
import com.food.order.entity.User;
import com.food.order.service.UserService;
import com.food.order.utils.codeUtils;
import com.food.order.utils.mailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private mailUtils mailUtility;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMSG(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(phone!=null){
            String codeGeneration=codeUtils.codeGenerate(4);
//            mailUtils mail=new mailUtils(codeGeneration);
//            Thread thread=new Thread(mail);
//            thread.start();
            mailUtility.sendMail(codeGeneration);

            redisTemplate.opsForValue().set(phone,codeGeneration,30, TimeUnit.SECONDS);

           // session.setAttribute(phone,codeGeneration);
            return R.success("successfully sending the mail");
        }
        return R.error("wrong with sending mail");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        String phone = map.get("phone").toString();
        String code=map.get("code").toString();
        //Object codeObtained = session.getAttribute(phone);

        Object codeObtained=redisTemplate.opsForValue().get(phone);
        if(codeObtained==null){
            return R.error("timeout");
        }
        if(codeObtained.equals(code)&&code!=null){
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user==null){
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("wrong verification");
    }
}
