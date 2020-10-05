package com.seegrid.Services;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.seegrid.algorithms.shuffle.IDeckShuffler;
import com.seegrid.algorithms.shuffle.factory.ShuffleAlgorithmsFactory;
import com.seegrid.customexception.DeckNotFoundException;
import com.seegrid.deckofcards.Deck.DeckOfCards;
import com.seegrid.entrypoint.AppConfig;
import com.seegrid.model.Deck;
import com.seegrid.repository.MongoDeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("deckCardDataService")

public class DeckCardDataService implements IDeckCardDataService {
    @Autowired
    private HazelcastInstance instance;

    @Autowired
    private MongoDeckRepository mongoRepository;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private IDeckShuffler deckShuffler;

    public void shuffle(String deckId) throws Exception {
        IMap<String, Deck> deckMap = instance.getMap("deck");    // get map from hazel cast
        Deck deck = deckMap.get(deckId);
        if (deck != null) {
            //shuffle
            deckShuffler = ShuffleAlgorithmsFactory.getShuffler(appConfig.getAlgorithm());
            deckShuffler.shuffle(deck.getDeckOfCards());
            //update inmemory map
            deckMap.put(deckId, deck);
            //update async to mongo db
            mongoRepository.saveAsync(deck);
        } else {
            throw new DeckNotFoundException("Deck is not available");
        }
    }

    public String dealCard(String deckId) throws Exception {
        IMap<String, Deck> deckMap = instance.getMap("deck");    // get map from hazel cast
        Deck deckFromDB = deckMap.get(deckId);
        if (deckFromDB != null) {
            //deal the card
            String card = deckFromDB.getDeckOfCards().dealCard();
            //update the map
            deckMap.put(deckId, deckFromDB);
            mongoRepository.saveAsync(deckFromDB);
            return card;
        }
        //if the given deck id is not found in map, then reject
        else {
            throw new DeckNotFoundException("Deck is not available");
        }
    }

    public String createDeck() {
        //Create deck Id
        String deckId = UUID.randomUUID().toString();
        //Create deck of cards
        DeckOfCards cards = new DeckOfCards();
        cards.createDeck();
        //Model object to be persisted in map as well as physical storage
        Deck deck = new Deck();
        deck.setDeckId(deckId);
        deck.setDeckOfCards(cards);
        //update hazelcast map
        IMap<String, Deck> deckMap = instance.getMap("deck");    // get map from hazel cast
        deckMap.put(deckId, deck);
        //update asynchronously in mongo db
        mongoRepository.saveAsync(deck);
        return deckId;
    }

    public void deleteDeck(String deckId) throws Exception {
        IMap<String, Deck> deckMap = instance.getMap("deck");    // get map from hazel cast
        Deck deckFromDB = deckMap.get(deckId);
        if(deckFromDB != null) {
            deckMap.delete(deckId);
            mongoRepository.deleteById(deckId);
        }
        else {
            throw new DeckNotFoundException("Deck not found ");
        }
    }
}
