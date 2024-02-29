package typeobjet.coin.metier;

public class Coin extends Serie {
    private Double faceValue;
    private Integer year;
    private Boolean owned;


    public Coin(Double faceValue, Integer year) {
        this.faceValue = faceValue;
        this.year = year;
        this.owned = false;
    }

    public Coin(Double faceValue, Integer year, Boolean owned) {
        this.faceValue = faceValue;
        this.year = year;
        this.owned = owned;
    }

    public Boolean getOwned() {
        return owned;
    }

    public void setOwned(Boolean owned) {
        this.owned = owned;
    }

    public Double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Double faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void printCoinInfo() {
        System.out.println(this.year + " " + this.faceValue + "€ " + super.getCountryName() + " " + owned);
    }
}
