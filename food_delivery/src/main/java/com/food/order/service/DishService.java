package com.food.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.food.order.dto.DishDto;
import com.food.order.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
