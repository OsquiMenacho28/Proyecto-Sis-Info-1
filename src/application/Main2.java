package application;

import java.util.ArrayList;
import DataBase.DBManager;
import DataBase.RowMirror;
import DataBase.TableMirror;
import DataBase.Value;

public class Main2{
	private static String DB_URL = "jdbc:mysql://localhost:2808/ferreteria_dimaco_database";
	private static String USER = "root";
	private static String PASS = "osquimenacho28";
	
	 public static void main(String[] args) throws Exception {
		DBManager DB = new DBManager(DB_URL, USER, PASS);
		TableMirror tm = DB.getTable("cliente");
		ArrayList <Value> values = new ArrayList<>();
		values.add(Value.create(1));
		values.add(Value.create("Oscar"));
		values.add(Value.create(10902717));
		RowMirror rm = new RowMirror(tm.getRelVar(), values);
		tm.add(rm);
		System.out.println(tm);
		//tm.remove(rm);
		//System.out.println(tm);
		/*tm.remove(tm.getRow(0));
		System.out.println(tm);
		RowMirror rm2 = tm.getRow(0);
		rm2.edit("NIT", 59873674);
		System.out.println(tm);*/
	}
}