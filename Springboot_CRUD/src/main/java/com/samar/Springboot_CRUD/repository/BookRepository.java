package com.samar.Springboot_CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samar.Springboot_CRUD.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
