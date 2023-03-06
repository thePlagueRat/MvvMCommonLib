package com.kotlin.lib.network.manager

import android.util.Log
import com.kotlin.lib.entity.ServiceParam
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetManager {
    private const val TAG = "NetManager"
    private var debug = true
    private val serviceMap = hashMapOf<String, Any>()
    private val serviceParamMap = hashMapOf<String, ServiceParam>()
    private lateinit var defaultServiceParam: ServiceParam

    fun setDebug(debug: Boolean) {
        this.debug = debug
    }

    fun <T> getService(serviceClass: Class<T>): T {
        val className = serviceClass.simpleName
        val service = serviceMap[className]
        return if (service == null) {
            val serviceParam = getServiceConfig(className)
            val newService = Retrofit
                    .Builder()
                    .baseUrl(serviceParam.baseUrl)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(getClient(serviceParam))
                    .addConverterFactory(serviceParam.factory)
                    .build()
                    .create(serviceClass)
            serviceMap[className] = newService!!
            newService
        } else {
            service as T
        }
    }

    private fun getClient(serviceParam: ServiceParam): OkHttpClient {
        var builder = OkHttpClient
                .Builder()
                /**
                 * 连接超时时间
                 */
                .connectTimeout(serviceParam.connectTimeout, TimeUnit.MILLISECONDS)
                /**
                 * 读取数据超时时间
                 */
                .readTimeout(serviceParam.readTimeout, TimeUnit.MILLISECONDS)
        serviceParam.interceptors.forEach {
            builder = builder.addInterceptor(it)
        }
        if (debug) {
            val loggingInterceptor = HttpLoggingInterceptor() {
                Log.e(TAG, it)
            }
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder = builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    fun init(baseUrl: String, factory: Converter.Factory = GsonConverterFactory.create(), connectTimeout: Long = 5000, readTimeout: Long = 20000, interceptors: MutableList<Interceptor> = mutableListOf()) {
        defaultServiceParam = ServiceParam(baseUrl, factory, connectTimeout, readTimeout, interceptors);
        Log.i(TAG, "NetManager init success")
    }

    fun putServiceConfig(serviceClassName: String, param: ServiceParam = defaultServiceParam) {
        serviceParamMap[serviceClassName] = param
    }

    fun getServiceConfig(serviceClassName: String): ServiceParam {
        var serviceParam = serviceParamMap[serviceClassName]
        if (serviceParam != null) {
            return serviceParam
        }
        return defaultServiceParam
    }

}