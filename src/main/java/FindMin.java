import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FindMin {

    Map priceMap = new TreeMap<Integer, Set<String>>();

    private static int MAX_PRODUCTS = 10;

    private class ReadTask implements Runnable {
        private String file;

        public ReadTask(String file) {
            this.file = file;
        }

        private Object addProduct(String[] product) {
            Integer price = Integer.valueOf(product[4]);
            Set<String> productSet = (Set) priceMap.get(price);
            if (productSet == null)
                productSet = new TreeSet<>();
            productSet.add(product[0]);
            return priceMap.put(price,productSet);
        }

        public void run() {
            BufferedReader br = null;
            String line;
            try {
                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] product = line.split(",");
                    addProduct(product);
                    System.out.println("priceMap.size()=" + priceMap.size() + ", productId= " + product[0] + ", price=" + product[4] + "]");
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
        for (Object key : priceMap.keySet()) {
            Set<String> productList  = (Set) priceMap.get(key);
            for (String product : productList) {
                System.out.println("price= " + key + " , productId=" + product + "]");
                pw.println(key + "," + product);
            }
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
