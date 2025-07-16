package core.base.solid;


/**
 * 5. DIP (Dependency Inversion Principle) - 의존 역전 원칙
 * 고수준 모듈은 저수준 모듈에 의존하면 안 되고, 둘 다 추상에 의존해야 한다
 */
public class DIP {
    /**
     * 위반 예시: 서비스가 구체 클래스에 직접 의존
     */
    private class Before {
        // 저수준 모듈: MySQL 데이터베이스에 직접 접근하는 클래스
        static class MySQLDatabase {
            public void save(String data) {
                System.out.println("MySQL에 데이터 저장: " + data);
                // 실제 MySQL JDBC 코드 등
            }
        }

        // 고수준 모듈: 사용자 등록 비즈니스 로직
        static class UserService {
            private MySQLDatabase db; // 직접 MySQLDatabase에 의존

            public UserService() {
                this.db = new MySQLDatabase(); // 구체적인 구현체에 직접 의존
            }

            public void registerUser(String username) {
                System.out.println("사용자 " + username + " 등록 시작");
                db.save(username); // 저수준 모듈 메서드 호출
                System.out.println("사용자 등록 완료");
            }
        }

        // 클라이언트 코드
        public class Application {
            public static void main(String[] args) {
                UserService userService = new UserService();
                userService.registerUser("홍길동");
            }
        }
    }

    private class After {
        interface UserRepository {
            void save(String data);
        }

        static class MySQLRepository implements UserRepository {
            @Override
            public void save(String data) {
                System.out.println("MySQL에 데이터 저장: " + data);
                // 실제 MySQL JDBC 코드 등
            }
        }

        static class PostgreSQLRepository implements UserRepository {
            @Override
            public void save(String data) {
                System.out.println("PostgreSQL에 데이터 저장: " + data);
                // 실제 PostgreSQL JDBC 코드 등
            }
        }

        static class UserService {
            private UserRepository userRepository; // 추상화(인터페이스)에 의존

            // 의존성 주입(Dependency Injection)을 통해 구현체를 받음
            public UserService(UserRepository userRepository) {
                this.userRepository = userRepository;
            }

            public void registerUser(String username) {
                System.out.println("사용자 " + username + " 등록 시작");
                userRepository.save(username);
                System.out.println("사용자 등록 완료");
            }
        }

        // 클라이언트 코드 (의존성 주입을 담당하는 부분)
        private class Application {
            public static void main(String[] args) {
                // 어떤 구현체를 사용할지는 외부(여기서는 main 메서드)에서 결정
                UserRepository mySqlRepo = new MySQLRepository();
                UserService mySqlUserService = new UserService(mySqlRepo);
                mySqlUserService.registerUser("이순신");

                System.out.println("-----------------");

                UserRepository pgSqlRepo = new PostgreSQLRepository();
                UserService pgSqlUserService = new UserService(pgSqlRepo);
                pgSqlUserService.registerUser("강감찬");

            }
        }
    }
}
