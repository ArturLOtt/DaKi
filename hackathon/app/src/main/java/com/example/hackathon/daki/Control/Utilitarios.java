package com.example.hackathon.daki.Control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hackathon.daki.Main;
import com.example.hackathon.daki.R;
import com.example.hackathon.daki.View.AboutActivity;
import com.example.hackathon.daki.View.CardList.CriarAnuncioActivity;
import com.example.hackathon.daki.View.CardList.ListaCardsAprovadosActivity;
import com.example.hackathon.daki.View.CardList.ListaCardsPendentesActivity;
import com.example.hackathon.daki.View.CardList.StartAnuncioActivity;
import com.example.hackathon.daki.View.CardList.VisualizarAprovadosActivity;
import com.example.hackathon.daki.View.LoginActivity;
import com.example.hackathon.daki.View.RegistroActivity;
import com.example.hackathon.daki.View.WelcomeActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Vaharamus on 06/03/2018.
 */

public class Utilitarios {

    private String token;




// Carrega a Foto do arquivo em um Bitmap
    public static Bitmap setPic(int width, int height, File fotoUrl, Context context) throws IOException {
        // Get the dimensions of the View
        int targetW = width;
        int targetH = height;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        Rect rect = new Rect(-1, -1, -1, -1);

        InputStream is = context.getContentResolver().openInputStream(Uri.fromFile(fotoUrl));
        BitmapFactory.decodeStream(is, rect, bmOptions);
        is.close();

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = 0;
        if (targetH != 0 && targetW != 0)
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(fotoUrl.toString(), bmOptions);

        return bitmap;
    }



    /*
         * Converte um Base64 byte[] em Bitmap
         */
    public static Bitmap bitmapFromBase64(byte[] dados) {
        byte[] fotoArray = Base64.decode(dados, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoArray, 0, fotoArray.length);
        return bitmap;
    }


    /*
     * Converte um Bitmap em Base64 byte[]
     */
    public static byte[] bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encode(byteArray, Base64.DEFAULT);
    }

    /*
     * Extrai o Bitmap de um ImageView
     */
    public static Bitmap bitmapFromImageView(ImageView view) {
        Drawable drawable = view.getDrawable();
        if (drawable != null) {
            if (drawable instanceof ColorDrawable) {
                return convertToBitmap(drawable, view.getWidth(), view.getHeight());
            } else if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        return null;
    }

    /*
     * Convert um ColorDrawable em Bitmap
     */
    private static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }


    /*
     * Cria um Bitmat com um contorno Circular
     */
    public static Bitmap toCircularBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /*
     * Cria um Bitmat com um contorno Circular
     */
    public static Bitmap circularBitmapAndText(int cor, int width, int height, String txt) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(cor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(100);
        canvas.drawText(txt, width / 2, height / 2 + 30, paint);
        return output;
    }



//>>>  galeria de foto


//metodo de troca de telas do menu
public static void getSwitchcase(MenuItem item, Activity activity){



        switch (item.getItemId()) {
//
//            case R.id.menuRegistro:
//                Intent registro = new Intent(activity, RegistroActivity.class);
//                activity.startActivity(registro);
//                break;

            case R.id.menuListaAprovados:
                Intent listaApr = new Intent(activity, ListaCardsAprovadosActivity.class);
                activity.startActivity(listaApr);
                break;

            case R.id.menuListaPendentes:
                Intent listaPen = new Intent(activity, ListaCardsPendentesActivity.class);
                activity.startActivity(listaPen);
                break;

            case R.id.menuCriarAnuncio:
                Intent criarAnuncio = new Intent(activity, CriarAnuncioActivity.class);
                activity.startActivity(criarAnuncio);
                break;
//
//            case R.id.menuVisualizarAnuncio:
//                Intent visualizarAnuncio = new Intent(activity, VisualizarAprovadosActivity.class);
//                activity.startActivity(visualizarAnuncio);
//                break;

            case R.id.menuAbout:
                Intent about = new Intent(activity, AboutActivity.class);
                activity.startActivity(about);
                break;
//
//            case R.id.settings:
//                Toast.makeText((Context) activity, "Settings", Toast.LENGTH_SHORT).show();
//                break;

            case R.id.menuLogout:

                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Main.context);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token");
                editor.commit();
                activity.finish();

                Intent logout = new Intent(activity, LoginActivity.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(logout);
                break;


            default:
        }

}



}
