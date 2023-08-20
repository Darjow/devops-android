
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    companion object {
        private var authToken: String? = null

        fun setAuthToken(token: String) {
            authToken = token
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (authToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer $authToken")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
