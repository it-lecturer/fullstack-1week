package sample.app.common.generator;

import com.github.f4b6a3.uuid.UuidCreator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class UUIDv7Generator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return UuidCreator.getTimeOrderedEpoch(); // UUID v7 생성
    }
}
