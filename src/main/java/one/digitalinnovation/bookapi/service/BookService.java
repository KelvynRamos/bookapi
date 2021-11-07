package one.digitalinnovation.bookapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.bookapi.dto.request.BookDTO;
import one.digitalinnovation.bookapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.bookapi.entity.Book;
import one.digitalinnovation.bookapi.exception.BookAlreadyRegisteredException;
import one.digitalinnovation.bookapi.exception.BookNotFoundException;
import one.digitalinnovation.bookapi.mapper.BookMapper;
import one.digitalinnovation.bookapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private BookRepository bookRepository;

    private final BookMapper bookMapper = BookMapper.INSTANCE;


    public BookDTO createBook(BookDTO bookDTO) throws BookAlreadyRegisteredException {
        verifyIsAlreadyRegistred(bookDTO.getTitle());
        Book bookToSave = bookMapper.toModel(bookDTO);
        Book savedBook = bookRepository.save(bookToSave);
        return bookMapper.toDTO(savedBook);
    }

    public List<BookDTO> listAll() {
        List<Book> allBook = bookRepository.findAll();
        return allBook.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id) throws BookNotFoundException {
        Book book = verifyIfExists(id);

        return bookMapper.toDTO(book);
    }


    public MessageResponseDTO updateByID(Long id, BookDTO bookDTO) throws BookNotFoundException {
        verifyIfExists(id);

        Book bookToUpdate = bookMapper.toModel(bookDTO);


        Book updatedBook = bookRepository.save(bookToUpdate);
        return createMessageResponse(updatedBook.getId(), "Updated book with ID");
    }

    public void delete(Long id) throws BookNotFoundException {
        verifyIfExists(id);

        bookRepository.deleteById(id);
    }
    
    private void verifyIsAlreadyRegistred(String title) throws BookAlreadyRegisteredException {
        Optional<Book> optSavedBook = bookRepository.findByTitle(title);
        if(optSavedBook.isPresent()){
            throw new BookAlreadyRegisteredException(title);
        }
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    private Book verifyIfExists(Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
