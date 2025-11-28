package art.cookedincode.spring6icecoldservice.listeners;

import art.cookedincode.spring6icecoldservice.config.KafkaConfig;
import art.cookedincode.spring6restmvcapi.events.DrinkPreparedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Georgi Ivanov.
 */
@Component
public class DrinkPreparedListener {

    AtomicInteger iceColdMessageCounter = new AtomicInteger(0);

    @KafkaListener(topics = KafkaConfig.DRINK_PREPARED_TOPIC, groupId = "ice-cold-listener")
    public void listen(DrinkPreparedEvent event) {
        System.out.println("I'm listening...");
        iceColdMessageCounter.incrementAndGet();
    }
}
