package Library.Management.System.com.example.Service.impl;

import Library.Management.System.com.example.Exception.GenreException;
import Library.Management.System.com.example.Service.GenreService;
import Library.Management.System.com.example.mapper.GenreMapper;
import Library.Management.System.com.example.modal.Genre;
import Library.Management.System.com.example.payload.dto.GenreDTO;
import Library.Management.System.com.example.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;


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
        GenreDTO dto = GenreMapper.toDTO(savedGenre);

        return  dto;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return GenreRepository.findAll().stream()
                .map(Genre->GenreMapper::toDTO(Genre))
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO getGenreById(Long genreId) throws Exception {
        Genre genre =  genreRepository.findById(genreId).orElseThrow(
                ()-> new GenreException("Genre Not found")
                );

        return GenreMapper.toDTO(genre);
    }

    @Override
    public GenreDTO updateGenre(Long genreId, GenreDTO genre) {

        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                ()->new GenreException("Genre not found")
        );
        return null;
    }

    @Override
    public void deleteGenre(Long genreId) {

    }

    @Override
    public void hardDeleteGenre(Long genreId) {

    }

    @Override
    public List<GenreDTO> getAllActiveGenresWithSubGentes() {
        return List.of();
    }

    @Override
    public List<GenreDTO> getTopLevelGenres() {
        return List.of();
    }

    @Override
    public long getTopActivesGenres() {
        return 0;
    }

    @Override
    public long getBookCountByGenres(Long genreId) {
        return 0;
    }

}
