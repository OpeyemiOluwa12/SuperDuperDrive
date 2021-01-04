package com.opeyemi.superduperdrive.services;

import com.opeyemi.superduperdrive.mapper.CredentialsMapper;
import com.opeyemi.superduperdrive.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> getAllCredentials(int userId) {
        return credentialsMapper.getCredentialsByUserId(userId);
    }

    public int addCredential(Credentials credentials) {
        String key = generateKey();
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), key);
        credentials.setKey(key);
        credentials.setPassword(encryptedPassword);

        return credentialsMapper.insert(credentials);
    }

    public void updateCredential(Credentials credentials) {
        String key = generateKey();
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), key);
        credentials.setKey(key);
        credentials.setPassword(encryptedPassword);

        credentialsMapper.update(credentials);
    }

    public int deleteCredential(int credentialId) {
        return credentialsMapper.delete(credentialId);
    }

    public String generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
