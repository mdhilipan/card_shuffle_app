package com.seegrid.repository;

import com.seegrid.model.Deck;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

/*
Extending MongoRepository and adding async behaviour to it
so we are not blocked while persisting
 */
@Repository
public interface MongoDeckRepository extends MongoRepository<Deck, String> {

     @Async
     default void saveAsync(Deck deck) {
         save(deck);
     }
}
