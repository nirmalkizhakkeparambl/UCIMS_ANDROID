package com.gisfy.unauthorizedconstructions.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{
    public static final String CONTACTS_TABLE_NAME = "Construction";

    //constructor
    public SQLiteHelper(Context context,
                        String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version){

        super(context, name, factory, version);

    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    public void insertData(String draftsman,String owner,String fathername,String address,String district,String municipality,
                           String surveyno,String village,String doorno,String locality,String streetname,String sitearea,String natureofconst,
                           String approvedplan,
                           String parking1,String parking2,String floor1,String floor2,String front,String front2,String right,String right2,String left,String left2,String back,String back2,String
                                   landuse1,String landuse2,String location,String Imagepath,String uploadid,String Imagepath2,String uploadid2,String Imagepath3,String uploadid3,String Imagepath4,String uploadid4,String phoneno,String ConstType,String ConstFloors,String FloorsonGround,String Nooffloors,String Existingfloors,String ConvertingFloors,
                           String roadwidening1,String roadwidening2,String area1,String area2,
                           String polygon,String videopath,String videoname,String totaluc,
                           String spin23,String date,String stiltpark,String pliarea,String builtIt,String age,
                           String mandal, byte checkBoxes[], String constructionStage){
        SQLiteDatabase database = getWritableDatabase();
        //query to insert record in database table
        String sql = "INSERT INTO Construction VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, draftsman);
        statement.bindString(2, owner);
        statement.bindString(3, fathername);
        statement.bindString(4, address);
        statement.bindString(5, district);
        statement.bindString(6, municipality);
        statement.bindString(7, surveyno);
        statement.bindString(8, village);
        statement.bindString(9, doorno);
        statement.bindString(10, locality);
        statement.bindString(11, streetname);
        statement.bindString(12, sitearea);
        statement.bindString(13, natureofconst);
        statement.bindString(14, approvedplan);
        statement.bindString(15, parking1);
        statement.bindString(16, parking2);
        statement.bindString(17, floor1);
        statement.bindString(18, floor2);
        statement.bindString(19, front);
        statement.bindString(20, front2);
        statement.bindString(21, right);
        statement.bindString(22, right2);
        statement.bindString(23, left);
        statement.bindString(24, left2);
        statement.bindString(25, back);
        statement.bindString(26, back2);
        statement.bindString(27, landuse1);
        statement.bindString(28, landuse2);
        statement.bindString(29, location);
        statement.bindString(30, Imagepath);
        statement.bindString(31, uploadid);
        statement.bindString(32, Imagepath2);
        statement.bindString(33, uploadid2);
        statement.bindString(34, Imagepath3);
        statement.bindString(35, uploadid3);
        statement.bindString(36, Imagepath4);
        statement.bindString(37, uploadid4);
        statement.bindString(38, phoneno);
        statement.bindString(39, ConstType);
        statement.bindString(40, ConstFloors);
        statement.bindString(41, FloorsonGround);
        statement.bindString(42, Nooffloors);
        statement.bindString(43, Existingfloors);
        statement.bindString(44, ConvertingFloors);
        statement.bindString(45, roadwidening1);
        statement.bindString(46, roadwidening2);
        statement.bindString(47, area1);
        statement.bindString(48, area2);
        statement.bindString(49, polygon);
        statement.bindString(50, videopath);
        statement.bindString(51, videoname);
        statement.bindString(52, totaluc);
        statement.bindString(53, spin23);
        statement.bindString(54, date);
        statement.bindString(55, stiltpark);
        statement.bindString(56, pliarea);
        statement.bindString(68, builtIt);
        statement.bindString(57, age);
        statement.bindString(58,mandal);
        statement.bindString(59, String.valueOf(checkBoxes[0]));
        statement.bindString(60, String.valueOf(checkBoxes[1]));
        statement.bindString(61, String.valueOf(checkBoxes[2]));
        statement.bindString(62, String.valueOf(checkBoxes[3]));
        statement.bindString(63, String.valueOf(checkBoxes[4]));
        statement.bindString(64, String.valueOf(checkBoxes[5]));
        statement.bindString(65, String.valueOf(checkBoxes[6]));
        statement.bindString(66, String.valueOf(checkBoxes[7]));
        statement.bindString(67,constructionStage);
        statement.executeInsert();
    }

    //updateData
    public void updateData(String draftsman,
                           String owner,
                           String fathername,
                           String address,
                           String district,
                           String municipality,
                           String surveyno,
                           String village,
                           String doorno,
                           String locality,
                           String streetname,
                           String sitearea,
                           String natureofconst,
                           String approvedplan,
                           String parking1,
                           String parking2,
                           String floor1,
                           String floor2,
                           String front,
                           String front2,
                           String right,
                           String right2,
                           String left,
                           String left2,
                           String back,
                           String back2,
                           String landuse1,
                           String landuse2,
                           String location,
                           String phoneno,
                           String imagepath,
                           String Imagepath2,
                           String Imagepath3,
                           String Imagepath4,
                           String ConstType,
                           String ConstFloors,
                           String FloorsonGround,
                           String Nooffloors,
                           String Existingfloors,
                           String ConvertingFloors,
                           String roadwidening1,
                           String roadwidening2,
                           String area1,
                           String area2,
                           String videopath,
                           String totaluc,
                           String spin23,
                           String date,
                           String stiltpark,
                           String plintharea,
                           String builtIt,
                           String age,
                           String polygon,
                           String mandal,
                           byte[] checkBoxes,
                           String constructionStage,
                           int id
                           )
    {
        SQLiteDatabase database = getWritableDatabase();
        //query to update record

        String sql="UPDATE Construction SET Draftsman =?,ownername =?,fathername =?,address =?," +
                "district =?,municipality =?, surveyno =?,village =?,doorno =?,locality =?," +
                "streetname =?,sitearea =?,natureofconst =?,approvedplan =?,parking =?,parking2 " +
                "=?,floor =?,floor2 =?,front =?,front2 =?,right1 =?,right2 =?,left1 =?,left2 =?," +
                "back =?,back2 =?,landuse1 =?,landuse2 =?,location =?,phoneno=?, Imagepath=?," +
                "Imagepath2=?,Imagepath3=?,Imagepath4=?,ConstructionType=?,ConstructionFloors=?," +
                "FloorsAsOnGround=?,NoOfFloors=?,ExistingFloors=?,ConvertingFloors=?, " +
                "roadwidening1=?, roadwidening2=?, area1=?, area2=?,videopath=?,totaluc=?," +
                "spinner23=?,date=?,StiltParking=?,PlinthArea=?,builiIt =?,age=?,polygon=?,mandal=?, " +
                "NoDeviation=?,AllDeviation=?,SetbacksDeviation=?,AdditionalFloorsDeviation=?, " +
                "RoadWindingDeviation=?,BuildupDeviation=?,ParkingDeviation=?,LandArea=?," +
                "ConstructionStage=? "+
                " WHERE id=?";

//        ContentValues contentValues = new ContentValues();
//        contentValues.put("Draftsman",draftsman);
//        contentValues.put("ownername",owner);
//        contentValues.put("fathername",fathername);
//        contentValues.put("address",address);
//        contentValues.put("district",district);
//        contentValues.put("municipality",municipality);
//        contentValues.put("surveyno",surveyno);
//        contentValues.put("village",village);
//        contentValues.put("doorno",doorno);
//        contentValues.put("locality",locality);
//        contentValues.put("streetname",streetname);
//        contentValues.put("sitearea",sitearea);
//        contentValues.put("natureofconst",natureofconst);
//        contentValues.put("approvedplan",approvedplan);
//        contentValues.put("parking",parking1);
//        contentValues.put("parking2",parking2);
//        contentValues.put("floor",floor1);
//        contentValues.put("floor2",floor2);
//        contentValues.put("front",front);
//        contentValues.put("front2",front2);
//        contentValues.put("right1",right);
//        contentValues.put("right2",right2);
//        contentValues.put("left1",left);
//        contentValues.put("left2",left2);
//        contentValues.put("back",back);
//        contentValues.put("back2",back2);
//        contentValues.put("landuse1",landuse1);
//        contentValues.put("landuse2",landuse2);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);
//        contentValues.put("",);


        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1 , draftsman);
        statement.bindString(2 , owner);
        statement.bindString(3 , fathername);
        statement.bindString(4 , address);
        statement.bindString(5 , district);
        statement.bindString(6 , municipality);
        statement.bindString(7 , surveyno);
        statement.bindString(8 , village);
        statement.bindString(9 , doorno);
        statement.bindString(10, locality);
        statement.bindString(11, streetname);
        statement.bindString(12, sitearea);
        statement.bindString(13, natureofconst);
        statement.bindString(14, approvedplan);
        statement.bindString(15, parking1);
        statement.bindString(16, parking2);
        statement.bindString(17, floor1);
        statement.bindString(18, floor2);
        statement.bindString(19, front);
        statement.bindString(20, front2);
        statement.bindString(21, right);
        statement.bindString(22, right2);
        statement.bindString(23, left);
        statement.bindString(24, left2);
        statement.bindString(25, back);
        statement.bindString(26, back2);
        statement.bindString(27, landuse1);
        statement.bindString(28, landuse2);
        statement.bindString(29, location);
        statement.bindString(30, phoneno);
        statement.bindString(31, imagepath);
        statement.bindString(32, Imagepath2);
        statement.bindString(33, Imagepath3);
        statement.bindString(34, Imagepath4);
        statement.bindString(35, ConstType);
        statement.bindString(36, ConstFloors);
        statement.bindString(37, FloorsonGround);
        statement.bindString(38, Nooffloors);
        statement.bindString(39, Existingfloors);
        statement.bindString(40, ConvertingFloors);
        statement.bindString(41, roadwidening1);
        statement.bindString(42, roadwidening2);
        statement.bindString(43, area1);
        statement.bindString(44, area2);
        statement.bindString(45, videopath);
        statement.bindString(46, totaluc);
        statement.bindString(47, spin23);
        statement.bindString(48, date);
        statement.bindString(49, stiltpark);
        statement.bindString(50, plintharea);
        statement.bindString(64, builtIt);
        statement.bindString(51, age);
        statement.bindString(52,polygon);
        statement.bindString(53, mandal);
        statement.bindString(54,String.valueOf(checkBoxes[0]));
        statement.bindString(55, String.valueOf(checkBoxes[1]));
        statement.bindString(56, String.valueOf(checkBoxes[2]));
        statement.bindString(57, String.valueOf(checkBoxes[3]));
        statement.bindString(58, String.valueOf(checkBoxes[4]));
        statement.bindString(59, String.valueOf(checkBoxes[5]));
        statement.bindString(60, String.valueOf(checkBoxes[6]));
        statement.bindString(61, String.valueOf(checkBoxes[7]));
        statement.bindString(62, constructionStage);
        statement.bindDouble(63, id);
        statement.execute();
        Log.d("DEBUGGING floor",floor1+"-"+floor2);
        Log.d("DEBUGGING",statement.toString());
        database.close();
    }

    //deleteData
    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();
        //query to delete record using id
        String sql = "DELETE FROM Construction WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);
        statement.execute();
        database.close();
    }
    public void deleteTable(){
        SQLiteDatabase database = getWritableDatabase();
        //query to delete record using id
        String sql = "DELETE FROM Construction";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.execute();
        database.close();
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion , int newversion) {


    }

    public void createtable(){
        SQLiteDatabase database = getWritableDatabase();
        String query="CREATE TABLE IF NOT EXISTS Construction(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Draftsman VARCHAR," +
                "ownername VARCHAR," +
                "fathername VARCHAR," +
                "address VARCHAR," +
                "district VARCHAR," +
                "municipality VARCHAR," +
                " surveyno VARCHAR," +
                "village VARCHAR," +
                "doorno VARCHAR," +
                "locality VARCHAR," +
                "streetname VARCHAR," +
                "sitearea VARCHAR," +
                "natureofconst VARCHAR," +
                "approvedplan VARCHAR," +
                "parking VARCHAR," +
                "parking2 VARCHAR," +
                "floor VARCHAR," +
                "floor2 VARCHAR," +
                "front VARCHAR," +
                "front2 VARCHAR," +
                "right1 VARCHAR," +
                "right2 VARCHAR," +
                "left1 VARCHAR," +
                "left2 VARCHAR," +
                "back VARCHAR," +
                "back2 VARCHAR," +
                "landuse1 VARCHAR," +
                "landuse2 VARCHAR," +
                "location VARCHAR," +
                "Imagepath VARCHAR," +
                "uploadid VARCHAR," +
                "Imagepath2 VARCHAR," +
                "uploadid2 VARCHAR," +
                "Imagepath3 VARCHAR," +
                "uploadid3 VARCHAR," +
                "Imagepath4 VARCHAR," +
                "uploadid4 VARCHAR ," +
                "phoneno VARCHAR," +
                "ConstructionType VARCHAR," +
                "ConstructionFloors VARCHAR," +
                "FloorsAsOnGround VARCHAR," +
                "NoOfFloors VARCHAR," +
                "ExistingFloors VARCHAR," +
                "ConvertingFloors VARCHAR," +
                "roadwidening1 VARCHAR, " +
                "roadwidening2 VARCHAR, " +
                "area1 VARCHAR," +
                " area2 VARCHAR," +
                "polygon VARCHAR," +
                "videopath VARCHAR," +
                "videoname VARCHAR," +
                "totaluc VARCHAR," +
                "spinner23 VARCHAR," +
                "date VARCHAR," +
                "StiltParking VARCHAR," +
                "PlinthArea VARCHAR," +
                "BuiliIt VARCHAR,"+
                "age VARCHAR,"+
                "mandal VARCHAR," +
                "NoDeviation INTEGER,"+
                "AllDeviation INTEGER,"+
                "SetbacksDeviation INTEGER,"+
                "AdditionalFloorsDeviation INTEGER,"+
                "RoadWindingDeviation INTEGER,"+
                "BuildupDeviation INTEGER,"+
                "ParkingDeviation INTEGER,"+
                "LandArea INTEGER,"+
                "ConstructionStage VARCHAR"+
                ")";
        database.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS Mandals(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MandalName VARCHAR)";

        database.execSQL(query);

        query="CREATE TABLE IF NOT EXISTS Villages(MandalId INTEGER, VillageName VARCHAR, FOREIGN" +
                " KEY(MandalId) REFERENCES Mandals(id))";

        database.execSQL(query);

    }

    public long insertMandal(String mandal){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MandalName",mandal);
        return database.insert("Mandals",null,cv);
    }

    public boolean insertVillage(long mandalId, String villageName){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MandalId",mandalId);
        cv.put("VillageName",villageName);
        long rowId =database.insert("Villages",null,cv);

        if(rowId==-1)
            return false;
        else
            return true;
    }

    public Cursor getMandals(){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM Mandals",null);
    }

    public Cursor getVillages(int mandalId){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM Villages WHERE MandalId="+mandalId,null);
    }

    public void deleteMandalsAndVillages(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("Mandals",null,null);
        database.delete("Villages",null,null);
    }

}