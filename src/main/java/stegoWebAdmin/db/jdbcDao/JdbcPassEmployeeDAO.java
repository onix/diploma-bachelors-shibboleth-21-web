package stegoWebAdmin.db.jdbcDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import stegoWebAdmin.db.dao.PassEmployeeDAO;
import stegoWebAdmin.models.PassEmployee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPassEmployeeDAO extends AbstractDAO implements PassEmployeeDAO {
    private static String ID_EMPLOYEE = "idEmployee";
    private static String IS_PASS_ACTIVE = "isPassActive";
    private static String DATE_OF_EXPIRATION = "dateOfExpiration";
    private static String AUTHENTICATION_ID = "authenticationId";

    private static String SELECT_PASS_BY_ID = "SELECT * FROM stego_acs.acs_pass_employee WHERE \"idEmployee\" = :idEmployee";
    private static String SELECT_PASS_BY_AUTHENTICATOR = "SELECT * FROM stego_acs.acs_pass_employee WHERE \"authenticationId\" = :authenticationId";
    private static String INSERT_NEW_PASS = "INSERT INTO stego_acs.acs_pass_employee (\"idEmployee\", \"isPassActive\", \"dateOfExpiration\", \"authenticationId\") VALUES (:idEmployee, :isPassActive, :dateOfExpiration, :authenticationId)";
    private static String UPDATE_PASS_BY_ID = "UPDATE stego_acs.acs_pass_employee SET \"isPassActive\" = :isPassActive, \"dateOfExpiration\" = :dateOfExpiration, \"authenticationId\" = :authenticationId WHERE \"idEmployee\" = :idEmployee;";
    private static String DELETE_PASS_BY_ID = "DELETE FROM stego_acs.acs_pass_employee WHERE \"idEmployee\" = :idEmployee";

    private Connection connection = null;

    private DriverManagerDataSource dataSource;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public PassEmployee findPassByEmployeeId(int employeeId) {
        if (employeeId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource selectParameters = new MapSqlParameterSource(ID_EMPLOYEE, employeeId);

                PassEmployee pass = (PassEmployee) namedSelect.queryForObject(SELECT_PASS_BY_ID, selectParameters,
                        new RowMapper() {
                            public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {                                 //Incorrect result size: expected 1, actual 0
                                return new PassEmployee(
                                        resultSet.getInt(ID_EMPLOYEE),
                                        resultSet.getBoolean(IS_PASS_ACTIVE),
                                        resultSet.getTimestamp(DATE_OF_EXPIRATION),
                                        resultSet.getBytes(AUTHENTICATION_ID)
                                );
                            }
                        }
                );
                connection.close();
                logger.info("Pass for employee with id " + pass.getIdEmployee() + " extracted.");
                return pass;
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public PassEmployee findByEmployeeAuthenticator(byte[] authenticator) {
        if (authenticator  != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource selectParameters = new MapSqlParameterSource(AUTHENTICATION_ID, authenticator);

                PassEmployee employee = (PassEmployee) namedSelect.queryForObject(SELECT_PASS_BY_AUTHENTICATOR, selectParameters,
                        new RowMapper() {
                            public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                                return new PassEmployee(
                                        resultSet.getInt(ID_EMPLOYEE),
                                        resultSet.getBoolean(IS_PASS_ACTIVE),
                                        resultSet.getTimestamp(DATE_OF_EXPIRATION),
                                        resultSet.getBytes(AUTHENTICATION_ID)
                                );
                            }
                        }
                );
                connection.close();
                logger.info("Employee with id " + employee.getIdEmployee() + " extracted.");
                return employee;
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public void insert(PassEmployee passEmployee) {
        if (passEmployee != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedInsert = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource insertParameters = new MapSqlParameterSource();

                insertParameters.addValue(ID_EMPLOYEE, passEmployee.getIdEmployee());
                insertParameters.addValue(IS_PASS_ACTIVE, passEmployee.isPassActive());
                insertParameters.addValue(DATE_OF_EXPIRATION, passEmployee.getDateOfExpiration());
                insertParameters.addValue(AUTHENTICATION_ID, passEmployee.getAuthenticationId());

                namedInsert.update(INSERT_NEW_PASS, insertParameters);
                logger.info("Pass entry for employee with id " + passEmployee.getIdEmployee() + " entry successfully added.");
                connection.close();
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public void update(PassEmployee passEmployee) {
        if (passEmployee != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedUpdate = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource updateParameters = new MapSqlParameterSource();

                updateParameters.addValue(ID_EMPLOYEE, passEmployee.getIdEmployee());
                updateParameters.addValue(IS_PASS_ACTIVE, passEmployee.isPassActive());
                updateParameters.addValue(DATE_OF_EXPIRATION, passEmployee.getDateOfExpiration());
                updateParameters.addValue(AUTHENTICATION_ID, passEmployee.getAuthenticationId());

                namedUpdate.update(UPDATE_PASS_BY_ID, updateParameters);
                logger.info("Pass for employee with id " + passEmployee.getIdEmployee() + " successfully updated.");
                connection.close();

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public boolean deleteByEmployeeId(int employeeId) {
        if (employeeId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedDelete = new NamedParameterJdbcTemplate(getDataSource());

                int count = namedDelete.update(DELETE_PASS_BY_ID, new MapSqlParameterSource(ID_EMPLOYEE, employeeId));

                connection.close();
                logger.info("Pass for employee with id " + employeeId + " successfully removed from DB.");
                return count == 1;
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return false;
    }
}
