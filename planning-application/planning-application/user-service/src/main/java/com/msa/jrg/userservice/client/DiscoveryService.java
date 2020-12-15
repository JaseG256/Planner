package com.msa.jrg.userservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(DiscoveryService.class);
    private final LoadBalancerClient loadBalancerClient;
    private final DiscoveryClient discoveryClient;

    public DiscoveryService(LoadBalancerClient loadBalancerClient, DiscoveryClient discoveryClient) {
        this.loadBalancerClient = loadBalancerClient;
        this.discoveryClient = discoveryClient;
    }

    public String getServiceAddressFor(String serviceName) {
        logger.trace("Service name: {}", serviceName);
        System.out.print(serviceName);
        logger.trace("Found service: {}", discoveryClient.getServices());
        System.out.print(discoveryClient.getServices());
        return Flux.defer(() -> {
            logger.trace("Service instances: {}", this.discoveryClient.getInstances(serviceName).toString());
            System.out.print(Flux.just(this.discoveryClient.getInstances(serviceName)));
            return Flux.just(this.discoveryClient.getInstances(serviceName))
                .flatMap(serviceInstances -> {
                    logger.trace(this.loadBalancerClient.choose(serviceName).toString());
                    System.out.print(Mono.just(this.loadBalancerClient.choose(serviceName)));
                    return Mono.just(this.loadBalancerClient.choose(serviceName));
                })
                .flatMap(serviceInstance -> {
                    logger.trace(serviceInstance.getUri().toString());
                    System.out.print(Mono.just(serviceInstance.getUri().toString()));
                    return Mono.just(serviceInstance.getUri().toString());
                });
        }).next().block();
    }
}
