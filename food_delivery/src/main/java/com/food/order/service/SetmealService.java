package com.food.order.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.food.order.dto.SetmealDto;
import com.food.order.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    public void deleteWithDish(List<Long> ids);
}
