package com.example.omshivgoraksha.data.remote

import android.util.Base64
import org.json.JSONObject

object JwtUtils {

    /**
     * Returns true if the JWT exists and is not expired.
     */
    fun isTokenValid(token: String?): Boolean {

        if (token.isNullOrBlank()) {
            return false
        }

        return try {

            val parts = token.split(".")

            // JWT must contain:
            // Header.Payload.Signature
            if (parts.size != 3) {
                return false
            }

            val payload = parts[1]

            val decodedBytes = Base64.decode(
                payload,
                Base64.URL_SAFE or
                        Base64.NO_WRAP or
                        Base64.NO_PADDING
            )

            val payloadJson =
                JSONObject(
                    String(decodedBytes, Charsets.UTF_8)
                )

            /*
             * exp is Unix timestamp in seconds.
             */
            val expirationTime =
                payloadJson.optLong("exp", 0L)

            if (expirationTime == 0L) {
                return false
            }

            val currentTime =
                System.currentTimeMillis() / 1000

            currentTime < expirationTime

        } catch (e: Exception) {

            false
        }
    }
}