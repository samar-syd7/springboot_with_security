package com.samar.Springboot_CRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samar.Springboot_CRUD.model.Book;
import com.samar.Springboot_CRUD.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookRepository bookRepository;

	@GetMapping
	public List<Book> getAllBooks() {
		return bookRepository.findAll();

	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			return ResponseEntity.ok(book);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookRepository.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book bookDetails) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			book.setTitle(bookDetails.getTitle());
			book.setAuthor(bookDetails.getAuthor());
			Book updatedBook = bookRepository.save(book);
			return ResponseEntity.ok(updatedBook);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
		bookRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}

}
