package InventoryModel;

import DataBaseManager.DBManager;
import DataBaseManager.LinkedCollection;
import DataBaseManager.RowMirror;

public class ColorsTable extends LinkedCollection<Color> {
    public ColorsTable(DBManager manager) throws Exception {
        super(manager, "color_producto");
        fill();
    }

    @Override
    public boolean add(RowMirror row) throws Exception {
        Color color = new Color(row);
        return super.add(color);
    }

    @Override
    public boolean remove(RowMirror row) throws Exception {
        Color color = this.getWithPK(row.get_pk_val());
        return super.remove(color);
    }

    public Color getColor(String color){
        for(Color colorO : this.getCollection()){
            if(colorO.getColor().equals(color))
                return colorO;
        }
        return null;
    }
}
