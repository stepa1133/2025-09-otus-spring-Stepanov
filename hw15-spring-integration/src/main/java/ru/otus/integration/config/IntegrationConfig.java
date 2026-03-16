package ru.otus.integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import ru.otus.integration.domain.Order;
import ru.otus.integration.domain.Product;
import ru.otus.integration.domain.ProductType;
import ru.otus.integration.service.CheeseService;
import ru.otus.integration.service.CottageCheeseService;
import ru.otus.integration.service.MilkService;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final OrderFilter orderFilter;

    @Bean
    public MessageChannel orderChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public IntegrationFlow orderFlow(MilkService milkService,
                                     CheeseService cheeseService,
                                     CottageCheeseService cottageCheeseService) {
        return IntegrationFlow.from(orderChannel())
                .filter(orderFilter, "filter")
                .split(Order.class, order -> order.getItems().entrySet())
                // Route by product type
                .<Map.Entry<ProductType, Integer>, ProductType>route(
                        Map.Entry::getKey,
                        mapping -> mapping

                                .subFlowMapping(ProductType.MILK, flow ->
                                        flow.handle(milkService, "produce")
                                )

                                .subFlowMapping(ProductType.CHEESE, flow ->
                                        flow.handle(cheeseService, "produce")
                                )

                                .subFlowMapping(ProductType.COTTAGE_CHEESE, flow ->
                                        flow.handle(cottageCheeseService, "produce")
                                )
                )

                // Collect results
                .aggregate(a -> a
                        .outputProcessor(group ->
                                group.getMessages()
                                        .stream()
                                        .map(m -> (Product) m.getPayload())
                                        .toList()
                        )
                )

                .log("ORDER COMPLETED")

                .get();
    }
}
