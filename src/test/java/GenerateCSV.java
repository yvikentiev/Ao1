import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateCSV {


    @Test
    public void generateFiles() throws FileNotFoundException {

        // create a new writer

        for (int i = 0; i < 1; i++) {
            PrintWriter pw = new PrintWriter(new FileOutputStream("file" + i + ".csv"));
            for (int l = 0; l < 100; l++) {
                Random rand = new Random();
                Integer productId = rand.nextInt(1000);
                Integer price = rand.nextInt(1000);
                String  name = "Laptop";
                String  condition = "New";
                String  state = "Store";
                pw.printf("%s,%s,%s,%s,%s\n", "" + productId, name, condition, state, "" + price);
            }
            pw.close();
        }

    }


}

