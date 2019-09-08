package com.cashbook.cashbook.activity

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.cashbook.cashbook.MainActivity
import com.cashbook.cashbook.R
import com.cashbook.cashbook.account.FingerPrintFragment
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator


class FingerPrintActivity : AppCompatActivity() {
    private val DEFAULT_KEY_NAME = "default_key"

    var keyStore: KeyStore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_print)

        if (supportFingerprint()) {
            initKey()
            initCipher()
        }
    }

    fun supportFingerprint(): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show()
            return false
        } else {
            val keyguardManager = getSystemService(KeyguardManager::class.java)
            val fingerprintManager = getSystemService(FingerprintManager::class.java)
            if (!fingerprintManager!!.isHardwareDetected) {
                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show()
                return false
            } else if (!keyguardManager!!.isKeyguardSecure) {
                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show()
                return false
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    @TargetApi(23)
    private fun initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore?.load(null)
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val builder = KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    @TargetApi(23)
    fun initCipher() {
        try {
            var key = keyStore?.getKey(DEFAULT_KEY_NAME, null);
            var cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            showFingerPrintDialog(cipher)
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }

    private fun showFingerPrintDialog(cipher: Cipher) {
        val fragment = FingerPrintFragment()
        fragment.setCipher(cipher)
//        fragment.show(getFragmentManager(), "fingerprint")
    }

    fun onAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
