package ru.home.geekbrains.java.core_03;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;




public class App {
    public static void main(String[] args) {


        App app = new App();

        app.readFile01();
        app.joinFile02();
        app.pagingText03();
    }



    //1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
    private void readFile01() {

        //Better using "File.separator" instead "/"

        String strPath = System.getProperty("user.dir") + "/data/01.txt";

        Path path =  Paths.get(strPath);

/*        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String currentLine = null;

            while ((currentLine = reader.readLine()) != null) {//while there is content on the current line
                System.out.println(currentLine); // print the current line
            }
        } catch (IOException ex) {
            ex.printStackTrace(); //handle an exception here
        }

        System.out.println();
        System.out.println("==========================================================================");
        System.out.println();

        // second way
        try{

            List<String> contents = Files.readAllLines(path);

            //Read from the stream
            for(String content:contents){//for each line of content in contents
                System.out.println(content);// print the line
            }

        }catch(IOException ex){
            ex.printStackTrace();//handle exception here
        }

        // whole file
        String str = new String(Files.readAllBytes(path),
                        StandardCharsets.UTF_8);


        */


        // to array
        try(FileChannel channel   = FileChannel.open(path)){


            // enough size
            ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());

            int bytesReadied;

            while ((bytesReadied = channel.read(buffer)) != -1) {
                buffer.flip(); // switch buffer

                byte[] chunk = new byte[bytesReadied];
                buffer.get(chunk);
                System.out.println(new String(chunk, StandardCharsets.UTF_8));

                buffer.clear();
            }
        }catch(IOException ex){
            ex.printStackTrace();//handle exception here
        }



    }


//2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт).
//  Может пригодиться следующая конструкция: ArrayList<InputStream> al = new ArrayList<>(); ...
//  Enumeration<InputStream> e = Collections.enumeration(al);

    private void joinFile02() {


        try {

            Path pathOut = Paths.get(System.getProperty("user.dir") + "/data/out.txt");
            FileChannel channelOut = FileChannel.open(pathOut, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE));

            String formating = System.getProperty("user.dir") + "/data/%1$02d.txt";

            for (int i = 1; i<= 5; i++) {

                appendToFile(String.format(formating, i), channelOut);
            }

            channelOut.close();




        }
        catch(IOException ex){
            ex.printStackTrace();//handle exception here
        }




    }


    private void appendToFile(String from,  FileChannel toChannel) throws IOException {

        try {

            FileChannel channelFrom = FileChannel.open(Paths.get(from));
            ByteBuffer buffer = ByteBuffer.allocate((int) channelFrom.size());

            // channelFrom.position(6)  - seek() in channel file

            channelFrom.read(buffer);
            buffer.flip();
            toChannel.write(buffer);

            channelFrom.close();

        } catch (IOException ex) {
            ex.printStackTrace();//handle exception here

        }
    }





//3. Написать консольное приложение, которое умеет постранично читать текстовые файлы
// (размером > 10 mb). Вводим страницу (за страницу можно принять 1800 символов),
// программа выводит ее в консоль.
// Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.


    private void pagingText03() {


        try {



            final int PAGE_SIZE = 1800;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));



            Path path = Paths.get(System.getProperty("user.dir") + "/data/big.txt");

            FileChannel channelFrom = FileChannel.open(path);
            ByteBuffer buffer = ByteBuffer.allocate(PAGE_SIZE); // maybe problem with multibyte encoding

            int pageCount = (int)channelFrom.size() / PAGE_SIZE;

            System.out.println("Всего страниц: " + pageCount);




            while(true) {

                System.out.print("> ");
                String input = br.readLine();
                int pos = Integer.parseInt(input);

                // ToDo check range in pos

                channelFrom.position(pos * PAGE_SIZE);
                
                channelFrom.read(buffer);
                buffer.flip();
                System.out.println(StandardCharsets.UTF_8.decode(buffer).toString());
                buffer.clear();
            }


        } catch (IOException ex) {
            ex.printStackTrace();//handle exception here

        }

    }

}
