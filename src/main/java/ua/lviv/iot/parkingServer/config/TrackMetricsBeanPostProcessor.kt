package ua.lviv.iot.parkingServer.config

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.config.annotation.TrackMetrics
import kotlin.reflect.KClass


@Component
class TrackMetricsBeanPostProcessor : BeanPostProcessor {
    val map = mutableMapOf<String, KClass<*>>()

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val beanClass = bean::class

        val annotation = beanClass.java.isAnnotationPresent(TrackMetrics::class.java)
        if (annotation) {
            map[beanName] = beanClass
        }

        return bean
    }


    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val beanClass = map[beanName]

        if (beanClass != null) {
            return Proxy.newProxyInstance(
                beanClass.java.classLoader,
                beanClass.java.interfaces,
                InvocationHandler { _, method, args ->
                    val startTime = System.nanoTime()
                    val runtime = Runtime.getRuntime()
                    val memoryBefore = runtime.totalMemory() - runtime.freeMemory()

                    val result = method.invoke(bean, *(args ?: emptyArray()))

                    val endTime = System.nanoTime()
                    val memoryAfter = runtime.totalMemory() - runtime.freeMemory()
                    val elapsedTime = endTime - startTime

                    println("Bean '$beanName' initialized in $elapsedTime ns.")
                    println("Memory usage change: ${memoryAfter - memoryBefore} bytes.")

                    return@InvocationHandler result
                }
            )
        }

        return bean
    }
}