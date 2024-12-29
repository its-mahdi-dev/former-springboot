package com.example.former.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.former.model.Field;

public interface FieldRepository extends JpaRepository<Field, Long> {
}

