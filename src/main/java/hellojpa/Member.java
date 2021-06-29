package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
// @Table(name="MER") => 매핑시 테이블 명 지정
public class Member {

    @Id
    private Long id;

    // @Column(unique = true, length = 10) DB에 영향을 주는 옵션
    private String name;

    public Member() {
    }

    public Member(long l, String a) {
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
}
