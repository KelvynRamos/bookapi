package one.digitalinnovation.bookapi.repository;

import one.digitalinnovation.bookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByTitle(String title);
}
