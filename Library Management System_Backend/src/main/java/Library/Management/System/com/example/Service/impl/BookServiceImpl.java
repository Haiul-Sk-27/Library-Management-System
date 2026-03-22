package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Exception.BookException;
import Library.Management.System.com.example.Service.BookService;
import Library.Management.System.com.example.mapper.BookMapper;
import Library.Management.System.com.example.modal.Book;
import Library.Management.System.com.example.payload.dto.BookDTO;
import Library.Management.System.com.example.payload.request.BookSearchRequest;
import Library.Management.System.com.example.payload.response.PageResponse;
import Library.Management.System.com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private  final BookRepository bookRepository;
    private  final BookMapper bookMapper;

    @Override
    public BookDTO createBook(BookDTO bookDTO) throws BookException {
        if(bookRepository.existsByIsbn(bookDTO.getIsbn())){
            throw new BookException("Book with isbn"+bookDTO.getIsbn());
        }

        Book book = bookMapper.toEntity(bookDTO);

        //total -10.
        //available -11.

        book.isAvailableCopiesValid(book);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public List<BookDTO> createBookBulk(List<BookDTO> bookDTOS) throws BookException {
        return bookDTOS.stream()
                .map(dto -> {
                    try {
                        return createBook(dto);
                    } catch (BookException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long bookId) throws BookException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found!"));
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO getBookByISBN(String isbn) throws BookException {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(()-> new BookException("Book not found!"));
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found"));

        bookMapper.updateEntityFromDTO(bookDTO, existingBook);
        Book savedBook = bookRepository.save(existingBook);

        return bookMapper.toDTO(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) throws BookException {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(()->new BookException("Book not found"));

        existingBook.setActive(false);

    }

    @Override
    public void hardDeleteBook(Long bookId) throws BookException {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(()-> new BookException("Book not found"));
        bookRepository.delete(existingBook);
    }

    @Override
    public PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {
        Pageable pageable = createPageable(
                searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection()
        );

        Page<Book> bookPage = bookRepository.SearchBooksWithFilters(
                searchRequest.getSearchTerm(),
                searchRequest.getGenreId(),
                searchRequest.isAvailableOnly(),
                pageable
        );

        return convertToPageResponse(bookPage);
    }

    /*---------STATS---------------*/
    @Override
    public Long getTotalActiveBooks() {
        return bookRepository.countByActiveTrue();
    }

    @Override
    public Long getTotalAvailablesBooks() {
        return bookRepository.countAvailableBooks();
    }

    /* --------------Helper--------------- */

    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {

        size = Math.max(1, Math.min(size, 10));

        Sort sort = "ASC".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }

    private PageResponse<BookDTO> convertToPageResponse(Page<Book> books) {

        List<BookDTO> content = books.getContent()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isLast(),
                books.isFirst(),
                books.isEmpty()
        );
    }
}
