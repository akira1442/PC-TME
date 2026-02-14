package pc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NaiveWorker implements Runnable{
	
	private File f;
	private long debut;
	private long fin;
	private Map<String, Integer> map;
	private long[] totalWords;
	

	
	public NaiveWorker(File f, long debut, long fin, Map<String, Integer> map, long[] totalWords) {
		super();
		this.f = f;
		this.debut = debut;
		this.fin = fin;
		this.map = map;
		this.totalWords = totalWords;
	}
	
	@Override
	public void run() {
		try (Scanner scanner = new Scanner(FileUtils.getRange(this.f, this.debut, this.fin))) {
			while (scanner.hasNext()) {
				String word = WordFrequency.cleanWord(scanner.next());
				if (!word.isEmpty()) {
	            	totalWords[0]++;
	            	map.compute(word, (w, c) -> c == null ? 1 : c + 1);
	            }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}