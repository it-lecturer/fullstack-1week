package core.base.spring.payroll;

import java.util.UUID;

public class Payroll {
    private UUID id = UUID.randomUUID();
    private Long employeeId;
    private String task;
    private int baseSalary;
    private int bonusAmount;

    public Payroll(Long employeeId, String task, int baseSalary, int bonusAmount) {
        this.employeeId = employeeId;
        this.task = task;
        this.baseSalary = baseSalary;
        this.bonusAmount = bonusAmount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(int bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public int calculateFinalSalary() {
        return baseSalary + bonusAmount;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", task='" + task + '\'' +
                ", baseSalary=" + baseSalary +
                ", bonusAmount=" + bonusAmount +
                '}';
    }
}
