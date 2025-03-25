package com.ramajogi.lifefoodlife.data.repository

import com.ramajogi.lifefoodlife.data.db.DeliveryDao
import com.ramajogi.lifefoodlife.data.network.ApiService
import com.ramajogi.lifefoodlife.data.network.AuthApiService
import com.ramajogi.lifefoodlife.data.network.AuthInterceptor
import com.ramajogi.lifefoodlife.data.network.TokenManager
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class DeliveryRepositoryImplTest {
    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var authApi: AuthApiService
    private val dao = mock<DeliveryDao>()
    private lateinit var repository: DeliveryRepositoryImpl
    private lateinit var tokenManager: TokenManager
    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        tokenManager = mock<TokenManager>().apply {
            `when`(getAccessToken()).thenReturn("old_token")
            `when`(getRefreshToken()).thenReturn("refresh_token")
            `when`(getAccessToken()).thenReturn("new_token") // After refresh
        }
        // Auth Retrofit (no interceptor)
        val authRetrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authApi = authRetrofit.create(AuthApiService::class.java)
        val interceptor = AuthInterceptor(tokenManager, authApi, Dispatchers.IO)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

        repository = DeliveryRepositoryImpl(dao, api)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun testPlaceDeliveryOrder() = runBlocking {
        val order = DeliveryOrder("ORD123", 1, DeliveryStatus.PLACED, Date(), Date())
        server.enqueue(MockResponse()
            .setBody("""{"orderId":"ORD123","recipeId":1,"status":"PLACED","placedAt":${Date().time},"estimatedDeliveryTime":${Date().time}}""")
            .setResponseCode(200))

        val result = repository.placeDeliveryOrder(1)
        assertEquals("ORD123", result.orderId)
        assertEquals(DeliveryStatus.PLACED, result.status)
    }

    @Test
    fun testUpdateDeliveryStatus() = runBlocking {
        val order = DeliveryOrder("ORD456", 2, DeliveryStatus.SHIPPED, Date(), Date())
        server.enqueue(MockResponse()
            .setBody("""{"orderId":"ORD456","recipeId":2,"status":"DELIVERED","placedAt":${Date().time},"estimatedDeliveryTime":${Date().time}}""")
            .setResponseCode(200))

        val result = repository.updateDeliveryStatus("ORD456", DeliveryStatus.DELIVERED)
        assertEquals(DeliveryStatus.DELIVERED, result?.status)
    }

    @Test
    fun testTokenRefreshOn401() = runBlocking {
        // Simulate 401 response for initial request
        server.enqueue(MockResponse().setResponseCode(401))
        // Simulate successful token refresh
        server.enqueue(MockResponse()
            .setBody("""{"accessToken":"new_token","refreshToken":"new_refresh_token"}""")
            .setResponseCode(200))
        // Simulate successful retry with new token
        server.enqueue(MockResponse()
            .setBody("""{"orderId":"ORD123","recipeId":1,"status":"PLACED","placedAt":${Date().time},"estimatedDeliveryTime":${Date().time}}""")
            .setResponseCode(200))

        val result = repository.placeDeliveryOrder(1)
        assertEquals("ORD123", result.orderId)
        assertEquals(DeliveryStatus.PLACED, result.status)
    }
}