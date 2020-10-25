# Intent Implícitos

En este ejercicio se muestra como enviar intent implícitos de forma que
se inicia el componente de otra aplicación que realizará la acción que
se solicita en el intent. Se muestran los siguientes ejemplos:

1. Abrir el navegador con una dirección web predefinida.
2. Realizar una llamada a un número de teléfono.
3. Mostrar un número de teléfono en el dial.
4. Realizar una búsqueda de una ubicación en un mapa
5. Mostrar la lista de contactos
6. Editar el primer contacto de la agenda

En este ejemplo se muestra cómo se inicializan los datos que acompañan
al intent, según la acción que se solicite. Para ver un dirección web el
código sería el siguiente:

 ```
 intent = new Intent(); intent.setAction(Intent.ACTION_VIEW);
 intent.setData(Uri.parse("http://www.google.es"));
 startActivity(intent);
```


# Permisos

En este ejemplo se solicitan los permisos necesarios para los intent
implícitos en tiempo de ejecución y manejar las respuestas del usuario a
partir de Android 6.0 (nivel de API 23).


1. Método
   [checkSelfPermission](https://developer.android.com/reference/android/content/Context#checkSelfPermission(java.lang.String)())
   que comprueba si la propia aplicación tiene el permiso específico que
   se pasa por parámetro.
2. Método
   [requestPermissions](https://developer.android.com/reference/android/support/v4/app/ActivityCompat.html#requestPermissions(android.app.Activity,%20java.lang.String[],%20int))
   que solicita un permiso concreto.
3. El método
   [shouldShowRequestPermissionRationale](https://developer.android.com/reference/android/support/v4/app/ActivityCompat.html#shouldShowRequestPermissionRationale(android.app.Activity,%20java.lang.String))
   para mostrar una explicación al usuario del motivo por el cual se
   requiere del persmiso.

