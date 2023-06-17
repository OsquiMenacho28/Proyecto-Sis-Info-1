package DataBaseManager;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DBManager {
	private String DB_URL = "jdbc:mysql://localhost:2808/ferreteria_dimaco_database";
	private String USER = "root";
	private String PASS = "osquimenacho28";
	
	private ArrayList <String> tables = new ArrayList<>();
	private HashMap <String, RelVar> table_struct = new HashMap<>();
	private HashMap <String, TableMirror> table_data = new HashMap<>();
	
	Connection connection = null;
	
	public DBManager(String DB_URL, String USER, String PASS) throws Exception {
		this.DB_URL = DB_URL;
		this.USER = USER;
		this.PASS = PASS;
		connect();
		retrieveTables();
		retrieveDatabase();
	}

	public Connection getConexionMYSQL() throws SQLException {
		Connection conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
	}
	
	public void connect() throws SQLException {
		try {
			connection = getConexionMYSQL();
			System.out.println("Successfully connected to MySQL DataBase Ferreteria_DIMACO_DataBase.");
			//connection.close();
		} catch (SQLException e1) {
			System.err.println("An error occurred while connecting MySQL DataBase: " + e1);
			e1.printStackTrace();
		}
	}
	
	public void getSchema() {
		try (Connection connection = getConexionMYSQL()) {
		    String currentSchema = connection.getCatalog();
		    System.out.println("Current schema: " + currentSchema);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	public void disconnect() {
		connection = null;
        try {
        // Closing the connection
          connection.close();
          if (connection.isClosed()) 
            System.out.println("DataBase connection closed.");
        } catch (Exception e) {
          e.printStackTrace();
        }
	}
	
	public void retrieveTables() throws Exception {
		//ResultSet rs = run_query("select table_name from information_schema.tables where table_schema = 'ferreteria_dimaco_database'");
		String tableName = null;

	    try (Connection connection = getConexionMYSQL()) {
	        DatabaseMetaData metaData = connection.getMetaData();
	        String[] types = {"TABLE"};
	        String catalog = connection.getCatalog();
	        String schema = connection.getSchema();
	        ResultSet table = metaData.getTables(catalog, schema, null, types);

	        while (table.next()) {
	            tableName = table.getString("TABLE_NAME");
	            tables.add(tableName);
	            retrieveRelvar(tableName);
	        }

	        //for (String tabla : tables) {
	            //System.out.println("Table name: " + tabla);
	        //}
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public RelVar retrieveRelvar(String table_name) throws Exception {
		//ResultSet rs = run_query("select * from information_schema.columns where table_name = '" + table_name + "'");
		String columnName = null;
		String columnDataType = null;
		ArrayList <String> columnNames = new ArrayList<>();
		ArrayList <DataType> columnsDataType = new ArrayList<>();
		
		try (Connection connection = getConexionMYSQL()) {
			DatabaseMetaData metaData = connection.getMetaData();
			String schema = connection.getSchema();
			ResultSet columns = metaData.getColumns(null, schema, table_name, null);
			
			while (columns.next()) {
				columnName = columns.getString("COLUMN_NAME");
				columnNames.add(columnName);
				columnDataType = columns.getString("TYPE_NAME");
				if (columnDataType.equals("FLOAT")) {
					columnsDataType.add(DataType.FLOAT_TYPE);
				}
				else {
					if (columnDataType.equals("INT")) {
						columnsDataType.add(DataType.INTEGER_TYPE);
					}
					else {
						if (columnDataType.equals("VARCHAR") || columnDataType.equals("CHAR")) {
							columnsDataType.add(DataType.STRING_TYPE);
						}
						else {
							if (columnDataType.equals("DATE")) {
								columnsDataType.add(DataType.DATE_TYPE);
							}
							else {
								columnsDataType.add(DataType.OTHER_DATATYPE);
							}
						}
					}
				}
				
				table_struct.put(table_name, new RelVar(columnNames, columnsDataType));
			}
			
			//for (Entry <String, RelVar> rv : table_struct.entrySet()) {
				//System.out.println("Attribute: " + rv.getKey() + ", Data type: " + rv.getValue());
			//}
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		return table_struct.get(table_name);
	}

	public void retrieveDatabase() throws Exception {
		try (Connection connection = getConexionMYSQL()) {
			for (String table : tables) {
				table_data.put(table, new TableMirror(this, table, table_struct.get(table)));
			}
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public ArrayList<String> getTableNames(){
		return this.tables;
	}
	
	public TableMirror getTable(String table_name) {
		return table_data.get(table_name);
	}
	
	public ResultSet runQuery(String query) throws SQLException {
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(query);
		return rs;
	}
	
	public void updateQuery(String query) throws SQLException {
		Statement s = connection.createStatement();
		s.executeUpdate(query);
	}
}