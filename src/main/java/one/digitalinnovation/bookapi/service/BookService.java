package one.digitalinnovation.bookapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.bookapi.dto.request.BookDTO;
import one.digitalinnovation.bookapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.bookapi.entity.Book;
import one.digitalinnovation.bookapi.exception.PersonNotFoundException;
import one.digitalinnovation.bookapi.mapper.BookMapper;
import one.digitalinnovation.bookapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private BookRepository bookRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;


    public MessageResponseDTO createBook(BookDTO bookDTO) {

        Book bookToSave = bookMapper.toModel(bookDTO);

        Book savedBook = bookRepository.save(bookToSave);
        return createMessageResponse(savedBook.getId(), "Created book with ID");
    }





    public List<BookDTO> listAll() {
        List<Book> allBook = bookRepository.findAll();
        return allBook.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id) throws PersonNotFoundException {
        Book book = verifyIfExists(id);

        return bookMapper.toDTO(book);
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    private Book verifyIfExists(Long id) throws PersonNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public MessageResponseDTO updateByID(Long id, BookDTO bookDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Book bookToUpdate = bookMapper.toModel(bookDTO);


        Book updatedBook = bookRepository.save(bookToUpdate);
        return createMessageResponse(updatedBook.getId(), "Updated book with ID");
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);

        bookRepository.deleteById(id);
    }
}
