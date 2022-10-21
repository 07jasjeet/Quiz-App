package jasjeet.singh.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName : EditText = findViewById(R.id.etName)
        val btnStart : Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            if (etName.text.isEmpty()){
                // == null doesn't work and == "" also doesn't work as etName.text is of form EditText.
                // (etName.text.toString() == null) also doesn't work.
                // (etName.text.toString() == "") works as we convert EditText to String.
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this,QuizQuestionActivity::class.java)
                // Intention to go from this activity to aforementioned activity.
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                // Passing this information
                startActivity(intent)
                // Execute the intention.
                finish()
                // The above code will definitely move us to the next activity, but we can still go
                // back to the previous activity by just hitting back button. This may be useful in
                // some cases but here, we will be needing to close the present startup activity.
            }
        }
    }
}