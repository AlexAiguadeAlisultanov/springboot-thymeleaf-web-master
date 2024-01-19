package com.example.springbootthymeleafweb.repository;

import com.example.springbootthymeleafweb.model.Prestec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestecRepository extends JpaRepository<Prestec, Integer> {
}