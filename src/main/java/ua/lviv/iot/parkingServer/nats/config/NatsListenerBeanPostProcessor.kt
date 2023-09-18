package ua.lviv.iot.parkingServer.nats.config

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.nats.config.controller.NatsController

@Component
class  NatsListenerBeanPostProcessor : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is NatsController<*, *>) {
            bean.connection.subscribe(bean.subject)
            val dispatcher = bean.connection.createDispatcher { message ->
                val response = bean.handle(message)
                bean.connection.publish(message.replyTo, response.toByteArray())
            }
            dispatcher.subscribe(bean.subject)
        }
        return bean
    }
}
