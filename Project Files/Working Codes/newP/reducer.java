import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
//	Set<String> distinct_src_IP = new HashSet<String>();
//    Set<String> distinct_dst_IP = new HashSet<String>();
//    Set<String> distinct_src_Port = new HashSet<String>();
//    Set<String> distinct_dst_Port = new HashSet<String>();
//    Set<String> distinct_cnt_Set = new HashSet<String>();
	
	@Override
	public void reduce(final Text key, final Iterable<IntWritable> values,final Context context) throws IOException, InterruptedException 
	{

	  	int col=0;
        
		Iterator<IntWritable> iterator = values.iterator();

		while (iterator.hasNext()) 
		{
            col = iterator.next().get();
              if(col>=5)
              { 
              			context.write(key,new IntWritable());
              }
		}
	}		
}