package com.gowhich.springdemo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(SpringDemoApplication.class);

	@Autowired
    JdbcTemplate jdbcTemplate;

	@Override
    public void run(String... strings) throws Exception {
        log.info("Creating tables");
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS ");
        jdbcTemplate.execute("CREATE TABLE customers ("+
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
        List<Object[]> splitUpNames = Arrays.asList("John Woo","Jeff Dean", "Josn Bokch", "Josh Long").stream()
                .map(name -> name.split(" ")).collect(Collectors.toList());

        splitUpNames.forEach(name -> log.info(String.format("Inserting customers record for %s %s", name[0], name[1])));
        jdbcTemplate.batchUpdate("INSERT INTO customers (first_name, last_name) VALUES (?, ?)", splitUpNames);
        log.info("Querying for customers records where first_name = 'Josh");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                new Object[] {"Josh"},
                (rs, column) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")))
        .forEach(customer -> log.info(customer.toString()));

    }
}
