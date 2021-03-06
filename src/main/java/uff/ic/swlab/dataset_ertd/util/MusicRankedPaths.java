package uff.ic.swlab.dataset_ertd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MusicRankedPaths extends HashMap<String, ArrayList<Score>> {

    public static Config conf = Config.getInsatnce();

    public MusicRankedPaths() {
        String linha, name;
        File dir = new File(conf.rawDataRootDir() + "/music_ranked_paths");
        for (File f : dir.listFiles()) {
            name = f.getName().trim().replaceAll(".txt$", "").replaceAll("^\\d*\\.", "");
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
                            cols[1] = cols[1].trim().replace("\"", "");
                            cols[2] = cols[2].trim();
                            ArrayList<Score> lista = get(name);
                            if (lista == null) {
                                lista = new ArrayList<>();
                                put(name, lista);
                            }
                            lista.add(new Score(cols[0], cols[1], Double.valueOf(cols[2])));
                        } else
                            System.out.println(String.format("Erro: class -> %1s, file -> %1s, line -> %1s.", "MusicScores", f.getName(), linha));
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MusicRankedPaths.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MusicRankedPaths.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<Score> getPaths(String entity1, String entity2) {
        return get(entity1 + "-" + entity2);
    }
}
