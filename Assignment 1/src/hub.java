import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.concurrent.ForkJoinPool;

public class hub
{
    static long startTime = 0;
    
    private static void tick()
    {
		startTime = System.currentTimeMillis();
	}
	private static float toc()
    {
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}

    static final ForkJoinPool fjPool = new ForkJoinPool();
    static float[] parallelFilter(float[] values, int sequencialThreshold, int filterWindow)
    {
        parallel filter = new parallel(values, sequencialThreshold, filterWindow);
        fjPool.invoke(filter);
        float[] output = filter.getValues();
        return output;
    }
    
    public static void main(String[] args) 
    {
        // reading in the file and populating 1D array
        
        /* 
        String inputFile = args[0];
		int filterSize = Integer.parseInt(args[1]);
		String outputFile = args[2];
        */

        int filterSize = 3; //Change to args later
        float [] values = new float[10]; // remove later
        try 
        {
            File file = new File("sampleInput1000000.txt"); //change to args later
            Scanner darkly = new Scanner(file);
            int loops = Integer.parseInt(darkly.nextLine());
            values = new float[loops];

            for (int i = 0; i < loops; i++)
            {
                String line = darkly.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(" "); 
                lineScanner.nextInt();
                values[i] = lineScanner.nextFloat();
                lineScanner.close();
            }

            darkly.close();
        } 
        catch(FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Running sequencial algorithm and timing the results
        System.gc();
        tick();
        sequencial sqFilter = new sequencial(filterSize, values);
        float seqTime = toc();
        System.out.println("Sequential median filtering took: " + seqTime+"s");


        // Running parallel algorithm and timing the results
        System.gc();
        tick();
        parallelFilter(values, 1, filterSize);
        float parTime = toc();
        System.out.println("Parallel median filtering took: " + parTime + "s");

    }

}