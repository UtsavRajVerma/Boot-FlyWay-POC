package com.example.flyway_poc.service;

import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class TargetIdService {
    public int getLastId(Context context,String table,String id) throws SQLException {
        int lastFetchedId = -1;

        Statement stmt = context.getConnection().createStatement();

        String maxIdQuery = "SELECT max( "+id+" ) FROM  "+table;

        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet maxIdRowSet = factory.createCachedRowSet();

        String mysqlUrl = "jdbc:mysql://localhost/Target";
        maxIdRowSet.setUrl(mysqlUrl);
        maxIdRowSet.setUsername("root");
        maxIdRowSet.setPassword("utsav");
        maxIdRowSet.setCommand(maxIdQuery);
        maxIdRowSet.execute();

        if (maxIdRowSet.next()) lastFetchedId = maxIdRowSet.getInt(1);
        else throw new SQLException();

        System.out.println("Tuple returned from " + table + ". Returned Last ID: " + lastFetchedId);
        return lastFetchedId;
    }
}
