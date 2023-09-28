package com.food.order.dto;

import com.food.order.entity.Setmeal;
import com.food.order.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
