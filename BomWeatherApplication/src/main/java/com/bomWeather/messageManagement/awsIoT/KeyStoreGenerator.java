package com.bomWeather.messageManagement.awsIoT;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The KeyStoreGenerator.
 * <p>
 * This class is responsible for parsing the private key and certificates
 * generated by AWS-IoT so that they can be used within the application.
 * <p>
 * The code provided here is taken from the sample from the AWS-IoT SDK.
 * <p>
 * <b>Warning: </b>None.
 * <p>
 * @author szeyick
 * @version 0.0.1
 */
public class KeyStoreGenerator {

	/**
	 * The logger to log events generated by this class.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(KeyStoreGenerator.class.getSimpleName());
	
	/**
	 * The KeyStorePasswordPair.
	 * <p>
	 * This class is a convenience class that is used to store the
	 * key store and password that has been generated from the private
	 * key and certificate generated from the AWS-IoT thing.
	 * <p> 
	 * <b>Warning: </b>None.
	 */
    public static class KeyStorePasswordPair {
        
    	public KeyStore keyStore;
        public String keyPassword;

        /**
         * @param keyStore - The keystore.
         * @param keyPassword - The password.
         */
        public KeyStorePasswordPair(KeyStore keyStore, String keyPassword) {
            this.keyStore = keyStore;
            this.keyPassword = keyPassword;
        }
    }

    /**
     * @param certificateFile - The location of the AWS-IoT generated certificate file.
     * @param privateKeyFile - The location of the AWS-IoT generated private key file.
     * @param keyAlgorithm - The key algorithm to use.
     * @return an object containing the generated files.
     */
    public static KeyStorePasswordPair getKeyStorePasswordPair(String certificateFile, String privateKeyFile,
            String keyAlgorithm) {
        if (certificateFile == null || privateKeyFile == null) {
        	LOGGER.info("Certificate or private key file missing");
            return null;
        }

        Certificate certificate = loadCertificateFromFile(certificateFile);
        PrivateKey privateKey = loadPrivateKeyFromFile(privateKeyFile, keyAlgorithm);
        if (certificate == null || privateKey == null) {
            return null;
        }
        return getKeyStorePasswordPair(certificate, privateKey);
    }

    /**
     * @param certificate - The AWS-IoT generated certificate as an object.
     * @param privateKey - The AWS-IoT generated private key as an object.
     * @return the certificate and private key as a combined object.
     */
    private static KeyStorePasswordPair getKeyStorePasswordPair(Certificate certificate, PrivateKey privateKey) {
        KeyStore keyStore = null;
        String keyPassword = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("alias", certificate);

            // randomly generated key password for the key in the KeyStore
            keyPassword = new BigInteger(128, new SecureRandom()).toString(32);
            keyStore.setKeyEntry("alias", privateKey, keyPassword.toCharArray(), new Certificate[] { certificate });
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            LOGGER.info("Failed to create key store");
            return null;
        }

        return new KeyStorePasswordPair(keyStore, keyPassword);
    }

    /**
     * @param filename - The name of the certificate file.
     * @return the parsed certificate as an object.
     */
    private static Certificate loadCertificateFromFile(String filename) {
        Certificate certificate = null;

        File file = new File(filename);
        if (!file.exists()) {
            LOGGER.info("Certificate file not found: " + filename);
            return null;
        }
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            certificate = certFactory.generateCertificate(stream);
        } catch (IOException | CertificateException e) {
        	LOGGER.info("Failed to load certificate file " + filename);
        }
        return certificate;
    }

    /**
     * @param filename - The name of the private key file.
     * @param algorithm - The algorithm to use.
     * @return the parsed private key as an object.
     */
    private static PrivateKey loadPrivateKeyFromFile(String filename, String algorithm) {
        PrivateKey privateKey = null;

        File file = new File(filename);
        if (!file.exists()) {
        	LOGGER.info("Private key file not found: " + filename);
            return null;
        }
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            privateKey = PrivateKeyReader.getPrivateKey(stream, algorithm);
        } catch (IOException | GeneralSecurityException e) {
            LOGGER.info("Failed to load private key from file " + filename);
        }
        return privateKey;
    }
}
