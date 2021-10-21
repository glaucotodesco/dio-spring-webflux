package com.example.stockquotesapi;

import java.time.LocalDateTime;

import com.example.stockquotesapi.entities.Quote;
import com.example.stockquotesapi.repository.QuoteRepository;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableScheduling
public class StockQuotesApiApplication {

	@Autowired
	private QuoteRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StockQuotesApiApplication.class, args);
	}

	@Scheduled(fixedDelay = 1000)
	public void gererateData(){

		Quote q= repository.findFirstBySymbolOrderByTimestampDesc("Teste")
				.map(this::generateData)
				.orElseGet(this::initData);
					
		log.info(q);		
	}

	private Quote generateData(Quote quote) {
		
		Quote newQuote = Quote.builder()
					.closeValue(quote.getCloseValue() * new RandomDataGenerator().nextUniform(-0.10, 0.10))
					.openValue(quote.getOpenValue() * new RandomDataGenerator().nextUniform(-0.10, 0.10)) //
					.symbol(quote.getSymbol())
					.timestamp(LocalDateTime.now())
					.build();

		return repository.save(newQuote);
	}

	private Quote initData() {
		return repository.save(
			Quote.builder()
					.closeValue(1.0)
					.openValue(1.0)
					.symbol("Teste")
					.timestamp(LocalDateTime.now())
					.build()
		);

	  
	}
}
