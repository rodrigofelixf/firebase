package br.com.rodrigo.firebasestack.view.formlogin

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.rodrigo.firebasestack.R
import br.com.rodrigo.firebasestack.databinding.ActivityEsqueciSenhaBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class EsqueciSenha : AppCompatActivity() {
    private val binding by lazy { ActivityEsqueciSenhaBinding.inflate(layoutInflater) }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonRedefinirSenha.setOnClickListener { view ->
            val email = binding.editEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                sendPasswordResetEmail(email, view)
            } else {
                val snackbar =
                    Snackbar.make(
                        view,
                        "Insira um endereco de email valido",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }


    }

    fun sendPasswordResetEmail(email: String, view: View) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snackbar =
                        Snackbar.make(view, "Email foi enviado com sucesso", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.BLUE)
                    snackbar.show()
                } else {
                    val snackbar =
                        Snackbar.make(
                            view,
                            "Ocorreu um erro ao enviar o e-mail",
                            Snackbar.LENGTH_SHORT
                        )
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
    }
}