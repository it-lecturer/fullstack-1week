package core.base.javaonly.withoutdi.payroll;

public class Payroll {
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


    public int calculateFinalSalary() {
        return baseSalary + bonusAmount;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "employeeId=" + employeeId +
                ", task='" + task + '\'' +
                ", baseSalary=" + baseSalary +
                ", bonusAmount=" + bonusAmount +
                '}';
    }
}
