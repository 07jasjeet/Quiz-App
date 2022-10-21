package jasjeet.singh.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class FinalResultpageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_resultpage)

        val tvResultNameView : TextView = findViewById(R.id.tvResultNameView)
        val tvScore : TextView = findViewById(R.id.tvScore)
        val btnFinish : Button = findViewById(R.id.btnFinish)

        val score = intent.getIntExtra(Constants.SCORE,0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)

        tvResultNameView.text = intent.getStringExtra(Constants.USER_NAME)

        tvScore.text = "Your score is $score out of $totalQuestions."

        btnFinish.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}