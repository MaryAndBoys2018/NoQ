import java.io.*;
import java.util.ArrayList;

public class readfromfile {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            File file = new File("test23.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            pw.println("cafe");
            pw.println("pizza");
            pw.println("Macdonalds");
            pw.close();


            //до цього моменту ми записали кафешки й файл
            br = new BufferedReader(new FileReader("test23.txt"));
            ArrayList<String> arr= new ArrayList();
            String line;
            int k=0;
            while ((line = br.readLine()) != null) {
                k++;
                arr.add(line);
            }


            for (int i=0; i<k; i++)
            {
                System.out.println(arr.get(i));
            }

        } catch (IOException e) {
            System.out.print("error :" + e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.print("error :" + e);
            }


        }
    }
}
