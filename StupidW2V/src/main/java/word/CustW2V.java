package word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class CustW2V {
    private final String fname;
    private Map<String, ArrayList<String>> rwords = new LinkedHashMap<>();
    public final String[] dict = new String[] {"chlamydia", "syphilis", "tetanus", "lice", "schistosomiasis", "filariasis",
            "zika", "measles", "endocarditis", "pericarditis", "hypogonadism", "pancreatitis", "hyperparathyroidism",
            "hypopituitarism", "mucositis", "gastritis", "carcinoma", "mesothelioma", "dementia", "narcolepsy"};
    private float[][] dictVals = new float[dict.length][dict.length];


    public CustW2V(String fname) {
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public void doGen() throws IOException {
        for (int i = 0; i < dict.length; i++) {
            URL url = getClass().getResource("y2017.txt");
            File file = new File(url.getPath());
            BufferedReader br = new BufferedReader(new FileReader(file));

            String rels = "";
            String st;
            while ((st = br.readLine()) != null) {
                if (st.contains(" " + dict[i] + " "))
                    rels += st;
            }
            HashMap<String, Integer> wcounts = new HashMap<>();
            for (String curr: rels.split(" ")) {
                wcounts.put(curr, (wcounts.getOrDefault(curr, 0) + 1));
            }
            Map<String,Integer> sorted = sortByValue(wcounts);
            int half = sorted.size()/2;
            int c = 0;
            for (String curr: sorted.keySet()){
                if (rwords.get(dict[i]) == null) {
                    rwords.put(dict[i], new ArrayList<>());
                }
                rwords.get(dict[i]).add(curr);
                if (c >= half)
                    break;
                c++;//cute
            }
            br.close();
        }
    }

    public static float computeSim(ArrayList<String> lst1, ArrayList<String> lst2) {
        float total = lst1.size()+lst2.size();
        float similarities = 0;
        for (String curr: lst1) {
            if (lst2.contains(curr))
                similarities++;
        }

        return (similarities)/(total-similarities);
    }


    public void doComp() {
        /*String ans = "uglyVals = [";
        for (String k: rwords.keySet()) {
            ans += "\n { \"name\": \""+k+"\", ";
            for (int i=0; i<dict.length; i++) {
                String s = dict[dict.length-(i+1)];
                ans += "\""+s+"\":"+computeSim(rwords.get(k), rwords.get(s))+", ";
            }
            ans += " }, ";
        }
        ans+= "\n ];";*/

        for (int i = 0; i < dict.length; i++) {
            for (int j = 0; j < dict.length; j++) {
                dictVals[i][j]=computeSim(rwords.get(dict[i]), rwords.get(dict[j]));
            }
        }
    }

    public String doPrint() {
        String ans = "upper\t";
        for (String col: dict) {
            if (col.length() > 6)
                ans +="\t"+col.substring(0, 6);
            else
                ans +="\t"+col;
        }
        for (int i = 0; i < dictVals.length; i++) {
            if (dict[i].length() > 6)
                ans += "\n"+dict[i].substring(0, 6)+"\t";
            else
                ans += "\n"+dict[i]+"\t";

            for (int j = 0; j < dictVals[0].length; j++) {
                ans += "\t"+String.format("%.4f", dictVals[i][j]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        CustW2V myW2V = new CustW2V("../corpora/y2017");
        try {
            myW2V.doGen();
            myW2V.doComp();
            System.out.print(myW2V.doPrint());
        } catch (IOException e) {
            System.err.print("problem IO "+e);
        }

    }



}
