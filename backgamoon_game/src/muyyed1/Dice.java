package muyyed1;

import java.io.IOException;
import java.util.Random;

public class Dice {

    static int loadGameCounter = 0;
    public int Dice1;
    public int sumTwoToSolveReptation;
    public int Dice2;
    public int numDiceRepetation1 = 0;// if duplicate will be 2
    public int numDiceRepetation2 = 0;// if duplicate will be 2
    public final Random rnd = new Random();

    // get random numbers 
    public void throwDices() throws IOException {
        
             
            Dice1 = rnd.nextInt(6) + 1;
            Dice2 = rnd.nextInt(6) + 1;
            if (Dice1 == Dice2) {
                numDiceRepetation1 = 2;
                numDiceRepetation2 = 2;
                sumTwoToSolveReptation=4; 
                
                System.out.println("**********************************");
                System.out.println("Burada tekrarlama var  ");
                System.out.println("Demek ---->  " + Dice1 + " " + Dice2 + "= " + Dice1 + " " + Dice2 + " " + Dice2 + " " + Dice1);
                System.out.println("**********************************");
            } else {
                numDiceRepetation1 = 1;
                numDiceRepetation2 = 1;
                sumTwoToSolveReptation=2; 
             
        }
        MyFile.saveDiceLog(Dice1, Dice2);

    }

    public void ChoosePlayer() throws IOException {
         if (Dice.loadGameCounter == 0) {
              
          

        Dice1 = rnd.nextInt(6) + 1;
        Dice2 = rnd.nextInt(6) + 1;
        while (Dice1 == Dice2) {
            Dice1 = rnd.nextInt(6) + 1;
            Dice2 = rnd.nextInt(6) + 1;
        }
        // now dices are different 
        numDiceRepetation1 = 1;
        numDiceRepetation2 = 1;
         sumTwoToSolveReptation=2; 
        MyFile.saveDiceLog(Dice1, Dice2);
         }
    }

//    void Show() {
//
//    }

    // to mention the function of [&&] between two numbers==> it works  if like follows: 
    //(1&&1==>1) , (1&&0==>0), (0&&0==>0) 
    
    boolean canUse(int selectedDice) {
        // numDiceInsideGoalSlot1 represents the number of stone inside the goal slot must be 
        // more than to hind the enemy from taking it out 
        if ((Dice1 == selectedDice && numDiceRepetation1 > 0) || (Dice2 == selectedDice && numDiceRepetation2 > 0)) {
            return true;
            // utility of this function is to use it to detect the ability to use 
            // one stone to other place or not 
        }
        return false;
    }
    
  void use(int selectedDice) {
        if (selectedDice == Dice1 && Dice1 > 0) {
            numDiceRepetation1--;
        } else if (selectedDice == Dice2 && Dice2 > 0) {
            numDiceRepetation2--;
        }
    }
void decrement(){
            sumTwoToSolveReptation=sumTwoToSolveReptation-1; 

}
    boolean hasDice() { 
        return sumTwoToSolveReptation>0;
    }
    
}
