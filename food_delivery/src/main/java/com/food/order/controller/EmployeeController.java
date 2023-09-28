package com.food.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.food.order.common.R;
import com.food.order.entity.Employee;
import com.food.order.service.EmployeeService;
//import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.DigestException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        log.info("sda,{}",emp);
        if(emp==null){
            return R.error("employee not exists");
        }

        if(!emp.getPassword().equals(password)){
            return R.error("wrong password");
        }

        if(emp.getStatus()==0){
            return R.error("the account can not be used");
        }

        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("successfully logout");
    }

    @PostMapping   //save employee info
    public R<String> saveEmployee(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long empID=(Long)request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empID);
//        employee.setUpdateUser(empID);

        log.info("add new employee");
        employeeService.save(employee);

        return R.success("add new employee successfully");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}");
        Page pageInfo=new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);  //query method
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

//        Long empID= (Long)request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empID);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("success update employee information");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("查找id为{}的员工",id);
        Employee employee = employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("non such employee");
    }
}
