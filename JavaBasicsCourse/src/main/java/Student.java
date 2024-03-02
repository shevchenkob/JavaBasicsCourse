import java.time.Duration;
import java.time.LocalDateTime;

class Student {
    private String name;
    private String curriculum;
    private LocalDateTime startDate;
    private Course[] courses;

    public Student(String name, String curriculum, LocalDateTime startDate, Course... courses) {
        this.name = name;
        this.curriculum = curriculum;
        this.startDate = startDate;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Duration getTotalDuration() {
        Duration totalDuration = Duration.ZERO;
        for (Course course : courses) {
            totalDuration = totalDuration.plusHours(course.getDuration());
        }
        return totalDuration;
    }
}