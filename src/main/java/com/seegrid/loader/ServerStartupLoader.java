package com.seegrid.loader;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.seegrid.customexception.ServerStartupException;
import com.seegrid.model.Deck;
import com.seegrid.repository.MongoDeckRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
/*
Loads decks from mongodb and updates in memory
required only when server is starting up
 */
@Component
public class ServerStartupLoader implements ApplicationContextAware {

  @Autowired
  private  MongoDeckRepository mongoDeckRepository;

  @Autowired
  private HazelcastInstance instance;

  /*
  This method loads documents from mongo database to inmemory
  map maintained by hazelcast cluster.
  This method is invoked at the server startup
   */
  public  void loadDocumentsToMemory() throws Exception{
      //Get instance map from in memory hazelcast
      IMap<String,Deck> inMemoryMap = instance.getMap("deck");
      try {
          List<Deck> decksInDB = mongoDeckRepository.findAll();
          for(Deck deck:decksInDB) {
              //update in memory map
              inMemoryMap.put(deck.getDeckId(),deck);
          }
      }
      catch(Exception e) {
          throw new ServerStartupException("Loading documents from mongo failed");
      }
  }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
