package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer pk;

    @Column(name = "author")
    private Long author;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "text")
    private String text;

    @Column(name = "ad")
    private Long ad;
}
