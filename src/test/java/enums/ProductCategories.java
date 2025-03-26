package enums;

public enum ProductCategories {
    SHOES("Shoes"),
    CLOTHING("Clothing"),
    BAGS("Bags"),
    ACCESSORIES("Accessories"),
    WOMEN("Women's"),
    MEN("Men's"),
    KIDS("Kids'"),
    CLEARANCE("Clearance"),
    BRANDS("Brands");

    private final String xpathValue;

    ProductCategories(String type) {
        this.xpathValue = type;
    }

    public String getXpathValue() {
        return xpathValue;
    }
}
