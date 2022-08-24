package duke;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private static final String SAVE_FILE_PATH = "./data/duke.txt";

    public static void saveTasks(TaskList taskList) throws DukeException {
        try {
            FileWriter writer = new FileWriter(SAVE_FILE_PATH);
            String tasks = taskList.toSaveFileString();
            writer.write(tasks);
            writer.close();
        } catch (IOException e) {
            throw new DukeException("There is a problem with saving the tasks");
        }
    }

    public static TaskList getSavedTasks() throws DukeException {
        try {
            File saveFile = new File(SAVE_FILE_PATH);
            //Create file if it does not exist
            saveFile.createNewFile();
            Scanner reader = new Scanner(saveFile);
            TaskList savedTasks = new TaskList();

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] splitted = data.split("\\s@\\s");
                String taskType = splitted[0];
                String task, date;
                boolean isDone;

                switch (taskType) {
                case "[T]":
                    task = splitted[2];
                    isDone = splitted[1].equals("[X]");
                    savedTasks.add(new ToDo(task, isDone));
                    break;

                case "[E]":
                    task = splitted[2];
                    isDone = splitted[1].equals("[X]");
                    date = splitted[3];
                    savedTasks.add(new Event(task, date, isDone));
                    break;

                case "[D]":
                    task = splitted[2];
                    isDone = splitted[1].equals("[X]");
                    date = splitted[3];
                    savedTasks.add(new Deadline(task, date, isDone));
                    break;

                default:
                    throw new DukeException("There is a problem loading your safe file");
                }
            }

            return savedTasks;
        } catch (IOException e) {
            throw new DukeException("There is a problem loading your safe file");
        }
    }
}