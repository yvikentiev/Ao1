import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FindMin {

    Map productPrice = new TreeMap<Integer, String>();

    private static int MAX_PRODUCTS = 10;

    private class ReadTask implements Runnable {
        private String file;

        public ReadTask(String file) {
            this.file = file;
        }

        public void run() {
            BufferedReader br = null;
            String line;
            try {
                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] product = line.split(",");
                    productPrice.put(Integer.valueOf(product[4]),product[0]);
                    System.out.println("productPrice.size()=" + productPrice.size() + ", productId= " + product[0] + ", price=" + product[4] + "]");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printMin() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("output.csv"));
        System.out.println("Min products");
        int i = 0;
        for (Object key : productPrice.keySet()) {
            System.out.println("price= " + key + " , productId=" + productPrice.get(key) + "]");
            pw.println(key+ "," + productPrice.get(key));
            if (i++ >=1000) break;
        }
        pw.flush();
    }

        public void test(String file, int number)  {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < number; i++) {
            ReadTask task = new ReadTask(file + i + ".csv");
            executor.execute(task);
        }
        try {
            executor.awaitTermination(3, TimeUnit.SECONDS);
            printMin();
        }
        catch (InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
