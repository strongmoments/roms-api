package com.roms.api.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.NativeQuery;
import org.hibernate.tuple.ValueGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static javax.persistence.FlushModeType.COMMIT;
public class CustomGenerator  implements ValueGenerator<String> {
    @Override
    public String generateValue(Session session, Object owner) {
        String prefix = "RD00000";
        NativeQuery nativeQuery = session.createSQLQuery("select count(id) as Id from employee_resource_demand");
        String number =  nativeQuery.setFlushMode(COMMIT).uniqueResult().toString();
        //number =  number.equalsIgnoreCase("0") ? "1" : number;
        return prefix.substring(0,prefix.length()-number.length()) + number;

    }

}





