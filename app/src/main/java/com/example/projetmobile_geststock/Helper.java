package com.example.projetmobile_geststock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public Helper(@Nullable Context context) {
        super(context, "productManager", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // exécute les requetes d'une maniére brute ' ne retourne pas de résultat'
        db.execSQL("CREATE TABLE produit(_id INTEGER PRIMARY KEY, nom TEXT, prix REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS produit");
        onCreate(db);
    }
    public void insertProduct(Produit p){
        // on va utiliser une requete normale
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("nom", p.getNom());
        cv.put("prix", p.getPrix());
        db.insert("produit",null,cv);
        // question de protection
        db.close();
    }

    public void updateProduct(Produit p){
        // on va utiliser une requete normale
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("nom", p.getNom());
        cv.put("prix", p.getPrix());
        db.update("produit",cv, "_id=?",new String[] {String.valueOf(p.getId())});
        // question de protection
        db.close();
    }
    public void deleteProduct(int id){
        // on va utiliser une requete normale
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("produit", "_id=?",new String[]{String.valueOf(id)});
        // question de protection
        db.close();

    }

    public Cursor getAllProducts(){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM produit",null);
        return c;
    }

    public Produit getProductById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("produit", new String[]{"_id", "nom", "prix"}, "_id=?"
                , new String[]{String.valueOf(id)},null,null,null);
        c.moveToFirst();
        Produit p = new Produit(c.getInt(0),c.getString(1),c.getDouble(2));
        return p;
    }
    public Cursor searchProducts(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM produit WHERE nom LIKE ?", new String[]{"%" + query + "%"});
        return c;
    }
    public Cursor sortProductsById() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("produit", new String[]{"_id", "nom", "prix"}, null, null, null, null, "_id");
    }

    public Cursor sortProductsByName() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("produit", new String[]{"_id", "nom", "prix"}, null, null, null, null, "nom");
    }

    public Cursor sortProductsByPrice() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("produit", new String[]{"_id", "nom", "prix"}, null, null, null, null, "prix");
    }
}