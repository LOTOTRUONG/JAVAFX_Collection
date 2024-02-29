package typeobjet.coin.metier;

public class Country extends Collection {
    static Integer[] seriesYear = {1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025};
    private String countryName;
    private Integer startingYear;
    private Serie[] serie = new Serie[24];

    public Country() {
    }

    public Country(String countryName) {
        this.countryName = countryName;
        for (int i = 0; i < seriesYear.length; i++) {
            this.serie[i] = new Serie(seriesYear[i]);
        }
    }

    public Serie[] getSerie() {
        return serie;
    }

    public void setSerie(Serie[] serie) {
        this.serie = serie;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void createCountryMatrix() {

    }

}
