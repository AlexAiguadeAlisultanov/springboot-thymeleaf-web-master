package com.example.springbootthymeleafweb.repository;

import com.example.springbootthymeleafweb.model.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariRepository extends JpaRepository<Usuari, String> {
}