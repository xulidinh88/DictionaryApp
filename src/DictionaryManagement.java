
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * TO DO: insertData
 */

public class DictionaryManagement extends Dictionary {

    Dictionary dictionaries = new Dictionary();
    public Scanner scanner = new Scanner(System.in);
    
    public DictionaryManagement() {
    	LinkedList<Word> words = new LinkedList<Word>();
    	insertFromFile();
    }
    
    /** insert data from file */
    private final File fileDictionaries = new File("DictEV.dic");
    public void insertFromFile()  {
        try {

            Scanner scanFile  = new Scanner(fileDictionaries, "UTF-8");
            
            while(scanFile.hasNextLine()) {
            	System.out.print("Run to here");
                String content = scanFile.nextLine();
                String [] arrayStr = content.split("=");
                Word wordDraft = new Word(arrayStr[0], arrayStr[1]);
                dictionaries.words.add(wordDraft);
            }
            scanFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
    }

    /** search from File */
    public String dictionaryLookup(String str) {
        boolean test = false;
        for (Word searchWord : dictionaries.words) {
            if (str.equals(searchWord.word_target)) {
            	test = true;
                return searchWord.word_explain; 
            } else if (str.equals(searchWord.word_explain)) {
            	test = true;
                return searchWord.word_target;   
            }
        }
        return "0";
        
    }
    /** add, remove, edit Dictionary*/
    public void editDictionary() {
        int key_test = 0;
        while (key_test == 0 ) {
            System.out.println("Select your choice: ");
            System.out.println("1, Edit word ?");
            System.out.println("2, Remove word ?");
            System.out.println("3, Add word ?");
            String value = scanner.nextLine();
            switch (value) {
                case "1": {
                    System.out.println("Input word to edit: ");
                    String temp_str = scanner.nextLine();
                    System.out.println("Input explain_word expert: ");
                    String target_str = scanner.nextLine();
                    for (Word searchWord : dictionaries.words) {
                        if (temp_str.equals(searchWord.word_target)) {
                            searchWord.word_explain = target_str;
                            break;
                        }
                        if (temp_str.equals(searchWord.word_explain)) {
                            searchWord.word_target = target_str;
                            break;
                        }
                    }
                    System.out.println("Do you want exit? Y/N");
                    String value_str = scanner.nextLine();
                    if (value_str.equals("Y")){
                        key_test = 1;
                    }
                    break;
                }
                case "2": {
                    System.out.println("Input word to remove: ");
                    String temp_str = scanner.nextLine();
                    for (Word searchWord : dictionaries.words) {
                        if (temp_str.equals(searchWord.word_target)) {
                            dictionaries.words.remove(searchWord);
                            break;
                        }
                        if (temp_str.equals(searchWord.word_explain)) {
                            dictionaries.words.remove(searchWord);
                            break;
                        }
                    }
                    System.out.println("Do you want exit? Y/N");
                    String value_str = scanner.nextLine();
                    if (value_str.equals("Y")){
                        key_test = 1;
                    }
                    break;
                }
                case "3": {
                    System.out.println("Add Word: ");
                    String str_1 = scanner.nextLine();
                    System.out.println("Meaning Word:");
                    String str_2 = scanner.nextLine();
                    Word wordDraft = new Word(str_1, str_2);
                    dictionaries.words.add(wordDraft);
                    System.out.println("Do you want exit? Y/N");
                    String value_str = scanner.nextLine();
                    if (value_str.equals("Y")){
                        key_test = 1;
                    }
                    break;
                }
                default : {
                    break;
                }
            }
        }

    }

    /** Dictionary export to file*/
    public void dictionaryExportToFile() {
        try {
            //Whatever the file path is.
            File text = new File("ExportFile.txt");
            FileOutputStream is = new FileOutputStream(text);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            for (Word searchWord : dictionaries.words) {
                w.write(searchWord.word_target + "\t" + searchWord.word_explain+"\n");
            }
            System.out.println("Export File Successfully!!!");
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file statsTest.txt");
        }

    }

    /** insert data by command */
    public void insertFromCommandLine() {
        String tempt_1 = scanner.nextLine();
        String tempt_2 = scanner.nextLine();
        Word wordDraft = new Word(tempt_1,tempt_2);
        dictionaries.words.add(wordDraft);
        tempt_1 = scanner.nextLine();
        tempt_2 = scanner.nextLine();
        wordDraft = new Word(tempt_1,tempt_2);
        dictionaries.words.add(wordDraft);
    }
}
