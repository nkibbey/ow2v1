package word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class W2V {
    Map<String, Set<String>> rwords;
    Set<String> dict;

    public  W2V() {
        rwords = new TreeMap<>();
        dict = new HashSet<>();
    }

    public void process(final String fname) {
        /*try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.print(line);
            }
        } catch (IOException e) {
            System.err.println("Problem with " + e);
        }*/


        try (Stream<String> stream = Files.lines(Paths.get(fname))) {

            //1. filter line 3
            //2. convert all content to upper case
            //3. convert it into a List
            /*list = stream
                    .filter(line -> !line.startsWith("line3"))
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public void processDict() throws IOException {
        String fname = "../models/model_2017";

        for (String word: dict){
            //rwords.put(word, new HashSet<>());
            //Set<String> rels = rwords.get(word);
            List<String> rabstracts;
            HashSet<String> rawRels = new HashSet<>();

            try (Stream<String> stream = Files.lines(Paths.get(fname))) {
                rabstracts = stream
                    .filter(line -> line.contains(" " + word + " "))
                    .collect(Collectors.toList());
                for (String ab:rabstracts) {

                    Pattern p = Pattern.compile("[^\\s]+");
                    Matcher m = p.matcher(ab);
                    HashMap<String, Integer> hm = new HashMap<>();
                    while (m.find()) {
                        String w = m.group();
                        hm.put(w, hm.getOrDefault(w, 0)+1);
                    }
                    /* Collections.addAll(ab.);
                    for (String curr: ab.split(" ")) { //get counts of all words in list of abstracts
                        //int offset = rawRels.getOrDefault(curr, 0);
                        //rawRels.put(curr, offset+1);
                        rawRels.add(curr);
                    }*/

                }

                // Traverse map and print all words whose count
                // is  1
         /*       Set<String> s = hm.keySet();
                Iterator<String> itr = s.iterator();

                while(itr.hasNext())
                {
                    String w = itr.next();

                    if (hm.get(w) == 1)
                        System.out.println(w);
                }

                rwords.replace(word, rawRels);
//                rwords.replace(word, rawRels.keySet());*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
