package ua.lviv.iot.parkingServer.config.bpp

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy
import mu.KLogger
import mu.KotlinLogging
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.annotation.TrackMetrics
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

    @Suppress("SpreadOperator")
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val beanClass = map[beanName]
        val logger: KLogger = KotlinLogging.logger {}
        beanClass?.apply {
            return Proxy.newProxyInstance(
                beanClass.java.classLoader,
                beanClass.java.interfaces,
                InvocationHandler { _, method, args ->
                    val (startTime, memoryBefore) = getMem()

                    val result = method.invoke(bean, *(args ?: emptyArray()))

                    val (endTime, memoryAfter) = getMem()

                    logger.info("Bean '$beanName' initialized in ${endTime - startTime} ns.")
                    logger.info("Memory usage change: ${memoryAfter - memoryBefore} bytes.")

                    return@InvocationHandler result
                }
            )
        }
        return bean
    }

    private fun getMem(): Pair<Long, Long> {
        val startTime = System.nanoTime()
        val runtime = Runtime.getRuntime()
        val memoryBefore = runtime.totalMemory() - runtime.freeMemory()
        return Pair(startTime, memoryBefore)
    }
}
