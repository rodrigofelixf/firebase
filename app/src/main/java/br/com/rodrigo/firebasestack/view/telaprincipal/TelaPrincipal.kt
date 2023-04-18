package br.com.rodrigo.firebasestack.view.telaprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.rodrigo.firebasestack.R
import br.com.rodrigo.firebasestack.databinding.ActivityTelaPrincipalBinding
import br.com.rodrigo.firebasestack.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : AppCompatActivity() {
    private val binding by lazy { ActivityTelaPrincipalBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonDeslogar.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            val voltarTelaLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }
    }
}