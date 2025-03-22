package com.lifecycle.life.androidcrypto

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@RequiresApi(Build.VERSION_CODES.M)
class `UserSettingsSerializer(
    private val cryptoManager: CryptoManager
) : Serializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings()


    override suspend fun readFrom(input: InputStream): UserSettings {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = UserSettings.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            return defaultValue
        }
    }


    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
       cryptoManager.encrypt(
           byteArray =   Json.encodeToString(
               serializer = UserSettings.serializer(),
               value = t
           ).encodeToByteArray(),
           outputStream = output
       )
    }
}