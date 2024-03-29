package recap_exercises.Ex06ToDoListMVC.service;

import recap_exercises.Ex06ToDoListMVC.model.Priority;
import recap_exercises.Ex06ToDoListMVC.repository.ToDoListSet;
import recap_exercises.Ex06ToDoListMVC.view.DisplayText;

import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class ToDoListServiceImpl implements ToDoListService {
    private static Scanner scanner;

    public ToDoListServiceImpl() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void getAllItems() {
        if (!ToDoListSet.getToDoListSet().isEmpty()) {
            ToDoListSet.getToDoListSet().forEach(DisplayText::displayItem);
        } else {
            DisplayText.displayNoItems();
        }
    }

    @Override
    public void addSingleItem() {
        DisplayText.askDescription();
        String description = scanner.nextLine();
        Boolean repeatPriorityQuestion = true;
        while (repeatPriorityQuestion) {
            DisplayText.askPriority();
            String priorityString = scanner.nextLine();
            Priority priorityValue = MatchPriorityToInput(priorityString);
            if (!Objects.isNull(priorityValue)) {
                repeatPriorityQuestion = false;
                ToDoListSet.addToDoItem(description, priorityValue);
            }
        }
    }

    @Override
    public void removeSingleItem(int todoIdToRemove) {
        if (ToDoListSet.isIdPresent(todoIdToRemove)) {
            ToDoListSet.removeToDoItemById(todoIdToRemove);
        }
        getAllItems();
    }

    @Override
    public void replaceSingleItem(int todoIdToReplace) {
        if (ToDoListSet.isIdPresent(todoIdToReplace)) {
            DisplayText.displayItem(ToDoListSet.getToDoItemById(todoIdToReplace));
            DisplayText.askDescription();
            String description = scanner.nextLine();
            Boolean repeatPriorityQuestion = true;
            while (repeatPriorityQuestion) {
                DisplayText.askDescription();
                String priorityString = scanner.nextLine();
                Priority priorityValue = MatchPriorityToInput(priorityString);
                if (!Objects.isNull(priorityValue)) {
                    repeatPriorityQuestion = false;
                    ToDoListSet.replaceItem(todoIdToReplace, description, priorityValue);
                    DisplayText.displayItem(ToDoListSet.getToDoItemById(todoIdToReplace));
                }
            }
        } else {
            DisplayText.toDoIdNotFound();
        }
    }

    private Priority MatchPriorityToInput(String priority) {
        for (Priority value : Priority.values()) {
            if (value.toString().equals(priority.toUpperCase())) {
                return value;
            }
        }
        return null;
    }
}
