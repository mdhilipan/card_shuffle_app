package com.seegrid.Services;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.seegrid.customexception.DeckNotFoundException;
import com.seegrid.customexception.NoMoreCardToDealException;
import com.seegrid.deckofcards.Deck.DeckOfCards;
import com.seegrid.entrypoint.AppConfig;
import com.seegrid.model.Deck;
import com.seegrid.repository.MongoDeckRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DeckCardDataServiceTest {

    private DeckOfCards cards;

    @Mock
    private HazelcastInstance instance;

    private Deck deck;
    @InjectMocks
    private DeckCardDataService service;
    @Mock
    private MongoDeckRepository mongoDeckRepository;
    @Mock
    private IMap<String, Deck> imap;
    @Mock
    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        cards = new DeckOfCards();
        cards.createDeck();
        deck.setDeckOfCards(cards);
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deal_DeckNotFoundException() {
        Mockito.doReturn(imap).when(instance).getMap("deck");
        Assertions.assertThrows(DeckNotFoundException.class, () -> {
            cards.setDeckId("NotExistingID");
            service.dealCard(cards.getDeckId());
        });
    }

    @Test
    void deal_returnCardValue() throws Exception{
        deck.setDeckId("DeckId");
        Mockito.doReturn(deck).when(imap).get(Mockito.anyString());
        Mockito.doReturn(imap).when(instance).getMap("deck");
        String cardValue = service.dealCard(deck.getDeckId());
        Mockito.verify(imap, Mockito.times(1)).put(Mockito.any(),
                Mockito.any());
        Mockito.verify(mongoDeckRepository, Mockito.times(1)).saveAsync(Mockito.any());
        assertNotNull(cardValue);
    }

    @Test
    void shuffle_DeckNotFoundException() {
        Mockito.doReturn(imap).when(instance).getMap("deck");
        Assertions.assertThrows(DeckNotFoundException.class, () -> {
            cards.setDeckId("NotExistingID");
            service.shuffle(cards.getDeckId());
        });
    }

    @Test
    void shuffle_successFul_update_Mongo() throws Exception{
        deck.setDeckId("DeckId");
        Mockito.doReturn(deck).when(imap).get(Mockito.anyString());
        Mockito.doReturn("PairWise").when(appConfig).getAlgorithm();
        Mockito.doReturn(imap).when(instance).getMap("deck");
        service.shuffle(deck.getDeckId());
        Mockito.verify(imap, Mockito.times(1)).put(Mockito.any(),
                Mockito.any());
        Mockito.verify(mongoDeckRepository, Mockito.times(1)).saveAsync(Mockito.any());
    }

    @Test
    void createDeck_successFul_update_Mongo() {
        Mockito.doReturn(imap).when(instance).getMap("deck");
        String deckId = service.createDeck();
        assertNotNull(deckId);
        Mockito.verify(imap, Mockito.times(1)).put(Mockito.any(),
                Mockito.any());
        Mockito.verify(mongoDeckRepository, Mockito.times(1)).saveAsync(Mockito.any());
    }

    @Test
    void deleteDeck_successFul_delete_Mongo() throws Exception{
        deck.setDeckId("DeckId");
        Mockito.doReturn(deck).when(imap).get(Mockito.anyString());
        Mockito.doReturn(imap).when(instance).getMap("deck");
        service.deleteDeck(deck.getDeckId());
        Mockito.verify(imap, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(mongoDeckRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void deleteDeck_DeckNotFoundException(){
        Mockito.doReturn(imap).when(instance).getMap("deck");
        Assertions.assertThrows(DeckNotFoundException.class, () -> {
            cards.setDeckId("NotExistingID");
            service.deleteDeck(cards.getDeckId());
        });
    }
}