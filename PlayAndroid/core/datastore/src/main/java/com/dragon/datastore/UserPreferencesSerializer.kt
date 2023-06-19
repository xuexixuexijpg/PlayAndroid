package com.dragon.datastore

import androidx.datastore.core.Serializer
import com.dragon.core.datastore.UserPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * 对其序列化操作 单user配置文件操作
 */
class UserPreferencesSerializer @Inject constructor(
    private val cryptoManager: CryptoManager
) :
    Serializer<UserPreferences> {

    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserPreferences {
        return UserPreferences.parseFrom(cryptoManager.decrypt(input))
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        cryptoManager.encrypt(t.toByteArray(),output)
    }
}