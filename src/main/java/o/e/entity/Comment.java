package o.e.entity;


import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "created_at")
    private Date createdAt;

    private Integer rate;

    // Конструкторы, геттеры/сеттеры
    public Comment() {
    }

    public Comment(Long id, String message, Long authorId, Date createdAt, Integer rate) {
        this.id = id;
        this.message = message;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
