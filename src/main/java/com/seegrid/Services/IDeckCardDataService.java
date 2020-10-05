package com.seegrid.Services;

import org.springframework.stereotype.Component;

@Component
/*
Service interface for create,shuffle and dealing a card
 */
public interface IDeckCardDataService {
    void shuffle(String deckId) throws Exception;
    String dealCard(String deckId) throws Exception;
    String createDeck();
    void deleteDeck(String deckId) throws Exception;
}
