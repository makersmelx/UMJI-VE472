public class Main {
    public static void main(String[] args) {
        String schema = "input/ex4.json";
        String srcDir = "input/results";
        String pipeline = "output/large.avro";
        CompactSmallFiles compactSmallFiles = new CompactSmallFiles();
        compactSmallFiles.compact(schema, srcDir, pipeline);
        ExtractSmallFiles extractSmallFiles = new ExtractSmallFiles();
        extractSmallFiles.extract(pipeline, "output/newResults");
    }
}