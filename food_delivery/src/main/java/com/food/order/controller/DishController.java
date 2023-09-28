package com.food.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.order.common.R;
import com.food.order.dto.DishDto;
import com.food.order.entity.Category;
import com.food.order.entity.Dish;
import com.food.order.entity.DishFlavor;
import com.food.order.service.CategoryService;
import com.food.order.service.DishFlavorService;
import com.food.order.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/dish")
@RestController
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("save {}",dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("成功添加菜品");
    }

    @GetMapping("/page")  //caution
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);

        Page<DishDto> pageInfo2=new Page<>(page,pageSize);
        BeanUtils.copyProperties(pageInfo,pageInfo2,"records");

        List<Dish> pages = pageInfo.getRecords();   //In order to obtain records properties
        List<DishDto> pageFinal=pages.stream().map((item)->{
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long id=item.getCategoryId();
            Category category=categoryService.getById(id);
            String nameCategory= category.getName();
            dishDto.setCategoryName(nameCategory);
            return dishDto;
        }).collect(Collectors.toList());

        pageInfo2.setRecords(pageFinal);

        return R.success(pageInfo2);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        log.info("回显{}用户",id);
        DishDto dishDto=dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info("save {}",dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("成功修改菜品");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        log.info("get the infomation of{}",dish.toString());
//        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        queryWrapper.eq(Dish::getStatus,1);
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> list=dishService.list(queryWrapper);
//        return R.success(list);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        log.info("get the infomation of{}",dish.toString());
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list=dishService.list(queryWrapper);

        List<DishDto> listDishDto=list.stream().map((item)->{
            DishDto dto=new DishDto();
            BeanUtils.copyProperties(item,dto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String name = category.getName();
                dto.setCategoryName(name);
            }

            LambdaQueryWrapper<DishFlavor> queryWrapper1=new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getDishId,item.getId());
            List<DishFlavor> flavors = dishFlavorService.list(queryWrapper1);
            dto.setFlavors(flavors);
            return dto;
        }).collect(Collectors.toList());
        return R.success(listDishDto);
    }

}
