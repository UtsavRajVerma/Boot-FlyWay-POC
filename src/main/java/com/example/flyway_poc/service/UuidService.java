package com.example.flyway_poc.service;

import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Configuration
public class UuidService {

    @Autowired
    FetchRowService fetchRowService;

    @Value("${table.sourceUUID}")
    String sourceUuid;

    @Value("${table.targetUUID}")
    String targetUuid;

    public void migrateUuidTable(Context context) throws SQLException {
        System.out.println("Migrating user_uuid...");
        final long MigrateUuidTime = System.currentTimeMillis() ;

        Statement insertUuidStmt = context.getConnection().createStatement();

        ResultSet source_uuid = fetchRowService.getResultSet(context,sourceUuid);
        ResultSet target_uuid = fetchRowService.getResultSet(context,targetUuid);

        Set<String> target_uuid_set= new HashSet<>();

        while(target_uuid.next()){
            target_uuid_set.add(target_uuid.getString(3));
        }

        while (source_uuid.next()){
            int id = source_uuid.getInt(1);
            int user_id = source_uuid.getInt(2);
            String uuid= source_uuid.getString(3);
            String platform= source_uuid.getString(4);;
            int status= source_uuid.getInt(5);
            String created_on = source_uuid.getString(6);
            String updated_on = source_uuid.getString(7);

            if(!target_uuid_set.contains(uuid)) {
                insertUuidStmt.execute("insert into Target.user_uuid"
                        + " (id,user_id,uuid,platform,status,created_on,updated_on) values"
                        + " ('" + id + "','" + user_id + "','" + uuid + "','" + platform + "','" + status + "','" + created_on + "','" + updated_on + "')");
            }
            target_uuid_set.add(source_uuid.getString(3));
        }
        final long Latency = System.currentTimeMillis() - MigrateUuidTime ;
        System.out.println("UUID Migrate Time: " + Latency);
        System.out.println("~~ user_uuid Migration Completed ~~");
    }
}
