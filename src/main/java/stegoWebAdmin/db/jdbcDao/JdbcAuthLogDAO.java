package stegoWebAdmin.db.jdbcDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import stegoWebAdmin.db.dao.AuthLogDAO;
import stegoWebAdmin.jsonResponses.AuthLogReadableJsonResponse;
import stegoWebAdmin.models.AuthLogEntry;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcAuthLogDAO extends AbstractDAO implements AuthLogDAO {
    private static String ID_LOG_ENTRY = "idEntry";
    private static String OFFICE_ID = "officeId";
    private static String TYPE_OF_ENTRY = "typeOfEntryId";
    private static String TIME_OF_ACTION = "timeOfAction";
    private static String EMPLOYEE_ID = "employeeId";

    private static String GET_ENTRIES_LIST = "SELECT * FROM stego_acs.acs_auth_log";
    private static String GET_LOG_WITH_NAMES = "SELECT \"idEntry\", \"officeId\", \"typeOfEntryId\", \"timeOfAction\", \"idEmployee\", \"name\", \"surname\"\n" +
            "\tFROM stego_acs.acs_auth_log\n" +
            "\t\tLEFT JOIN stego_acs.acs_employees \n" +
            "\t\t\tON stego_acs.acs_auth_log.\"employeeId\" = stego_acs.acs_employees.\"idEmployee\"\n" +
            "\t\t\t\tORDER BY \"idEntry\" DESC";
    private static String INSERT_NEW_ENTRY = "INSERT INTO stego_acs.acs_auth_log (\"officeId\", \"typeOfEntryId\", \"timeOfAction\", \"employeeId\") VALUES (:officeId, :typeOfEntryId, :timeOfAction, :employeeId)";
    //private static String DELETE_OFFICE_BY_ID = "DELETE FROM stego_acs.acs_auth_log WHERE \"idEntry\" = :idEntry";

    private Connection connection = null;

    private DriverManagerDataSource dataSource;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public ArrayList<AuthLogEntry> findAllEntries() {
        connection = null;
        try {
            connection = dataSource.getConnection();
            List<AuthLogEntry> logEntries = new ArrayList<AuthLogEntry>();
            JdbcTemplate query = new JdbcTemplate(getDataSource());
            List<Map<String, Object>> rows = query.queryForList(GET_ENTRIES_LIST);

            for (Map row : rows) {
                AuthLogEntry logEntry = new AuthLogEntry();
                logEntry.setIdEntry((Integer) row.get(ID_LOG_ENTRY));
                logEntry.setOfficeId((Integer) row.get(OFFICE_ID));
                logEntry.setTypeOfEntryId((Integer) row.get(TYPE_OF_ENTRY));
                logEntry.setTimeOfAction((Timestamp) row.get(TIME_OF_ACTION));
                if (row.get(EMPLOYEE_ID) == null) {
                    logEntry.setEmployeeId(0);
                }
                else
                    logEntry.setEmployeeId((Integer) row.get(EMPLOYEE_ID));
                logEntries.add(logEntry);
            }

            connection.close();
            logger.info("Extracted authentication log list.");
            return (ArrayList<AuthLogEntry>) logEntries;
        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public ArrayList<AuthLogReadableJsonResponse> findEntriesInReadableForm() {
        connection = null;
        try {
            connection = dataSource.getConnection();
            ArrayList<AuthLogReadableJsonResponse> log = new ArrayList<AuthLogReadableJsonResponse>();
            JdbcTemplate query = new JdbcTemplate(getDataSource());
            List<Map<String, Object>> rows = query.queryForList(GET_LOG_WITH_NAMES);

            for (Map row : rows) {
                AuthLogReadableJsonResponse logEntry = new AuthLogReadableJsonResponse();
                logEntry.setIdEntry((Integer) row.get(ID_LOG_ENTRY));
                logEntry.setOfficeId((Integer) row.get(OFFICE_ID));
                logEntry.setTypeOfEntryId((Integer) row.get(TYPE_OF_ENTRY));
                logEntry.setTimeOfAction((Timestamp) row.get(TIME_OF_ACTION));
                if (row.get("idEmployee") == null) {
                    logEntry.setIdEmployee(0);
                    logEntry.setName("");
                    logEntry.setSurname("");
                }
                else {
                    logEntry.setIdEmployee((Integer) row.get("idEmployee"));
                    logEntry.setName((String) row.get("name"));
                    logEntry.setSurname((String) row.get("surname"));
                }
                log.add(logEntry);
            }

            connection.close();
            logger.info("Extracted authentication log list.");
            return log;
        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public void insert(AuthLogEntry entry) {
        if (entry != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedInsert = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource insertParameters = new MapSqlParameterSource();

                insertParameters.addValue(OFFICE_ID, entry.getOfficeId());
                insertParameters.addValue(TYPE_OF_ENTRY, entry.getTypeOfEntryId());
                insertParameters.addValue(TIME_OF_ACTION, entry.getTimeOfAction());
                if (entry.getEmployeeId() == 0)
                    insertParameters.addValue(EMPLOYEE_ID, null);
                else
                    insertParameters.addValue(EMPLOYEE_ID, entry.getEmployeeId());

                namedInsert.update(INSERT_NEW_ENTRY, insertParameters);
                logger.info("New authentication log entry successfully added.");
                connection.close();
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }
}
