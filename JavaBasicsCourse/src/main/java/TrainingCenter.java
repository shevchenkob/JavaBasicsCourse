import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TrainingCenter {
    private static final int BUSINESS_START_HOUR = 10; // 10:00 AM
    private static final int BUSINESS_END_HOUR = 18; // 6:00 PM

    public static void main(String[] args) {

        // Define students
        Student ivanov = new Student("Ivanov Ivan", "Java Developer",
                LocalDateTime.of(2020, Month.JUNE, 1, 10, 0),
                new Course("Java", 16),
                new Course("JDBC", 24),
                new Course("Spring", 16));

        Student sidorov = new Student("Sidorov Ivan", "J2EE Developer",
                LocalDateTime.of(2020, Month.JUNE, 1, 10, 0),
                new Course("Test design", 10),
                new Course("Page Object", 16),
                new Course("Selenium", 16));

        // Get report date from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter report date (yyyy-MM-dd'T'HH:mm): ");
        LocalDateTime reportDate = LocalDateTime.parse(scanner.nextLine());

        // Get report type from user input
        System.out.print("Enter report type (full or short): ");
        String reportType = scanner.nextLine();

        // Generate reports
        generateReport(ivanov, reportDate, reportType);
        generateReport(sidorov, reportDate, reportType);
    }

    public static void generateReport(Student student, LocalDateTime reportDate, String reportType) {
        LocalDateTime startDate = student.getStartDate();
        Duration totalDuration = student.getTotalDuration();

        // Calculate completion date
        LocalDateTime completionDate = startDate;
        long remainingHours = totalDuration.toHours();
        while (remainingHours > 0) {
            completionDate = completionDate.plusDays(1);
            if (completionDate.getDayOfWeek() != DayOfWeek.SATURDAY && completionDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                remainingHours -= 8; // Decrease remaining hours for each working day
            }
        }

        // Calculate remaining or elapsed time
        long daysUntilCompletion = ChronoUnit.DAYS.between(reportDate, completionDate);
        long hoursUntilCompletion = ChronoUnit.HOURS.between(reportDate, completionDate);
        if (hoursUntilCompletion < 0) {
            hoursUntilCompletion *= -1;
        } else if (hoursUntilCompletion >= 24) {
            hoursUntilCompletion %= 24;
        }

        // Generate report
        switch (reportType) {
            case "short":
                if (reportDate.isBefore(completionDate)) {
                    System.out.printf("%s (%s) - Training is not finished. %d d %d hours are left until the end.%n",
                            student.getName(), student.getCurriculum(),
                            daysUntilCompletion, hoursUntilCompletion);
                } else {
                    System.out.printf("%s (%s) - Training completed. %d hours have passed since the end.%n",
                            student.getName(), student.getCurriculum(), hoursUntilCompletion);
                }
                break;
            case "full":
                System.out.printf("Student: %s%n", student.getName());
                System.out.printf("Program: %s%n", student.getCurriculum());
                System.out.printf("Start Date: %s (%s)%n", startDate.toLocalDate(), startDate.getDayOfWeek());
                System.out.printf("End Date: %s (%s)%n", completionDate.toLocalDate(), completionDate.getDayOfWeek());
                System.out.printf("Total Program Duration: %d hours%n", totalDuration.toHours());
                System.out.printf("Working Hours: %02d:00 - %02d:00%n", BUSINESS_START_HOUR, BUSINESS_END_HOUR);
                if (reportDate.isBefore(completionDate)) {
                    System.out.printf("Remaining Time: %d days %02d hours%n", daysUntilCompletion, hoursUntilCompletion);
                } else {
                    System.out.printf("Time Since Completion: %d hours%n", hoursUntilCompletion);
                }
                System.out.println();
                break;
            default:
                System.out.println("Entered value is incorrect");
        }
    }
}
