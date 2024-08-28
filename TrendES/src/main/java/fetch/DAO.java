package fetch;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public  class DAO {
    public String tableName;
    public Connection conn;
        private static final Logger LOGGER = Logger.getLogger(DAO.class);


    public DAO(String tableName) throws SQLException, ClassNotFoundException {
        this.tableName = tableName;
        conn=getConnection();
    }
    protected void finalize() throws SQLException
    {
        conn.commit();
        conn.close();
    }
    public void commit() throws SQLException
    {
        conn.commit();
    }
    @SuppressWarnings("deprecation")
	public int create(JsonObject json)
            throws SQLException, ClassNotFoundException {
//        String tableName = getTableName();
        String identifier = "id";
        StringBuilder tableBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        tableBuilder.append("INSERT INTO ");
        tableBuilder.append(tableName);
        tableBuilder.append("(");
        valueBuilder.append("(");
        List<String> columnNames = getColumnNames();

        String delimiter = "";
        for (String columnName : columnNames) {
            if (!columnName.equals(identifier)) {
                tableBuilder.append(delimiter);
                valueBuilder.append(delimiter);
                tableBuilder.append(columnName);
                valueBuilder.append('?');
                delimiter = ",";
            }
        }
        tableBuilder.append(")");
        valueBuilder.append(")");
        tableBuilder.append(" VALUES ");
        tableBuilder.append(valueBuilder.toString());
        String SQL = tableBuilder.toString();

        PreparedStatement createStatement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        int pos = 1;
        for (String columnName : columnNames) {
            if (!columnName.equals(identifier)) {
                if(columnName.trim().equals("fetchtime"))
                {
                    JsonElement jsonElement = json.get(camelize(columnName));
                    LOGGER.info(jsonElement.getAsString());
                    java.util.Date  date1;
                    date1 = new java.util.Date(jsonElement.getAsString());        
                    LOGGER.info(date1);  
                    createStatement.setObject(pos++, date1.toInstant().atZone(ZoneId.of("GMT")).toLocalDateTime());
                }
                else if(columnName.trim().equals("processed"))
                {
                    JsonElement jsonElement = json.get(camelize(columnName));
                    createStatement.setBoolean(pos++, jsonElement.getAsBoolean());
                }
                else if(columnName.trim().equals("docid"))
                {
                    JsonElement jsonElement = json.get(camelize(columnName));
                    createStatement.setBigDecimal(pos++, jsonElement.getAsBigDecimal());
                }
                else
                {
                JsonElement jsonElement = json.get(camelize(columnName));
                createStatement.setString(pos++, jsonElement.getAsString().trim());
                }
            }
        }
        createStatement.executeUpdate();
        ResultSet rs = createStatement.getGeneratedKeys();
        int key = -1;
        if (rs.next()) {
            key = rs.getInt(1);
        }
        createStatement.close();
        return key;
    }

    public JsonObject retrieve(int id)
            throws SQLException, ClassNotFoundException {
        String idName = "id";
        String SQL = "SELECT * from " + tableName + " where " + idName + "=" + id;
        Statement retrieveStatement = conn.createStatement();
        ResultSet resultSet = retrieveStatement.executeQuery(SQL);
        JsonObject json = null;
        if (resultSet.next()) {
            json = new JsonObject();
            for (String columnName : getColumnNames()) {
                String jsonName = camelize(columnName);
                json.addProperty(jsonName, resultSet.getString(columnName));
            }
        }
        return json;
    }
    public ResultSet retrieveAll()
            throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * from " + tableName;
        Statement retrieveStatement = conn.createStatement();
        ResultSet resultSet = retrieveStatement.executeQuery(SQL);
        return resultSet;
    }
public Boolean urlIsInDb(String url)
            throws SQLException, ClassNotFoundException {
        String SQL = "SELECT * from " + tableName + " where " + "url" + "='" + url.trim() + "'";        Statement retrieveStatement = conn.createStatement();
        //LOGGER.info(SQL);
        ResultSet resultSet = retrieveStatement.executeQuery(SQL);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }
    public void update(JsonObject json)
            throws SQLException, ClassNotFoundException {
        String identifier = "id";
        List<String> columnNames = getColumnNames();

        String updateFragment = "UPDATE " + tableName +  " SET ";
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(updateFragment);

        String delimiter = "";
        for (String columnName : columnNames) {
            queryBuilder.append(delimiter);
            if (!columnName.equals(identifier)) {
                queryBuilder.append(columnName);
                queryBuilder.append("=?");
                delimiter = ",";
            }
        }

        queryBuilder.append(" where ");
        queryBuilder.append(identifier);
        queryBuilder.append("=");
        queryBuilder.append(json.get("id").getAsBigInteger());
        String SQL = queryBuilder.toString();

        PreparedStatement updateStatement = conn.prepareCall(SQL);
        int pos = 1;
        for (String columnName : columnNames) {
            if (!columnName.equals(identifier)) {
                JsonElement jsonElement = json.get(camelize(columnName));
                updateStatement.setString(pos++, jsonElement.getAsString());
            }
        }
        updateStatement.executeUpdate();
    }

    public void destroy(int id)
        throws SQLException, ClassNotFoundException
    {
        String identifierName = "id";
        String SQL ="delete from "+tableName +" where "+ identifierName +"=?";
        PreparedStatement destroyStatement = conn.prepareStatement(SQL);
        destroyStatement.setInt(1, id);
        destroyStatement.executeUpdate();
    }

    private List<String> getColumnNames()
            throws SQLException, ClassNotFoundException {
        List<String> columnNames = new ArrayList<String>();
        DatabaseMetaData md = conn.getMetaData();
        ResultSet columnsResultSet = md.getColumns(null, null, tableName, "%");
        while (columnsResultSet.next()) {
            String columnName = columnsResultSet.getString(4);
            columnNames.add(columnName);
        }
        return columnNames;
    }
    private String camelize(String SQLName) {
        String toks[] = SQLName.split("_");
        String identifier ="id";
        if (SQLName.equals(identifier)) {
            return "id";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(toks[0]);
        for (int i = 1; i < toks.length; i++) {
            sb.append(toks[i].substring(0, 1).toUpperCase());
            sb.append(toks[i].substring(1));

        }
        return sb.toString();
    }
    public Connection getConnection()
            throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost:5432/trend";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, "postgres", "tbontb");
        return conn;
    }
}