package com.moronlu18.intentdataview;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static int PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private boolean request_call_phone_acepted = false;
    private final static int PERMISSIONS_REQUEST_CAMERA = 2;
    private boolean request_camera_acepted = false;
    private final static int PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private boolean request_read_contacts = false;
    private static final int PERMISSION_REQUEST_CODE = 300;


    private Spinner spinner;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spTypeIntent);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.intents, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //Solicitamos los permisos
        requestPermissions();


    }

    /**
     * Método que pide TODOS los pemisos
     */

    public void requestPermissions() {
        String[] perms = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS};
        requestPermissions(perms, PERMISSION_REQUEST_CODE);

    }

    public void onClick(View view) {
        int position = spinner.getSelectedItemPosition();
        Intent intent = null;
        switch (position) {
            case 0:
                //Ver en un navegador una dirección web. Se utiliza el método setAction() y setData().
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.moronlu18.com/wordpress"));
                startActivity(intent);
                break;
            case 1:
                //Realiza directamente una llamada al número que se pasa en la URI
                //Se comprueba si la aplicación tiene el permiso y en caso contrario se solicita.
                //Puede que no funcione porque restricciones  del sistema sobre las aplicaciones que pueden iniciar una llamada
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                    intent = new Intent(Intent.ACTION_CALL,
                            Uri.parse("tel:(+34)123456789"));
                    startActivity(intent);
                } else
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_CAMERA);
                break;
            case 2:

                if (request_call_phone_acepted) {
                    //Muestra en la pantalla del dial el número indicado en la URI
                    intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:(+34)608033422"));
                    startActivity(intent);
                } else
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CAMERA);
                break;
            case 3:
                //Mostrar una posición en una aplicación Google Maps. Necesita ejecutarse en un Emulador con SDK Google API
                intent = new Intent(Intent.ACTION_VIEW,

                        Uri.parse("geo:36.720443,-4.419311?z=19"));
                startActivity(intent);
                break;
            case 4:
                //En este intent se añade una búsqueda dentro de la URI "?q= "
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=PC Box Arango"));
                startActivity(intent);
                break;
            case 5:
                //Muestra la pantalla para capturar una imagen.
                if (request_camera_acepted) {
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    //Este método devuelve true si el usuario ha candelado previamente el permiso. Si el usuario
                    //ha selecionado la opción Don't ask el método devuelve false. Se puede mostrar un mensaje
                    //1. Toast.makeText(this, R.string.message_permission_camera,Toast.LENGTH_SHORT).show();
                    //2. Solicitar el permiso
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                }
                break;
            case 6:
                //Muestra todos los contactos de la agenda
                if (request_read_contacts) {
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("content://contacts/people/"));
                    startActivity(intent);
                } else
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_CAMERA);
                break;
            case 7:

                //Muestra la pantalla de edición del primer contacto
                if (request_read_contacts) {
                    intent = new Intent(Intent.ACTION_EDIT,
                            Uri.parse("content://contacts/people/1"));
                    startActivity(intent);
                } else
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_CAMERA);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            String result = data.toUri(0);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Controlar la respuesta de los permisos solicitados
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                //Si la solucitus se cancela el array siempre es vacío.
                if (grantResults.length > 0) {
                    request_call_phone_acepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    request_camera_acepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    request_read_contacts = grantResults[2] == PackageManager.PERMISSION_GRANTED;                }
                break;
            case PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length > 0) {
                    request_call_phone_acepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                }
                break;
            case PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    request_camera_acepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                }
                break;
            case PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0) {
                    request_read_contacts = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                }
                break;

        }
    }
}
