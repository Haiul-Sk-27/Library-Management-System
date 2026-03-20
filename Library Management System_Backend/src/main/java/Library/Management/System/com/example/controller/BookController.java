package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Exception.BookException;
import Library.Management.System.com.example.Service.BookService;
import Library.Management.System.com.example.payload.dto.BookDTO;
import Library.Management.System.com.example.payload.response.BookSearchRequest;
import Library.Management.System.com.example.payload.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @Valid @RequestBody BookDTO bookDTO
    ) throws BookException {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BookDTO>> createBooksBulk(
            @Valid @RequestBody List<BookDTO> bookDTOS
    ) throws BookException {
        List<BookDTO> createdBooks = bookService.createBookBulk(bookDTOS);
        return new ResponseEntity<>(createdBooks, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @PathVariable Long id
    ) throws BookException {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(
            @PathVariable String isbn
    ) throws BookException {
        BookDTO book = bookService.getBookByISBN(isbn);
        return ResponseEntity.ok(book);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO bookDTO
    ) throws BookException {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    // Soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable Long id
    ) throws BookException {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Hard delete
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteBook(
            @PathVariable Long id
    ) throws BookException {
        bookService.hardDeleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<BookDTO>> searchBooks(
            @RequestBody BookSearchRequest searchRequest
    ) {
        PageResponse<BookDTO> response =
                bookService.searchBooksWithFilters(searchRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/stats/total-active")
    public ResponseEntity<Long> getTotalActiveBooks() {
        return ResponseEntity.ok(bookService.getTotalActiveBooks());
    }

    @GetMapping("/stats/total-available")
    public ResponseEntity<Long> getTotalAvailableBooks() {
        return ResponseEntity.ok(bookService.getTotalAvailablesBooks());
    }
}