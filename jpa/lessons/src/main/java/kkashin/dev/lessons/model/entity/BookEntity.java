package kkashin.dev.lessons.model.entity;

import jakarta.persistence.*;
import kkashin.dev.lessons.model.Author;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    public BookEntity() {}
    public BookEntity(
            Long id,
            String name,
            Author author,
            Integer publicationYear,
            Integer pageNumber,
            Integer cost
    ) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
        this.pageNumber = pageNumber;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
