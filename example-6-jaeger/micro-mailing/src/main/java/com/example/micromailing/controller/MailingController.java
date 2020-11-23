package com.example.micromailing.controller;

import com.example.micromailing.pojo.Customer;
import com.example.micromailing.pojo.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
public class MailingController {

    private static final Logger log = LoggerFactory.getLogger(MailingController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/constructEmail/{customerId}")
    public String constructEmail(@PathVariable int customerId) {
        log.info("Called MailingController::constructEmail, customerId = {}", customerId);
        // sekvencni volani
        Customer customer = restTemplate.getForObject("http://crm:8080/customers/" + customerId, Customer.class);
        List<Product> products = Arrays.asList(restTemplate.getForObject("http://products:8080/products", Product[].class));
        return "TODO send email to: " + customer.getEmail() + " to say hello to: " + customer.getName()
                + ", send him info about : " + products.size() + " products";

        // paralelni volani
//        WebClient webClientCrm = WebClient.create("http://crm:8080");
//        WebClient webClientProducts = WebClient.create("http://products:8080");
//        Mono<Customer> customerMono = webClientCrm
//                .get()
//                .uri("customers/" + customerId)
//                .retrieve()
//                .bodyToMono(Customer.class);
//        Flux<Product> products = webClientProducts
//                .get()
//                .uri("products")
//                .retrieve()
//                .bodyToFlux(Product.class);

    }

}
