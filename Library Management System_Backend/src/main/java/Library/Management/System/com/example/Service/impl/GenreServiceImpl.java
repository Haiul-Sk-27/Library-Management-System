package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Service.GenreService;
import Library.Management.System.com.example.modal.Genre;
import Library.Management.System.com.example.payload.dto.GenreDTO;
import Library.Management.System.com.example.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {
//        return genreRepository.save(genreDTO);



        Genre genre = Genre.builder()
                .code(genreDTO.getCode())
                .name(genreDTO.getName())
                .description(genreDTO.getDescription())
                .displayOrder(genreDTO.getDisplayOrder())
                .active(true)
                .build();

        if (genreDTO.getParentGenreId() != null){
            Genre parentGenre = genreRepository.findById(genreDTO.getParentGenreId()).get();
            genre.setParentGenre(parentGenre);
        }

        Genre savedGenre = genreRepository.save(genre);
        GenreDTO dto = GenreDTO.builder()
                .id(savedGenre.getId())
                .code(savedGenre.getName())
                .description(savedGenre.getDescription())
                .displayOrder(savedGenre.getDisplayOrder())
                .active(savedGenre.getActive())
                .createAt(savedGenre.getCreatedAt())
                .updatedAt(savedGenre.getUpdateAt())
                .build();

        if(savedGenre.getParentGenre() != null){
            dto.setParentGenreId(savedGenre.getParentGenre().getId());
            dto.setParentGenreId(savedGenre.getParentGenre().getName());
        }

//        dto.setSubGenre(savedGenre.getSubGenre().stream()
//                .filter(subGenre->subGenre.getActive())
//                .map(subGenre->));

//        dto.setBookCount((long)(savedGenre.getBook));
        return  null;
    }
}
