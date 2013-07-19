package stegoWebAdmin.db.jdbcDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import stegoWebAdmin.db.dao.OfficeDAO;
import stegoWebAdmin.models.Office;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcOfficeDAO extends AbstractDAO implements OfficeDAO {
    private static String ID_OFFICE = "idOffice";
    private static String NAME = "name";
    private static String ADDRESS = "address";

    private static String GET_OFFICES_LIST = "SELECT * FROM stego_acs.acs_offices";
    private static String SELECT_OFFICE_BY_ID = "SELECT * FROM stego_acs.acs_offices WHERE \"idOffice\" = :idOffice";
    private static String INSERT_NEW_OFFICE = "INSERT INTO stego_acs.acs_offices (name, address) VALUES (:name, :address)";
    private static String UPDATE_OFFICE_BY_ID = "UPDATE stego_acs.acs_offices SET name = :name, address = :address WHERE \"idOffice\" = :idOffice;";
    private static String DELETE_OFFICE_BY_ID = "DELETE FROM stego_acs.acs_offices WHERE \"idOffice\" = :idOffice";

    private Connection connection = null;

    private DriverManagerDataSource dataSource;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public ArrayList<Office> findAllOffices() {
        connection = null;
        try {
            connection = dataSource.getConnection();
            List<Office> offices = new ArrayList<Office>();
            JdbcTemplate query = new JdbcTemplate(getDataSource());
            List<Map<String, Object>> rows = query.queryForList(GET_OFFICES_LIST);

            for (Map row : rows) {
                Office office = new Office();
                office.setIdOffice((Integer) row.get(ID_OFFICE));
                office.setName((String) row.get(NAME));
                office.setAddress((String) row.get(ADDRESS));
                offices.add(office);
            }

            connection.close();
            logger.info("Extracted offices list.");
            return (ArrayList<Office>) offices;
        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public Office findByOfficeId(int officeId) {
        if (officeId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource selectParameters = new MapSqlParameterSource(ID_OFFICE, officeId);

                Office office = (Office) namedSelect.queryForObject(SELECT_OFFICE_BY_ID, selectParameters,
                        new RowMapper() {
                            public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                                return new Office(resultSet.getInt(ID_OFFICE),
                                        resultSet.getString(NAME),
                                        resultSet.getString(ADDRESS));
                            }
                        }
                );


                logger.info(office.getName());
                connection.close();
                logger.info("Office with id " + office.getIdOffice() + " edited.");
                return office;
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public void insert(Office office) {
        if (office != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedInsert = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource insertParameters = new MapSqlParameterSource();

                insertParameters.addValue(NAME, office.getName());
                insertParameters.addValue(ADDRESS, office.getAddress());

                namedInsert.update(INSERT_NEW_OFFICE, insertParameters);
                logger.info("New office entry successfully added.");
                connection.close();
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public void update(Office office) {
        if (office != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedUpdate = new NamedParameterJdbcTemplate(getDataSource());
                MapSqlParameterSource updateParameters = new MapSqlParameterSource();

                updateParameters.addValue(ID_OFFICE, office.getIdOffice());
                updateParameters.addValue(NAME, office.getName());
                updateParameters.addValue(ADDRESS, office.getAddress());


                logger.info(office.getName());

                namedUpdate.update(UPDATE_OFFICE_BY_ID, updateParameters);
                logger.info("Office " + office.getIdOffice() + " successfully updated.");
                connection.close();

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public boolean deleteByOfficeId(int officeId) {
        if (officeId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedDelete = new NamedParameterJdbcTemplate(getDataSource());

                int count = namedDelete.update(DELETE_OFFICE_BY_ID, new MapSqlParameterSource(ID_OFFICE, officeId));

                connection.close();
                logger.info("Office with login " + officeId + " successfully removed from DB.");
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
