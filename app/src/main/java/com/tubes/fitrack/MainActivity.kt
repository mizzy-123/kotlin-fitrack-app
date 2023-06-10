package com.tubes.fitrack

//import android.support.v7.app.AppCompatActivit
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.tubes.fitrack.api.RetrofitClient
import com.tubes.fitrack.api.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    private lateinit var btn_login: Button
//    lateinit var txt1: TextView
//    lateinit var txt2: TextView

    companion object {
        var name1: String = ""
        var email1: String = ""
        var status1 = false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        if (status1){
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val emailUser: String = email.text.toString()
            val passUser: String = password.text.toString()
            loginUser(emailUser, passUser)
        }

    }

    private fun loginUser(email: String, password: String) {
        RetrofitClient.instance.login(email, password).enqueue(object : Callback<Users>{
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val status = userResponse?.status
                    val user = userResponse?.user

                    // Tampilkan status, name, dan email
                    if (status == true && user != null) {
                        val name = user.name
                        val email = user.email

                        name1 = name
                        email1 = email
                        status1 = status

                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    // Tangani kesalahan saat permintaan tidak berhasil
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun initComponents(){
        email = findViewById(R.id.email)
        password = findViewById(R.id.pass)
        btn_login = findViewById(R.id.btn_login)
//        txt1 = findViewById(R.id.txt1)
//        txt2 = findViewById(R.id.txt2)
    }
}