import java.util.Scanner;

public class Biodata {
    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);

	String nama, tinggal;
	int umur;

	System.out.print("Input\nMasukkan nama : ");
	nama = scanner.nextLine();

	System.out.print("Masukkan umur : ");
        umur = scanner.nextInt();
	scanner.nextLine(); // menampung enter dari input diatas

	System.out.print("Masukkan alamat : ");
	tinggal = scanner.nextLine();

	System.out.printf("\nOutput\nNama = %s\nUmur = %d\nAlamat = %s\n", nama, umur, tinggal);

    }
}
