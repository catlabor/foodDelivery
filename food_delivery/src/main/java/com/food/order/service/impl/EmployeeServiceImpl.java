package com.food.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.entity.Employee;
import com.food.order.mapper.EmployeeMapper;
import com.food.order.service.EmployeeService;
import org.springframework.stereotype.Service;



@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
