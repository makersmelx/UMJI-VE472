package ve472.hw3;

import org.apache.avro.Schema;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.avro.mapred.*;
import org.apache.hadoop.fs.Path;
import org.apache.avro.mapreduce.AvroKeyInputFormat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import ex4.avro.AvroFile;

public class ex2 {

    public static class Map extends MapReduceBase implements Mapper<AvroKey<AvroFile>,NullWritable, Text, IntWritable> {
        private String decodeByte(ByteBuffer byteBuffer) throws CharacterCodingException {
            Charset charset = StandardCharsets.UTF_8;
            CharsetDecoder charsetDecoder = charset.newDecoder();
            CharBuffer charBuffer = charsetDecoder.decode(byteBuffer.asReadOnlyBuffer());
            return charBuffer.toString();
        }
        public void map(AvroKey<AvroFile> key, NullWritable value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String raw = decodeByte(key.datum().getFilecontent());

            String[] content = raw.split("\n");
            Text id = new Text();
            IntWritable grade = new IntWritable();

            for (String line : content) {
                String[] record = line.split(",");
                if (record.length != 3){
                    System.err.println("Error with this file.");
                    return;
                }
                int num = Integer.parseInt(record[2]);
                grade.set(num);
                id.set(record[1]);
                output.collect(id, grade);
            }
        }


    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, AvroKey<CharSequence>, AvroValue<Integer>> {
        @Override
        public void reduce(Text key, Iterator<IntWritable> itr, OutputCollector<AvroKey<CharSequence>, AvroValue<Integer>> outputCollector, Reporter reporter) throws IOException {
            int maxGrade = -1;
            while (itr.hasNext()) {
                IntWritable value = itr.next();
                maxGrade = Math.max(maxGrade,value.get());
            }
            outputCollector.collect(new AvroKey<>(key.toString()),new AvroValue<>(maxGrade));
        }
    }


    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(ex2.class);
        conf.setJobName("ex2");

        AvroJob.setInputSchema(conf,AvroFile.getClassSchema());
        AvroJob.setOutputSchema(conf, Schema.create(Schema.Type.INT));

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(Map.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat((Class<? extends InputFormat>) AvroKeyInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        JobClient.runJob(conf);
    }
}
