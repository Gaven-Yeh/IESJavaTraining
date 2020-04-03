package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.ResultSet;
import java.sql.Statement;

public class V4__anonymise extends BaseJavaMigration {
    public void migrate(Context context) throws Exception {
        try(Statement select = context.getConnection().createStatement()) {
            try(ResultSet rows = select.executeQuery("SELECT id FROM employee ORDER BY id")) {
                while (rows.next()) {
                    int id = rows.getInt(1);
                    String anonymisedName = "Anonymous" + id;
                    try (Statement update = context.getConnection().createStatement()) {
                        update.execute("UPDATE employee SET name='" + anonymisedName + "' WHERE id=" + id);
                    }
                }
            }
        }
    }
}