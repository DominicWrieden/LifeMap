package com.dominicwrieden.api.implementation.old.authentication

import com.dominicwrieden.api.BuildConfig
import com.dominicwrieden.api.implementation.old.authentication.model.LoginResponse
import com.dominicwrieden.api.implementation.old.authentication.source.local.AuthenticationSharedPreferences
import com.dominicwrieden.api.implementation.old.authentication.source.retrofit.AuthenticationApi
import com.squareup.moshi.Moshi
import okhttp3.*
import java.io.IOException

internal class AuthenticationInterceptor(
    private val authenticationSharedPreferences: AuthenticationSharedPreferences,
    private val authenticationManager: AuthenticationManager
) : Interceptor {

    companion object {
        private val TOKEN_INVALID = "invalid"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        //original request
        val originalRequest = chain.request()

        //token of the original request
        val originalToken = authenticationSharedPreferences.getToken()


        val response = chain.proceed(originalRequest)

        //if request failed because of an invalid token
        if (response.code() > 200 && response.body()?.string()?.contains(TOKEN_INVALID) == true) {


            synchronized(this) {

                response.close()

                //get current token to check if the original token has already been updated, through this process
                val currentToken = authenticationSharedPreferences.getToken()

                if (currentToken == originalToken) {

                    val refreshTokenResponseCode = refreshToken()

                    if (refreshTokenResponseCode > 200) {
                        logout()

                        return response
                    }


                }

                val newToken = authenticationSharedPreferences.getToken()

                if (newToken.isNotBlank()) {
                    val updatedOriginalRequestUrl =
                        originalRequest.url().toString().replace(originalToken, newToken)
                    val newRequest = originalRequest.newBuilder()
                        .url(updatedOriginalRequestUrl)
                        .build()

                    return chain.proceed(newRequest)
                }
            }


        }


        return response
    }


    private fun refreshToken(): Int {

        // Builds a client...
        val client = OkHttpClient.Builder().build()


        val authenticationUrl =
            HttpUrl.get(BuildConfig.API_BASE_URL + AuthenticationApi.AUTHENTICATION_REQUEST_ENDPOINT)
                .newBuilder()
                .addQueryParameter(
                    AuthenticationApi.AUTHENTICATION_REQUEST_QUERY_EMAIL,
                    authenticationSharedPreferences.getUserName()
                )
                .addQueryParameter(
                    AuthenticationApi.AUTHENTICATION_REQUEST_QUERY_PASSWORD,
                    authenticationSharedPreferences.getPassword()
                )
                .build()


        val authenticationRequest = Request.Builder()
            .post(RequestBody.create(null, ""))
            .url(authenticationUrl)
            .build()


        var response: Response? = null
        var code = 0

        try {
            response = client.newCall(authenticationRequest).execute()

            if (response != null) {

                code = response.code()

                when (code) {
                    200 -> {
                        // READS NEW TOKENS AND SAVES THEM -----------------------------------------
                        val moshi = Moshi.Builder().build()

                        val loginResponse = moshi.adapter(LoginResponse::class.java)
                            .fromJson(response.body()!!.string())

                        authenticationSharedPreferences.saveToken(loginResponse!!.token)


                    }
                    else -> {
                    }
                }

                response.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return code
    }


    /**
     * Clears the access token, refresh token and account id from the shared preferences.
     * Also closes all activities and opens the login activity
     */
    private fun logout() {
        //TODO should not call logout. It should emit an AUTHENTICATION_ERROR, and the app should respond with an logout
        authenticationManager.logout()
    }
}

