/**
 * */
public class DictionaryCommandLine extends DictionaryManagement{

    /** show Data*/
    public void showAllWords() {
        for (Word searchWord: dictionaries.words ) {
            System.out.println(dictionaries.words.indexOf(searchWord) + 1 + "\t" +
                    searchWord.word_target + "\t\t" + searchWord.word_explain );
        }
    }
    /** insert Data && show Data*/
    public void dictionaryBasic() {
        insertFromCommandLine();
        showAllWords();
    }

    /** to display word start with a word */
    public void dictionarySearcher() {
        System.out.println("Input word in order to search: ");
        String str = scanner.nextLine();
        for (Word searchWord: dictionaries.words ) {
            if(searchWord.word_target.startsWith(str)) {
                System.out.println(searchWord.word_target);
            } else if (searchWord.word_explain.startsWith(str)) {
                System.out.println(searchWord.word_explain);
            }
        }
    }
    /** advance insert Data and show data */
    public void dictionaryAdvanced() {
        insertFromFile();
        showAllWords();
        dictionaryLookup("s");
        dictionarySearcher();
        editDictionary();
        dictionaryExportToFile();
    }
}
