package com.example.androidjuttu.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Link to the used resource to setup VisaHelper: https://youtu.be/A-cIZoSa5bA
/* Since we only ever want to have one active connection to the database
 * this class is a singleton. The use of singleton classes are discouraged in android
 * https://programmerr47.medium.com/singletons-in-android-63ddf972a7e7.
 */
public final class VisaHelper extends SQLiteOpenHelper {

    private static VisaHelper visaHelper = null;
    private SQLiteDatabase sqlDb;
 /*
  * Since SQLiteOpenHelper needs Context and using a static context can cause a memory leak,
  * weakreference is used instead.
  * https://stackoverflow.com/questions/16626135/singleton-with-context-in-android
  */
    private WeakReference<Context> context;
    private static String dbName, dbPath, fullDBPath;
    private static String rootDir;
    private static final int schemaVersion = 1;

    @SuppressLint("SdCardPath")
    private VisaHelper(Context context, String databaseName) {

        super(context, dbName, null, schemaVersion);
        this.context = new WeakReference<>(context);

        String rootDir = context.getFilesDir().toString() + "/";
        //rootDir = "/data/data/com.example.androidjuttu/";
        dbPath = rootDir+"db/";
        fullDBPath = dbPath + databaseName;

        createFolderIfNotExists(dbPath);

        dbName = databaseName;

        // Copy database and then connect to it
        try {
            this.CreateDatabase();
            this.OpenDatabase();
        } catch (RuntimeException e) {
            Log.i("Database", "An error occurred.");
        }

    }

    public static VisaHelper MakeServer(Context context, String databaseName) {

        if (visaHelper == null)
            visaHelper = new VisaHelper(context, databaseName);

        return visaHelper;
    }

    // Get the question that corresponds to a topic.
    // If no amount is give all the questions that have the asked topic will be returned
    public Questions GetQuestions(Question.Topic topic) {
        String query;

        if (topic == Question.Topic.ALL)
            query = "SELECT * FROM tbl_aineisto;";
        else
            query = String.format("SELECT * FROM tbl_aineisto WHERE aihe='%s';",
                    topic.toString());

        return Query(query);
    }

    public Questions GetQuestions(Question.Topic topic, int amount) {
        String query;

        if (topic == Question.Topic.ALL) {
            query = String.format(Locale.ENGLISH,
                    "SELECT * FROM tbl_aineisto LIMIT %d;", amount);
        } else {
            query = String.format(Locale.ENGLISH,
                    "SELECT * FROM tbl_aineisto WHERE aihe='%s' LIMIT %d;",
                    topic.toString(), amount);
        }
        return Query(query);
    }

    @SuppressLint("Range")
    private Questions Query(String query) {
        List<Question> questions = new ArrayList<>();
        String ans = "";

        Cursor cursor = sqlDb.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
                // the do while loop is needed to get all the questions, else we miss the first one
                do {
                    ans = cursor.getString(cursor.getColumnIndex("oikea_vastaus"));

                    /* The correct answers in the database are in numbers so we need to
                     * change them to strings
                     */
                    if (ans.equals("1")) {
                        ans = cursor.getString(cursor.getColumnIndex("vastaus1"));
                    } else if (ans.equals("2")) {
                        ans = cursor.getString(cursor.getColumnIndex("vastaus2"));
                    } else if (ans.equals("3")) {
                        ans = cursor.getString(cursor.getColumnIndex("vastaus3"));
                    }

                    questions.add(
                            new Question(
                                    cursor.getString(cursor.getColumnIndex("aihe")),
                                    cursor.getString(cursor.getColumnIndex("kysymys")),
                                    cursor.getString(cursor.getColumnIndex("vastaus1")),
                                    cursor.getString(cursor.getColumnIndex("vastaus2")),
                                    cursor.getString(cursor.getColumnIndex("vastaus3")),
                                    ans
                            ));
                } while (cursor.moveToNext());
            ans = "";
            cursor.close();
        } else {
            Toast.makeText(this.context.get(),"Kysymysten hakemisessa ilmeni ongelma.",
                    Toast.LENGTH_SHORT).show();
            // Log.i("Database", "Failed to get data from database.");
        }
        close();
        return new Questions(questions);
    }


    public boolean DatabaseExists() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(fullDBPath, null,
                                            SQLiteDatabase.OPEN_READONLY);
        } catch (RuntimeException e) {
            // Show a message to the user
            Toast.makeText(this.context.get(),"Palvelinta ei löytynyt.",
                          Toast.LENGTH_SHORT).show();
            // Log.i("Database", "Database doesn't exist.");
        }


        if (db != null)
            db.close();

        return db != null;
    }

    public void CreateDatabase() {
        if (!DatabaseExists()) {
            this.getWritableDatabase();
            CopyDatabase();
        }
    }

    private boolean createFolderIfNotExists(String path) {
        File folder = new File(path);
        if (folder.exists())
            return true;
        else
            return folder.mkdirs();
    }

    private void CopyFromAssetsToStorage(Context Context, String SourceFile, String DestinationFile) throws IOException {
        InputStream IS = Context.getAssets().open(SourceFile);
        OutputStream OS = new FileOutputStream(DestinationFile);
        CopyStream(IS, OS);
        OS.flush();
        OS.close();
        IS.close();
    }

    private void CopyStream(InputStream Input, OutputStream Output) throws IOException {
        byte[] buffer = new byte[5120];
        int length = Input.read(buffer);
        while (length > 0) {
            Output.write(buffer, 0, length);
            length = Input.read(buffer);
        }
    }

    private void CopyDatabase() {
        try {

            File file = new File(dbPath, dbName);
            Context Context = context.get();
            Log.i("Db creation", "Tring to put to " +dbPath + dbName);
            CopyFromAssetsToStorage(Context, dbName, dbPath + dbName);
            //Context context = this.context.get();
            // this inputStream opens a buffer to the database file
            /*InputStream cin = this.context.get().getAssets().open(dbName);
            // This used to write the database to /data/data/myapp/databases on the phone

            FileOutputStream fos = new FileOutputStream(file);


            int len;
            byte[] buf = new byte[1024];
            // write the database
            while ((len = cin.read(buf)) > 0)
                fos.write(buf, 0, len);
            fos.flush();
            fos.close();

            cin.close();*/

        } catch (FileNotFoundException e) {
           //throw new RuntimeException()
            e.printStackTrace();
            Log.i("Database", "Error in writing database to phone. " + e);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.i("Database", " " + e);
        }
        this.getReadableDatabase();
    }

    public void OpenDatabase() {
        this.sqlDb = SQLiteDatabase.openDatabase(dbPath + dbName, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (sqlDb != null)
            sqlDb.close();
        super.close();
    }

    @Override
    // This is used if we don't have a prepopulated database
    public void onCreate(SQLiteDatabase db) { /* Left empty on purpose */}

    @Override
    // this is used for updating the database
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) { /* Left empty on purpose */}
}
