package typeobjet.coin.metier;

import main.collection.CollectionApplication;

import java.io.*;

public class Collection {
    static String[] countries = {"Andorra", "Austria", "Belgium", "Cyprus", "Estonia", "Finland", "France", "Germany",
            "Greece", "Ireland", "Italy", "Latvia", "Lithuania", "Luxembourg", "Malta", "Monaco", "Netherlands",
            "Portugal", "San Marino", "Slovakia", "Slovenia", "Spain", "Vatican City"};
    private String collectionName;
    private Country[] countryArray = new Country[23];

    public Collection() {

    }

    public Collection(String collectionName, boolean read) {
        this.collectionName = collectionName;
        for (int i = 0; i < countries.length; i++) {
            countryArray[i] = new Country(countries[i]);
        }
        readCSVFile();
    }

    // Main constructor
    public Collection(String collectionName) {
        this.collectionName = collectionName;
        //Each collection is made of the 23 countries listed on the countries array
        for (int i = 0; i < countries.length; i++) {
            countryArray[i] = new Country(countries[i]);
        }

        //Calls createFolder method
        createFolder(collectionName);

        //Creates a csv file for each country which store the owned attribute of each coin
        createCSV(true);
        //  PrimaryController.collectionArrayList.add(this);
        System.out.println("Collection constructed successfully");
    }

    //Creates the folder in which the collection csv files will be allocated
    public void createFolder(String collectionName) {
        File file = new File(CollectionApplication.projectRootPath + "\\src\\main\\resources\\collections\\" + collectionName);
        try {
            if (file.mkdir()) {
                System.out.println("Directory Created");
            } else {
                System.out.println("Directory is not created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Read the collection csvfiles and set all of the coins owned attribute
    public void readCSVFile() {
        for (int n = 0; n < Collection.countries.length; n++) {
            String filepath = "src\\main\\resources\\collections\\" + this.getCollectionName() + "\\" + Collection.countries[n] + ".csv";
            BufferedReader reader = null;
            String line = "";
            try {
                reader = new BufferedReader(new FileReader(filepath));
                int j = 0;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    for (int i = 1; i < values.length; i++) { //i Starts in 1 because the 0 column represents the serie year
                        this.countryArray[n].getSerie()[j].getCoinArray()[i - 1].setOwned(Boolean.parseBoolean(values[i]));
                        // System.out.println(values[i]+",");
                    }
                    j++;
                    System.out.println("\n");
                }
                // System.out.println("Successful read");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    // System.out.println("CSV File closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //Create CSV file for each country. Mode True means each owned attribute is set false. Mode False is used to save the file at is is.
    public void createCSV(Boolean mode) {
        for (int i = 0; i < Collection.countries.length; i++) {
            File countryFile = new File(CollectionApplication.projectRootPath + "\\src\\main\\resources\\collections\\" + this.getCollectionName() + "\\"
                    + countries[i] + ".csv");
            try {
                FileWriter writer = new FileWriter(countryFile);
                for (int j = 0; j < Country.seriesYear.length; j++) {
                    if (mode) {
                        writer.write(Country.seriesYear[j] + ",false,false,false,false,false,false,false,false\n");
                    } else {
                        writer.write(Integer.toString(Country.seriesYear[j]));
                        for (int n = 0; n < Serie.coins.length; n++) {
                            writer.write("," + this.getCountryArray()[i].getSerie()[j].getCoinArray()[n].getOwned());
                        }
                        writer.write("\n");
                    }
                }
                writer.close();
            } catch (Exception e2) {
                System.out.println("Failed to initialize countries CSV");
            }
        }
    }

    public Country[] getCountryArray() {
        return countryArray;
    }

    public void setCountryArray(Country[] countryArray) {
        this.countryArray = countryArray;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
