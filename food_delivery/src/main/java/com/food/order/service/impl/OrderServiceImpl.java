package com.food.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.common.BaseContext;
import com.food.order.common.CustomException;
import com.food.order.entity.*;
import com.food.order.mapper.OrderMapper;
import com.food.order.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Transactional
    public void submit(Orders orders) {
        Long currentId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        List<ShoppingCart> lists = shoppingCartService.list(queryWrapper);

        if(lists==null||lists.size()==0){
            throw new CustomException("购物车为空");
        }

        User user = userService.getById(currentId);

        Long addressBookId = orders.getAddressBookId();
        AddressBook address = addressBookService.getById(addressBookId);
        if(address==null){
            throw new CustomException("地址簿为空");
        }

        AtomicInteger amount=new AtomicInteger();
        Long orderId=IdWorker.getId();

        List<OrderDetail> orderDetailList= lists.stream().map((item)->{
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setName(item.getName());
            orderDetail.setOrderId(orderId);
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(currentId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(address.getConsignee());
        orders.setPhone(address.getPhone());
        orders.setAddress((address.getProvinceName() == null ? "" : address.getProvinceName())
                + (address.getCityName() == null ? "" : address.getCityName())
                + (address.getDistrictName() == null ? "" : address.getDistrictName())
                + (address.getDetail() == null ? "" : address.getDetail()));
        this.save(orders);


       orderDetailService.saveBatch(orderDetailList);

       shoppingCartService.remove(queryWrapper);

    }
}