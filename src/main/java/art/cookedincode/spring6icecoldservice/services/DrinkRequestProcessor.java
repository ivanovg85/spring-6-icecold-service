package art.cookedincode.spring6icecoldservice.services;

import art.cookedincode.spring6restmvcapi.events.DrinkRequestEvent;

/**
 * Created by Georgi Ivanov.
 */
public interface DrinkRequestProcessor {

    void processDrinkRequest(DrinkRequestEvent event);
}
