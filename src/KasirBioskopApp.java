import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class KasirBioskopApp {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Boolean isDone = false;
            String labelKursi = "";
            int jumlahKursi;
            List<HashMap> listStatusKursi = new ArrayList<>();
            List<String> listKodeUnikKursi = new ArrayList<>();
            System.out.println("=================== Selamat Datang (Cinema XXI), Silahkan masukkan konfigurasi denah studio ===================");

            System.out.print("Masukkan Label Kursi : ");
            labelKursi = scanner.next();
            jumlahKursi = inputJumlahKursi();

            String kodeKursi = labelKursi+"_"+String.valueOf(jumlahKursi);
            for (int i = 1; i <= jumlahKursi; i++) {
                HashMap<String, String> statusKursi = new HashMap<>();
                String kodeUnikKursi = labelKursi+i;
                statusKursi.put(kodeUnikKursi,"free");
                listStatusKursi.add(statusKursi);
                listKodeUnikKursi.add(kodeUnikKursi);

//          System.out.println(kodeUnikKursi);
            }
            System.out.println("=================== Aplikasi Cinema XXI Layout (kursi tersedia "+kodeKursi+") ===================");
            pemilihanMenuFunction(listKodeUnikKursi,listStatusKursi);

        }

        //Konfigurasi Denah
        private static int inputJumlahKursi(){
            Scanner scanner = new Scanner(System.in);
            int jumlahKursi = 0;
            int maxJumlahKursi = 20;
            Boolean isDone = false;
            while (!isDone) {
                System.out.print("Masukkan Jumlah Kursi : ");
                String jumlah = scanner.next();
                if(jumlah.matches("\\d+")){
                    jumlahKursi = Integer.valueOf(jumlah);

                    if (jumlahKursi > maxJumlahKursi) {
                        System.out.println("Gagal input jumlah kursi, jumlah kursi yang tersedia adalah 20 Unit");
                        delay(3000);
                        isDone= false;
                    }else{
                        isDone = true;
                    }
                }else{
                    System.out.println("Hanya bisa menginput angka");
                    delay(3000);
                    isDone = false;
                }
            }
            return jumlahKursi;
        }

        //Pemilihan Menu
        private static void pemilihanMenuFunction(List<String>listKodeUnikKursi,List<HashMap>listStatusKursi){
            Scanner scanner = new Scanner(System.in);
            Boolean isDone = false;
            System.out.println("========================= Pilih salah satu menu di bawah ini ==========================");
            System.out.println("A) Pemesanan Kursi ");
            System.out.println("B) Batalkan Kursi ");
            System.out.println("C) Laporan Denah ");
            System.out.println("D) Laporan Transaksi ");
            System.out.println("");
            System.out.println("Masukkan Exit untuk keluar ");
            while (!isDone) {
                System.out.println("===========================");
                System.out.print("Masukkan :");
                String opsi = scanner.next();

                if(opsi.equalsIgnoreCase("A")){
                    pemesananKursi(listKodeUnikKursi,listStatusKursi);
                    isDone = true;
                }

                if(opsi.equalsIgnoreCase("B")){
                    batalkanKursi(listKodeUnikKursi,listStatusKursi);
                    isDone = true;
                }else if(opsi.equalsIgnoreCase("C")){
                    cekDenahStatus(listKodeUnikKursi,listStatusKursi);
                    isDone = true;
                }else if(opsi.equalsIgnoreCase("D")){
                    cekStatusTransaksi(listKodeUnikKursi,listStatusKursi);
                    isDone = true;
                }else if(opsi.equalsIgnoreCase("Exit")){
                    System.exit(0);
                }else{
                    System.out.println("Opsi yang Anda pilih tidak tersedia!");
                    isDone = false;
                }
            }
        }

        //Pemesanan Kursi
        private static List<HashMap> pemesananKursi(List<String>listKodeUnikKursi,List<HashMap>listStatusKursi){
            Scanner scanner = new Scanner(System.in);
            Boolean isDone = false;
            List<HashMap> listStatus = new ArrayList<>();
            System.out.println("");
            System.out.println("List Kode Kursi ");
            System.out.println("---------------------------");
            for (String kodeUnikKursi : listKodeUnikKursi) {
                System.out.println(kodeUnikKursi);
            }
            System.out.println("");
            while(!isDone){
                System.out.print("book_seat :");
                String kodeUnikKursi = scanner.next();
                if(listKodeUnikKursi.contains(kodeUnikKursi)){
                    for(HashMap statusKursi : listStatusKursi){
                        String str = statusKursi.keySet().toString();
                        String str1 = str.replaceAll("\\[|\\]", "");
                        String str2 = str1.trim();
                        if(str2.equals(kodeUnikKursi)){
                            String status = statusKursi.get(kodeUnikKursi).toString();
                            if(status.equals("sold")){
                                System.out.println("");
                                System.out.println("Maaf kursi yang anda masukkan sudah terjual, silahkan melakukan pemesanan ulang!");
                                delay(3000);
                                System.out.println("");
                            }else{
                                LocalDateTime localDateTime = LocalDateTime.now();
                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
                                String waktuPemesanan = localDateTime.format(dateTimeFormatter);
                                statusKursi.put(kodeUnikKursi,"sold");
                                statusKursi.put("waktuPemesanan",waktuPemesanan);
                                System.out.println("Berhasil memesan Kursi  "+kodeUnikKursi);
                                delay(3000);
                            }
                        }
                    }
                    pemilihanMenuFunction(listKodeUnikKursi,listStatusKursi);
                }else{
                    System.out.println("Maaf Kode kursi yang anda masukkan tidak valid, silahkan memasukkan kode kursi yang valid!");
                    delay(3000);
                    isDone = false;
                }
            }
            listStatus = listStatusKursi;
            return listStatus;
        }

        private static List<HashMap> batalkanKursi(List<String>listKodeUnikKursi,List<HashMap>listStatusKursi){
            Scanner scanner = new Scanner(System.in);
            Boolean isDone = false;
            List<HashMap> listStatus = new ArrayList<>();
            System.out.println("");
            System.out.println("List Kode Kursi ");
            System.out.println("---------------------------");
            for (String kodeUnikKursi : listKodeUnikKursi) {
                System.out.println(kodeUnikKursi);
            }
            System.out.println("");
            while(!isDone){
                System.out.print("cancel_seat :");
                String kodeUnikKursi = scanner.next();
                if(listKodeUnikKursi.contains(kodeUnikKursi)){
                    for(HashMap statusKursi : listStatusKursi){
                        String status1 = statusKursi.keySet().toString();
                        if(status1.contains(kodeUnikKursi)){
                            String status = statusKursi.get(kodeUnikKursi).toString();
                            if(status.equals("sold")){
                                statusKursi.put(kodeUnikKursi,"free");
                                System.out.println("Berhasil membatalkan Kursi!");
                                delay(3000);
                                pemilihanMenuFunction(listKodeUnikKursi,listStatusKursi);
                            }else{
                                System.out.println("Tidak bisa membatalkan kursi yang statusnya free!");
                                delay(3000);
                                pemilihanMenuFunction(listKodeUnikKursi,listStatusKursi);
                            }
                        }
                    }
                }else{
                    System.out.println("Maaf Kode kursi yang anda masukkan tidak tersedia, silahkan memasukkan kode kursi yang tersedia!");
                    isDone = false;
                }
            }
            return listStatus;
        }

        public static void delay(int milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                // Handle the exception here
            }
        }

        private static void cekDenahStatus(List<String>listKodeUnikKursi,List<HashMap>listStatusKursi){
            Scanner scanner = new Scanner(System.in);
            System.out.println("=================== Denah Status =================== ");
            int total = listStatusKursi.size();
            for(int i=0; i<total; i++){
                HashMap statusKursi = listStatusKursi.get(i);
                String nomorKursiTemp = statusKursi.keySet().toString();
                String nomorKursi = nomorKursiTemp.replaceAll("\\[|\\]", "");
                String status;
                if(nomorKursi.contains(",")){
                    String str1 = nomorKursi.replaceAll("waktuPemesanan","");
                    String str2 = str1.replace(",","");
                    status = statusKursi.get(str2.trim()).toString();
                    nomorKursi = str2.trim();
                }else{
                    status = statusKursi.get(nomorKursi).toString();
                }
                System.out.println(nomorKursi +"_"+status);

            }
            System.out.println("");
            System.out.println("Untuk kembali ke Menu ketik apa saja lalu tekan Enter");
            String scan = scanner.next();
            if(scan != null){
                pemilihanMenuFunction(listKodeUnikKursi,listStatusKursi);
            }
        }

        private static void cekStatusTransaksi(List<String>listKodeUnikKursi,List<HashMap>listStatusKursi){
            Scanner scanner = new Scanner(System.in);
            System.out.println("=================== Status Transaksi =================== ");
            int total = listStatusKursi.size();
            int y = 0;
            int free = 0;
            int sold = 0;
            for(int i=0; i<total; i++){
                HashMap statusTransaksi = listStatusKursi.get(i);
                if(statusTransaksi.containsKey("waktuPemesanan")){
                    String nomorKursiTemp = statusTransaksi.keySet().toString();
                    String nomorKursi = nomorKursiTemp.replaceAll("\\[|\\]", "");
                    String str1 = nomorKursi.replaceAll("waktuPemesanan","");
                    String str2 = str1.replace(",","");
                    String status = statusTransaksi.get(str2.trim()).toString();
                    if(status.equals("free")){
                        free++;
                        continue;
                    }else {
                        String waktuPemesanan = statusTransaksi.get("waktuPemesanan").toString();
                        System.out.println(str2.trim() + ", " + waktuPemesanan);
                        System.out.println("");
                        sold++;
                    }
                }else{
                    free++;
                }
            }
            System.out.println("Total "+ free +" free, " +sold+" sold");

            System.out.println("");
            System.out.println("------------------------------------------------------------");
            System.out.println("Untuk kembali ke Menu ketik apa saja lalu tekan Enter");
            String scan = scanner.next();
            if(scan != null){
                pemilihanMenuFunction(listKodeUnikKursi,listStatusKursi);
            }
        }

}
