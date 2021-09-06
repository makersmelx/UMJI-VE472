import ex4.avro.AvroFile;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class CompactSmallFiles {
    private final ArrayList<File> files = new ArrayList<>();

    private void listFiles(String dir) {
        File file = new File(dir);
        if (file.isDirectory()) {
            files.addAll(Arrays.asList(Objects.requireNonNull(file.listFiles())));
        }
    }

    public int compact(String schemaFile, String srcDir, String output) throws NullPointerException {
        int ret = 0;
        listFiles(srcDir);
        try {
            Schema schema = new Schema.Parser().parse(new File(schemaFile));
            DatumWriter<AvroFile> datumWriter = new SpecificDatumWriter<>(AvroFile.class);
            DataFileWriter<AvroFile> dataFileWriter = new DataFileWriter<>(datumWriter);
            dataFileWriter.setCodec(CodecFactory.snappyCodec());
            dataFileWriter.create(schema, new File(output));
            for (File file : files) {
                StringBuilder buffer = new StringBuilder();
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    buffer.append(scanner.nextLine()).append("\n");
                }
                String sha = DigestUtils.shaHex(buffer.toString());
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer.toString().getBytes());
                AvroFile avroFile = AvroFile.newBuilder()
                        .setFilename(file.getName())
                        .setFilecontent(byteBuffer)
                        .setChecksum(sha)
                        .build();
                dataFileWriter.append(avroFile);
                scanner.close();
                byteBuffer.clear();
            }
            dataFileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            ret = 1;
        }
        files.clear();
        return ret;
    }

}
