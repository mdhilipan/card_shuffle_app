package com.seegrid.controller;

import com.seegrid.Services.IDeckCardDataService;
import com.seegrid.customexception.DeckNotFoundException;
import com.seegrid.customexception.NoMoreCardToDealException;
import com.seegrid.model.Deck;
import com.seegrid.repository.MongoDeckRepository;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
/* Main Rest resource contains definitions for Creating deck,shuffling and dealing a deck,deleting a deck
Spring Rest with Repositories autowired
 */
public class MainController {
    Logger controlLogger = LoggerFactory.getLogger(MainController.class);

    //Service methods
    @Autowired
    private IDeckCardDataService deckCardDataService;

    //Creates a new deck
    @RequestMapping(value = "/deck", method = RequestMethod.PUT)
    public String addNewDeck()
    {
        return deckCardDataService.createDeck();
    }

    //Shuffles a deck
    @RequestMapping(value = "deck/shuffle/{deckId}", method = RequestMethod.POST)
    public String shuffle(@PathVariable("deckId") String deckId, HttpServletResponse response) throws Exception {
        try {
            deckCardDataService.shuffle(deckId);
        } catch (DeckNotFoundException de) {
            controlLogger.error("Requested Deck not found");
            response.setStatus(Response.SC_BAD_REQUEST);
            return "Deck Not Found";
        } catch (Exception e) {
            throw e;
        }
        return "Deck shuffled";
    }

    //Deals a card
    @RequestMapping(value = "/deck/deal/{deckId}", method = RequestMethod.GET)
    public String deal(@PathVariable("deckId") String deckId, HttpServletResponse response) throws Exception {
        try {
            return deckCardDataService.dealCard(deckId);
        } catch (DeckNotFoundException de) {
            controlLogger.error("Requested Deck not found");
            response.setStatus(Response.SC_BAD_REQUEST);
            return "Deck Not Found";
        } catch (NoMoreCardToDealException nme) {
            controlLogger.error("No more card to deal");
            response.setStatus(Response.SC_NOT_ACCEPTABLE);
            return "No more card to deal";
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/deck/delete/{deckId}", method = RequestMethod.DELETE)
    public String deleteDeck(@PathVariable("deckId") String deckId, HttpServletResponse response) throws Exception {
        try {
            deckCardDataService.deleteDeck(deckId);
            return "Deleted";
        } catch (DeckNotFoundException de) {
            controlLogger.error("Requested Deck not found");
            response.setStatus(Response.SC_BAD_REQUEST);
            return "Deck Not Found";
        } catch (Exception e) {
            throw e;
        }
    }
}
