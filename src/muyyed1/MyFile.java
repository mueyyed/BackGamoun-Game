package muyyed1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static muyyed1.Muyyed1.diceObj;

public class MyFile {

    static int typePlayer;
    static File tableFile, logFile, oldFile;
    public static FileWriter writer1;
    public static int counterFisrtTurn=0; 

    public static void createFiles() throws IOException {
        MyFile.tableFile = new File("table.dat");
        MyFile.tableFile.createNewFile();

        MyFile.logFile = new File("log.dat");
        MyFile.logFile.createNewFile();

        MyFile.oldFile = new File("old.dat");
        MyFile.oldFile.createNewFile();
    }

    static void clearOld() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(oldFile);
        writer.print("");
        writer.close();
    }

    static boolean hasOldGame() throws FileNotFoundException, IOException {
        // done
    // to check if there is an old game
        BufferedReader reader = new BufferedReader(new FileReader("table.dat"));
        String line;
        while ((line = reader.readLine()) != null) {
            if("=============== Game Table ================".equals(line))
            return true; 
        }

        return false;
    }

    
//    // stop=1
//    static int getoldData() {
//        // TODO
//        return 0;
//    }

    static void saveInTableFile(Slot[] table) throws FileNotFoundException {
        // save to table
        // create file if not created

        // Override on old data in [   ]
        PrintWriter Writer = new PrintWriter(tableFile);
        Writer.print("");

        Writer.append("=============== Game Table ================\n");
        Writer.append("  01_02_03_04_05_06_07_08_09_10_11_12 \n");
        Writer.append("-------------------------------------------\n");
        Writer.append("# ");
        String temp = "";
        for (int i = 0; i < 12; i++) {//0-11
            temp = String.valueOf(table[i].value) + table[i].GetPlayerAsString() + " ";
            Writer.append(temp);
            temp = "";
        }

        Writer.append("#");
        Writer.append("\n");
        Writer.append("-------------------------------------------\n");
        Writer.append("Dice1     = " + String.valueOf(diceObj.Dice1) + "   Dice2       = " + String.valueOf(diceObj.Dice2) + "\n");
        Writer.append("x broken  = " + String.valueOf(Table.X_playerbroken) + "   y broken    = " + String.valueOf(Table.Y_playerbroken) + "\n");
        Writer.append("X fetched = " + String.valueOf(Table.playerfetched_X) + "  Y fetched   = " + String.valueOf(Table.playerfetched_Y) + "\n");
        Writer.append("-------------------------------------------\n");
        Writer.append("# ");
        String temp1 = "";

        //looping from        23->12
        for (int i = 23; i > 11; i--) {
            temp1 = String.valueOf(table[i].value) + table[i].GetPlayerAsString() + " ";
            Writer.append(temp1);
        }
        Writer.append("# ");
        Writer.append("\n");
        Writer.append("-------------------------------------------");
        Writer.append("\n 24_23_22_21_20_19_18_17_16_15_14_13");
        Writer.append("\n");
        Writer.append("=============== Game Table ================");

        Writer.close();

    }

    static void saveDiceLog(int Dice1, int Dice2) throws IOException {

        BufferedWriter out = new BufferedWriter(new FileWriter("log.dat", true));
      
        if(counterFisrtTurn==0)
       {
            out.write(String.valueOf(Dice1));
        out.write(String.valueOf("\n"));
        out.write(String.valueOf(Dice2) + "\n");
        counterFisrtTurn=1; 
       }

        if (Dice1 > Dice2) {
            typePlayer = 0;
            out.write("X " + String.valueOf(Dice1) + " " + String.valueOf(Dice2) + "\n");
        } else {
            typePlayer = 1;
            out.write("Y " + String.valueOf(Dice1) + " " + String.valueOf(Dice2) + "\n");
        }
        out.close();
 
    }

//    //stop=2
//    static void saveMoveLog(int selectedDice, int source) {
////        throw new UnsupportedOperationException("Not supported yet.");
////To change body of generated methods, choose Tools | Templates.
//    }
//
//    //stop=3; 
//    static void savePutLog(int selectedDice) {
//
//    }
}
