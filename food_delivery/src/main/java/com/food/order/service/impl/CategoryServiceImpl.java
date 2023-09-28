package com.food.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.common.CustomException;
import com.food.order.entity.Category;
import com.food.order.entity.Dish;
import com.food.order.entity.Setmeal;
import com.food.order.mapper.CategoryMapper;
import com.food.order.service.CategoryService;
import com.food.order.service.DishService;
import com.food.order.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{


    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int countDish=dishService.count(dishLambdaQueryWrapper);

        if(countDish>0){
            throw new CustomException("该分类下有菜品");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int countSetmeal= setmealService.count(setmealLambdaQueryWrapper);

        if(countSetmeal>0){
            throw new CustomException("该分类下有套餐");
        }

        super.removeById(id);
    }
}
