import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            switch(sc.nextLine()) {
                case "comp":
                    comp();
                    break;
                case "decomp":
                    decomp();
                    break;
                case "size":
                    size();
                    break;
                case "equal":
                    equal();
                    break;
                case "about":
                    about();
                    break;
                case "exit":
                    sc.close();
                    return;
                default:
                    System.out.println("wrong command");
            }
        }
    }
    public static void comp() {

    }
    public static void decomp() {
        
    }
    public static void size() {
        
    }
    public static void equal() {
        
    }
    public static void about() {
        
    }
}