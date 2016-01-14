import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by jvaze on 1/13/16.
 */
public class Powerball {
    HashMap<String, ArrayList<Integer>> sequenceMap;
    HashMap<String, ArrayList<Integer>> powerMap;

    public static void main(String args[]) {

        /**
         * Enter sequence of white winning balls.
         * eg. if the winning sequence is 3,9,16,83,92 you should have String[] winningSequence = {"3","9","16","83","92"} below.
         * Note: If the number you are entering is a single digit eg. 6, DON'T enter "06", enter "6".
          */
        String[] winningSequence = {"2", "6", "16", "29", "65"};

        // Enter the powerball number
        String powerballNumber = "8";

        Powerball p = new Powerball();
        p.createPowerMap(System.getProperty("user.dir") + "/Input/Numbers.csv");

        p.generateResults(winningSequence, powerballNumber);

        System.out.println("1. See results in Output/PowerballResult.csv" + "\n" +
        "2. Use the 'Excel Row Number' value." + "\n" +
                "3. Look in the excel Nat shared this morning for that row number." + "\n" +
                "4. Find the corresponding 'Row' and 'Column' value and identify that ticket in the image Pooja shared to verify the result.");

    }

    public void createPowerMap(String csvFilePath) {
        powerMap = new HashMap<>();
        sequenceMap = new HashMap<>();
        File file = new File(csvFilePath);

        try{
            Scanner inputStream = new Scanner(file);
            int rowCount = 0; // check row count in the Excel Nat shared
            while(inputStream.hasNext()){
                String data = inputStream.next();
                String[] row = data.split(",");
                for(int i = 0; i < row.length; i++) {
                    if(i < row.length-1) {
                        if (!sequenceMap.containsKey(row[i])) {
                            ArrayList<Integer> rowCountList = new ArrayList<>();
                            rowCountList.add(rowCount);
                            sequenceMap.put(row[i], rowCountList);
                        }
                        else
                            sequenceMap.get(row[i]).add(rowCount);
                    }
                    else {
                        if (!powerMap.containsKey(row[i])) {
                            ArrayList<Integer> rowCountList = new ArrayList<>();
                            rowCountList.add(rowCount);
                            powerMap.put(row[i], rowCountList);
                        }
                        else
                            powerMap.get(row[i]).add(rowCount);
                    }

                }
                rowCount++;
            }
            inputStream.close();


        }catch (FileNotFoundException e){

            e.printStackTrace();
        }

    }

    public void generateResults(String[] winningNumberSequence, String powerballNumber) {
        HashMap<Integer, Sequence> sequenceResult = new HashMap<>();
        //HashMap<Integer,Integer> powerballResult = new HashMap<>();
        for(int i = 0; i < winningNumberSequence.length; i++) {
            if(sequenceMap.containsKey(winningNumberSequence[i])) {
                for(Integer rowNumber: sequenceMap.get(winningNumberSequence[i])) {
                    if (!sequenceResult.containsKey(rowNumber)) {
                        Sequence newRow = new Sequence(1);
                        sequenceResult.put(rowNumber,newRow);
                    }
                    else {
                        Sequence s = sequenceResult.get(rowNumber);
                        int count = s.getMatchCount();
                        s.setMatchCount(count+1);
                        //sequenceResult.put(rowNumber,s);
                    }
                }
            }
        }
        if(powerMap.containsKey(powerballNumber)) {
            for(Integer rowNumber: powerMap.get(powerballNumber)) {
                if (!sequenceResult.containsKey(rowNumber)) {
                    Sequence newRow = new Sequence(true);
                    sequenceResult.put(rowNumber,newRow);
                }
                else {
                    Sequence s = sequenceResult.get(rowNumber);
                    s.setPowerballMatch(true);
                    //sequenceResult.put(rowNumber,s);
                }
            }
        }

        generateResultCSV(sequenceResult);

    }

    private static void generateResultCSV(HashMap<Integer, Sequence> sequenceResult)
    {
        try
        {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/Output/PowerballResult.csv");
            writer.append("Excel Row Number,# Matching White Balls,Powerball Match" + "\n");

            // Iterating Result HashMap to print results
            for (Integer key : sequenceResult.keySet()) {
                writer.append(key+2 + "," + sequenceResult.get(key).getMatchCount() + "," + sequenceResult.get(key).isPowerballMatch() + "\n");
            }
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
