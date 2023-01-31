package com.project.dicefinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRoller {

    //Initialize class constants.
    private static int nbFaces;
    private static int nbDices;

    //Changes class constants.
    public void setGlobalVariables(int nbFaces, int nbDices) {
        this.nbFaces = nbFaces;
        this.nbDices = nbDices;
    }

    //Roll dices using user input.
    public List getRoll(int nbFaces, int nbDices) {
        //Set class constants with input.
        setGlobalVariables(nbFaces, nbDices);

        //Creates List to receive roll results.
        List resultList = new ArrayList();

        //Creates randomGenerator
        Random randomGenerator = new Random();

        //For every nbDices, generate a random number in range 1 - nbFaces, add in resultList.
        for (int roll = 1; roll <= nbDices; roll++){
            int randomNumber = randomGenerator.nextInt(nbFaces) + 1;
            resultList.add(randomNumber);
        }

        //Returns resultList
        return resultList;
    }

    //Class results by value using previously generated roll.
    public int[] getClassedResults(List myRoll) {
        int classedResults[];
        classedResults = new int[nbFaces];
        for (int i = 0; i < myRoll.size(); i++){
            String resultToInt = myRoll.get(i) + "";
            classedResults[Integer.parseInt(resultToInt)-1]++;
        }
        return classedResults;
    }

    //Addition total of the results using previously generated roll.
    public int getTotal(List myRoll) {
        int total = 0;
        for (int i = 0; i < myRoll.size(); i++) {
            String rollToInt = myRoll.get(i).toString();
            total += Integer.parseInt(rollToInt);
        }
        return total;
    }

}
