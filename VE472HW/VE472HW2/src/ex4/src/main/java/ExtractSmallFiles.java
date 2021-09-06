import ex4.avro.AvroFile;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class ExtractSmallFiles {
    private String decodeByte(ByteBuffer byteBuffer) throws CharacterCodingException {
        Charset charset = StandardCharsets.UTF_8;
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer = charsetDecoder.decode(byteBuffer.asReadOnlyBuffer());
        return charBuffer.toString();
    }

    public int extract(String avroFile, String destDir) {
        int ret = 0;
        try {
            File dir = new File(destDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            DatumReader<AvroFile> datumReader = new SpecificDatumReader<>(AvroFile.class);
            DataFileReader<AvroFile> dataFileReader = new DataFileReader<AvroFile>(new File(avroFile), datumReader);
            AvroFile itr = null;
            while (dataFileReader.hasNext()) {
                itr = dataFileReader.next();
                String buffer = decodeByte(itr.getFilecontent());
                String sha = DigestUtils.shaHex(buffer);
                String filename = itr.getFilename().toString();
                if (!itr.getChecksum().toString().equals(sha)) {
                    throw new Exception("Wrong checksum for " + filename);
                }
                File file = new File(destDir + "/" + filename);
                FileWriter writer = new FileWriter(file);
                writer.write(buffer);
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
