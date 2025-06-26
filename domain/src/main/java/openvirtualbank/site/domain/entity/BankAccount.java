package openvirtualbank.site.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;
import java.math.BigDecimal;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_account")
public class BankAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private Long financialProductId;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String productCode;

    private String accountNickname;

    @Column(nullable = false)
    private String currencyCode;

    @Column(nullable = false)
    private LocalDateTime openedAt;

    @Column(nullable = false)
    private LocalDateTime closedAt;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Boolean isJointAccount;

    private String linkedPhoneNumber;

    @Column(nullable = false)
    private Boolean isSmsNotificationEnabled;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isFrozen;

    private String frozenReason;

    private String branchCode;

    private Long employeeId;

    @Column(nullable = false)
    private String accountOrigin;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal holdAmount;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private String updatedBy;

    @Column(nullable = false)
    private Long version;
}
