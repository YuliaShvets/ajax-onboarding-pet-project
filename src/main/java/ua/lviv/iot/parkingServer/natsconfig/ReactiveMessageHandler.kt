package ua.lviv.iot.parkingServer.natsconfig

import io.nats.client.Message
import io.nats.client.MessageHandler
import reactor.core.scheduler.Scheduler
import ua.lviv.iot.parkingServer.natscontroller.NatsController

class ReactiveMessageHandler(
    private val natsController: NatsController<*, *>,
    private val scheduler: Scheduler
) : MessageHandler {

    override fun onMessage(msg: Message) {
        natsController.handle(msg)
            .map { it.toByteArray() }
            .doOnNext { natsController.connection.publish(msg.replyTo, it) }
            .subscribeOn(scheduler)
            .subscribe()
    }
}
