package com.example.stockquotesapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

import com.example.stockquotesapi.entities.Quote;

@RepositoryRestResource(collectionResourceRel = "quotes", path = "quotes")
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Optional <Quote> findFirstBySymbolOrderByTimestampDesc(String string);
    
    
}
