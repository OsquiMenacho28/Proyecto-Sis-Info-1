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
        return super.add(new Client(row));
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Client client = this.getWithPK(row.get_pk_val());
        return super.remove(client);
    }
}
