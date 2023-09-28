package com.food.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.order.common.R;
import com.food.order.dto.SetmealDto;
import com.food.order.entity.Category;
import com.food.order.entity.Setmeal;
import com.food.order.service.CategoryService;
import com.food.order.service.SetmealDishService;
import com.food.order.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("save setmeal data");
        setmealService.saveWithDish(setmealDto);
        return R.success("successfully save setmeal information");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);

        Page<SetmealDto> setmealDtoPage=new Page<>();
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");

        List<Setmeal> list=pageInfo.getRecords();
        List<SetmealDto> listNew=list.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            Long numberCategory=item.getCategoryId();
            Category category = categoryService.getById(numberCategory);
            if(category!=null){
                BeanUtils.copyProperties(item,setmealDto);
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(listNew);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("delete the setmeal");
        setmealService.deleteWithDish(ids);
        return R.success("success delete setmeal");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
