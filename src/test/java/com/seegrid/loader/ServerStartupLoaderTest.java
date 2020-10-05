package com.seegrid.loader;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.seegrid.deckofcards.Deck.DeckOfCards;
import com.seegrid.model.Deck;
import com.seegrid.repository.MongoDeckRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)

class ServerStartupLoaderTest {

    @Mock
    private MongoDeckRepository mongoDeckRepository;
    @Spy
    private HazelcastInstance hazelcastInstance;
    @InjectMocks
    private ServerStartupLoader loader;

    @Mock
    private IMap<String,Deck> imap;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loadDocumentsToMemory() throws Exception {
        List<Deck> mock = new ArrayList<>();
        Deck deck = new Deck();
        String deckid = UUID.randomUUID().toString();
        deck.setDeckId(deckid);
        DeckOfCards deckOfCards = new DeckOfCards().createDeck();
        deck.setDeckOfCards(deckOfCards);
        mock.add(deck);
        Mockito.doReturn(imap).when(hazelcastInstance).getMap("deck");
        Mockito.when(mongoDeckRepository.findAll()).thenReturn(mock);
        loader.loadDocumentsToMemory();
        //verify whether imap is updated
        Mockito.verify(imap, Mockito.times(1)).put(Mockito.any(),
                Mockito.any());

    }
}