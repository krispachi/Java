import java.util.Scanner;

public class Tutor {
    public static void main(String[] args) {

        Scanner inputUser = new Scanner(System.in);

        // meminta input dari user
        System.out.print("Ketik NAMAMU!!! : ");
        String input = inputUser.next();

        // menampilkan hasil
        System.out.println("halo " + input);

    }
}
