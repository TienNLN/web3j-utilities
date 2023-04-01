package com.tiennln.testaquariux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UserWallet {

    @Id
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ASSET_NAME")
    private String assetName;

    @Column(name = "BALANCE")
    private BigDecimal balance;
}
