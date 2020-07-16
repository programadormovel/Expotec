package com.itbbelval.expotec;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static com.itbbelval.expotec.R.layout.activity_cupom;

public class CupomActivity extends AppCompatActivity {

    private ImageView mImageCupom;
    private String email;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_cupom);

        Intent it = getIntent();
        email = it.getStringExtra("email");
        nome = it.getStringExtra("nome");

        mImageCupom = (ImageView) findViewById(R.id.image_coupon);
        gerarCupom(email);
    }

    private void gerarCupom(String email) {
        //Criando novo objeto
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        //String texto ="Av, Guilherme Perereca Guglielmo, 1000 - Centro, Barueri-SP.";
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String dia = new SimpleDateFormat("dd").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

        String texto = dia + ";" + data.trim() + " " + hora.trim() + ";" +  email;

        try{
            //Se o campo não estiver vazio gere a imagem
            if ( !texto.isEmpty() ){

                BitMatrix bitMatrix = multiFormatWriter.encode(texto.trim(), BarcodeFormat.QR_CODE, 500,500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                //Adicionando a imagem gerada ao ImageView vinculado
                mImageCupom.setImageBitmap(bitmap);

            }else{
                //Se a caixa de texto estiver vazia imprima a menssagem

                Toast.makeText(this, "Campo vazio", Toast.LENGTH_SHORT).show();
                //messageDialog("Campo de texto vazio!");
            }

        }catch (WriterException ex){
            ex.printStackTrace();
        }
    }
}
