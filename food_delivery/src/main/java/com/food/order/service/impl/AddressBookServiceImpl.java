package com.food.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.food.order.entity.AddressBook;
import com.food.order.mapper.AddressBookMapper;
import com.food.order.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
