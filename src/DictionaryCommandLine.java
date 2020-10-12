import java.util.ArrayList;
import java.util.LinkedList;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Author : Xuân Linh
 */
public class DictionaryCommandLine extends DictionaryManagement {

	/** show Data */
	public void showAllWords() {
		for (Word searchWord : dictionaries.words) {
			System.out.println(dictionaries.words.indexOf(searchWord) + 1 + "\t" + searchWord.word_target + "\t\t"
					+ searchWord.word_explain);
		}
	}

	/** insert Data && show Data */
	public void dictionaryBasic() {
		insertFromCommandLine();
		showAllWords();
	}

	/** to display word start with a word */
	public LinkedList<Word> dictionarySearcher(String str) {
		LinkedList<Word> temptListWords = new LinkedList<Word>();
		for (Word searchWord : dictionaries.words) {
			if (searchWord.word_target.startsWith(str)) {
				temptListWords.add(searchWord);
			}
		}
		return temptListWords;
	}

	/** advance insert Data and show data */
	public void dictionaryAdvanced() {
		insertFromFile();
		showAllWords();
		dictionaryLookup("s");
		dictionarySearcher("s");
		editDictionary();
		dictionaryExportToFile();
	}
}
