import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class driver extends Configured implements Tool {
	
	static long start,stop;
	public int run(String[] args) throws Exception {
		
		
		Configuration conf = getConf();
        Job job = Job.getInstance(conf);
        
		//Job job = Job.getInstance(getConf());
		job.setJobName("DDoS");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setJarByClass(driver.class);
		//job.addCacheFile(new Path( ("hdfs://master:54310/usr/local/hadoop/input/training")).toUri() );
		
		
//		URI[] cachefiles = 	job.getCacheFiles();
//		for(int i=0;i<cachefiles.length;i++)
//			System.out.println("Cachefile added: "+cachefiles.toString());
	
		
		job.setMapperClass(mapper.class);
		//job.setCombinerClass(reducer.class);
		job.setReducerClass(reducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		Path inputFilePath = new Path(args[0]);
		Path outputFilePath = new Path(args[1]);

		/* This line is to accept the input recursively */
		//FileInputFormat.setInputDirRecursive(job, true);

		FileInputFormat.addInputPath(job, inputFilePath);
		FileOutputFormat.setOutputPath(job, outputFilePath);

		/*
		 * Delete output filepath if already exists
		 */
		FileSystem fs = FileSystem.newInstance(getConf());

		if (fs.exists(outputFilePath)) {
			fs.delete(outputFilePath, true);
		}
		
		
		return job.waitForCompletion(true) ? 0: 1;
	}

	public static void main(String[] args) throws Exception {
		start =  System.currentTimeMillis();
		driver wordcountDriver = new driver();		
		int res = ToolRunner.run(wordcountDriver, args);
		
		stop =  System.currentTimeMillis();
		long diff = (stop-start);
		
        System.out.println(diff);
		
		System.exit(res);
		
	}
}