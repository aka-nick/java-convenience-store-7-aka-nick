package store.config.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CsvFileDataReader {

    private static final int SIZE_OF_COLUMN_LINE = 1;
    private static final String SEPARATOR_OF_CSV = ",";

    public static Map<Integer, Map<String, String>> readDataTable(Path path) {
        try {
            List<String> columns = readColumns(path);
            return combineReadDataWithColumn(path, columns);
        } catch (IOException e) {
            throw new IllegalArgumentException("FILE IO를 실패했습니다.", e);
        }
    }

    private static List<String> readColumns(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.limit(SIZE_OF_COLUMN_LINE)
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .toList();
        }
    }

    private static Map<Integer, Map<String, String>> combineReadDataWithColumn(Path path, List<String> columns)
            throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            Integer[] rowIdx = {0};
            return lines.skip(SIZE_OF_COLUMN_LINE)
                    .map(line -> line.split(SEPARATOR_OF_CSV))
                    .collect(Collectors.toMap(tokens -> rowIdx[0]++,
                            tokens -> IntStream.range(0, tokens.length)
                                    .boxed()
                                    .collect(Collectors.toMap(columns::get, i -> tokens[i]))));
        }
    }

}
