import trabalhoia.Partition;
import java.util.*;

public class RedesNeurais {
    public List<String> atributos = List.of("Sexo", "Idade", "Altura", "Peso", "Cabelo", "Classe");
    public Map<Integer, String> classes = Map.of(0, "Homer", 1, "Bart", 2, "Lisa");
    public Map<Integer, Integer> thresholds = new HashMap<>();
    public List<Integer> ignoreAttribute = new ArrayList<>();
    public List<Integer> ignoreEntry = new ArrayList<>();
    public List<Partition> partitions = new ArrayList<>();
    public Integer[][] dados = new Integer[12][6];
    public Double entropy;

    public Random rand = new Random();

    public RedesNeurais() {
        initializeData();
    }

    public void initializeData() {
        for(int i = 0; i < 12; i++) {
            Integer val = dados[i][5] =rand.nextInt(0, 3);
            switch(val) {
                case 0 -> {
                    dados[i][0] = 0;
                    dados[i][1] = rand.nextInt(32, 41);
                    dados[i][2] = rand.nextInt(174, 186);
                    dados[i][3] = rand.nextInt(108, 119);
                    dados[i][4] = rand.nextInt(0, 5);
                }

                case 1 -> {
                    dados[i][0] = 0;
                    dados[i][1] = rand.nextInt(8, 13);
                    dados[i][2] = rand.nextInt(82, 98);
                    dados[i][3] = rand.nextInt(36, 45);
                    dados[i][4] = rand.nextInt(3, 8);
                }

                case 2 -> {
                    dados[i][0] = 1;
                    dados[i][1] = rand.nextInt(6, 11);
                    dados[i][2] = rand.nextInt(62, 78);
                    dados[i][3] = rand.nextInt(32, 39);
                    dados[i][4] = rand.nextInt(12, 16);
                }
            }
        }
    }

    public void dataset() {
        for(int i = 0; i < 6; i++) {
            System.out.printf("| %-12s ", atributos.get(i));
        }
        System.out.println("|");
        for(int i = 0; i < 12; i++) {
            System.out.printf("| %-12s ", (dados[i][0] == 0) ? "M" : "F");
            for(int j = 1; j < 5; j++) {
                System.out.printf("| %-12s ", dados[i][j]);
            }
            System.out.printf("| %-12s |", classes.get(dados[i][5]));
            System.out.println();
        }
    }

    public void buildDecisionTree() {
        dataset();
        for(int i = 0; i < atributos.size(); i++) {
            if(isHomogenius()) break;
            nextDecision();
        }
        System.out.println();
        for(var aux : partitions) {
            System.out.println(atributos.get(aux.attribute) + " " + aux.criteria);
            System.out.println("\t" + "Classe " + aux.group + " - " + aux.definition);
        }
    }

    public boolean isHomogenius() {
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < dados.length; i++) {
            if(ignoreEntry.contains(i)) continue;
            set.add(dados[i][5]);
            if(set.size() > 1) return false;
        }
        return true;
    }

    public void nextDecision() {
        maxEntropy();
        Double maxGain = 0.0, current = 0.0;
        Integer index = 0;
        for(int i = 0; i < atributos.size() - 1; i++) {
            if(ignoreAttribute.contains(i)) continue;
            current = attributeGain(i);
            if(current > maxGain) {
                maxGain = current;
                index = i;
            }
        }
        Map<Integer, List<Integer>> g1 = new HashMap<>(), g2 = new HashMap<>();
        for(int i = 0; i < dados.length; i++) {
            if(ignoreEntry.contains(i)) continue;
            Integer threshold = thresholds.get(index);
            if(dados[i][index] < threshold) {
                g1.computeIfAbsent(dados[i][5], k -> new ArrayList<>()).add(i);
            } else {
                g2.computeIfAbsent(dados[i][5], k -> new ArrayList<>()).add(i);
            }
        }
        Integer size01 = g1.size(), size02 = g2.size(), classe = 0;
        Boolean once = true;
        String criteria;
        if(size01 < size02) {
            for(var aux : g1.values()) {
                ignoreEntry.addAll(aux);
                if(once) {
                    classe = dados[aux.get(0)][5];
                    once = false;
                }
            }
            criteria = "< " + thresholds.get(index);
        } else {
            for(var aux : g2.values()) {
                ignoreEntry.addAll(aux);
                if(once) {
                    classe = dados[aux.get(0)][5];
                    once = false;
                }
            }
            criteria = ">= " + thresholds.get(index);
        }
        partitions.add(new Partition(index, classe, criteria, classes.get(classe)));
        ignoreAttribute.add(index);
    }

    public void maxEntropy() {
        Double entropy = 0.0;
        Double n = 0.0;
        Map<Integer, Integer> values = new HashMap<>();
        for(int i = 0; i < dados.length; i++) {
            if(ignoreEntry.contains(i)) continue;
            values.merge(dados[i][5], 1, Integer::sum);
            n += 1;
        }
        for(var aux : values.values()) {
            entropy += -(aux/n) * (Math.log(aux/n) / Math.log(2));
        }
        this.entropy = entropy;
    }

    public Double attributeGain(int index) {
        Double max = 0.0;
        Integer currentThresold = 0;
        for(int i = 0; i < dados.length; i++) {
            Double current = currentThresholdGainCalculation(index, dados[i][index]);
            if(current > max) {
                max = current;
                currentThresold = i;
            }
        }
        if(thresholds.get(index) != null) {
            thresholds.replace(index, dados[currentThresold][index]);
        } else {
            thresholds.put(index, dados[currentThresold][index]);
        }
        return max;
    }

    public Double currentThresholdGainCalculation(Integer index, Integer threshold) {
        Double n = 0.0, ans = 0.0;
        Map<Integer, Map<Integer, Integer>> values = new HashMap<>();
        Map<Integer, Integer> g1 = new HashMap<>();
        Map<Integer, Integer> g2 = new HashMap<>();
        for(int i = 0; i < dados.length; i++) {
            if(ignoreEntry.contains(i)) continue;
            if(dados[i][index] < threshold) {
                g1.merge(dados[i][5], 1, Integer::sum);
            } else {
                g2.merge(dados[i][5], 1, Integer::sum);
            }
            n++;
        }
        values.put(0, g1);
        values.put(1, g2);
        for(var map : values.values()) {
            Double local = 0.0;
            // sum corresponds to the total dataset amount of the momentary option
            Double sum = (double) map.values().stream().mapToInt(Integer::intValue).sum();
            for(var aux : map.values()) {
                local += -(aux/sum) * (Math.log(aux/sum) / Math.log(2));
            }
            ans += (sum/n) * local;
        }
        ans = entropy - ans;
        return ans;
    }
}
