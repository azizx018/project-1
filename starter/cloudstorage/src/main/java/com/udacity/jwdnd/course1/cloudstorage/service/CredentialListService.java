package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialListService {
    private  final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialListService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }
    private void encryptPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

    }

    public int updateCred(Credential credential) {
        encryptPassword(credential);
        credential.setCredentialId(credential.getCredentialId());
        credential.setUrl(credential.getUrl());
        credential.setUsername(credential.getUsername());
        credential.setKey(credential.getKey());
        credential.setPassword(credential.getPassword());
        return credentialMapper.updateCred(credential);

    }
    public int saveCred(Credential credential, Integer userId) {
        encryptPassword(credential);
        return credentialMapper.saveCred(new Credential(null, credential.getUrl(), credential.getUsername(),
                credential.getKey(), credential.getPassword(), userId));
    }
    public List<Credential> getCredByUser(Integer userId) {
        return credentialMapper.getCredentialByUser(userId);
    }
    public int deleteCred(Integer credentialId) {
        return credentialMapper.deleteCredByCredId(credentialId);
    }
}
