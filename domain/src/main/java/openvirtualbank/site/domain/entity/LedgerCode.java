package openvirtualbank.site.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ledger_code")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerCode extends BaseEntity {

    @Id
    @Column(name = "ledger_code", length = 20)
    private String code;

    private String accountName;

    private String accountType;

    private String parentCode;

    private Boolean isLeaf;

    private String currency;

    private Boolean isActive;
}
