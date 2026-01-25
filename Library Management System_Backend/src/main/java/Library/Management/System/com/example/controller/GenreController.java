package Library.Management.System.com.example.controller;

import Library.Management.System.com.example.Service.GenreService;
import Library.Management.System.com.example.modal.Genre;
import Library.Management.System.com.example.payload.dto.GenreDTO;
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
}
