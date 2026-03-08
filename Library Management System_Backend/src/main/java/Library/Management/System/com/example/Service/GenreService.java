package Library.Management.System.com.example.Service;

import Library.Management.System.com.example.payload.dto.GenreDTO;

import java.util.List;

public interface GenreService {

    GenreDTO createGenre(GenreDTO genre);
    List<GenreDTO> getAllGenres();
    GenreDTO getGenreById(Long genreId) throws Exception;
    GenreDTO updateGenre (Long genreId, GenreDTO genre);
    void deleteGenre(Long genreId);
    void hardDeleteGenre(Long genreId);
    List<GenreDTO> getAllActiveGenresWithSubGentes();
    List<GenreDTO> getTopLevelGenres();
   // Page<GenreDTO> searchingGenres(String searchTerm, Pageable pageable)
    long getTotalActivesGenres();
    long getBookCountByGenres(Long genreId);
}
