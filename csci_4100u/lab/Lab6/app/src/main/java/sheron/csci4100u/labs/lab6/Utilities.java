package sheron.csci4100u.labs.lab6;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;


class Utilities {

    // Write argument provided contacts to file
    static void writeToFile(String file, ArrayList<Contact> contacts, Activity activity){
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(activity.openFileOutput(
                            file,
                            Context.MODE_PRIVATE
                    ))
            );

            for (int i = 0; i < contacts.size(); i++) {
                writer.write(i + " " + contacts.get(i).getContactLine());
                writer.newLine();
            }

            writer.close();
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }


    // Reads contacts from file and returns an array list of contacts
    static ArrayList<Contact> readFromFile(String file, Activity activity){
        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            InputStream inputStream = activity.openFileInput(file);

            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream)
                );

                String line;
                Scanner scan;

                while((line = reader.readLine()) != null) {
                    scan = new Scanner(line);
                    contacts.add(new Contact(scan.nextInt(),
                                             scan.next(),
                                             scan.next(),
                                             scan.next()));
                }

                reader.close();
            }
        } catch(FileNotFoundException fnf) {
            Log.d(activity.getLocalClassName(), file + " not found");
        } catch (IOException io) {
            io.printStackTrace();
        }

        return contacts;
    }


    // Toast wrapper
    static void toast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}

