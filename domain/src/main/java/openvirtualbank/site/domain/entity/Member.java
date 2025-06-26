package openvirtualbank.site.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import openvirtualbank.site.domain.enums.member.MemberRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<BankAccount> bankAccounts = new ArrayList<>();

    private String email;

    private String password;

    private LocalDate birthDate;

    private String firstName;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;


}
