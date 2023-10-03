package ua.lviv.iot.parkingServer.natsconfig

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import reactor.core.scheduler.Scheduler
import ua.lviv.iot.parkingServer.natscontroller.NatsController

@Component
class NatsConfigurationBeanPostProcessor(private val handleMessageScheduler: Scheduler) : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is NatsController<*, *>) {
            val reactiveHandler = ReactiveMessageHandler(bean, handleMessageScheduler)
            bean.connection
                .createDispatcher(reactiveHandler)
                .subscribe(bean.subject)
        }
        return bean
    }
}
