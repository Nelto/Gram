import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Grammar {
    private ArrayList<String> terminals = new ArrayList<>();
    private ArrayList<String> nonterminals = new ArrayList<>();
    private ArrayList<String> rules = new ArrayList<>();
    private String starter;

    public enum TYPE {
        Left_Regular, Right_Regular, Context_Free, Context_Sensitive, Unrestricted
    }

    public Grammar(){
        Init();
    }

    private void Init(){
        Scanner in = new Scanner(System.in);

        System.out. println("Введите терминальные символы через пробел:");
        System.out. println("Терминальными символами могут быть символы латинского алфавита в нижнем регистре, кроме символа 'е', и цифры.");
        String inner = in.nextLine().trim();
        terminals.addAll(Arrays.asList(inner.split(" ")));

        System.out.println("Введите нетерминалы через пробел:");
        inner = in.nextLine().trim();
        nonterminals.addAll(Arrays.asList(inner.split(" ")));

        System.out.println("Введите правила грамматики. Каждое правило - на отдельной строке.");
        System.out.println("Пример строки: S -> aB.");
        System.out.println("Для символа пустой строки (эпсилон) зарезервирован символ 'e'.");
        System.out.println("Для завершения ввода введите пустую строку.");
        while (in.hasNextLine()) {
            inner = in.nextLine().trim();
            if (inner.isEmpty() || inner.equals(" ")) break;
            rules.add(inner);
        }

        System.out.println("Введите стартовый символ грамматики:");
        starter = in.nextLine().trim();
        in.close();
    }

    public TYPE identify(){
        if (this.isLeftRegular()) return TYPE.Left_Regular;
        if (this.isRightRegular()) return TYPE.Right_Regular;
        if (this.isContextFree()) return TYPE.Context_Free;
        if (this.isContextSensitive()) return TYPE.Context_Sensitive;
        return TYPE.Unrestricted;
    }

    public boolean isLeftRegular(){
        String[] rl;
        for (String rule:this.rules) {
            if (Pattern.matches("^[A-Z]",rule.split("->")[0].trim())){
                rl = rule.split("->")[1].trim().split("\\|");
                for (String r:rl) {
                    if (!Pattern.matches("[A-Z][a-df-z0-9]|e|[a-df-z0-9]",r.trim())) {
                        return false;
                    }
                }
            }
            else return false;
        }
        return true;
    }

    public boolean isRightRegular(){
        String[] rl;
        for (String rule:this.rules) {
            if (Pattern.matches("^[A-Z]",rule.split("->")[0].trim())){
                rl = rule.split("->")[1].trim().split("\\|");
                for (String r:rl) {
                    if (!Pattern.matches("^[a-df-z0-9][A-Z]|e|[a-df-z0-9]",r.trim())) {
                        return false;
                    }
                }
            }
            else return false;
        }
        return true;
    }

    public boolean isContextFree(){
        String[] rl;
        for (String rule:this.rules) {
            if (Pattern.matches("^[A-Z]",rule.split("->")[0].trim())){
                rl = rule.split("->")[1].trim().split("\\|");
                for (String r:rl) {
                    if (!Pattern.matches("^[a-df-z0-9]*[A-Z][a-df-z0-9]*|e|[a-df-z0-9]*",r.trim())) {
                        return false;
                    }
                }
            }
            else return false;
        }
        return true;
    }

    public boolean isContextSensitive(){
        String[] rl;
        String left;
        String right;
        char[] leftCh;
        char[] rightCh;
        for (String rule:this.rules) {
            left = rule.split("->")[0].trim();
            if (Pattern.matches("^[a-df-z0-9]+[A-Z]|[a-df-z0-9]+[A-Z][a-df-z0-9]+|[A-Z][a-df-z0-9]+|[A-Z][A-Z]+",left)) {
                rl = rule.split("->")[1].trim().split("\\|");
                if (rl.length > 1) return false;
                right = rl[0].trim();
                if (!right.equals("e")){
                    leftCh = left.toCharArray();
                    rightCh = right.toCharArray();
                    if (leftCh.length != rightCh.length) return false;
                    for (int i = 0; i < leftCh.length; i++) {
                        if (Pattern.matches("^[A-Z]",leftCh[i]+"")&& rightCh[i] == leftCh[i]) return false;
                        if (Pattern.matches("^[a-df-z0-9]",leftCh[i]+"")&& rightCh[i] != leftCh[i]) return false;
                    }
                }
            }
        }
        return true;
    }
}
