package com.itbbelval.expotec.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import com.itbbelval.expotec.MainActivity;
import com.itbbelval.expotec.R;

public class Alerta {

    private static AlertDialog dialog;

    public static void mostra(Context context,
                              int estilo,
                              int mensagem,
                              int titulo,
                              int icone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                context,
                estilo);
        dialog =
                builder.setMessage(mensagem)
                        .setTitle(titulo)
                        .setIcon(icone)
                        .setCancelable(true)
                        .create();

        dialog.show();
    }

    public static void remover(AlertDialog dialog) {
        dialog.dismiss();
    }
}
