package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) //연관관계는 무한루프때문에 @ToString 안하는게 좋다
@NamedQuery( //실무에서 거의 사용되지않음
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
public class Member extends JpaBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

//    @NoArgsConstructor 때문에 필요없음
//    protected Member() {
//    }

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }

    }

    public void changeTeam(Team team) {
        this.team = team; //내 팀을 바꾸고
        team.getMembers().add(this); //연관관계 팀에 추가 해줘야함
    }


}
