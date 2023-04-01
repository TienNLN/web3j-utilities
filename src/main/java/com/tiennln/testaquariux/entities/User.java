package com.tiennln.testaquariux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author TienNLN on 01/04/2023
 */
@Entity
@Table(name = "USERS", schema = "PUBLIC")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 5906525586761429416L;

    @Id
    @Column(name = "WALLET_ADDRESS", nullable = false, unique = true)
    private String walletAddress;

    @Column(name = "PRIVATE_KEY", nullable = false, unique = true)
    private String privateKey;
}
