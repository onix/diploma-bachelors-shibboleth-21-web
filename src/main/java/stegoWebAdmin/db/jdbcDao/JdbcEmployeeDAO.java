package stegoWebAdmin.db.jdbcDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import stegoWebAdmin.db.dao.EmployeeDAO;
import stegoWebAdmin.models.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcEmployeeDAO extends AbstractDAO implements EmployeeDAO {
    private static String ID_EMPLOYEE = "idEmployee";
    private static String NAME = "name";
    private static String SURNAME = "surname";
    private static String SECOND_NAME = "secondName";
    private static String ADDRESS = "address";
    private static String PHONE = "phone";
    private static String EMAIL = "email";
    private static String PHOTO = "photo";

    private static String GET_EMPLOYEES_LIST = "SELECT \"idEmployee\", name, surname, address, phone, email FROM stego_acs.acs_employees";
    private static String FIND_EMPLOYEES_BY_SURNAME = "SELECT \"idEmployee\", name, surname, address, phone, email FROM stego_acs.acs_employees WHERE LOWER(surname) LIKE :searchPattern";
    private static String FIND_EMPLOYEES_BY_PHONE = "SELECT \"idEmployee\", name, surname, address, phone, email FROM stego_acs.acs_employees WHERE LOWER(phone) LIKE :searchPattern";
    private static String FIND_EMPLOYEES_BY_EMAIL = "SELECT \"idEmployee\", name, surname, address, phone, email FROM stego_acs.acs_employees WHERE LOWER(email) LIKE :searchPattern";
    private static String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM stego_acs.acs_employees WHERE \"idEmployee\" = :idEmployee";
    private static String INSERT_NEW_EMPLOYEE = "INSERT INTO stego_acs.acs_employees (name, surname, \"secondName\", address, phone, email, photo) VALUES (:name, :surname, :secondName, :address, :phone, :email, :photo)";
    private static String UPDATE_EMPLOYEE_BY_ID = "UPDATE stego_acs.acs_employees SET name = :name, surname = :surname, \"secondName\" = :secondName, address = :address, phone = :phone, email = :email, photo = :photo WHERE \"idEmployee\" = :idEmployee;";
    private static String DELETE_EMPLOYEE_BY_ID = "DELETE FROM stego_acs.acs_employees WHERE \"idEmployee\" = :idEmployee";

    private Connection connection = null;

    private DriverManagerDataSource dataSource;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public ArrayList<Employee> findAllEmployees() {
        connection = null;
        try {
            connection = dataSource.getConnection();
            List<Employee> employees = new ArrayList<Employee>();
            JdbcTemplate query = new JdbcTemplate(getDataSource());
            List<Map<String, Object>> rows = query.queryForList(GET_EMPLOYEES_LIST);

            for (Map row : rows) {
                Employee employee = new Employee();
                employee.setIdEmployee((Integer) row.get(ID_EMPLOYEE));
                employee.setName((String) row.get(NAME));
                employee.setSurname((String) row.get(SURNAME));
                employee.setSecondName((String) row.get(SECOND_NAME));
                employee.setAddress((String) row.get(ADDRESS));
                employee.setPhone((String) row.get(PHONE));
                employee.setEmail((String) row.get(EMAIL));
                //employee.setPhoto((byte[]) row.get(PHOTO)); //TODO Optimize maybe
                employees.add(employee);
            }

            connection.close();
            logger.info("Extracted offices list.");
            return (ArrayList<Employee>) employees;
        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public ArrayList<Employee> findAllEmployeesBySurname(String surname) { //TODO merge methods
        connection = null;
        try {
            connection = dataSource.getConnection();
            List<Employee> employees = new ArrayList<Employee>();

            NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(getDataSource());
            MapSqlParameterSource selectParameters = new MapSqlParameterSource("searchPattern", "%" + surname.toLowerCase().trim() + "%");

            List<Map<String, Object>> rows = namedSelect.queryForList(FIND_EMPLOYEES_BY_SURNAME, selectParameters);
            logger.info("searchPattern by surname: " + selectParameters.getValue("searchPattern"));
            logger.info("by surname: " + rows.toString());

            for (Map row : rows) {
                Employee employee = new Employee();
                employee.setIdEmployee((Integer) row.get(ID_EMPLOYEE));
                employee.setName((String) row.get(NAME));
                employee.setSurname((String) row.get(SURNAME));
                employee.setSecondName((String) row.get(SECOND_NAME));
                employee.setAddress((String) row.get(ADDRESS));
                employee.setPhone((String) row.get(PHONE));
                employee.setEmail((String) row.get(EMAIL));
                employees.add(employee);
            }

            connection.close();
            logger.info("Extracted offices list.");
            return (ArrayList<Employee>) employees;
        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public ArrayList<Employee> findAllEmployeesByPhone(String phone) {
        connection = null;
        try {
            connection = dataSource.getConnection();
            List<Employee> employees = new ArrayList<Employee>();

            NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(getDataSource());
            MapSqlParameterSource selectParameters = new MapSqlParameterSource("searchPattern", "%" + phone.toLowerCase().trim() + "%");

            List<Map<String, Object>> rows = namedSelect.queryForList(FIND_EMPLOYEES_BY_PHONE, selectParameters);
            logger.info("searchPattern by phone: " + selectParameters.getValue("searchPattern"));
            logger.info("by phone: " + rows.toString());

            for (Map row : rows) {
                Employee employee = new Employee();
                employee.setIdEmployee((Integer) row.get(ID_EMPLOYEE));
                employee.setName((String) row.get(NAME));
                employee.setSurname((String) row.get(SURNAME));
                employee.setSecondName((String) row.get(SECOND_NAME));
                employee.setAddress((String) row.get(ADDRESS));
                employee.setPhone((String) row.get(PHONE));
                employee.setEmail((String) row.get(EMAIL));
                employees.add(employee);
            }

            connection.close();
            logger.info("Extracted offices list.");
            return (ArrayList<Employee>) employees;
        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public ArrayList<Employee> findAllEmployeesByEmail(String email) {
        connection = null;
        try {
            connection = dataSource.getConnection();
            List<Employee> employees = new ArrayList<Employee>();

            NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(getDataSource());
            MapSqlParameterSource selectParameters = new MapSqlParameterSource("searchPattern", "%" + email.toLowerCase().trim() + "%");
            List<Map<String, Object>> rows = namedSelect.queryForList(FIND_EMPLOYEES_BY_EMAIL, selectParameters);
            logger.info("searchPattern by email: " + selectParameters.getValue("searchPattern"));
            logger.info("by email: " + rows.toString());

            for (Map row : rows) {
                Employee employee = new Employee();
                employee.setIdEmployee((Integer) row.get(ID_EMPLOYEE));
                employee.setName((String) row.get(NAME));
                employee.setSurname((String) row.get(SURNAME));
                employee.setSecondName((String) row.get(SECOND_NAME));
                employee.setAddress((String) row.get(ADDRESS));
                employee.setPhone((String) row.get(PHONE));
                employee.setEmail((String) row.get(EMAIL));
                employees.add(employee);
            }

            connection.close();
            logger.info("Extracted offices list.");
            return (ArrayList<Employee>) employees;
        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public Employee findByEmployeeId(int employeeId) {
        if (employeeId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource selectParameters = new MapSqlParameterSource(ID_EMPLOYEE, employeeId);

                Employee employee = (Employee) namedSelect.queryForObject(SELECT_EMPLOYEE_BY_ID, selectParameters,
                        new RowMapper() {
                            public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                                return new Employee(
                                        resultSet.getInt(ID_EMPLOYEE),
                                        resultSet.getString(NAME),
                                        resultSet.getString(SURNAME),
                                        resultSet.getString(SECOND_NAME),
                                        resultSet.getString(ADDRESS),
                                        resultSet.getString(PHONE),
                                        resultSet.getString(EMAIL),
                                        resultSet.getBytes(PHOTO));
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
    public int insert(Employee employee) {
        if (employee != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedInsert = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource insertParameters = new MapSqlParameterSource();

                insertParameters.addValue(NAME, employee.getName());
                insertParameters.addValue(SURNAME, employee.getSurname());
                insertParameters.addValue(SECOND_NAME, employee.getSecondName());
                insertParameters.addValue(ADDRESS, employee.getAddress());
                insertParameters.addValue(PHONE, employee.getPhone());
                insertParameters.addValue(EMAIL, employee.getEmail());
                insertParameters.addValue(PHOTO, employee.getPhoto());

                KeyHolder lastIncrement = new GeneratedKeyHolder();
                namedInsert.update(INSERT_NEW_EMPLOYEE, insertParameters, lastIncrement, new String[]{ID_EMPLOYEE});
                logger.info("New employee entry successfully added.");
                connection.close();
                return lastIncrement.getKey().intValue();
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return 0;
    }

    @Override
    public void update(Employee employee) {
        if (employee != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedUpdate = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource updateParameters = new MapSqlParameterSource();

                updateParameters.addValue(ID_EMPLOYEE, employee.getIdEmployee());
                updateParameters.addValue(NAME, employee.getName());
                updateParameters.addValue(SURNAME, employee.getSurname());
                updateParameters.addValue(SECOND_NAME, employee.getSecondName());
                updateParameters.addValue(ADDRESS, employee.getAddress());
                updateParameters.addValue(PHONE, employee.getPhone());
                updateParameters.addValue(EMAIL, employee.getEmail());
                updateParameters.addValue(PHOTO, employee.getPhoto());

                namedUpdate.update(UPDATE_EMPLOYEE_BY_ID, updateParameters);
                logger.info("Employee with id " + employee.getIdEmployee() + " successfully updated.");
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

                int count = namedDelete.update(DELETE_EMPLOYEE_BY_ID, new MapSqlParameterSource(ID_EMPLOYEE, employeeId));

                connection.close();
                logger.info("Employee with id " + employeeId + " successfully removed from DB.");
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
