import java.util.Arrays;


public class sequencial
{
    float[] values;
    int filterSize; 

    public sequencial(int filterSize, float[] values)
    {
        this.filterSize = filterSize;
        this.values = values;
    }

    public float[] applyFilter()
    {
        float[] output = new float[filterSize];
        int window = (filterSize -1)/2;

        for(int i = 0; i < values.length; i++)
        {
            float[] windowValues = new float[filterSize];
            if( i < window || i > (windowValues.length - window - 1)) 
            {
                output[i] = values[i];
            }
            else
            {
                for(int j = i - window; j < window + i + 1; j++)
                {
                    windowValues[j] = values[j];
                }
                Arrays.sort(windowValues);
                
                output[i] = windowValues[(windowValues.length - 1)/ 2];
            }
            
        }
        return output;
    }

}
