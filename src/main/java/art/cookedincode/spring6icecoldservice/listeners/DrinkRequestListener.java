package art.cookedincode.spring6icecoldservice.listeners;

import art.cookedincode.spring6icecoldservice.config.KafkaConfig;
import art.cookedincode.spring6icecoldservice.services.DrinkRequestProcessor;
import art.cookedincode.spring6restmvcapi.events.DrinkPreparedEvent;
import art.cookedincode.spring6restmvcapi.events.DrinkRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Georgi Ivanov.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrinkRequestListener {

    private final DrinkRequestProcessor drinkRequestProcessor;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(groupId = "IceColdListener", topics = KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC)
    public void listenDrinkRequest(DrinkRequestEvent drinkRequestEvent) {
        log.debug("I am listening - drink request");

        drinkRequestProcessor.processDrinkRequest(drinkRequestEvent);

        kafkaTemplate.send(KafkaConfig.DRINK_PREPARED_TOPIC, DrinkPreparedEvent.builder()
                .beerOrderLine(drinkRequestEvent.getBeerOrderLine())
                .build());
    }
}
