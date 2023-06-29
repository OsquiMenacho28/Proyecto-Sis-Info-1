package DataBaseManager;

public abstract class LinkedCollection extends TableMirror{
    public LinkedCollection(DBManager manager, String name, RelVar relvar) throws Exception {
        super(manager, name, relvar);
    }
    public LinkedCollection(TableMirror table) throws Exception {
        super(table.getManager(), table.getName() , table.getRelVar());
        table.getManager().putTable(table.getName(), this);
    }
}
