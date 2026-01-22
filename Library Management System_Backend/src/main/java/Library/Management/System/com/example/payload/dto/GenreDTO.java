package Library.Management.System.com.example.payload.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {

    private  Long id;

    @NotBlank(message = "Genre code is manadatory")
    private String code;

    @NotBlank(message = "genra name is mandatory")
    private String name;

    @Size(max = 500,message = "description must much exist 500 character")
    private String description;

    private Integer displayOrder =0;

    private  boolean active;

    private Long parentGenreId;

    private String parentGenreName;

    private List<GenreDTO> subGenre;

    private Long bookCount;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;
}
