package com.app.trekking.database;

/**
 * Created by lam on 6/24/18.email
 */

public class DatabaseConfig {
    public static String databaseName = "TrekkingApp";
    public static String userTableName = "User";
    public static String tourTableName = "Tour";
    public static String companyTableName = "Tour";
    public static String locationTableName = "Tour";
    public static String locationInfoTableName = "Tour";

    public static String idColumn = "_id";

    public static String nameUserColumn = "name";
    public static String emailUserColumn = "email";
    public static String passwordUserColumn = "password";
    public static String typeUserColumn = "type";

    public static String nameCompanyColumn = "name";
    public static String descriptionCompanyColumn = "description";
    public static String locationCompanyColumn = "location";

    public static String userTourColumn = "id_user";
    public static String dateTourColumn = "date";
    public static String nameTourColumn = "name";
    public static String descriptionTourColumn = "description";


    public static String latitudeLocationColumn = "latitude";
    public static String longitudeLocationColumn = "longitude";
    public static String nameLocationColumn = "name";

    public static String idTourLocationInfoColumn = "id_tour";
    public static String idLocationLocationInfoColumn = "id_location";
}
