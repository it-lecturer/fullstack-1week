package core.base.solid;

/**
 * 확장엔 열려 있고, 변경엔 닫혀 있어야 한다.
 * 2. Open-Closed Principle (개방 폐쇄 원칙)
 */
public class OCP {
    /**
     * 새로운 할인 정책이 생길 때마다 수정함.
     */
    static class Before {
        public int getDiscount(String userType) {
            if (userType.equals("VIP")) return 20;
            else if (userType.equals("BASIC")) return 10;
            else return 0;
        }
    }

    /**
     * 전략 패턴으로 확장성 확보
     * 기존 코드는 건드리지 않고 새로운 정책을 "확장"만 하도록 설계하자.
     */
    static class After {
        interface DiscountPolicy {
            int discount();
        }

        class VipDiscount implements DiscountPolicy {
            public int discount() { return 20; }
        }

        class BasicDiscount implements DiscountPolicy {
            public int discount() { return 10; }
        }

        class DiscountService {
            private final DiscountPolicy policy;

            public DiscountService(DiscountPolicy policy) {
                this.policy = policy;
            }

            public int getDiscount() {
                return policy.discount();
            }
        }
    }
}
