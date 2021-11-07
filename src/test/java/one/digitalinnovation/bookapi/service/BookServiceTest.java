package one.digitalinnovation.bookapi.service;

import one.digitalinnovation.bookapi.builder.BookDTOBuilder;
import one.digitalinnovation.bookapi.dto.request.BookDTO;
import one.digitalinnovation.bookapi.entity.Book;
import one.digitalinnovation.bookapi.exception.BookAlreadyRegisteredException;
import one.digitalinnovation.bookapi.mapper.BookMapper;
import one.digitalinnovation.bookapi.repository.BookRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private static final long INVALID_BOOK_ID = 1L;

    @Mock
    private BookRepository bookRepository;

    private BookMapper bookMapper = BookMapper.INSTANCE;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookInformedThenItShouldBeCreated() throws BookAlreadyRegisteredException {
        // given
        BookDTO expectedBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedSavedBook = bookMapper.toModel(expectedBookDTO);

        // when
        when(bookRepository.findByTitle(expectedBookDTO.getTitle())).thenReturn(Optional.empty());
        when((bookRepository.save(expectedSavedBook))).thenReturn(expectedSavedBook);

        // then
        BookDTO createdBookDTO = bookService.createBook(expectedBookDTO);

        assertThat(createdBookDTO.getId(), is(equalTo(expectedBookDTO.getId())));
        assertThat(createdBookDTO.getTitle(), is(equalTo(expectedBookDTO.getTitle())));
        assertThat(createdBookDTO.getEdition(), is(equalTo(expectedBookDTO.getEdition())));

    }

    @Test
    void whenAlreadyRegisteredBookInformedThenAnExceptionShouldBeThrows() throws BookAlreadyRegisteredException {
        // given
        BookDTO expectedBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book duplicatedBook = bookMapper.toModel(expectedBookDTO);

        // when
        when(bookRepository.findByTitle(expectedBookDTO.getTitle())).thenReturn(Optional.of(duplicatedBook));

        // then
        Assertions.assertThrows(BookAlreadyRegisteredException.class, () -> bookService.createBook(expectedBookDTO));





    }


}
