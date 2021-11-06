package one.digitalinnovation.bookapi.mapper;

import one.digitalinnovation.bookapi.dto.request.BookDTO;
import one.digitalinnovation.bookapi.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "publicationDate", source = "publicationDate", dateFormat = "dd-MM-yyyy")
    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book book);

}
