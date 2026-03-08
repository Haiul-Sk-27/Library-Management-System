package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Service.GenreService;

import Library.Management.System.com.example.payload.dto.GenreDTO;
import Library.Management.System.com.example.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genre){
        GenreDTO createdGenre = genreService.createGenre(genre);
        return ResponseEntity.ok(createdGenre);
    }

    @GetMapping()
    public ResponseEntity<?> allGenre(){
        List<GenreDTO > genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<?> getGenreById(@PathVariable("genreId") Long genreId) throws Exception {
        GenreDTO genres = genreService.getGenreById(genreId);
        return ResponseEntity.ok(genres);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<?> updateGenre(
            @RequestParam("genreId") Long genreId,
            @RequestBody GenreDTO genreDTO
    ){
        GenreDTO genre = genreService.updateGenre(genreId,genreDTO);
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<?> deleteGenre(
            @RequestParam("genreId") Long genreId
    ){
        genreService.deleteGenre(genreId);
        ApiResponse response = new ApiResponse("Genre delete - soft delete",true);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{genreId}/hard")
    public ResponseEntity<?> hardDeleteGenre(
            @RequestParam("genreId") Long genreId
    ){
        genreService.hardDeleteGenre(genreId);
        ApiResponse response = new ApiResponse("Genre delete - hard delete",true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-level")
    public ResponseEntity<?> getTopLevelGenres(){
        List<GenreDTO> genres = genreService.getTopLevelGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getTotalActive(){
        Long genres = genreService.getTotalActivesGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}/book-count")
    public ResponseEntity<?> getBookCountByGenres(
            @PathVariable Long id
    ){
        Long count = genreService.getBookCountByGenres(id);
        return ResponseEntity.ok(count);
    }
}
