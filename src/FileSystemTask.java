import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Задание № 2: создание, копирование, рекурсивный обход и удаление
 * файлов и директорий с помощью java.nio.file.
 */
public class FileSystemTask {

    private static final String SURNAME = "Karmanov";
    private static final String NAME = "Aleksandr.txt";

    public static void main(String[] args) throws IOException {
        Path root = Paths.get(SURNAME);

        // Если программу запускают повторно — начинаем с чистой структуры
        if (Files.exists(root)) {
            deleteRecursively(root);
        }

        // 1. Директория <surname>
        Files.createDirectories(root);

        // 2. Файл <name> внутри <surname>
        Path nameFile = root.resolve(NAME);
        Files.createFile(nameFile);
        Files.writeString(nameFile, "Карманов Александр Евгеньевич, КБ-251");

        // 3. Вложенные директории dir1/dir2/dir3 и копия файла <name>
        Path dir1 = root.resolve("dir1");
        Path dir2 = dir1.resolve("dir2");
        Path dir3 = dir2.resolve("dir3");
        Files.createDirectories(dir3);
        Files.copy(nameFile, dir3.resolve(NAME), StandardCopyOption.REPLACE_EXISTING);

        // 4. file1 в dir1
        Files.createFile(dir1.resolve("file1"));

        // 5. file2 в dir2
        Files.createFile(dir2.resolve("file2"));

        // 6. Рекурсивный обход с метками D: и F:
        System.out.println("Структура директории " + root + ":");
        printTree(root);

        // 7. Удаление dir1 со всем содержимым
        deleteRecursively(dir1);
        System.out.println();
        System.out.println("После удаления " + dir1 + ":");
        printTree(root);
    }

    /** Выводит все файлы и директории с метками D: / F: и отступом по глубине. */
    private static void printTree(Path root) throws IOException {
        try (Stream<Path> paths = Files.walk(root)) {
            paths.forEach(path -> {
                int depth = root.relativize(path).getNameCount();
                if (path.equals(root)) depth = 0;
                String indent = "    ".repeat(depth);
                String mark = Files.isDirectory(path) ? "D: " : "F: ";
                System.out.println(indent + mark + path.getFileName());
            });
        }
    }

    /** Удаляет директорию со всем содержимым: сначала вложенные элементы, потом родителей. */
    private static void deleteRecursively(Path dir) throws IOException {
        try (Stream<Path> paths = Files.walk(dir)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.delete(path);
            }
        }
    }
}
