package com.msa.jrg.userservice.client;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiscoveryService {

    private final LoadBalancerClient loadBalancerClient;
    private final DiscoveryClient discoveryClient;

    public DiscoveryService(LoadBalancerClient loadBalancerClient, DiscoveryClient discoveryClient) {
        this.loadBalancerClient = loadBalancerClient;
        this.discoveryClient = discoveryClient;
    }

    public String getServiceAddressFor(String serviceName) {
        return Flux.defer(() -> Flux.just(this.discoveryClient.getInstances(serviceName))
                .flatMap(serviceInstances -> Mono.just(this.loadBalancerClient.choose(serviceName)))
                .flatMap(serviceInstance -> Mono.just(serviceInstance.getUri().toString())))
                .next().block();
    }
}
