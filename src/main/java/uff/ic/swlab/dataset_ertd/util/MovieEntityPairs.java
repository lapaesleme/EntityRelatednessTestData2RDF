package uff.ic.swlab.dataset_ertd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MovieEntityPairs extends ArrayList<Pair> {

    public static Config conf = Config.getInsatnce();

    public MovieEntityPairs() {
        String linha;
        File f = new File(conf.rawDataRootDir() + "/movie_entity_pairs.txt");
        try (InputStream in = new FileInputStream(f);) {
            Scanner sc = new Scanner(in);
            int count = 0;
            while (sc.hasNext()) {
                linha = sc.nextLine();
                linha = linha.replace('\u00A0', '\0').replace('\u00C2', '\0');
                count++;
                if (count > 1 && linha != null && !linha.equals("")) {
                    String[] cols = linha.split("\t");
                    if (cols.length == 3) {
                        cols[0] = cols[0].trim();
                        cols[1] = cols[1].trim();
                        cols[2] = cols[2].trim();
                        if (cols.length == 3)
                            add(new Pair(cols[0], null, cols[1], cols[2]));
                    } else
                        System.out.println(String.format("Error: class -> %1s, line -> %1s.", "MovieEntityPairs", linha));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MovieEntityPairs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MovieEntityPairs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
