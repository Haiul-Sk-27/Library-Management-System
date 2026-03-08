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

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {
        Genre genre = genreMapper.toEntity(genreDTO);
        Genre savedGenre = genreRepository.save(genre);
        GenreDTO dto = GenreMapper.toDTO(savedGenre);

        return  dto;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreMapper::toDTO)
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
    public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) {

        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                ()->new GenreException("Genre not found")
        );

        genreMapper.updateEntityFromDTO(genreDTO,existingGenre);
        Genre updateGenre = genreRepository.save(existingGenre);
        return genreMapper.toDTO(updateGenre);
    }

    @Override
    public void deleteGenre(Long genreId) {

        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                ()->new GenreException("Genre not found")
        );

        existingGenre.setActive(false);
        genreRepository.save(existingGenre);
    }

    @Override
    public void hardDeleteGenre(Long genreId) {
        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                ()->new GenreException("Genre not found")
        );

        genreRepository.delete(existingGenre);
    }

    @Override
    public List<GenreDTO> getAllActiveGenresWithSubGentes() {

        List<Genre> topLevelGenres = genreRepository.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
        return genreMapper.toDTOList(topLevelGenres);
    }

    @Override
    public List<GenreDTO> getTopLevelGenres() {
        List<Genre> topLevelGenres = genreRepository.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
        return genreMapper.toDTOList(topLevelGenres);
    }

    @Override
    public long getTotalActivesGenres() {

        return genreRepository.countByActiveTrue();
    }

    @Override
    public long getBookCountByGenres(Long genreId) {

        return 0;
    }

}
