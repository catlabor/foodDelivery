package com.food.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.food.order.common.BaseContext;
import com.food.order.common.R;
import com.food.order.entity.ShoppingCart;
import com.food.order.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController{

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        if(dishId!=null){
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
            queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        }else {
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        }

        ShoppingCart one = shoppingCartService.getOne(queryWrapper);

        if(one!=null){
            Integer number = one.getNumber();
            number=number+1;
            one.setNumber(number);
            shoppingCartService.updateById(one);
        }else{
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setUserId(BaseContext.getCurrentId());
            one=shoppingCart;
            shoppingCartService.save(shoppingCart);
        }

        return R.success(one);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        if(dishId!=null){
            Long dishid=shoppingCart.getDishId();
            queryWrapper.eq(ShoppingCart::getDishId,dishid);
        }else {
            Long setmealID=shoppingCart.getSetmealId();
            queryWrapper.eq(ShoppingCart::getDishId,setmealID);
        }
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        Integer num=one.getNumber()-1;
        one.setNumber(num);
        if(num>=1){
            shoppingCartService.updateById(one);
        }else{
            shoppingCartService.removeById(one);
        }
        return R.success(one);
    }

    @DeleteMapping("/clean")
    public R<String> delete(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("successfully delete the shoppingcart");
    }

}
