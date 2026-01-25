package Library.Management.System.com.example.Exception;


import Library.Management.System.com.example.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalException{

    @ExceptionHandler(GenreException.class)
    public ResponseEntity<ApiResponse>handleGenreException(GenreException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(e.getMessage(),false));
    }
}
