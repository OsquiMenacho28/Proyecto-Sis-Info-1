package ElectronicInvoice;

public class HardwareStore {
    private static final int NIT = 1098448019;
    private static final String Name = "FERRETERÍA DIMACO - DISTRIBUIDORA DE MATERIALES DE CONSTRUCCIÓN";
    private static final String Municipality = "LA PAZ";
    private static final int Telephone = 70129668;
    private static final int branchCode = 0;
    private static final String Address = "CALLE PICADA CHACO NRO. 876 FINAL CEMENTERIO GENERAL, ZONA BAJO TEJAR";
    private static final int salePointCode = 0;
    private static final int economicActivityCode = 461090;
    private static final int SINProductCode = 61265;

    public static int getNIT() {
        return NIT;
    }

    public static String getName() {
        return Name;
    }

    public static String getMunicipality() {
        return Municipality;
    }

    public static int getTelephone() {
        return Telephone;
    }

    public static int getBranchCode() {
        return branchCode;
    }

    public static String getAddress() {
        return Address;
    }

    public static int getSalePointCode() {
        return salePointCode;
    }

    public static int getEconomicActivityCode() {
        return economicActivityCode;
    }

    public static int getSINProductCode() {
        return SINProductCode;
    }
}
