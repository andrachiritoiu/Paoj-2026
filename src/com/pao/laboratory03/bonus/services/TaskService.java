package com.pao.laboratory03.bonus.services;

import com.pao.laboratory03.bonus.exception.DuplicateTaskException;
import com.pao.laboratory03.bonus.exception.TaskNotFoundException;
import com.pao.laboratory03.bonus.exception.InvalidTransitionException;
import com.pao.laboratory03.bonus.model.Task;
import com.pao.laboratory03.bonus.types.Priority;
import com.pao.laboratory03.bonus.types.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService {
    private static TaskService instance;

    private final Map<String, Task> tasksById = new HashMap<>();
    private final Map<Priority, List<Task>> tasksByPriority = new HashMap<>();
    private final List<String> auditLog = new ArrayList<>();

    private int nextId = 1;

    private TaskService() {
        for (Priority priority : Priority.values()) {
            tasksByPriority.put(priority, new ArrayList<>());
        }
    }

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    private String generateId() {
        return String.format("T%03d", nextId++);
    }

    public Task addTask(String title, Priority priority) {
        String id = generateId();
        return addTaskInternal(id, title, priority);
    }

    // metodă adăugată doar ca să poți demonstra DuplicateTaskException pe id duplicat
    public Task addTaskWithCustomId(String id, String title, Priority priority) {
        return addTaskInternal(id, title, priority);
    }

    private Task addTaskInternal(String id, String title, Priority priority) {
        if (tasksById.containsKey(id)) {
            throw new DuplicateTaskException("Există deja un task cu id-ul " + id);
        }

        Task task = new Task(id, title, priority);

        tasksById.put(id, task);
        tasksByPriority.get(priority).add(task);
        auditLog.add("[ADD] " + task.getId() + ": '" + task.getTitle() + "' (" + task.getPriority() + ")");

        return task;
    }

    public void assignTask(String taskId, String assignee) {
        Task task = findTaskById(taskId);
        task.setAssignee(assignee);
        auditLog.add("[ASSIGN] " + taskId + " → " + assignee);
    }

    public void changeStatus(String taskId, Status newStatus) {
        Task task = findTaskById(taskId);
        Status oldStatus = task.getStatus();

        if (!oldStatus.canTransitionTo(newStatus)) {
            throw new InvalidTransitionException(oldStatus, newStatus);
        }

        task.setStatus(newStatus);
        auditLog.add("[STATUS] " + taskId + ": " + oldStatus + " → " + newStatus);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return new ArrayList<>(tasksByPriority.getOrDefault(priority, new ArrayList<>()));
    }

    public Map<Status, Long> getStatusSummary() {
        Map<Status, Long> summary = new HashMap<>();

        for (Status status : Status.values()) {
            summary.put(status, 0L);
        }

        for (Task task : tasksById.values()) {
            Status status = task.getStatus();
            summary.put(status, summary.get(status) + 1);
        }

        return summary;
    }

    public List<Task> getUnassignedTasks() {
        List<Task> unassigned = new ArrayList<>();

        for (Task task : tasksById.values()) {
            if (task.getAssignee() == null) {
                unassigned.add(task);
            }
        }

        return unassigned;
    }

    public void printAuditLog() {
        for (String entry : auditLog) {
            System.out.println(entry);
        }
    }

    public double getTotalUrgencyScore(int baseDays) {
        double total = 0;

        for (Task task : tasksById.values()) {
            if (task.getStatus() != Status.DONE && task.getStatus() != Status.CANCELLED) {
                total += task.getPriority().calculateScore(baseDays);
            }
        }

        return total;
    }

    public Task findTaskById(String taskId) {
        Task task = tasksById.get(taskId);

        if (task == null) {
            throw new TaskNotFoundException("Task-ul '" + taskId + "' nu a fost găsit");
        }

        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksById.values());
    }
}