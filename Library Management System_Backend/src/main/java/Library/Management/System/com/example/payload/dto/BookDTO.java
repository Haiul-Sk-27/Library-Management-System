package Library.Management.System.com.example.payload.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;

    @NotBlank(message = "Title is mandatory")
    @Size(min = 1, max = 255)
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(min = 1, max = 255)
    private String author;

    @NotNull(message = "Genre ID is mandatory")
    private Long genreId;

    private String genreName;
    private String genreCode;

    @Size(max = 100)
    private String publisher;

    private LocalDate publicationDate;

    @Size(max = 20)
    private String language;

    @Min(1)
    @Max(50000)
    private Integer pages;

    @Size(max = 2000)
    private String description;

    @NotNull
    @Min(0)
    private Integer totalCopies;

    @NotNull
    @Min(0)
    private Integer availableCopies;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    private String coverImage;

    private Boolean active = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @AssertTrue(message = "Available copies cannot exceed total copies")
    public boolean isAvailableCopiesValid() {
        if (availableCopies == null || totalCopies == null) return true;
        return availableCopies <= totalCopies;
    }
}