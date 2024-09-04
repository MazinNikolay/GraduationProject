package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Schema(description = "id автора комментария")
    private Integer author;
    @Schema(description = "ссылка на аватар автора комментария")
    private String authorImage;
    @Schema(description = "имя создателя комментария")
    private String authorFirstName;
    @Schema(description = "дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    private Long createdAt;
    @Schema(description = "id комментария")
    private Integer pk;
    @Schema(description = "текст комментария")
    private String text;

    public static Comment toDto(CommentEntity commentEntity, UserEntity userEntity) {
        return Comment.builder()
                .pk(commentEntity.getPk())
                .author(commentEntity.getAuthor())
                .authorImage(userEntity.getImage())
                .authorFirstName(userEntity.getFirstName())
                .createdAt(commentEntity.getCreatedAt())
                .text(commentEntity.getText())
                .build();
    }

    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .pk(pk)
                .author(author)
                .createdAt(createdAt)
                .text(text)
                .build();
    }
}
