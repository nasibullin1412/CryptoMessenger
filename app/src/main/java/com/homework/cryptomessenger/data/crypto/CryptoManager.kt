package com.homework.cryptomessenger.data.crypto

import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoManager {

    /**
     * Default nonce size in bytes.
     */
    private const val IV_SIZE = 12

    /**
     * Default authentication tag size in bits.
     */
    private const val TAG_SIZE = 128

    /**
     * Alias for the application AES key.
     */
    private const val ALIAS_KEY = "my_key"

    /**
     * AES-GCM cipher.
     */
    private const val CIPHER_AES_GCM = "AES/GCM/NoPadding"

    private val cipher: Cipher by lazy { Cipher.getInstance(CIPHER_AES_GCM); }

    /**
     * Encrypt a piece of text with AES-GCM cipher.
     *
     *
     * Note that the cipher can encrypt any byte sequence.
     *
     * @param plainText text to be encrypted
     * @return concatenated nonce and cipher output
     */
    fun encrypt(plainText: ByteArray, key: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"))
        val iv: ByteArray = cipher.iv
        val cipherText = ByteArray(IV_SIZE + cipher.getOutputSize(plainText.size))
        System.arraycopy(iv, 0, cipherText, 0, IV_SIZE)
        cipher.doFinal(plainText, 0, plainText.size, cipherText, IV_SIZE)
        return cipherText
    }

    fun decrypt(cipherText: ByteArray, key: ByteArray?): ByteArray {
        cipher.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec(key, "AES"),
            GCMParameterSpec(TAG_SIZE, cipherText, 0, IV_SIZE)
        )
        return cipher.doFinal(cipherText, IV_SIZE, cipherText.size - IV_SIZE)
    }
}
