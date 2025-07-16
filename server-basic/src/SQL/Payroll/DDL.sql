-- 직원 테이블 생성
CREATE TABLE employee (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      job_level VARCHAR(50) NOT NULL,
      salary INT NOT NULL,
      CONSTRAINT chk_job_level CHECK (job_level IN (
        'Assistant', 'SeniorAssistant', 'AssistantManager', 'Manager',
        'DeputyManager', 'SeniorManager', 'Director', 'ManagingDirector',
        'SeniorManagingDirector', 'VicePresident', 'President'
      ))
);


-- 급여 테이블 생성
CREATE TABLE payroll (
     id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
     employee_id BIGINT NOT NULL,
     task VARCHAR(255) NOT NULL,
     base_salary INT NOT NULL,
     bonus_amount INT NOT NULL,
     CONSTRAINT fk_payroll_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);