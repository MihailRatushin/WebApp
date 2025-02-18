package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.PieOrder;

public interface OrderRepository extends CrudRepository<PieOrder, Integer> {

}
