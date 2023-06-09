package com.tiennln.testaquariux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author TienNLN on 02/04/2023
 */
@Entity
@Table(name = "USERS_WALLET", schema = "PUBLIC")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWallet implements Serializable {
    @Serial
    private static final long serialVersionUID = 8779561839001490084L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ASSET_NAME")
    private String assetName;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
