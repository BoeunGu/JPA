package hellojpa;

import javax.persistence.*;
import java.util.Date;

//Jpa가 관리하는 클래스로 인식
@Entity
public class Member {

    @Id
    private Long id;

    @Column(name ="name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    //enum타입으로 DB에 매칭
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    //DB에서 (날짜-시간)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    //큰 컨텐츠 사이즈를 사용 할때
    private String description;

    public Member(){

    }




}
