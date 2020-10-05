package com.seegrid.model;

import com.seegrid.deckofcards.Deck.DeckOfCards;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/*
Main model of deck, persisted in database
 */
@Document
public class Deck implements Serializable {
    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public DeckOfCards getDeckOfCards() {
        return deckOfCards;
    }

    public void setDeckOfCards(DeckOfCards deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    @Id
    private String deckId;
    private DeckOfCards deckOfCards;
}
