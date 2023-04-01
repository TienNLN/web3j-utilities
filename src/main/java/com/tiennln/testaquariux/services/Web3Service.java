package com.tiennln.testaquariux.services;

import com.tiennln.testaquariux.dtos.responses.WalletAssetResponse;

import java.util.List;

/**
 * The interface Web3 service.
 *
 * @author TienNLN on 31/03/2023
 */
public interface Web3Service {

    /**
     * Gets wallet balance.
     *
     * @param walletAddress the wallet address
     * @return the wallet balance
     */
    List<WalletAssetResponse> getWalletBalance(String walletAddress);
}
