package core.base.javaonly.withoutdi.employee;

public class Employee {
    private Long id;
    private String name;
    private JobLevel jobLevel;
    private int salary;

    public Employee(Long id, String name, JobLevel jobLevel, int salary) {
        this.id = id;
        this.name = name;
        this.jobLevel = jobLevel;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobLevel=" + jobLevel +
                ", salary=" + salary +
                '}';
    }
}