package com.tiennln.testaquariux.services.impl;

import com.tiennln.testaquariux.entities.User;
import com.tiennln.testaquariux.repositories.UserRepository;
import com.tiennln.testaquariux.services.UsersService;
import com.tiennln.testaquariux.utils.CredentialUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author TienNLN on 01/04/2023
 */
@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private UserRepository userRepository;

    @PostConstruct
    @Async
    public void initData() {
        var user = User.builder()
                .privateKey("OGQ1OTgwN2E1Yzc2YTRhMmJkNzZjYWNkYTY1OTE4YTljNTJkOWQ0NTJlZDFmMTljOTYyNmUyZjMwYjdhNGE1Ng==")
                .walletAddress("0x27d7E67b846233C5E8Fed8a1B9e5588934e23f34")
                .build();

        userRepository.save(user);
    }

    @Override
    public String getPrivateKeyByAddress(String address) {
        var userOpt = userRepository.findById(address);

        return userOpt.map(user -> CredentialUtil.decodePrivateKey(user.getPrivateKey()))
                .orElse(null);
    }
}
