package pc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class WordFrequency {
	
	private static class WordCount implements Comparable<WordCount>{
		// TODO : essentiellement un mot et un compteur
		String mot;
		int cpt;
		
		public WordCount(String mot, int cpt) {
			
			this.mot = mot;
			this.cpt = cpt;
		}

		@Override
		public int compareTo(WordCount wc) {
			// TODO Auto-generated method stub
			return - Integer.compare(this.cpt, wc.cpt);
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return this.cpt;
		}

		public String getWord() {
			// TODO Auto-generated method stub
			return this.mot;
		}
		
	}

    public static void main(String[] args) throws IOException {
        // Allow filename as optional first argument, default to WarAndPeace.txt
        // Optional second argument is mode (e.g., "list" or "listfreq").
        String filename = args.length > 0 ? args[0] : "data/WarAndPeace.txt";
        String mode = args.length > 1 ? args[1] : "list"; /*"count";*/

        // Check if file is readable
        File file = new File(filename);
        if (!file.exists() || !file.canRead()) {
            System.err.println("Could not open '" + filename + "'. Please provide a readable text file as the first argument.");
            System.err.println("Usage: java WordFrequency [path/to/textfile] [mode]");
            System.exit(2);
        }

        long fileSize = file.length();

        System.out.println("Preparing to parse " + filename + " (mode=" + mode + "), containing " + fileSize + " bytes");

        long start = System.nanoTime();

        Scanner scanner = new Scanner(file);

        if (mode.equals("count")) {
            long totalWords = 0;
        	while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    // TODO : ici on peut agir sur le mot lu
                }
            }
        	System.out.println("Total words: " + totalWords);
        } else if (mode.equals("list")) {
            long totalWords = 0;
            List<String> words = new ArrayList<>();
        	while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    // TODO : tester si le mot "word" déjà dans "words"
                    if (!words.contains(word)) {
                    	words.add(word);
                    }
                }
            }
        	System.out.println("Total words: " + totalWords);
        	System.out.println("Unique words: " + words.size());
        } else if (mode.equals("listfreq")) {
        	long totalWords = 0;
            List<WordCount> words = new ArrayList<>();
        	while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    // TODO : trouver si le mot est déjà dans "words"
                    // si oui incrémenter son compteur
                    // sinon l'ajouter à la liste
                    boolean contains = false;
                    int indice = 0;
                    
                    for (WordCount wc : words) {
                    	if (wc.mot.equals(word)) {
                    		words.get(indice).cpt++;
                    		contains = true;
                    		break;
                    	}
                    	indice++;
                    }
                    if (!contains) {
                    	words.add(new WordCount(word, 1));
                    }
                }
            }
        	System.out.println("Total words: " + totalWords);
        	System.out.println("Unique words: " + words.size());
            // TODO : trier la liste par fréquence décroissante puis ordre alphabétique croissant
        	Collections.sort(words);
        	// puis afficher les 5 mots les plus fréquents avec leur fréquence
           for (WordCount wc : words.subList(0, 5)) {
				System.out.println(wc.getCount() + " " + wc.getWord());
			}
        } else if (mode.equals("tree")) {
        	long totalWords = 0;
            Map<String, Integer> map = new TreeMap<>();
            while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    // TODO : mettre à jour la map
                    if (map.containsKey(word)) {
                    	map.put(word, 1);
                    }
                    else {
                    	map.put(word,1);
                    }
                }
            }
            System.out.println("Total words: " + totalWords);
            System.out.println("Unique words: " + map.size());

            
            // TODO : extraire le map dans une ArrayList
            // trier la liste par fréquence décroissante puis ordre alphabétique croissant
        	// puis afficher les 5 mots les plus fréquents avec leur fréquence

        } else if (mode.equals("hash")) {
        	long totalWords = 0;
        	Map<String, Integer> map = new HashMap<>();
            while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    // TODO : mettre à jour la map
                }
            }
            System.out.println("Total words: " + totalWords);
            System.out.println("Unique words: " + map.size());

            // TODO : extraire le map dans une ArrayList
            // trier la liste par fréquence décroissante puis ordre alphabétique croissant
        	// puis afficher les 5 mots les plus fréquents avec leur fréquence

        } else {
            System.err.println("Unknown mode '" + mode + "'. Supported modes: count, list, listfreq, tree, hash");
            System.exit(1);
        }

        scanner.close();

        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Total runtime (wall clock) : " + durationMs + " ms for mode " + mode);
    }

    private static String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

}