package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Exception.BookException;
import Library.Management.System.com.example.Service.BookService;
import Library.Management.System.com.example.payload.dto.BookDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookService bookService;

    @PostMapping("/admin")
    public ResponseEntity<BookDTO> createBook(
            @Valid @RequestBody BookDTO bookDTO
    ) throws BookException {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
}
