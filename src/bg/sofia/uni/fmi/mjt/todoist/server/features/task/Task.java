package bg.sofia.uni.fmi.mjt.todoist.server.features.task;

import java.time.LocalDate;
import java.util.Objects;

public class Task {

    private String name;
    private LocalDate date;
    private LocalDate dueDate;
    private String description;

    public Task(String name, LocalDate date, LocalDate dueDate, String description) {
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return null;
    }
}
