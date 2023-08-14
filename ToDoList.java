import java.util.*;
class App {
    String taskDescription;
    String taskDueDate;
    int taskPriority;
    boolean tdone = false;
}
public class ToDoList{
    public static void main(String[] ar) {
        Scanner scaobj = new Scanner(System.in);
        List<App> tc = new ArrayList<>();

        System.out.println("\n---To-Do List---");

        while (true) {
            System.out.println("\nChoose option:");
            System.out.println("\n1. Add Task");
            System.out.println("2. Display Tasks");
            System.out.println("3. Mark as Done");
            System.out.println("4. List Pending Tasks");
            System.out.println("5. Remove Completed Tasks");
            System.out.println("6. Exit");
            System.out.print("\nSelect an option: ");
            int c = Integer.parseInt(scaobj.nextLine());

            switch (c) {
                case 1:
                    App taskObj = addTask(scaobj);
                    tc.add(taskObj);
					
                    System.out.println("\nAdded..");
                    break;
                case 2:
                    System.out.println("\nDisplayOptions:");
                    System.out.println("1. All Tasks");
                    System.out.println("2. Tasks by Due Date");
                    System.out.println("3. Tasks by Priority");
                    System.out.print("Select an option: ");
                    int dc = Integer.parseInt(scaobj.nextLine());
                    switch (dc) {
                        case 1:
                            displayT(tc, null);
                            break;
                        case 2:
                            displayT(tc, Comparator.comparing(taskItem -> taskItem.taskDueDate));
                            break;
                        case 3:
                            displayT(tc, Comparator.comparingInt(taskItem -> taskItem.taskPriority));
                            break;
                        default:
                            System.out.println("Invalid option.\n");
                    }
                    break;
                case 3:
                    markAsDone(tc, scaobj);
                    break;
                case 4:
                    listPendingT(tc);
                    break;
                case 5:
                    removeCompletedT(tc);
                    break;
                case 6:
                    System.out.println("Exit");
                    scaobj.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid. Select given options.\n");
            }
        }
    }

    public static App addTask(Scanner scanner) {
        App task = new App();

        System.out.print("Enter task description: ");
        task.taskDescription = scanner.nextLine();

        System.out.print("Enter due date: ");
        task.taskDueDate = scanner.nextLine();

        System.out.print("Enter priority: ");
        task.taskPriority = Integer.parseInt(scanner.nextLine());

        return task;
    }

    public static void displayT(List<App> taskCollection, Comparator<App> comp) {
        if (comp != null) {
            Collections.sort(taskCollection, comp);
        }

        System.out.format("%-20s%-15s%-10s%-10s\n", "Description", "Due Date", "Priority", "Status");
        System.out.println("--------------------------------------------------------------");

        for (App task : taskCollection) {
            String status = task.tdone ? "Done" : "Pending";
            System.out.format("%-20s%-15s%-10d%-10s\n", task.taskDescription, task.taskDueDate, task.taskPriority, status);
        }
        System.out.println();
    }

    public static void markAsDone(List<App> taskCollection, Scanner scanner) {
        System.out.print("Enter task no to mark as done: ");
        int taskNo = Integer.parseInt(scanner.nextLine());

        if (taskNo > 0 && taskNo <= taskCollection.size()) {
            App task = taskCollection.get(taskNo - 1);
            task.tdone = true;
            System.out.println("Task marked as done!\n");
        } else {
            System.out.println("Invalid task number.\n");
        }
    }

    public static void listPendingT(List<App> taskCollecection) {
        List<App> pendingT = new ArrayList<>();
        for (App task : taskCollecection) {
            if (!task.tdone) {
                pendingT.add(task);
            }
        }

        if (pendingT.isEmpty()) {
            System.out.println("No pending tasks...");
        } else {
            System.out.println("\nPending Tasks (Ordered by Priority and Due Date):");
            displayT(pendingT, Comparator.comparingInt(taskItem -> taskItem.taskPriority));
        }
    }

    public static void removeCompletedT(List<App> taskCollection) {
        List<App> completedT = new ArrayList<>();
        Iterator<App> iterator = taskCollection.iterator();
        while (iterator.hasNext()) {
            App task = iterator.next();
            if (task.tdone) {
                iterator.remove();
                completedT.add(task);
            }
        }

        if (completedT.isEmpty()) {
            System.out.println("No completed tasks to remove.");
        } else {
            System.out.println("\nRemoved Completed Tasks:");
            displayT(completedT, null);
        }
    }
}
