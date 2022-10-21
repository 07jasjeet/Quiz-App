package jasjeet.singh.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mUserName: String? = null

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var ivImage: ImageView? = null
    private var tvQuestion: TextView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null

    private var mCurrentPos: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOption: Int? = null
    private var mQuestion: Question? = null
    private var score: Int = 0



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        // This calls the parent Constructor.
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class.
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.ivImage)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        // Options
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        btnSubmit = findViewById(R.id.btnSubmit)

        mQuestionsList = Constants.getQuestions()
        mQuestion = mQuestionsList!![mCurrentPos-1]

        setQuestion()

        // We tick the option of which lets us build interface for onClick events or just inherit
        // from View.OnClickListener to MainActivity.
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)

        btnSubmit?.setOnClickListener (this)

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when (btnSubmit?.text) {        // To stop option selection after results are shown.


            "Submit" -> {
                when (view) {

                    tvOptionOne -> {
                        if (mSelectedOption == null) {
                            selectedOptionView(1,tvOptionOne)
                        } else {
                            revertPreviousOption(mSelectedOption!!)
                            selectedOptionView(1,tvOptionOne)
                        }
                    }

                    tvOptionTwo -> {
                        if (mSelectedOption == null) {
                            selectedOptionView(2,tvOptionTwo)
                        } else {
                            revertPreviousOption(mSelectedOption!!)
                            selectedOptionView(2,tvOptionTwo)
                        }
                    }

                    tvOptionThree -> {
                        if (mSelectedOption == null) {
                            selectedOptionView(3,tvOptionThree)
                        } else {
                            revertPreviousOption(mSelectedOption!!)
                            selectedOptionView(3,tvOptionThree)
                        }
                    }

                    tvOptionFour -> {
                        if (mSelectedOption == null) {
                            selectedOptionView(4,tvOptionFour)
                        } else {
                            revertPreviousOption(mSelectedOption!!)
                            selectedOptionView(4,tvOptionFour)
                        }
                    }

                    btnSubmit -> {
                        // If mSelectedOption is null then it will do nothing.
                        mSelectedOption?.let {
                            if (it == mQuestion!!.correctAnswer) {
                                when (it) {
                                    1 -> tvOptionOne?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                    2 -> tvOptionTwo?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                    3 -> tvOptionThree?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                    4 -> tvOptionFour?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                }
                                score++
                                // Increase Score on correct answer.
                            } else {
                                when (it) {
                                    1 ->tvOptionOne?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                                    2 ->tvOptionTwo?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                                    3 ->tvOptionThree?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                                    4 ->{tvOptionFour?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                                    }

                                }
                                when (mQuestion!!.correctAnswer) {
                                    1 -> tvOptionOne?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                    2 -> tvOptionTwo?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                    3 -> tvOptionThree?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                    4 -> tvOptionFour?.setBackgroundResource(R.drawable.correct_option_border_bg)
                                }
                            }
                            if (mCurrentPos != mQuestionsList!!.size) {
                                btnSubmit?.text = "Next"
                            }else {
                                btnSubmit?.text = "Finish Quiz"
                                // At last question, we would like to have our btnSubmit's text to be "Finish"
                                // as there are no more questions left.
                            }
                        }
                        // End of lambda expression (null safe body)
                    }
                }
            }


            "Next" -> {
                if (view == btnSubmit) {
                    btnSubmit?.text = "Submit"
                    revertPreviousOption(0)

                    // Setting Questions for next round.
                    mCurrentPos++
                    mQuestion = mQuestionsList!![mCurrentPos - 1]
                    setQuestion()

                    // If we keep hitting submit, the previous selected option executes.
                    mSelectedOption = null
                }
            }


            "Finish Quiz" -> {      // At last question, change activity to result page, i.e., mCurrentPos == mQuestionsList!!.size
                val intent = Intent(this,FinalResultpageActivity::class.java)
                intent.putExtra(Constants.USER_NAME, mUserName)
                intent.putExtra(Constants.SCORE, score)
                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                startActivity(intent)
                finish()
                // Intent is added here because when it was kept in above else if block, switching
                // animation became laggy because of all the code after transition.
            }
        }
    }



    // This function was created to set an options background to default from selected if user changes
    // his/her mind after selecting before hitting submit.
    // Additionally, this functions is also used for resetting all options when we want to load next
    // question.
    private fun revertPreviousOption(mSelectedOption: Int){
        when (mSelectedOption){
            1 -> defaultView(tvOptionOne)
            2 -> defaultView(tvOptionTwo)
            3 -> defaultView(tvOptionThree)
            4 -> defaultView(tvOptionFour)
            else -> {
                // Reset all the option backgrounds.
                defaultView(tvOptionOne)
                defaultView(tvOptionTwo)
                defaultView(tvOptionThree)
                defaultView(tvOptionFour)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        Log.i("QuestionsList size is", "${mQuestionsList?.size}")

        for (i in mQuestionsList!!) {       // i is individual question in questionsList
            Log.e("Question", i.question)
        }

        mQuestion?.let { ivImage?.setImageResource(it.image) }
        progressBar?.progress = mCurrentPos
        tvProgress?.text = "${mCurrentPos}/${progressBar?.max}"
        tvQuestion?.text = mQuestion?.question
        // Options
        tvOptionOne?.text = mQuestion?.optionOne
        tvOptionTwo?.text = mQuestion?.optionTwo
        tvOptionThree?.text = mQuestion?.optionThree
        tvOptionFour?.text = mQuestion?.optionFour
    }

    private fun defaultView(tv: TextView?){
        tv?.typeface = Typeface.DEFAULT     // One way to edit typeface and other is in selectedOptionView()
        // tv?.setBackgroundResource(R.drawable.default_option_border_bg)
        tv?.background = ContextCompat.getDrawable(
            this,
            R.drawable.default_option_border_bg
        )
        // tvOptionOne.setTextColor(Color.parseColor("#FF0000"))
        // We can also parse color to a TextView with the above command.
    }

    private fun selectedOptionView(selectedOption: Int?, tv: TextView?){
        mSelectedOption = selectedOption
        tv?.setTypeface(tv.typeface, Typeface.BOLD)     // Other way to edit typeface.
        tv?.setBackgroundResource(R.drawable.selected_option_border_bg)
    }
}

/************************************ Previous Code Model *****************************************/

// This code was written inside onCreate; newer code model is separated into functions.

/*
        tvOptionOne?.setOnClickListener {
            if (btnSubmit?.text == "Submit") {      // To stop option selection after results are shown.
                if (mSelectedOption == null) {
                    selectedOptionView(1,tvOptionOne)
                } else {
                    revertPreviousOption(mSelectedOption!!)
                    selectedOptionView(1,tvOptionOne)
                }
            }
        }

        tvOptionTwo?.setOnClickListener {
            if (btnSubmit?.text == "Submit") {
                if (mSelectedOption == null) {
                    selectedOptionView(2,tvOptionTwo)
                } else {
                    revertPreviousOption(mSelectedOption!!)
                    selectedOptionView(2,tvOptionTwo)
                }
            }
        }

        tvOptionThree?.setOnClickListener {
            if (btnSubmit?.text == "Submit") {
                if (mSelectedOption == null) {
                    selectedOptionView(3,tvOptionThree)
                } else {
                    revertPreviousOption(mSelectedOption!!)
                    selectedOptionView(3,tvOptionThree)
                }
            }
        }

        tvOptionFour?.setOnClickListener {
            if (btnSubmit?.text == "Submit") {
                if (mSelectedOption == null) {
                    selectedOptionView(4,tvOptionFour)
                } else {
                    revertPreviousOption(mSelectedOption!!)
                    selectedOptionView(4,tvOptionFour)
                }
            }
        }*/

/*btnSubmit?.setOnClickListener {
    if (btnSubmit?.text == "Submit") {
        // If mSelectedOption is null then it will do nothing.
        mSelectedOption?.let {
            if (it == mQuestion!!.correctAnswer) {
                when (it) {
                    1 -> tvOptionOne?.setBackgroundResource(R.drawable.correct_option_border_bg)
                    2 -> tvOptionTwo?.setBackgroundResource(R.drawable.correct_option_border_bg)
                    3 -> tvOptionThree?.setBackgroundResource(R.drawable.correct_option_border_bg)
                    4 -> tvOptionFour?.setBackgroundResource(R.drawable.correct_option_border_bg)
                }
                score++
                // Increase Score on correct answer.
            } else {
                when (it) {
                    1 ->tvOptionOne?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                    2 ->tvOptionTwo?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                    3 ->tvOptionThree?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                    4 ->{tvOptionFour?.setBackgroundResource(R.drawable.incorrect_option_border_bg)
                    }

                }
                when (mQuestion!!.correctAnswer) {
                    1 -> tvOptionOne?.setBackgroundResource(R.drawable.correct_option_border_bg)
                    2 -> tvOptionTwo?.setBackgroundResource(R.drawable.correct_option_border_bg)
                    3 -> tvOptionThree?.setBackgroundResource(R.drawable.correct_option_border_bg)
                    4 -> tvOptionFour?.setBackgroundResource(R.drawable.correct_option_border_bg)
                }
            }
            if (mCurrentPos != mQuestionsList!!.size) {
                btnSubmit?.text = "Next"
            }else {
                btnSubmit?.text = "Finish Quiz"
                // At last question, we would like to have our btnSubmit's text to be "Finish"
                // as there are no more questions left.
            }
        }
        // End of lambda expression (null safe body)

    }else if(btnSubmit?.text == "Next"){
        // These tasks should execute no matter.
        btnSubmit?.text = "Submit"
        revertPreviousOption(0)

        // Setting Questions for next round.
        mCurrentPos++
        mQuestion = mQuestionsList!![mCurrentPos-1]
        setQuestion()

        // If we keep hitting submit, the previous selected option executes.
        mSelectedOption = null

    }else{      // At last question, change activity to result page, i.e., mCurrentPos == mQuestionsList!!.size
        val intent = Intent(this,FinalResultpageActivity::class.java)
        intent.putExtra(Constants.USER_NAME, mUserName)
        intent.putExtra(Constants.SCORE, score)
        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
        startActivity(intent)
        finish()
        // Intent is added here because when it was kept in above else if block, switching
        // animation became laggy because of all the code after transition.
    }
}*/
// End of btnSubmit.setOnClickListener


