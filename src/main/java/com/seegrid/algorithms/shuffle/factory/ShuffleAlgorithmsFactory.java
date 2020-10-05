package com.seegrid.algorithms.shuffle.factory;

import com.seegrid.algorithms.shuffle.IDeckShuffler;
import com.seegrid.algorithms.shuffle.PairWiseCardShuffler;

/*
Factory for handling different shuffling algorithms
 */
public class ShuffleAlgorithmsFactory {

    public static IDeckShuffler getShuffler(String algorithm) {
        if(algorithm == null || algorithm.isEmpty()) {
            return null;
        }
        if(("PairWise").equalsIgnoreCase(algorithm)) {
            return new PairWiseCardShuffler();
        }
        //Add further algorithm types here
       return null;
    }

}
