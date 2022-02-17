package bg.sofia.uni.fmi.mjt.todoist.features.task;

import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final String name;
    private LocalDate date;
    private LocalDate dueDate;
    private String description;

    private boolean isCompleted;

    public Task(String name, LocalDate date, LocalDate dueDate, String description) {
        Utils.assertNonNull(name, "Task name");
        Utils.assertNonEmpty(name, "Task name");

        this.name = name;
        this.date = date;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void finish() {
        this.isCompleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return name.equals(task.name) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }

    public String serialize() {
        StringBuilder result = new StringBuilder("add-task name=" + this.name);

        if (this.date != null) {
            result.append(" date=").append(this.date.format(FORMATTER));
        }

        if (this.dueDate != null) {
            result.append(" due-date=").append(this.dueDate.format(FORMATTER));
        }

        if (this.description != null) {
            result.append(" description=").append(this.description);
        }

        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Name: " + this.name);

        if (this.date != null) {
            sb.append(System.lineSeparator()).append("Date: ").append(this.date.format(FORMATTER));
        }

        if (this.dueDate != null) {
            sb.append(System.lineSeparator()).append("Due date: ").append(this.dueDate.format(FORMATTER));
        }

        if (this.description != null) {
            sb.append(System.lineSeparator()).append("Description: ").append(this.description);
        }

        return sb.toString();
    }
}
