package muyyed1;

import java.io.FileNotFoundException;

public class Table {

    // game data
    public static final int PLAYER_X = 0, PLAYER_Y = 1, NO_PLAYER = -1;
    public static int X_playerbroken = 0, Y_playerbroken = 0,
            playerfetched_X = 0, playerfetched_Y = 0;
//    public final int[][] tableSlot = new int[2][24]; // 0: 123 , 1 : 123
    public Slot[] tableSlot = new Slot[24];
    public int now_player = 0;
    //[3,0,0,2] //Arrays.fill(myarray, 42);
//    public final int[] who = new int[24];

    public static int num_X_last_quarter;
    public static int num_Y_fisrt_quarter;

    static int counter = 0;

    public Table() {
        // for x player
        for (int i = 0; i < 24; i++) {
            if (i == 0) {
                tableSlot[i] = new Slot(5, PLAYER_Y);
            } else if (i == 4) {
                tableSlot[i] = new Slot(3, PLAYER_X);
            } else if (i == 6) {
                tableSlot[i] = new Slot(5, PLAYER_X);
            } else if (i == 11) {
                tableSlot[i] = new Slot(2, PLAYER_Y);
            } else if (i == 12) {
                tableSlot[i] = new Slot(2, PLAYER_X);
            } else if (i == 17) {
                tableSlot[i] = new Slot(5, PLAYER_Y);
            } else if (i == 19) {
                tableSlot[i] = new Slot(3, PLAYER_Y);
            } else if (i == 23) {
                tableSlot[i] = new Slot(5, PLAYER_X);
            } else {
                tableSlot[i] = new Slot();
            }
        }
        // set turn
        now_player = 0;
    }

    // TODO TODO TODO
    public Table(Slot[] slot, int turn) {
        // for x player
        for (int i = 0; i < 24; i++) {
            tableSlot[i] = slot[i];
        }
        // set turn
        this.now_player = turn;

    }

    void BoaardDisplay(Dice dice) throws FileNotFoundException {
        System.out.println("================================== Game Table ====================================");
        System.out.println("  01_02_03_04_05_06_07_08_09_10_11_12");
        System.out.println("---------------------------------------");
        System.out.print("# ");

        //  make loop run from   0-11
        for (int i = 0; i < 12; i++) {
            System.out.print(tableSlot[i].value + tableSlot[i].GetPlayerAsString() + " ");
        }
        System.out.print("#");
        System.out.print("\n");
        System.out.println("---------------------------------------");
        // System.out.println("");
        System.out.println("    Dice1     =" + dice.Dice1 + "   Dice2       =" + dice.Dice2);
        System.out.println("    x broken  =" + X_playerbroken + "   Y broken    =" + Y_playerbroken);
        System.out.println("    x fetched =" + playerfetched_X + "   Y fetched   =" + playerfetched_Y);
        System.out.println("");
        System.out.println("---------------------------------------");
        System.out.print("# ");
        for (int i = 23; i > 11; i--) {//23->12
            System.out.print(tableSlot[i].value + tableSlot[i].GetPlayerAsString() + " ");
        }
        System.out.print("# ");
        System.out.print("\n");
        System.out.println("---------------------------------------");
        System.out.print("  24_23_22_21_20_19_18_17_16_15_14_13");
        System.out.print("\n");
        System.out.print("================================== Game Table ====================================");

        MyFile.saveInTableFile(tableSlot);
    }

    void ShowPlayerToPlayNow() {
        System.out.print("\nPlayer----[" + GetPlayerTurnString() + "]--- is playing now:\n");
    }

    String GetPlayerTurnString() {
        return now_player == PLAYER_X ? "X" : "Y";
    }

    boolean DoesPlayerHaveBroken() {
        if (now_player == PLAYER_X) {
            return X_playerbroken != 0;
        }
        if (now_player == PLAYER_Y) {
            return Y_playerbroken != 0;
        }
        return false;
    }

    boolean CanNormalMove(Dice dice, int selectedDiceToMove, int source) {

        {
            //====these are condition to acheive the normal move without constraints:===== 
            // 1- check dice: if entered value == valid dice
            // 2- if user has this stone and it has the same selected type to move
            // 3- if dest has enemy stones
            // 4_a check if all stone in last quarter according to its type Y
            // 4_b check if all stone in last quarter according to its type X
            // 4_c check dest if is not in  range of slots
        }

        // 1- check dice: if entered value == valid dice
        if (dice.canUse(selectedDiceToMove) == false) {
            return false;
        }

        // 2- if user has this stone and it has the same selected type to move
        //  tableSlot[source -1].playre==> because the index of arr starts from [0] not [1]
        if (tableSlot[source - 1].player != now_player) {
            return false;
        }

        // 3- if dest has enemy stones
        int dest = now_player == PLAYER_X ? (source + selectedDiceToMove) - 1
                : (source - selectedDiceToMove) - 1;
        
        // to control the index in case of overflow
//        if(dest>=24)dest=23; 
//        else
//            dest =1; 
         

        // 4_a check if all stone in last quarter according to its type Y
        if (GetPlayerTurnString() == "X") {
            if (IsAllStonesInLastQuarterFor_X()) {
                return true;
            }
        } else {
            // 4_b check if all stone in last quarter according to its type X
            if (IsAllStonesInLastQuarterFor_Y()) {
                return true;
            }
        }

        // 4_c check dest if is not in  range of slots
//        if (dest < 0 || dest > 23) {
//            return false;
//        }

        if ( // 1=ıf enemey
                tableSlot[dest].player != now_player && tableSlot[dest].player != NO_PLAYER
                // AND 2- ıf enemy has more than 1 
                && tableSlot[dest].value > 1) {
            return false;
        }
        return true;
    }

    void move(Dice dice, int selectedDice, int source) {
        // move
        if (IsAllStonesInLastQuarterFor_Y() || IsAllStonesInLastQuarterFor_X()) {
            // to handle taking stones out of table of backgammon 
            //feeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeetch

            if (counter == 0) {
                num_X_last_quarter = return_Int_IsAllStonesInLastQuarterFor_X();
                num_Y_fisrt_quarter = return_Int_IsAllStonesInLastQuarterFor_Y();
            }

            if (IsAllStonesInLastQuarterFor_Y()) {

                num_Y_fisrt_quarter = num_Y_fisrt_quarter - 1;
                playerfetched_Y = playerfetched_Y + 1;
                tableSlot[source - 1].value--;
            } else {
                num_X_last_quarter = num_X_last_quarter - 1;
                playerfetched_X = playerfetched_X + 1;
                tableSlot[source - 1].value--;
            }

//                   (   ) :  sumOfAllDice_Y  -1;
//           int dest2= now_player == PLAYER_Y ? (24 - dice) : dice -1;
        }
        else {
            int dest = now_player == PLAYER_X ? (source + selectedDice) - 1 : (source - selectedDice) - 1;
            tableSlot[source - 1].value--;
            if (tableSlot[dest].player != now_player && tableSlot[dest].player != NO_PLAYER && tableSlot[dest].value == 1) {
                tableSlot[dest].value = 1;
                if (now_player == PLAYER_X) {
                    Y_playerbroken++;
                } else {
                    X_playerbroken++;
                }
            } else {
                tableSlot[dest].value = tableSlot[dest].value + 1;
            }

            tableSlot[dest].player = now_player;
        }

        /// if slot is empty return none player
        if (tableSlot[source - 1].value <= 0) {
            tableSlot[source - 1].value = 0;
            tableSlot[source - 1].player = NO_PLAYER;
        }
        // remove dice                       ///teak
        dice.use(selectedDice);
    }

    boolean CanPutBroken(Dice dice, int selectedDice) {
        // 1- check dice: if entered value == valid dice
        if (dice.canUse(selectedDice) == false) {
            return false;
        }

        // 2- if dest has enemy stones
        int dest = now_player == PLAYER_X ? (24 - selectedDice) - 1 : selectedDice - 1;
        if (tableSlot[dest].player != now_player && tableSlot[dest].player != NO_PLAYER) {
            return false;
        }
        return true;
    }

    void PutBroken(Dice dice, int selectedDice) {
        // put     
        int dest = now_player == PLAYER_Y ? (24 - selectedDice) - 1 : selectedDice - 1;
        tableSlot[dest].value = tableSlot[dest].value + 1;
        tableSlot[dest].player = now_player;

        // remove dice
        dice.use(selectedDice);

        {
//                if (table.DoesPlayerHaveBroken() == true) {
//                    System.out.println("-----------------------------------------------------");
//                    System.out.println("|       insert broken broken values                  |");
//                    System.out.println("-----------------------------------------------------");
//
//                    // 1- re_insert broken X 
//                    // range is inverse because we insert stones completely in the other side
//                    if (Table.X_playerbroken != 0) {
//                        for (int i = 18; i < 24; i++) {
//                            // here compare with x to get stone out in case it is one in its slot 
//                            // we insert it inside slot if it has no stone at all
//
//                            if (table.now_player == Table.PLAYER_X) {
//                                if (table.tableSlot[i].player == Table.PLAYER_X || table.tableSlot[i].value == (-1)
//                                        || (table.tableSlot[i].player == Table.PLAYER_Y && table.tableSlot[i].value == 1)) {
//                                    Table.X_playerbroken--;
//                                    table.tableSlot[i].player = Table.PLAYER_X;
//                                    table.tableSlot[i].value = 1;
//                                }
//                            }
//
//                        }
//                    } else // 2- re_insert broken Y 
//                    {
//                        for (int i = 0; i < 5; i++) {
//                            // here compare with x to get stone out in case it is one in its slot 
//                            if (table.now_player == Table.PLAYER_Y) {
//                                if (table.tableSlot[i].player == Table.PLAYER_Y || table.tableSlot[i].value == (-1)
//                                        || (table.tableSlot[i].player == Table.PLAYER_X && table.tableSlot[i].value == 1)) {
//                                    Table.Y_playerbroken--;
//                                    table.tableSlot[i].player = Table.PLAYER_Y;
//                                    table.tableSlot[i].value = 1;
//                                }
//                            }
//                        }
//                    }
//
//                }
//               
        }

    }

    boolean IsHaveWinner() {
        return playerfetched_X == 15 || playerfetched_Y == 15;
    }

    String TheWinner() {
        if (playerfetched_X == 15) {
            return "X";
        } else if (playerfetched_Y == 15) {
            return "Y";
        }
        return "NO WINNER";
    }

    // searching in ragne [18----24]  to find X
    private boolean IsAllStonesInLastQuarterFor_X() {
        int sumOfAllDice_X = 0;
        for (int i = 18; i < 24; i++) {
            if (tableSlot[i].player == PLAYER_X) {
                sumOfAllDice_X = sumOfAllDice_X + tableSlot[i].value;
            }
        }
        if (sumOfAllDice_X < 15) {
            for (int i = 0; i < 18; i++) {
                if (tableSlot[i].player == PLAYER_X) {
                    return false;
                }
            }
        }

        // if player x
        // sum for( 18 -> <=23) if(table[i].player == playerx )  sum +=value;
        // if sum == 15 return true
        return sumOfAllDice_X == 15;
    }

    private boolean IsAllStonesInLastQuarterFor_Y() {
        int sumOfAllDice_Y = 0;
        for (int i = 0; i < 6; i++) {

            if (tableSlot[i].player == PLAYER_Y) {
                sumOfAllDice_Y = sumOfAllDice_Y + tableSlot[i].value;
            }
        }
        if (sumOfAllDice_Y < 15) {
        for (int i = 6; i < 24; i++) {

            if (tableSlot[i].player == PLAYER_Y) {
                return false;
            }
        }
        }
 

            // if player x
            // sum for( 18 -> <=23) if(table[i].player == playerx )  sum +=value;
            // if sum == 15 return true
            return sumOfAllDice_Y == 15;

        }
    
    
      

    private int return_Int_IsAllStonesInLastQuarterFor_Y() {
        int sumOfAllDice_X = 0;
        for (int i = 0; i < 6; i++) {

            if (tableSlot[i].player == PLAYER_Y) {
                sumOfAllDice_X = sumOfAllDice_X + tableSlot[i].value;
            }
        }

        // if player x
        // sum for( 18 -> <=23) if(table[i].player == playerx )  sum +=value;
        // if sum == 15 return true
        return sumOfAllDice_X;
    }

    private int return_Int_IsAllStonesInLastQuarterFor_X() {
        int sumOfAllDice_Y = 0;
        for (int i = 18; i < 24; i++) {
            if (tableSlot[i].player == PLAYER_X) {
                sumOfAllDice_Y = sumOfAllDice_Y + tableSlot[i].value;
            }
        }

        // if player x
        // sum for( 18 -> <=23) if(table[i].player == playerx )  sum +=value;
        // if sum == 15 return true
        return sumOfAllDice_Y;
    }

}
