package com.tracker.model.features;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class F2_Job_Similarity {

	public static final String COMMA = ","; // 逗号
	public static final String SHARP = "#"; // 逗号
	private static final String TABLE_DELIMETER = "\t"; // TABLE键
	public static final String A = "A";
	public static final String B = "B";

	public static class Step1_Map extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			if (line == null || line.equals(""))
				return;
			String[] values = line.split(COMMA);
			if (values.length < 4)
				return;
			String rowindex = values[0];
			String colindex = values[1];
			String elevalue = values[2];
			String squavalue = values[3];
			context.write(new Text(colindex), new Text("a" + SHARP + rowindex + SHARP + elevalue + SHARP + squavalue));
			context.write(new Text(colindex), new Text("b" + SHARP + rowindex + SHARP + elevalue + SHARP + squavalue));
		}
	}

	public static class Step1_Reduce extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			Map<String, String[]> valAMap = new HashMap<String, String[]>();
			Map<String, String[]> valBMap = new HashMap<String, String[]>();
			for (Text v : values) {
				String[] arr = v.toString().split(SHARP);
				if (arr[0].equals("a"))
					valAMap.put(arr[1], new String[] { arr[2], arr[3] });
				else if (arr[0].equals("b"))
					valBMap.put(arr[1], new String[] { arr[2], arr[3] });
			}
			for (Entry<String, String[]> entryA : valAMap.entrySet())
				for (Entry<String, String[]> entryB : valBMap.entrySet()) {
					// out key = j, value = i \t k \t val
					String i = entryA.getKey();
					String k = entryB.getKey();
					if (i.compareTo(k) < 0)// 对角线值为1，且为上三角矩阵
						context.write(key, new Text(i + TABLE_DELIMETER + k + TABLE_DELIMETER + entryA.getValue()[0] + TABLE_DELIMETER
								+ entryA.getValue()[1] + TABLE_DELIMETER + entryB.getValue()[0] + TABLE_DELIMETER
								+ entryB.getValue()[1]));
				}
		}
	}

	public static class Step2_Map extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] values = value.toString().split(TABLE_DELIMETER);
			if (values.length == 7) {
				String i = values[1];
				String k = values[2];
				String valA = values[3];
				String squareA = values[4];
				String valB = values[5];
				String squareB = values[6];
				context.write(new Text(i + COMMA + k), new Text(valA + COMMA + squareA + COMMA + valB + COMMA + squareB));
			}
		}
	}

	public static class Step2_Reduce extends Reducer<Text, Text, Text, FloatWritable> {

		int squareA, squareB;
		boolean first = true;

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int mulV = 0;
			for (Text v : values) {
				String[] value = v.toString().split(COMMA);
				int valA = Integer.valueOf(value[0]);
				int valB = Integer.valueOf(value[2]);
				if (first) {
					squareA = Integer.valueOf(value[1]);
					squareB = Integer.valueOf(value[3]);
					first = false;
				}
				mulV += valA * valB;
			}
			float ret = (float) (mulV / (Math.sqrt(squareA) * Math.sqrt(squareB)));
			if (ret >= 0.01)// 满足相似度阈值
				context.write(key, new FloatWritable(ret));
		}
	}

	public static void main(String[] args) throws Exception {
		String STEP1 = "/step1";
		String FINAL = "/final";
		Configuration conf = new Configuration();
		String[] remainingArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (remainingArgs.length != 2) {
			System.err.println("Usage: F2_Job_Similarity <in> <out>");
			System.exit(2);
		}
		Path in_1 = new Path(remainingArgs[0]);
		Path out_1 = new Path(remainingArgs[1] + STEP1);
		Path out_2 = new Path(remainingArgs[1] + FINAL);
		/** first stage */
		Job jobStep1 = new Job(conf, "F2_Job_Similarity_1");
		jobStep1.setJarByClass(F2_Job_Similarity.class);
		jobStep1.setMapperClass(Step1_Map.class);
		jobStep1.setReducerClass(Step1_Reduce.class);

		jobStep1.setOutputKeyClass(Text.class);
		jobStep1.setOutputValueClass(Text.class);
		jobStep1.setInputFormatClass(TextInputFormat.class);
		jobStep1.setOutputFormatClass(TextOutputFormat.class);
		jobStep1.setNumReduceTasks(5);
		FileInputFormat.addInputPath(jobStep1, in_1);
		FileOutputFormat.setOutputPath(jobStep1, out_1);
		if (!jobStep1.waitForCompletion(true))
			System.exit(1);
		/** second stage */
		Job jobStep2 = new Job(conf, "F2_Job_Similarity_2");
		jobStep2.setJarByClass(F2_Job_Similarity.class);
		jobStep2.setMapperClass(Step2_Map.class);
		jobStep2.setReducerClass(Step2_Reduce.class);

		jobStep2.setOutputKeyClass(Text.class);
		jobStep2.setMapOutputKeyClass(Text.class);
		jobStep2.setMapOutputValueClass(Text.class);
		jobStep2.setOutputValueClass(FloatWritable.class);
		jobStep2.setInputFormatClass(TextInputFormat.class);
		jobStep2.setOutputFormatClass(TextOutputFormat.class);
		jobStep2.setNumReduceTasks(5);
		FileInputFormat.addInputPath(jobStep2, out_1);
		FileOutputFormat.setOutputPath(jobStep2, out_2);
		System.exit(jobStep2.waitForCompletion(true) ? 0 : 1);

	}
}
