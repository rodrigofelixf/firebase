package br.com.rodrigo.firebasestack.view.telaprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.rodrigo.firebasestack.R
import br.com.rodrigo.firebasestack.databinding.ActivityTelaPrincipalBinding
import br.com.rodrigo.firebasestack.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {
    private val binding by lazy {
        ActivityTelaPrincipalBinding.inflate(layoutInflater)
    }
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonDeslogar.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            val voltarTelaLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }

        binding.buttonGravar.setOnClickListener {
            val usuariosMap = hashMapOf(
                "nome" to "Marcos",
                "sobrenome" to "Duarte",
                "idade" to 28
            )


            db.collection("Usuarios")
                .document("Marcos")
                .set(usuariosMap)
                .addOnCompleteListener {
                    Log.d("db", "Sucesso ao salvar usuario")
                }.addOnFailureListener {

                }
        }

        binding.buttonLerDados.setOnClickListener {
            db.collection("Usuarios").document("Marcos")
                .addSnapshotListener { documento, error ->
                    if (documento != null) {
                        val nome = documento.getString("nome")
                        val sobrenome = documento.getString("sobrenome")
                        val idade = documento.getLong("idade")?.toString()
                        binding.textResultado.text = "Nome: ${nome} \nSobrenome: $sobrenome \nIdade: ${idade}"
                    }
                }
        }

        binding.buttonAtualizar.setOnClickListener {
            db.collection("Usuarios")
                .document("Marcos")
                .update("sobrenome", "da Silva", "idade", 21)
                .addOnCompleteListener {
                    Log.d("db", "Sucesso ao atualizar os dados do usuario")
                }
        }

        binding.buttonDeletar.setOnClickListener {
            db.collection("Usuarios")
                .document("Maria")
                .delete()
                .addOnCompleteListener {
                    Log.d("db", "Sucesso ao excluir os dados do usuario")
                }
        }
    }
}