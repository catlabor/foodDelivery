package com.food.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.order.common.R;
import com.food.order.entity.Category;
import com.food.order.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("add category,{}",category);
        categoryService.save(category);
        return R.success("add category success");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long id){
        log.info("delete category {}",id);
        categoryService.remove(id);
        return R.success("delete success");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("update category information");
        categoryService.updateById(category);
        return R.success("成功修改分类信息");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){

        LambdaQueryWrapper<Category>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> lists = categoryService.list(queryWrapper);
        return R.success(lists);
    }

}
