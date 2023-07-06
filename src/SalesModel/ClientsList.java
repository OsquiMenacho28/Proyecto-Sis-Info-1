package SalesModel;

import DataBaseManager.DBManager;
import DataBaseManager.LinkedCollection;
import DataBaseManager.RelVar;
import DataBaseManager.RowMirror;

public class ClientsList extends LinkedCollection<Client>{

    public static RelVar clientRV;
    public ClientsList(DBManager manager) throws Exception {
        super(manager, "cliente");
        clientRV = this.getRelVar();
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        return false;
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        return false;
    }
}
