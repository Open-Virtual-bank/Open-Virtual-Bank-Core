package openvirtualbank.site.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "ledger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ledger extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ledger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_code", nullable = false)
    private LedgerCode ledgerCode;

    @Column(name = "entry_seq", nullable = false)
    private Integer entrySeq;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Column(name = "direction")
    private String direction;

    @Column(name = "occured_at")
    private LocalDateTime occuredAt;
}
