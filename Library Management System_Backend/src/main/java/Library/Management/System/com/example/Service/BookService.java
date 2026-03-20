package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.Exception.BookException;
import Library.Management.System.com.example.payload.dto.BookDTO;
import Library.Management.System.com.example.payload.response.BookSearchRequest;
import Library.Management.System.com.example.payload.response.PageResponse;

import java.util.List;

public interface BookService {

    BookDTO createBook(BookDTO bookDTO) throws BookException;
    List<BookDTO> createBookBulk(List<BookDTO> bookDTOS) throws BookException;
    BookDTO getBookById(Long bookId) throws BookException;
    BookDTO getBookByISBN(String isbn) throws BookException;
    BookDTO updateBook(Long bookId,BookDTO bookDTO) throws BookException;
    void deleteBook(Long bookId) throws BookException;
    void hardDeleteBook(Long bookId) throws BookException;
    PageResponse<BookDTO> searchBooksWithFilters(
            BookSearchRequest searchRequest
    );

    Long getTotalActiveBooks();
    Long getTotalAvailablesBooks();
}
