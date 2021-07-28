

//Adim: mueyyed garzuddin 
//no: 1306180132

package muyyed1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Muyyed1 {

    final static Scanner scanner = new Scanner(System.in);
    static Dice diceObj;
    static Table table;
    static Slot slot;
    static int rounds = 0;

    public static void main(String[] args) throws IOException {
        /*
        - we have 3 files: log.dat - tableSlot.dat - old.dat
        1- TODO: check files (for old game): 
        if old.dat has not finished game show continue 
        option for user else start new game just
        
        2- show start menu 
            a- continue => load data from old.dat
            b- start new game => clear old.dat
            c- exit
        
        3- show game menu:
            a- show the board
            b- show dices
            c- show who will play
            d- show options (1)enter dice - 2)enter source)
            [if user has broken flake he has just the first option]
        
        4- playing game:
           a- read data from user           
           b- check move if valid for (normal move - set broken flake) 
            normal move
            [
                0- check dice: if entered value == valid dice
                1- if there is stone in source or not
                2- if source has stone for other player or not in distination (source+dice)
                3- 
                3-
            ]
            put broken flake
            [
                0-check dice: if entered value == valid dice
                1- if destination(dice) has other player stone or not
            ]
           
           b- change board 
           c- check if game is end
           d- go next game
         */

        //creating files log.dat , tableSlot.dat , old.dat
        MyFile.createFiles();
        // clear old saved data    
        //   MyFile.clearOld();
        // 2- show start menu 
        System.out.println("Successfully wrote to the file.");

        ShowStartMenu();

    }

    private static void ShowStartMenu() throws FileNotFoundException, IOException {
        // a- continue => load data from old.dat
        // b- start new game => clear old.dat
        String option = "";
        do {
            System.out.println("Select From List Below:");
            System.out.println(" ---------------------");
            System.out.println("| [0]- exit           |");
            System.out.println("| [1] play new game   |");
            System.out.println("| [2]-continue game   |");
            System.out.println(" ---------------------");
            option = scanner.next();

            switch (option) {
                case "1":
                    MyFile.clearOld();
                    diceObj = new Dice();
                    table = new Table();
                    Play();
                    return;
                case "2":
                    if (MyFile.hasOldGame()) {
                        Load();
                        return;
                    } else {
                        System.out.println("No Game to continue");
                        break;
                    }
                default:
                    System.out.println("---------------------------------------------");
                    System.out.println("||   wrong option=" + option + "===>> Select again      ||");
                    System.out.println("---------------------------------------------");
            }
        } while (!option.equals("0"));
//        Exit(0);
    }

    private static void Play() throws IOException {
        // flip turn and throw dices
        if (rounds > 0) {
            diceObj.throwDices();
            if (table.now_player == Table.PLAYER_X) {
                table.now_player = Table.PLAYER_Y;
            } else if (table.now_player == Table.PLAYER_Y) {
                table.now_player = Table.PLAYER_X;
            }
        } else {
            diceObj.ChoosePlayer();
            if (diceObj.Dice1 > diceObj.Dice2) {
                table.now_player = Table.PLAYER_X;
            } else {
                table.now_player = Table.PLAYER_Y;
            }
        }

        // show the tableSlot
        table.BoaardDisplay(diceObj);
        // show dices dice.BoaardDisplay(); done in tableSlot.show();

        // show who will play
        table.ShowPlayerToPlayNow();

        // show game menu
        String option = "";
        playloop:
        do {
            // [if user has broken flake he has just the first option]
            String message = "";
            if (table.DoesPlayerHaveBroken() == false) {
                message = "[0]-exit \n[1]-move the stone \n[2]-cancel ";
            } else {
                message = "[0]-exit \n[1]-Broken => select right slot to insert again \n[3]-cancel";
            }

            System.out.println("-------------------------");
            if (message.equals("[0]-exit \n[1]-move the stone \n[2]-cancel ")) {
                System.out.println(message);
            } else {
                System.out.println("\n[0]-Exit \n[1]-Broken => select right slot to insert again \n[3]-cancel\n");
            }
            System.out.println("-------------------------");
            System.out.println("Select From List up:");
            // read data from user
            option = scanner.next();

            switch (option) {
// normal move
                case "1":
                    // to handle the case of Broken Stones
                    if (message.equals("[0]-exit \n[1]-Broken => select right slot to insert again \n[3]-cancel")) {
                        String str = "";
                        if (Table.X_playerbroken > 0) {
                            str = "X";
                            requirementToGetBackBroken(str);
                        } else {
                            str = "Y";
                            requirementToGetBackBroken(str);
                        }
                            diceObj.decrement();

                        break;
                    } else {     // to handle the normal situations 
                         
                        System.out.println("select the Dice that you want to move : ");
                        int selectedDiceToExecuteMovementUsingIt = scanner.nextInt();
                        System.out.println("Select number the slot you want to move from : ");
                        int source = scanner.nextInt();
                        
                        if (table.DoesPlayerHaveBroken() == false && 
                                table.CanNormalMove(diceObj, selectedDiceToExecuteMovementUsingIt, source)) {
                            
                            table.move(diceObj, selectedDiceToExecuteMovementUsingIt, source);
                            diceObj.decrement();
                            if(table.IsHaveWinner())
                            {
                                System.out.println("===================");
                                System.out.println("||  "+table.TheWinner()+" ||");
                                System.out.println("===================");
                                
                            }
                        } // here to get back broken stones                    
                        else {
                            System.out.println(" invalid Movaement , Try again");
                        }
                    }

                    //MyFile.saveMoveLog(selectedDiceToExecuteMovement, source);
                    break;

                // put broken stone
                case "2":
                    // read data
                    System.out.println("enter v: ");
                    int selectedDiceToPut = scanner.nextInt();
                    if (table.DoesPlayerHaveBroken() && table.CanPutBroken(diceObj, selectedDiceToPut)) {
                        table.PutBroken(diceObj, selectedDiceToPut);
                    } else {
                        System.out.println("This put for broken stone invalid");
                    }
                    //MyFile.savePutLog(selectedDiceToPut);
                    break;//for switch

// to cancel procedure
                case "0":

                    System.exit(0);
                    break;

                case "3":
                    System.out.println("Wrong Option Try Again ");
                    break playloop;
            }

            // show the tableSlot
            table.BoaardDisplay(diceObj);

            // show who will play
            table.ShowPlayerToPlayNow();
        } while (diceObj.hasDice());

        // check if game has winner
        if (table.IsHaveWinner()) {
            System.out.println(table.TheWinner());
            System.exit(0);
        } else {
            rounds++;
            Play();
        }

    }

    private static void Load() throws IOException {
        // read data from files
        Dice.loadGameCounter = 1;
        int counter = 0;
        int check = 0;
        table = new Table();
        diceObj = new Dice();

        BufferedReader reader = new BufferedReader(new FileReader("table.dat"));
        String line, temp;
        while ((line = reader.readLine()) != null) {
            temp = line;
            if ("=============== Game Table ================".equals(line)
                    || "  01_02_03_04_05_06_07_08_09_10_11_12 ".equals(line)
                    || "-------------------------------------------".equals(line)
                    || " 24_23_22_21_20_19_18_17_16_15_14_13".equals(line)
                    || " ".equals(line)) {
                continue;
            } 
            else if (line.charAt(0) == '#'&&check==0)
            {
                check = 1;
                String parts[] = line.split(" ");
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].equals("#") || parts[i].equals(" ") || parts[i].equals("  ")) {
                        continue;
                    }
                    else if (parts[i].equals("0"))
                    {
                        int temp1 = counter++;
                        table.tableSlot[temp1].player = Table.NO_PLAYER;
                        table.tableSlot[temp1].value = Table.PLAYER_X;
                    }
                    else if (parts[i].contains("X") || parts[i].contains("Y"))
                    {                             //   int y = Character.getNumericValue(x);
                        int temp2 = counter++;
                        if(parts[i].length()>2) // check if number bigger than 9
                        {
                           int varMoreThan9_15 = Character.getNumericValue(parts[i].charAt(0))*10
                                   +Character.getNumericValue(parts[i].charAt(1));
                           table.tableSlot[temp2].value=varMoreThan9_15; 
                            if (parts[i].charAt(2) == 'X') {
                            table.tableSlot[temp2].player = Table.PLAYER_X;
                        }
                            else 
                            {
                            table.tableSlot[temp2].player = Table.PLAYER_Y;
                        }
                        }
                        else
                        {
                        table.tableSlot[temp2].value = Character.getNumericValue(parts[i].charAt(0)); // cast (char ==> int)
                        if (parts[i].charAt(1) == 'X') {
                            table.tableSlot[temp2].player = Table.PLAYER_X;
                        } else {
                            table.tableSlot[temp2].player = Table.PLAYER_Y;
                        } 
                        }
                     
                    }
                }
            }  
            else if (line.charAt(0) == '#' && check == 1) {
                   String parts[] = line.split(" ");
                for (int i = parts.length-1; i >=0 ; i--) {
                    if (parts[i].equals("#") || parts[i].equals(" ") || parts[i].equals("  ")
                            || parts[i].equals("")) {
                        continue;
                    }
                    else if (parts[i].equals("0"))
                    {
                        int temp1 = counter++;
                        table.tableSlot[temp1].player = Table.NO_PLAYER;
                        table.tableSlot[temp1].value = Table.PLAYER_X;
                    }
                    else if (parts[i].contains("X") || parts[i].contains("Y"))
                    {                             //   int y = Character.getNumericValue(x);
                        int temp2 = counter++;
                        if(parts[i].length()>2) // check if number bigger than 9
                        {
                           int varMoreThan9_15 = Character.getNumericValue(parts[i].charAt(0))*10
                                   +Character.getNumericValue(parts[i].charAt(1));
                           table.tableSlot[temp2].value=varMoreThan9_15; 
                            if (parts[i].charAt(2) == 'X') {
                            table.tableSlot[temp2].player = Table.PLAYER_X;
                        }
                            else 
                            {
                            table.tableSlot[temp2].player = Table.PLAYER_Y;
                        }
                        }
                        else
                        {
                        table.tableSlot[temp2].value = Character.getNumericValue(parts[i].charAt(0)); // cast (char ==> int)
                        if (parts[i].charAt(1) == 'X') {
                            table.tableSlot[temp2].player = Table.PLAYER_X;
                        } else {
                            table.tableSlot[temp2].player = Table.PLAYER_Y;
                        } 
                        }
                     
                    }
                }
                
                
                
//                String parts[] = line.split(" ");
//                for (int i = 0; i < parts.length; i++) {
//                    if (parts[i].equals("#") || parts[i].equals(" ") || parts[i].equals("  ")) {
//                        continue;
//                    } else if (parts[i].equals("0")) {
//                        int temp1 = counter++;
////                           slot=new Slot(Table.PLAYER_X, Table.NO_PLAYER) ;
//                        table.tableSlot[temp1].player = Table.NO_PLAYER;
//                        table.tableSlot[temp1].value = Table.PLAYER_X;
//                    } else if (parts[i].contains("X") || parts[i].contains("Y")) {                             //   int y = Character.getNumericValue(x);
//                        int temp2 = counter++;
//                        table.tableSlot[temp2].value = Character.getNumericValue(parts[i].charAt(0)); // cast (char ==> int)
//                        if (parts[i].charAt(1) == 'X') {
//                            table.tableSlot[temp2].player = Table.PLAYER_X;
//                        } else {
//                            table.tableSlot[temp2].player = Table.PLAYER_Y;
//                        }
//                    }
//                }
            }
            
            
            else if (line.charAt(0) == 'D') {
                String parts[] = line.split(" ");
                int temp6 = 0;
                for (int i = 0; i < parts.length; i++) {
                    String ss = parts[i];
                    if (parts[i].equals("Dice1     =")) {
                        continue;
                    } else if (parts[i].equals("     ")) {
                        continue;
                    } else if (parts[i].equals("=") || parts[i].equals("   ")) {
                        continue;
                    } else if (parts[i].equals("Dice2       =")) {
                        continue;
                    } else if (parts[i].equals("1")
                            || parts[i].equals("2")
                            || parts[i].equals("3")
                            || parts[i].equals("4")
                            || parts[i].equals("5")
                            || parts[i].equals("6")
                            || parts[i].equals("7")
                            || parts[i].equals("8")
                            || parts[i].equals("9")) {
                        if (temp6 == 0) {
                            temp6 = temp6 + 1;
                            String str = parts[i];
                            int u = Integer.parseInt(str);
                            diceObj.Dice1 = u;
                        } else if (temp6 == 1) {
                            String str1 = parts[i];
                            int m = Integer.parseInt(str1);
                            diceObj.Dice2 = m;
                        }
                    }

                }
            } 
            else if (line.charAt(0) == 'x') {
                int temp44 = 0;
                int tempo = 1;
                if (tempo == 1) {
                    String parts[] = line.split(" ");

                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("x") || parts[i].equals("broken")
                                || parts[i].equals("=") || parts[i].equals("y")
                                ||parts[i].equals("")) {
                            continue;
                        } else {
                            if (!parts[i].equals("")) {
                                if (
                                           parts[i].equals("0") 
                                        ||(parts[i].equals("1")
                                        || parts[i].equals("2")
                                        || parts[i].equals("3")
                                        || parts[i].equals("4")
                                        || parts[i].equals("5")
                                        || parts[i].equals("6")
                                        || parts[i].equals("7")
                                        || parts[i].equals("8")
                                        || parts[i].equals("9"))) {
                                    temp44 = 1;
                                    Table.X_playerbroken = Integer.parseInt(parts[i]);
                                } else if (temp44 == 1) {
                                    Table.Y_playerbroken = Integer.parseInt(parts[i]);
                                }
                            }
                        }
                    }
                } 

            }
            else if (line.charAt(0) == 'X') {

                    String parts[] = line.split(" ");
                    int temp4 = 0;
                    
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("X") || parts[i].equals("fetched")
                                || parts[i].equals("=") || parts[i].equals("Y")
                                || parts[i].equals("")
                                ) {
                            continue;
                        } else {
                            
                            if (
                                    parts[i].equals("0")||
                                    parts[i].equals("1")
                                    || parts[i].equals("2")
                                    || parts[i].equals("3")
                                    || parts[i].equals("4")
                                    || parts[i].equals("5")
                                    || parts[i].equals("6")
                                    || parts[i].equals("7")
                                    || parts[i].equals("8")
                                    || parts[i].equals("9")) {
                               if(temp4==0){
                                    String str = parts[i];
                                int u = Integer.parseInt(str);
                                Table.playerfetched_X = u;
                                temp4 = 1;
                               }
                               else if (temp4 == 1) {
                                String str = parts[i];
                                int u = Integer.parseInt(str);
                                Table.playerfetched_Y = u;
                            }
                            } 
                            
                        }
                    }
                }// end of line which has [Fetched]

        }// End While

        if (diceObj.Dice1 == diceObj.Dice2) {
            diceObj.numDiceRepetation1 = 2;
            diceObj.numDiceRepetation2 = 2;
            diceObj.sumTwoToSolveReptation = 4;
            
               System.out.println("**********************************");
                System.out.println("Burada tekrarlama var  ");
                System.out.println("Demek ---->  " + diceObj.Dice1 + " " + diceObj.Dice2 + "= " + diceObj.Dice1 +
                        " " + diceObj.Dice2 + " " + diceObj.Dice2 + " " + diceObj.Dice1);
                System.out.println("**********************************");

        } else {
            diceObj.numDiceRepetation1 = 1;
            diceObj.numDiceRepetation2 = 1;
            diceObj.sumTwoToSolveReptation = 2;
        }

//          dice = new Dice();
//        String playerS = "X";
        //        int player;
        //        if (playerS == "X") {
        //            slot.player = Table.PLAYER_X;
        //        } else {
        //            slot.player = Table.PLAYER_Y;
        //        slot.player
        // slot.player = player;
        // save it to classes
//        tableSlot = new Table(slot,player);
        Play();
    }

    private static void requirementToGetBackBroken(String str) {
        System.out.println("select dice to putback broken dice");
        int selectedDiceToExecuteMovementUsingIt3 = scanner.nextInt();
        if (table.CanPutBroken(diceObj, selectedDiceToExecuteMovementUsingIt3)) {
            table.PutBroken(diceObj, selectedDiceToExecuteMovementUsingIt3);

            if (str.equals("X")) {
                Table.X_playerbroken--;
            } else {
                Table.Y_playerbroken--;
            }

        }

    }
}
