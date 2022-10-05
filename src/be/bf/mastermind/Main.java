package be.bf.mastermind;

import java.security.SecureRandom;
import java.util.*;

public class Main {
    public static final Scanner INPUT = new Scanner(System.in);
    public static final int BOARD_WIDTH = 4;
    public static final int BOARD_LENGTH = 10;
    public static final Map<Integer, String> COLORS_MAP = new HashMap<>();

    public static String solution;
    public static boolean find = false; // Par défaut, je ne trouve pas la solution
    public static String essai;
    public static int nbEssai = 0;
    public static ArrayList<Character> indice = new ArrayList<Character>();

    public static void main(String[] args) {
        //Couleur jouable
        COLORS_MAP.put(0, "J");
        COLORS_MAP.put(1, "B");
        COLORS_MAP.put(2, "P");
        COLORS_MAP.put(3, "O");
        COLORS_MAP.put(4, "N");
        COLORS_MAP.put(5, "C");

        //Couleur indice
        COLORS_MAP.put(-1, "W"); //Existe mais pas bonne place
        COLORS_MAP.put(-2, "R"); //Existe et bonne place

        creerSolution();
        while (!find && nbEssai < BOARD_LENGTH) {
            essai();
            afficherEssai();
            valideSolution();
            if (!find) {
                afficherIndice();
            }
        }
        afficherFin();
    }

    public static void afficherFin() {
        if (find) {
            System.out.printf("\nFeliciation tu as trouve !!\n");
        } else {
            System.out.printf("\nEt non dommage jeune padawan\nLa solution est %s", solution);
        }
    }

    public static void afficherIndice() {
        StringBuilder builder = new StringBuilder();
        for(char c: indice) {
            builder.append(c);
        }
        System.out.printf(" => Indice: %s\n", builder);
    }

    /**
     * Procédure permettant l'initialisation de la solution
     * @modify solution tq solution.size = BOARD_WIDTH
     */
    public static void creerSolution() {
//        System.out.println("Quel est la solution ?");
//        solution = INPUT.next();
//        while (solution.length() != BOARD_WIDTH) {
//            System.out.println("La solution ne doit etre que de 4 couleurs");
//            solution = INPUT.next();
//        }
        SecureRandom sr = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        while (builder.length() < 4) {
            int color = sr.nextInt(0, 5);
            if (builder.indexOf(COLORS_MAP.get(color)) == -1) {
                builder.append(COLORS_MAP.get(color));
            }
        }

        solution = builder.toString();
    }

    /**
     * Procédure permettant de créer un essai
     * @modify essai tq essai.size = BOARD_WIDTH
     */
    public static void essai() {
        System.out.printf("Couleur possible: ");
        for(String color: COLORS_MAP.values()) {
            if (color.equals("R") || color.equals("W")) continue;
            System.out.printf("%s ", color);
        }
        System.out.print("\nEntrez votre essai: ");
        essai = INPUT.next();
        while (essai.length() != BOARD_WIDTH) {
            System.out.println("L'essai ne doit etre que de 4 couleurs");
            essai = INPUT.next();
        }
        nbEssai++;
    }

    /**
     * Procédure permettant la validation de la solution et la créer des indices
     * @modify find
     * @modify indice ssi !find
     */
    public static void valideSolution() {
        Map<Character, List<Integer>> pos = new HashMap<>();
        for(int i = 0; i < solution.length(); i++) {
            char c = solution.charAt(i);
            if (!pos.containsKey(c)) {
                pos.put(c, new ArrayList<>());
            }
            pos.get(c).add(i);
        }
        find = solution.equals(essai);
        if (!find) {
            indice.clear();
            for(int i = 0; i < BOARD_WIDTH; i++){
                char sc = solution.charAt(i);
                char ec = essai.charAt(i);
                if (ec == sc) {
                    indice.add('r');
                    pos.get(ec).remove(i);
                } else if (pos.containsKey(ec) && pos.get(ec).size() > 0){
                    indice.add('w');
                    pos.get(ec).remove(i);
                }
            }
        }
    }

    /**
     * Procédure permettant l'affichage de l'essai en cours
     */
    public static void afficherEssai() {
        System.out.printf("Essai: %s", essai);
    }
}
