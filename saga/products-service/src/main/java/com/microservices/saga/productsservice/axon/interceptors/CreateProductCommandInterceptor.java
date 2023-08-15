package com.microservices.saga.productsservice.axon.interceptors;

import com.microservices.saga.productsservice.axon.command.CreateProductCommand;
import com.microservices.saga.productsservice.axon.lookup.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Intercepts CreateProductCommand to check if product already exists
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository productLookupRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            log.info("Intercepted command: " + command.getPayloadType());

            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                val createProductCommand = (CreateProductCommand) command.getPayload();
                val productLookupEntity = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(),
                        createProductCommand.getTitle());

                if (productLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Product with productId %s or title %s already exists", createProductCommand.getProductId(), createProductCommand.getTitle())
                    );
                }
            }
            return command;
        };
    }
}
