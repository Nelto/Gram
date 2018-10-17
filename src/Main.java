import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        switch (grammar.identify()){
            case Left_Regular: System.out.println("Это леволинейная грамматика.");
                break;
            case Right_Regular: System.out.println("Это праворегулярная грамматика.");
                break;
            case Context_Free: System.out.println("Это контестно-свободная грамматика.");
                break;
            case Context_Sensitive: System.out.println("Это контестно-зависимая грамматика.");
                break;
            case Unrestricted: System.out.println("Это неограниченная грамматика.");
                break;
        }

    }
}
