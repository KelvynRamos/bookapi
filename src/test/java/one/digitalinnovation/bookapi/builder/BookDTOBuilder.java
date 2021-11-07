package one.digitalinnovation.bookapi.builder;

import lombok.Builder;
import one.digitalinnovation.bookapi.dto.request.BookDTO;
import one.digitalinnovation.bookapi.entity.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
public class BookDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String title = "A Roda do Tempo";

    @Builder.Default
    private Long edition = 1L;

    @Builder.Default
    private String publicationDate = "22-07-1991";

    public BookDTO toBookDTO(){
        return new BookDTO(id,
                title,
                edition,
                publicationDate);
    }
}
