package Library.Management.System.com.example.repository;

import Library.Management.System.com.example.modal.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    //book - java programing
    //progies

//    @Query("select b from Book b where"+
//            ":searchTerm is null or"+
//            "lower(b.title) like (concat('%', :searchTerm,'%')) OR"+
//            "lower(b.author) like (concat('%',:searchTerm,'%')) OR"+
//            "lower(b.isbn) like (concat('%',:searchTerm,'%')) OR"+
//            "(:genreId is null of b.genre.id =:genreId) AND"+
//            "(:availableOnly == false Or b.availableCopies > 0) AND"+
//            "b.active = true"
//    )

    @Query("""
        SELECT b FROM Book b
        WHERE
            (:searchTerm IS NULL OR
             LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
        AND (:genreId IS NULL OR b.genre.id = :genreId)
        AND (:availableOnly = false OR b.availableCopies > 0)
        AND b.active = true
    """)
    Page<Book> SearchBooksWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("genreId") Long genreId,
            @Param("availableOnly") boolean availableOnly,
            Pageable pageable
    );

    long countByActiveTrue();

    @Query("select count(b) from Book b where b.availableCopies > 0 and b.active = true")
    long countAvailableBooks();
}
