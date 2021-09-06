package ve472.hw3;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.*;

public class ex1 {
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            IntWritable grade = new IntWritable(1);
            Text uid = new Text();
            String line = value.toString();
            StringTokenizer itr = new StringTokenizer(line, ",");
            itr.nextToken();
            uid.set(itr.nextToken());
            grade.set(Integer.parseInt(itr.nextToken()));
            output.collect(uid, grade);
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int maxGrade = 0;
            while (values.hasNext()) {
                maxGrade = Math.max(maxGrade, values.next().get());
            }
            IntWritable grade = new IntWritable(maxGrade);
            output.collect(key, grade);
        }
    }


    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(ex1.class);
        conf.setJobName("ex1");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        JobClient.runJob(conf);
    }
}
