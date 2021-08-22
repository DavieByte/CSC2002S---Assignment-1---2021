import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class parallel extends RecursiveAction
{
    static int SEQUENCIAl_THRESHOLD = 1;
    static float[] values;
    static float[] output;
    int fiw; // first index in filter window
    int liw; // last idex in filter window
    static int filterWindow;
    
    public parallel (int low, int high)
    {
        fiw = low;
        liw = high;
    }

    public parallel(float[] values, int sequencialThreshold, int window)
    {
        fiw = 0;
        liw = values.length;
        this.values = values;
        SEQUENCIAl_THRESHOLD = sequencialThreshold;
        output = new float[values.length];
        filterWindow = window;
    }

    @Override
    protected void compute()
    {
        if(liw - fiw <= SEQUENCIAl_THRESHOLD)
        {
            int buffer = filterWindow / 2;
            int start;
            int end;

            if (fiw < buffer)
            {
                start = buffer;
            }
            else
            {
                start = fiw;
            }

            if(values.length - liw < buffer)
            {
                end = values.length - buffer;
            }
            else
            {
                end = liw;
            }

            for(int i = fiw; i < liw; i++)
            {
                if( i < start || i + buffer >= values.length)
                {
                    output[i] = values[i];
                }
                else
                {
                    float[] windowValues = Arrays.copyOfRange(values, i - (filterWindow/2), i + (filterWindow/2) + 1);
                    Arrays.sort(windowValues);
                    output[i] = windowValues[windowValues.length/2];
                }
            }
        }
        else
        {
            parallel left = new parallel(fiw, (liw + fiw)/2);
            parallel right = new parallel((liw + fiw)/2, liw);
            left.fork();
            right.compute();
            left.join();
        }
    }

    public float[] getValues()
    {
        return this.values;
    }

}