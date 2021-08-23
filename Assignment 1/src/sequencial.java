import java.util.Arrays;
public class sequencial
{
    float[] values;
    int filterSize; 
    float[] output;

    public sequencial(int filterSize, float[] values)
    {
        this.filterSize = filterSize;
        this.values = values;
        output = new float[values.length];
    }

    public float[] applyFilter()
    {
       float[] windowValues = new float[filterSize];

       //compensating for boundray values
       for(int i = 0; i < Math.floor(filterSize/2); i++)
       {
            output[i] = values[i];
            output[values.length - 1 - i] = values[values.length - 1- 1];
       }
       //inserting correct values into output array
       for(int j = (int) Math.floor(filterSize/2), l = 0; j < values.length - Math.floor(filterSize/2); j++, l++)
       {
            int f = l;
            for(int k = 0; k < filterSize; k++)
            {
                windowValues[k] = values[f];
                f++;
            }
            Arrays.sort(windowValues);
            output[j] = windowValues[(filterSize -1)/2];
       }
       return output;
    }

}
