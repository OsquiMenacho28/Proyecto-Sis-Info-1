package SalesModel;

import DataBaseManager.DBManager;
import DataBaseManager.LinkedCollection;
import DataBaseManager.RelVar;
import DataBaseManager.RowMirror;

public class SalesList extends LinkedCollection<Sale> {

    public static RelVar saleRV;
    public SalesList(DBManager manager) throws Exception {
        super(manager, "venta");
        saleRV = this.table.getRelVar();
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        return super.add(new Sale(row));
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Sale sale = this.getWithPK(row.get_pk_val());
        if(sale != null){
            return super.remove(sale);
        }
        return false;
    }
}
