package art.cookedincode.spring6icecoldservice.listeners;

import art.cookedincode.spring6icecoldservice.config.KafkaConfig;
import art.cookedincode.spring6restmvcapi.events.DrinkRequestEvent;
import art.cookedincode.spring6restmvcapi.model.BeerDTO;
import art.cookedincode.spring6restmvcapi.model.BeerOrderLineDTO;
import art.cookedincode.spring6restmvcapi.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Georgi Ivanov.
 */
@SpringBootTest
@EmbeddedKafka(controlledShutdown = true, topics = {KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC, KafkaConfig.DRINK_PREPARED_TOPIC}, partitions = 1)
class DrinkRequestListenerTest {

    @Autowired
    private DrinkRequestListener drinkRequestListener;

    @Autowired
    DrinkPreparedListener drinkPreparedListener;

    @Test
    void listenDrinkRequest() {
        drinkRequestListener.listenDrinkRequest(DrinkRequestEvent.builder()
                .beerOrderLine(createDto())
                .build());

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertEquals(1, drinkPreparedListener.iceColdMessageCounter.get());
        });
    }

    public BeerOrderLineDTO createDto() {
        return BeerOrderLineDTO.builder()
                .beer(BeerDTO.builder()
                        .id(UUID.randomUUID())
                        .beerStyle(BeerStyle.IPA)
                        .beerName("Test Beer")
                        .build())
                .build();
    }
}