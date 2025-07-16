package core.base.solid;

/**
 * 하나의 클래스는 하나의 책임만 가져야 한다.
 * 1. Single Responsibility Principle (단일 책임 원칙)
 */
public class SRP {

    static class Before {
        /**
         * UserService 가 너무 많은 책임을 가짐
         */
        static class UserService {
            public void registerUser(String name) {
                System.out.println("사용자 등록: " + name);
            }

            public void sendWelcomeEmail(String email) {
                System.out.println("환영 이메일 발송: " + email);
            }

            public void saveToDatabase(String name) {
                System.out.println("DB 저장: " + name);
            }
        }
    }


    /**
     * 책임 분리
     * 변경 이유가 하나만 있도록 클래스 구조를 쪼개자
     */
    static class After {
        static class UserService {
            private final UserRepository repository;
            private final EmailService emailService;

            public UserService(UserRepository repository, EmailService emailService) {
                this.repository = repository;
                this.emailService = emailService;
            }

            public void register(String name, String email) {
                repository.save(name);
                emailService.sendWelcome(email);
            }
        }

        static class UserRepository {
            public void save(String name) {
                System.out.println("DB 저장: " + name);
            }
        }

        static class EmailService {
            public void sendWelcome(String email) {
                System.out.println("환영 이메일 발송: " + email);
            }
        }
    }
}
