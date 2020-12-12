const sjcl = require('sjcl')

export default {
    /**
     * 암호화 한다.
     *
     * @param text 암호화할 텍스트
     * @param key Base64 인코딩된 암호화 키
     * @returns {string} Base64 인코딩된 암호화 값
     */
    encrypt(text, key) {
        const cipher = this._cipher(key)
        const iv = this._iv()
        const t = sjcl.codec.utf8String.toBits(text)
        const encrypted = sjcl.mode.gcm.encrypt(cipher, t, iv)
        const concat = sjcl.bitArray.concat(iv, encrypted)
        return sjcl.codec.base64.fromBits(concat)
    },
    /**
     * 복호화 한다.
     * @param encoded Base64 인코딩된 암호화 값
     * @param key Base64 인코딩된 암호화 키
     * @returns {string} 복호화된 텍스트
     */
    decrypt(encoded, key) {
        const cipher = this._cipher(key)
        const concat = sjcl.codec.base64.toBits(encoded)
        const iv = sjcl.bitArray.bitSlice(concat, 0, 12 * 8)
        const encrypted = sjcl.bitArray.bitSlice(concat, 12 * 8, undefined)
        const decrypted = sjcl.mode.gcm.decrypt(cipher, encrypted, iv)
        return sjcl.codec.utf8String.fromBits(decrypted)
    },
    _cipher(key) {
        return new sjcl.cipher.aes(sjcl.codec.base64.toBits(key))
    },
    _iv() {
        return sjcl.random.randomWords(3)
    }
}
