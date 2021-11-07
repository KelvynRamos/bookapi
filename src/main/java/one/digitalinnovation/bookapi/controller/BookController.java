package one.digitalinnovation.bookapi.controller;

import lombok.AllArgsConstructor;
import one.digitalinnovation.bookapi.dto.request.BookDTO;
import one.digitalinnovation.bookapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.bookapi.exception.BookAlreadyRegisteredException;
import one.digitalinnovation.bookapi.exception.BookNotFoundException;
import one.digitalinnovation.bookapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO) throws BookAlreadyRegisteredException {
        return bookService.createBook(bookDTO);
    }

    @GetMapping
    public List<BookDTO> listAll(){ return bookService.listAll();}

    @GetMapping("/{id}")
    public BookDTO findById(@PathVariable Long id) throws BookNotFoundException {
        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) throws BookNotFoundException {
        return bookService.updateByID(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BookNotFoundException {
        bookService.delete(id);
    }

}
