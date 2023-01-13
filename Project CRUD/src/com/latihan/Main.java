package com.latihan;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.File;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.time.Year;

public class Main {
    public static void main(String[] args) throws IOException {
	Scanner terminalInput = new Scanner(System.in);
	String pilihanUser;
	Boolean isLanjutkan = true;

	while(isLanjutkan) {
	    clearScreen();
	    System.out.println("Database Perpustakaan\n");
	    System.out.println("1.\tLihat seluruh data buku");
	    System.out.println("2.\tCari data buku");
	    System.out.println("3.\tTambah data buku");
	    System.out.println("4.\tUbah data buku");
	    System.out.println("5.\tHapus data buku");
	    System.out.println("6.\tLihat data buku yang dihapus");
	    System.out.println("7.\tHapus data buku yang dihapus");
	    System.out.println("8.\tPulihkan data buku yang dihapus");

	    System.out.print("\n\nPilihan anda: ");
	    pilihanUser = terminalInput.next();

	    switch(pilihanUser) {
		case "1":
		    System.out.println("\n=================");
		    System.out.println("LIST SELURUH BUKU");
		    System.out.println("=================");
		    tampilkanData();
		    break;
		case "2":
		    System.out.println("\n=========");
		    System.out.println("CARI BUKU");
		    System.out.println("=========");
		    cariData();
		    break;
		case "3":
		    System.out.println("\n================");
		    System.out.println("TAMBAH DATA BUKU");
		    System.out.println("================");
		    tambahData();
		    tampilkanData();
		    break;
        	case "4":
		    System.out.println("\n==============");
		    System.out.println("UBAH DATA BUKU");
		    System.out.println("==============");
		    break;
		case "5":
		    System.out.println("\n===============");
		    System.out.println("HAPUS DATA BUKU");
		    System.out.println("===============");
		    deleteData();
		    break;
		case "6":
                    System.out.println("\n=============================");
                    System.out.println("LIHAT DATA BUKU YANG TERHAPUS");
                    System.out.println("=============================");
                    tampilkanCacheDB();
                    break;
		case "7":
                    System.out.println("\n=============================");
                    System.out.println("HAPUS DATA BUKU YANG TERHAPUS");
                    System.out.println("=============================");
                    hapusCacheDB();
                    break;
		case "8":
		    System.out.println("\n===========°====================");
                    System.out.println("PULIHKAN DATA BUKU YANG TERHAPUS");
                    System.out.println("================================");
                    pulihkanCacheDB();
                    break;
        	default:
		    System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih [1-8]");
	    }

	    isLanjutkan = getYesOrNo("Apakah anda ingin melanjutkan");
	}

	System.out.println("Terimakasih telah berkunjung...");
    }

    private static void pulihkanCacheDB() throws IOException {
	FileReader fileInput;
	BufferedReader bufferInput;

	try {
            fileInput = new FileReader("../cacheDB.txt");
	    bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.err.println("cacheDB tidak ditemukan");
            return;
        }

	FileWriter fileOutput = new FileWriter("../database.txt", true);
	BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

	System.out.println("\nKekurangan: Saat pulihkan, data di cacheDB masih ada");
	System.out.println("List cacheDB");
	tampilkanCacheDB();

	Scanner terminalInput = new Scanner(System.in);
	System.out.print("\nPulihkan buku nomor berapa?: ");
	int pilihPulih = terminalInput.nextInt();

	String data = bufferInput.readLine();
	int entryCount = 0;
	while(data != null) {
	    entryCount++;

	    if(pilihPulih == entryCount) {
		bufferOutput.write(data);
		bufferOutput.newLine();
	    }

	    data = bufferInput.readLine();
	}
	bufferOutput.flush();
	bufferOutput.close();

    }

    private static void tampilkanCacheDB() throws IOException {
	File cacheDB1;
	FileReader cacheDBfile;
	BufferedReader cacheDBbuffer;

	try {
	    cacheDB1 = new File("../cacheDB.txt");
            cacheDBfile = new FileReader(cacheDB1);
	    cacheDBbuffer = new BufferedReader(cacheDBfile);
        } catch (Exception e) {
            System.err.println("cacheDB tidak ditemukan");
            System.err.println("Mungkin sudah terhapus");
            return;
        }

	// menampilkan isi cacheDB
        System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku");
        System.out.println("-----------------------------------------------------------------------------------------------");

        String data = cacheDBbuffer.readLine();
        int nomorData = 0;
        while(data != null) {
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("| %2s ", nomorData);
            System.out.printf("|\t%4s  ",stringToken.nextToken());
            System.out.printf("|\t%-20s   ",stringToken.nextToken());
            System.out.printf("|\t%-20s   ",stringToken.nextToken());
            System.out.printf("|\t%s   ",stringToken.nextToken());
            System.out.print("\n");

            data = cacheDBbuffer.readLine();
        }

        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    private static void hapusCacheDB() throws IOException {
	File cacheDB1;

	try {
	    cacheDB1 = new File("../cacheDB.txt");
        } catch (Exception e) {
            System.err.println("cacheDB tidak ditemukan");
            System.err.println("Mungkin sudah terhapus");
            return;
        }

	tampilkanCacheDB();
	// hapus cacheDB
        boolean isHapus = getYesOrNo("Apakah anda ingin menghapus cacheDB?");
        if(isHapus) {
            boolean konfirm = getYesOrNo("Yakin menghapus cacheDB??");
            if(konfirm) {
                cacheDB1.delete();
	    }
        }
    }

    private static void deleteData() throws IOException {
	// kita ambil database original
	File database = new File("../database.txt");
	FileReader fileInput = new FileReader(database);
	BufferedReader bufferedInput = new BufferedReader(fileInput);

	// kita buat database sementara
	File tempDB = new File("../tempDB.txt");
	FileWriter fileOutput = new FileWriter(tempDB);
	BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

	// [eksperimental] buat database cache
	FileWriter fileOut = new FileWriter("../cacheDB.txt", true);
	BufferedWriter bufferOut = new BufferedWriter(fileOut);

	// tampilkan data
	System.out.println("\nList Buku");
	tampilkanData();

	// kita ambil user input untuk mendelete data
	Scanner terminalInput = new Scanner(System.in);
	System.out.print("\nMasukkan nomor buku yang akan dihapus: ");
	int deleteNum = terminalInput.nextInt();

	// looping untuk membaca tiap data baris dan skip data yang akan didelete
	boolean isFound = false;
	int entryCounts = 0;

	String data = bufferedInput.readLine();

	while(data != null) {
	    boolean isDelete = false;
	    entryCounts++;

	    StringTokenizer st = new StringTokenizer(data, ",");

	    // menampilkan data yang akan dihapus
	    if(deleteNum == entryCounts) {
		System.out.println("\nData yang ingin anda hapus adalah: ");
		System.out.println("-------------------------------------");
		System.out.println("Referensi     : " + st.nextToken());
		System.out.println("Tahun         : " + st.nextToken());
                System.out.println("Penulis       : " + st.nextToken());
                System.out.println("Penerbit      : " + st.nextToken());
                System.out.println("Judul         : " + st.nextToken());

		isDelete = getYesOrNo("Apakah anda yakin akan menghapus itu?");
		isFound = true;
	    }

	    if(isDelete) {
		// skip pindahkan data dari original ke sementara
		System.out.println("Data berhasil dihapus");
		// [eksperimental] menulis data
		bufferOut.write(data);
		bufferOut.newLine();
	    } else {
		// kita pindahkan data dari original ke sementara
		bufferedOutput.write(data);
		bufferedOutput.newLine();
	    }

	    data = bufferedInput.readLine();
	}

	if(!isFound) {
	    System.err.println("Buku tidak ditemukan");
	}

	// menulis data ke database
	bufferedOutput.flush();
	// delete original file
	database.delete();
	// rename file sementara ke database
	tempDB.renameTo(database);

	// [eksperimental] menulis data ke cacheDB
	bufferOut.flush();

    }

    private static void tambahData() throws IOException {
	FileWriter fileOutput = new FileWriter("../database.txt", true);
	BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

	//mengambil input dari user
	Scanner terminalInput = new Scanner(System.in);
	String penulis, judul, penerbit, tahun;

	System.out.print("\nMasukkan nama penulis: ");
	penulis = terminalInput.nextLine();
	System.out.print("Masukkan judul buku: ");
        judul = terminalInput.nextLine();
        System.out.print("Masukkan nama penerbit: ");
        penerbit = terminalInput.nextLine();
        System.out.print("Masukkan tahun terbit, format=(YYYY): ");
        tahun = ambilTahun();

	// cek buku di database
	String[] keywords = {tahun+","+penulis+","+penerbit+","+judul};
	System.out.println(Arrays.toString(keywords));
	boolean isExist = cekBukuDiDatabase(keywords, false);

	// menulis buku di database
	if(!isExist) {
	    long nomorEntry = ambilEntryPerTahun(penulis, tahun) + 1;
	    String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
	    String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;
	    System.out.println("\nData yang akan anda masukkan adalah");
	    System.out.println("-------------------------------------");
            System.out.println("Primary Key  : " + primaryKey);
	    System.out.println("Tahun Terbit : " + tahun);
            System.out.println("Penulis      : " + penulis);
            System.out.println("Judul Buku   : " + judul);
            System.out.println("Penerbit     : " + penerbit);

	    boolean isTambah = getYesOrNo("Apakah anda ingin menambah data tersebut?");

	    if(isTambah) {
		bufferOutput.write(primaryKey+","+tahun+","+penulis+","+penerbit+","+judul);
		bufferOutput.newLine();
		bufferOutput.flush();
	    }
	} else {
	    System.out.println("Buku yang akan anda masukkan sudah tersedia di database dengan data berikut:");
	    cekBukuDiDatabase(keywords, true);
	}

	bufferOutput.close();
    }

    private static long ambilEntryPerTahun(String penulis, String tahun) throws IOException {
	FileReader fileInput = new FileReader("../database.txt");
	BufferedReader bufferInput = new BufferedReader(fileInput);

	long entry = 0;
	String data = bufferInput.readLine();
	Scanner dataScanner;
	String primaryKey;

	while(data != null) {
	    dataScanner = new Scanner(data);
	    dataScanner.useDelimiter(",");
	    primaryKey = dataScanner.next();

	    dataScanner = new Scanner(primaryKey);
	    dataScanner.useDelimiter("_");
	    penulis = penulis.replaceAll("\\s+", "");

	    if(penulis.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next())) {
		entry = dataScanner.nextInt();
	    }

	    data = bufferInput.readLine();
	}
	return entry;
    }

    private static String ambilTahun() throws IOException {
	boolean tahunValid = false;
	Scanner terminalInput = new Scanner(System.in);
	String tahunInput = terminalInput.nextLine();
        while(!tahunValid) {
            try {
                Year.parse(tahunInput);
                tahunValid = true;
            } catch (Exception e) {
                System.err.println("Format tahun yang anda masukkan salah, format=(YYYY)");
                System.out.print("Silahlan masukkan tahun terbit lagi: ");
                tahunValid = false;
                tahunInput = terminalInput.nextLine();
            }
        }
	return tahunInput;
    }

    private static void cariData() throws IOException {
	// membaca database, ada atau tidak
	try {
            File file = new File("../database.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
	    tambahData();
            return;
        }

	// ambil keyword dari user
	Scanner terminalInput = new Scanner(System.in);
	System.out.print("\nMasukkan kata kunci untuk mencari buku: ");
	String cariString = terminalInput.nextLine();

	System.out.println("Keywords = " + cariString);

	String[] keywords = cariString.split("\\s+");
	//System.out.println(keywords[0]);
	//System.out.println(keywords[1]);

	// kita cek keyword di database
	cekBukuDiDatabase(keywords, true);

    }
    private static boolean cekBukuDiDatabase(String[] keywords, boolean isDisplay) throws IOException {
	FileReader fileInput = new FileReader("../database.txt");
	BufferedReader bufferInput = new BufferedReader(fileInput);

	String data = bufferInput.readLine();
	boolean isExist = false;
	int nomorData = 0;

	if(isDisplay) {
	    System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku");
            System.out.println("-----------------------------------------------------------------------------------------------");
	}

	while(data != null) {
	    // cek keywords didalam baris
	    isExist = true;

	    for(String keyword:keywords) {
		isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
	    }

	    // jika keywordsnya cocok maka tampilkan
	    if(isExist) {
	        if(isDisplay) {
		    nomorData++;
        	    StringTokenizer stringToken = new StringTokenizer(data, ",");

        	    stringToken.nextToken();
            	    System.out.printf("| %2s ", nomorData);
            	    System.out.printf("|\t%4s  ",stringToken.nextToken());
            	    System.out.printf("|\t%-20s   ",stringToken.nextToken());
          	    System.out.printf("|\t%-20s   ",stringToken.nextToken());
        	    System.out.printf("|\t%s   ",stringToken.nextToken());
        	    System.out.print("\n");
		} else {
		    break;
		}
	    }

	    data = bufferInput.readLine();
	}
	if(isDisplay) {
	    System.out.println("-----------------------------------------------------------------------------------------------");
	}
	return isExist;
    }

    private static void tampilkanData() throws IOException {
	FileReader fileInput;
	BufferedReader bufferInput;

	try {
	    fileInput = new FileReader("../database.txt");
	    bufferInput = new BufferedReader(fileInput);
	    // mungkin di dalam kurung buffered bisa ketik "new FileReader" langsung tanpa membuat variabel untuk menampung FileReader
	} catch (Exception e) {
	    System.err.println("Database tidak ditemukan");
	    System.err.println("Silahkan tambah data terlebih dahulu");
	    tambahData();
	    return;
	}

	System.out.println("\n| No |\tTahun |\tPenulis                |\tPenerbit               |\tJudul Buku");
	System.out.println("-----------------------------------------------------------------------------------------------");

	String data = bufferInput.readLine();
	int nomorData = 0;
	while(data != null) {
	    nomorData++;
	    StringTokenizer stringToken = new StringTokenizer(data, ",");

	    stringToken.nextToken();
	    System.out.printf("| %2s ", nomorData);
	    System.out.printf("|\t%4s  ",stringToken.nextToken());
	    System.out.printf("|\t%-20s   ",stringToken.nextToken());
            System.out.printf("|\t%-20s   ",stringToken.nextToken());
	    System.out.printf("|\t%s   ",stringToken.nextToken());
            System.out.print("\n");

	    data = bufferInput.readLine();
	}

	System.out.println("-----------------------------------------------------------------------------------------------");
    }

    private static boolean getYesOrNo(String message) {
	Scanner terminalInput = new Scanner(System.in);
	System.out.print("\n" + message + " (Y/n)? ");
	String pilihanUser = terminalInput.next();

	while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
	    System.err.println("Pilihan anda bukan y atau n");
	    System.out.print("\n" + message + " (Y/n)? ");
	    pilihanUser = terminalInput.next();
	}

	return pilihanUser.equalsIgnoreCase("y");
    }

    private static void clearScreen() {
	try {
	    if(System.getProperty("os.name").contains("Windows")) {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	    } else {
		System.out.print("\033\143");
	    }
	} catch(Exception ex) {
	    System.err.println("Tidak bisa clear screen");
	}
    }
}

