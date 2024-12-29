package com.example.former.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.former.model.Form;


public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findByPublishStatusTrue();
}

