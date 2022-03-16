package com.example.newsapp.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitInstance {

    private const val BASE_URL = "https://newsapi.org/"
    private const val API_KEY_HEADER = "X-Api-Key"
    private const val API_KEY = "962dc8a491734f0b9b822724c04b8eca"

    private val retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader(API_KEY_HEADER, API_KEY)
                    .build()
                it.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }

}

//class
//NetworkHelper(
//    val
//    context:
//    Context
//) {
//
//
//    fun
//            isNetworkConnected():
//            Boolean {
//
//
//        var result =
//            false
//
//
//        val connectivityManager =
//
//
//            context.getSystemService(Context.CONNECTIVITY_SERVICE)
//                    as
//                    ConnectivityManager
//
//
//        if (Build.VERSION.SDK_INT
//            >=
//            Build.VERSION_CODES.M
//        ) {
//
//
//            val networkCapabilities = connectivityManager.activeNetwork
//                ?: return
//            false
//
//
//            val activeNetwork =
//
//
//                connectivityManager.getNetworkCapabilities(networkCapabilities)
//                    ?: return
//            false
//
//
//            result
//            = when {
//
//
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//                ->
//                    true
//
//
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
//                ->
//                    true
//
//
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
//                ->
//                    true
//
//
//                else
//                ->
//                    false
//
//
//            }
//
//
//        } else {
//
//
//            connectivityManager.run {
//
//
//                connectivityManager.activeNetworkInfo?.run {
//
//
//                    result
//                    = when (type) {
//
//
//                    ConnectivityManager.TYPE_WIFI
//                    ->
//                        true
//
//
//                    ConnectivityManager.TYPE_MOBILE
//                    ->
//                        true
//
//
//                    ConnectivityManager.TYPE_ETHERNET
//                    ->
//                        true
//
//
//                    else
//                    ->
//                        false
//                }
//                }
//            }
//        }
//
//        return result
//
//
//    }
//
//
//}