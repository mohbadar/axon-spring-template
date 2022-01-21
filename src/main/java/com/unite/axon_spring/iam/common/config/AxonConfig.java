package com.unite.axon_spring.iam.common.config;

import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.messaging.StreamableMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Autowired
    public void configureProcessors(EventProcessingConfigurer eventProcessingConfigurer) {
        TrackingEventProcessorConfiguration tepConfig = TrackingEventProcessorConfiguration.forSingleThreadedProcessing().andInitialTrackingToken(StreamableMessageSource::createHeadToken);
        eventProcessingConfigurer.registerTrackingEventProcessorConfiguration(config -> tepConfig);
    }
}