package Library.Management.System.com.example.mapper;

import Library.Management.System.com.example.modal.Genre;
import Library.Management.System.com.example.payload.dto.GenreDTO;
import Library.Management.System.com.example.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    public static GenreDTO toDTO(Genre savedGenre){
        if(savedGenre ==null){
            return  null;
        }
        GenreDTO dto = GenreDTO.builder()
                .id(savedGenre.getId())
                .code(savedGenre.getCode())
                .name(savedGenre.getName())
                .description(savedGenre.getDescription())
                .displayOrder(savedGenre.getDisplayOrder())
                .active(savedGenre.getActive())
                .createAt(savedGenre.getCreatedAt())
                .updatedAt(savedGenre.getUpdateAt())
                .build();

        if(savedGenre.getParentGenre() != null){
            dto.setParentGenreId(savedGenre.getParentGenre().getId());
            dto.setParentGenreName(savedGenre.getParentGenre().getName());
        }

        if(savedGenre.getSubGenre() != null && !savedGenre.getSubGenre().isEmpty());

        dto.setSubGenre(savedGenre.getSubGenre().stream()
                .filter(subGenre->subGenre.getActive())
                .map(subGenre->toDTO(subGenre)).collect(Collectors.toList()));

//        dto.setBookCount((long)(savedGenre.getBook));

        return  dto;
    }

}
