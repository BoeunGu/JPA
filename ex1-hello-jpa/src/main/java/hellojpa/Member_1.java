package hellojpa;

import javax.persistence.*;

@Entity
public class Member_1 {

    @Id@GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    //연관관계 매핑 (참조를 가져옴-> 객체지향적 설계)
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
}
