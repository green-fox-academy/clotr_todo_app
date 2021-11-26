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
          break;
        case "-r":
          if (args.length == 1) {
            System.out.println("Nem lehetséges az eltávolítás: nem adott meg indexet!");
            break;
          } else if (!isNumeric(args[1])) {
            System.out.println("Nem lehetséges az eltávolítás: a megadott index nem szám!");
            break;
          } else if ((Integer.parseInt(args[1])) > getNumberOfToDos() ||
              (Integer.parseInt(args[1])) < 1) {
            System.out.println("Nem lehetséges az eltávolítás: túlindexelési probléma adódott!");
            break;
          } else {
            removeFromList(args[1]);
          }
          break;
        case "-c":
          if (args.length == 1) {
            System.out.println("Nem lehetséges a feladat végrehajtása: nem adott meg indexet!");
            break;
          } else if (!isNumeric(args[1])) {
            System.out.println("Nem lehetséges a feladat végrehajtása: a megadott index nem szám!");
            break;
          } else if ((Integer.parseInt(args[1])) > getNumberOfToDos() ||
              (Integer.parseInt(args[1])) < 1) {
            System.out.println(
                "Nem lehetséges a feladat végrehajtása: túlindexelési probléma adódott!");
            break;
          } else {
            completeTask(args[1]);
          }
          break;
        default:
          System.out.println("Nem támogatott argumentum!");
          System.out.println();
          printHelp();
      }
    }
  }

  private static void completeTask(String arg) {
    List<String> content = readFile(todoListPath);
    String newContentRow = content.get(Integer.parseInt(arg) - 1);
    content.set(Integer.parseInt(arg) - 1, newContentRow.replace("//U", "//C"));
    writeFile(content, todoListPath);
  }

  private static boolean isNumeric(String string) {
    try {
      Integer.parseInt(string);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }


  private static int getNumberOfToDos() {
    List content = readFile(todoListPath);
    return content.size();
  }


  private static void removeFromList(String arg) {
    List content = readFile(todoListPath);
    content.remove(Integer.parseInt(arg) - 1);
    writeFile(content, todoListPath);
  }

  private static void addToList(String toDoItem) {
    List<String> content = new ArrayList<>();
    content.add(toDoItem + "//U");
    appendFile(content, todoListPath);
  }

  private static void appendFile(List content, Path todoListPath) {
    try {
      Files.write(todoListPath, content, StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.out.println("Unable to write file: " + todoListPath);
    }
  }

  private static void writeFile(List content, Path todoListPath) {
    try {
      Files.write(todoListPath, content);
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
        int rowSize = (list.get(i)).toString().length();
        if ((list.get(i)).toString().contains("//U")) {
          System.out.println(
              i + 1 + " - " + " [ ] " + ((list.get(i)).toString()).substring(0, rowSize - 3));
        } else if (((list.get(i)).toString().contains("//C"))) {
          System.out.println(
              i + 1 + " - " + " [x] " + ((list.get(i)).toString()).substring(0, rowSize - 3));
        }
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

