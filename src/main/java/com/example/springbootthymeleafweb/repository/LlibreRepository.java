package com.example.springbootthymeleafweb.repository;

import com.example.springbootthymeleafweb.model.Llibre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LlibreRepository extends JpaRepository<Llibre, String> {
}