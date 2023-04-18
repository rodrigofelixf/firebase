package br.com.rodrigo.firebasestack.view.formcadastro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.rodrigo.firebasestack.R
import br.com.rodrigo.firebasestack.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

    private val binding by lazy { ActivityFormCadastroBinding.inflate(layoutInflater) }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonCadastrar.setOnClickListener { view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar =
                    Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {

                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { cadastro ->

                        if (cadastro.isSuccessful) {
                            val snackbar =
                                Snackbar.make(
                                    view,
                                    "Sucesso ao cadastrar Usuario",
                                    Snackbar.LENGTH_SHORT
                                )
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                            binding.editEmail.setText("")
                            binding.editSenha.setText("")
                        }
                    }.addOnFailureListener { exception ->

                        val mensagemErro = when (exception) {
                            is FirebaseAuthWeakPasswordException -> "Digite uma senha com no minimo 6 caracteres!"
                            is FirebaseAuthInvalidCredentialsException -> "Digite um Email Valido!"
                            is FirebaseAuthUserCollisionException -> "Esta conta ja foi cadastrada!"
                            is FirebaseNetworkException -> "Sem conexao com a internet!"
                            else -> "Erro ao cadastrar usuario!"
                        }

                        val snackbar =
                            Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()

                    }
            }
        }


    }


}


