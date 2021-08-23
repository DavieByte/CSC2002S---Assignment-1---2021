import java.util.concurrent.ForkJoinPool;

public class experiment 
{
    static float [] values;
    static int filterSize;
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static float startTime = 0;
    float[] seqArray;
    float[] parArray;

    public experiment(float[] values, int filterSize)
    {
        this.values = values;
        this.filterSize = filterSize;
    }

    public void runSequencialExperiment()
    {
        sequencial seqFilter = new sequencial(filterSize, values);
        seqArray = seqFilter.applyFilter();
    }

    public float[] getSeqArray()
    {
        return seqArray;
    }

    public void runParallelExperiment()
    {
        parallel parFilter = new parallel(values, 1, filterSize);
        fjPool.invoke(parFilter);
        parArray = parFilter.getValues();
    }

    public float[] getParArray()
    {
        return parArray;
    }
}
