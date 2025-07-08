package openvirtualbank.site.member.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import openvirtualbank.site.domain.entity.BaseEntity;
import openvirtualbank.site.member.enums.MemberRole;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MemberRole memberRole;

	@Builder
	public Member(String email, String password, LocalDate birthDate, String firstName, MemberRole memberRole) {
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.firstName = firstName;
		this.memberRole = memberRole;
	}

}
