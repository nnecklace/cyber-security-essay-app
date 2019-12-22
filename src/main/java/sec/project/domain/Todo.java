package sec.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Todo extends AbstractPersistable<Long> {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Boolean done;
    private Long userid;

    public Todo() {
        super();
    }

    public Todo(String content, Long userid) {
        this();
        this.content = content;
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public Long getUserid() {
        return userid;
    }

    public Boolean getDone() {
        return done;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}