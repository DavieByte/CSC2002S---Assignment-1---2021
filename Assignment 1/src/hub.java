import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner; 

public class hub
{
    static long startTime = 0;
    
    private static void tick()
    {
		startTime = System.currentTimeMillis();
	}
	private static float toc()
    {
		return (System.currentTimeMillis() - startTime); 
	}
    public static void main(String[] args) 
    {
        // reading in the file and populating 1D array
        String inputFile = args[0];
		int filterSize = Integer.parseInt(args[1]);
		String outputFile = args[2];
        float [] values = new float[10];
        try 
        {
            File file = new File(inputFile); //change to args later
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

        // Instantiating experiment class
        experiment filter; 
        // Running sequencial algorithm and timing the results
        float[] seqTimes = new float[20];
        for(int i = 0; i < 20; i++)
        {
            filter = new experiment(values, filterSize);
            System.gc();
            tick();
            filter.runSequencialExperiment();
            seqTimes[i] = toc();
        }
        float sum = 0;
        for(int i = 0; i < 20; i++)
        {
            sum += seqTimes[i];
        }
        float seqAverage = sum/20;
        System.out.println("Sequencial median filtering took: " + seqAverage + "s");

        // Running parallel algorithm and timing the results
        float[] parTimes = new float[20];
        for(int i = 0; i < 20; i++)
        {
            filter = new experiment(values, filterSize);
            System.gc();
            tick();
            filter.runParallelExperiment();
            parTimes[i] = toc();
        }
        sum = 0;
        for(int j = 0; j < 20; j++)
        {
            sum += parTimes[j];
        }
        float parAverage = sum/20;
        System.out.println("Parallel median filtering took: " + parAverage + "s");

        // Writing results to an output text file
        try
        {
            filter = new experiment(values, filterSize);
            filter.runParallelExperiment();
            float[] parArray = filter.parArray();

            File out = new File(outputFile);
            out.createNewFile();
            PrintWriter pw = new PrintWriter(out);
            pw.println(parArray.length);
            for(int i = 0; i < parArray.length; i++)
            {
                pw.println(i + " " + parArray[i]);
            }
            pw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

}