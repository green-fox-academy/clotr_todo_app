import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class todo {
  static Path manualPath = Paths.get("manual.txt");
  static Path todoListPath = Paths.get("todolist.txt");

  public static void main(String[] args) {
    argumentCheck(args);

  }

  private static void argumentCheck(String[] args) {
    if (args.length == 0) {
      printHelp();
    } else {
      switch (args[0]) {
        case "-l":
          listTodo();
          break;
        case "-a":
          if (args.length == 1) {
            System.out.println("Nem lehetséges új feladat hozzáadása: nincs megadva feladat!");
            break;
          } else {
            addToList(args[1]);
          }
      }
    }
  }

  private static void addToList(String toDoItem) {
    List<String> content = new ArrayList<>();
    content.add(toDoItem);
    writeFile(content, todoListPath);
  }

  private static void writeFile(List content, Path todoListPath) {
    try {
      Files.write(todoListPath, content, StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.out.println("Unable to write file: " + todoListPath);
    }
  }

  private static void listTodo() {
    displayList(readFile(todoListPath));
  }

  private static void displayList(List list) {
    if (list.isEmpty()) {
      System.out.println("Nincs mára tennivalód!");
    } else {
      for (int i = 0; i < list.size(); i++) {
        System.out.println(i + 1 + " - " + (list.get(i)).toString());
      }
    }
  }

  private static void printHelp() {
    displayContent(readFile(manualPath));
  }

  private static List readFile(Path path) {
    List<String> fileContent = new ArrayList<>();
    try {
      fileContent = Files.readAllLines(path);
    } catch (IOException e) {
      System.out.println("Unable to read file: " + path);

    }
    return fileContent;
  }

  private static void displayContent(List list) {
    for (Object row : list) {
      System.out.println(row.toString());
    }

  }
}