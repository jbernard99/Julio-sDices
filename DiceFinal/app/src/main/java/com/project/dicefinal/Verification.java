package com.project.dicefinal;

public class Verification {

    final int MAX_DICES_VALUE = 9999;
    final int MAX_FACES_VALUE = 999;

    public boolean checkInput(String rawFacesInput, String rawDicesInput) {
        if (numericCheck(rawFacesInput) && numericCheck(rawDicesInput)) {
            if (inFacesRange(Integer.parseInt(rawFacesInput)) && inDicesRange(Integer.parseInt(rawDicesInput))) {
                return true;
            }
            return false;
        }
        return false;
    }

    //Checks if user input is numeric or not
    private boolean numericCheck(String rawInput) {
        try {
            Integer.parseInt(rawInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Checks if nbFaces input is in the min max range
    private boolean inFacesRange(int nbFaces) {
        if (nbFaces > 1 && nbFaces <= MAX_FACES_VALUE) {
            return true;
        }
        return false;
    }

    //Checks if nbDices input is in the min max range
    private boolean inDicesRange(int nbDices) {
        if (nbDices > 0 && nbDices <= MAX_DICES_VALUE) {
            return true;
        }
        return false;
    }

}
