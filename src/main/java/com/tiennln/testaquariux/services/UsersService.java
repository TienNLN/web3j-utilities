package com.tiennln.testaquariux.services;

/**
 * The interface User service.
 *
 * @author TienNLN on 01/04/2023
 */
public interface UsersService {

    /**
     * Gets private key by address.
     *
     * @param address the address
     * @return the private key by address
     */
    String getPrivateKeyByAddress(String address);
}
