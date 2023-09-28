package com.food.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.common.CustomException;
import com.food.order.dto.SetmealDto;
import com.food.order.entity.Setmeal;
import com.food.order.entity.SetmealDish;
import com.food.order.mapper.SetmealMapper;
import com.food.order.service.SetmealDishService;
import com.food.order.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealdishes = setmealDto.getSetmealDishes();
        setmealdishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealdishes);
    }

    @Transactional
    @Override
    public void deleteWithDish(List<Long> ids){
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        if(this.count(queryWrapper)>0){
            throw new CustomException("商品处于起售状态");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper1);

    }
}
